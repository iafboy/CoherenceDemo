package com.oracle.mw.sc.haier.Services;

import com.haier.coherence.xml.EAILogData;
import com.oracle.mw.sc.haier.Constants.CommonConstant;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.extractor.ReflectionExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by xiaoju on 2016/12/27.
 */
@Service(value = "CoherenceConsuService")
public class CoherenceConsuService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected NamedCache<String, Object> cache = null;
    private ExecutorService executor;

    @Value("${haier.naming.provider.url}")
    private String provider_url;
    @Value("${haier.mq.jnditopic}")
    private String jnditopic;
    @Value("${haier.coherence.subsname}")
    private String subsname;
    @Value("${haier.coherence.cachename}")
    private String cachename;
    @Value("${haier.threadPool.size}")
    private String threadpoolsize;
    private boolean begin;

    public void setBegin() {
        begin = true;
        //init first connection with coherence
        executor = Executors.newFixedThreadPool(Integer.parseInt(threadpoolsize));
        //executor=Executors.newCachedThreadPool();
        cache = CacheFactory.getCache(cachename);
        logger.debug("Cache is empty ? " + cache.isEmpty());
        cache.addIndex(new ReflectionExtractor("getMessage_id"), true, null);
    }

    public void processWork() {
        if (!begin)
            return;
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                CommonConstant.INITIAL_CONTEXT_FACTORY);
        System.setProperty(Context.PROVIDER_URL, provider_url);
        TopicConnection connection = null;
        TopicSession session = null;
        Context ctx = null;
        try {
            ctx = new InitialContext();
            // 1.通过JNDI查询ConnectionFactory
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("jndiConnectionFactory");
            // 2.通过工厂建立连接connection
            connection = factory.createTopicConnection();
            connection.setClientID("coherence");
            // 3.创建session
            session = connection.createTopicSession(false,
                    TopicSession.AUTO_ACKNOWLEDGE);
            // 4.创建Topic
            Topic topic = (Topic) ctx.lookup(jnditopic);
            // 5.创建订阅者
            TopicSubscriber tsubscriber = session.createDurableSubscriber(topic, subsname);
            connection.start();
            // 构造消息体
            Message message = tsubscriber.receiveNoWait();
            while (true) {
                try {
                    if (message != null && message instanceof TextMessage) {
                        TextMessage tm = (TextMessage) message;
                        logger.info("RECV MESSAGE ---> " + tm.getText());
                        //persistence object to coherence
                        if (tm.getText() != null && !"".equals(tm.getText().trim())) {
                            if (!executor.isShutdown()) {
                                Future<Integer> threadResult = executor.submit(new PCThreed(cache, tm));
                                logger.info("object submitted to loading task for message ID[" + tm.getJMSMessageID() + "]");
                            }
                        } else {
                            logger.info("filter the Object of message ID [ " + tm.getJMSMessageID() + " ] " + " Value Class type is " + tm.getClass());
                        }
                    } else if (message != null) {
                        logger.info("filter the Object of message ID [ " + message.getJMSMessageID() + " ] " + " Value Class type is " + message.getClass());
                    }

                    message = tsubscriber.receiveNoWait();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        } catch (NamingException ne) {
            logger.error(ne.getMessage(), ne);
        } catch (Exception jse) {
            logger.error(jse.getMessage(), jse);
        } finally {
            if (session != null)
                try {
                    session.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
            if (ctx != null)
                try {
                    ctx.close();
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
        }

    }
}

class PCThreed implements Callable<Integer> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private TextMessage tm;
    private NamedCache<String, Object> cache;

    public PCThreed(NamedCache<String, Object> cache_, TextMessage tm_) {
        this.tm = tm_;
        this.cache = cache_;
    }

    public Integer call() throws Exception {
        try {
            long beginTime = new Date().getTime();
            logger.info("Thread[" + Thread.currentThread().getId() + "] begin to work");
            //EAILogData cco = JSON.parseObject(tm.getText(), EAILogData.class);
            //EAILogMsg msg = JSON.parseObject(tm.getText(), EAILogMsg.class);//JOSN方式数据
            EAILogData msg = getObjFromXML(tm.getText());
            com.haier.coherence.entity.EAILogData cco = transfer(msg);
            if (cco != null) {
                cache.put(cco.getMessage_id().toString(), cco);
                logger.info("Thread[" + Thread.currentThread().getId() + "] loaded object [" + cco.getLog_id() + "] to coherence cost " + (new Date().getTime() - beginTime) + "ms");
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            return -1;
        }
        return 0;
    }

    private EAILogData getObjFromXML(String text) {
        EAILogData res = null;
        try {
            JAXBContext context = JAXBContext.newInstance(EAILogData.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            res = (EAILogData) unmarshaller.unmarshal(new StringReader(text));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
        return res;
    }

    private com.haier.coherence.entity.EAILogData transfer(EAILogData msg) {
        com.haier.coherence.entity.EAILogData cco = null;
        if (msg == null || msg.getMessageId() == null || "".equals(msg.getMessageId().trim())) {
            logger.info("message is null");
            return null;
        }
        logger.info(msg.toString());
        if ("1".equals(msg.getLogType()) && cache.containsKey(msg.getMessageId().toString())) {
            cco = (com.haier.coherence.entity.EAILogData) cache.get(msg.getMessageId().toString());
        } else {
            cco = new com.haier.coherence.entity.EAILogData();
            cco.setLog_id(UUID.randomUUID().toString());
            cco.setMessage_id(msg.getMessageId());
        }
        if (msg.getSvcId() != null && !"".equals(msg.getSvcId().trim()) && (cco.getSvc_id() == null || "".equals(cco.getSvc_id())))
            cco.setSvc_id(msg.getSvcId());
        if (msg.getLogUri() != null && !"".equals(msg.getLogUri().trim()) && (cco.getOsb_url() == null || "".equals(cco.getOsb_url())))
            cco.setOsb_url(msg.getLogUri());
        if (msg.getLogHost() != null && !"".equals(msg.getLogHost().trim()) && (cco.getDest_url() == null || "".equals(cco.getDest_url())))
            cco.setDest_url(msg.getLogHost());
        if (msg.getLogclientHost() != null && !"".equals(msg.getLogclientHost().trim()) && (cco.getSource_url() == null || "".equals(cco.getSource_url())))
            cco.setSource_url(msg.getLogclientHost());
        String dateStr = msg.getLogRequestTime();
        Date date = new Date();
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(dateStr);
            cco.setGmt_create(new Timestamp(date.getTime()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if ("1".equals(msg.getLogType())) {
            cco.setDetail_out(msg.getLogResponseData());
            cco.setDetail(cco.getDetail() + msg.getLogResponseData());
            if (cco.getMessage_time() != null && cco.getGmt_create() != null)
                cco.setLog_time(cco.getMessage_time().getTime() - cco.getGmt_create().getTime());
            cco.setError_detail(msg.getErrorDetail());
            cco.setError_title(msg.getErrorTitle());
            cco.setEntry_id(msg.getErrorTitle());
            dateStr = msg.getLogResponseTime();
            try {
                date = sdf.parse(dateStr);
                if (date != null)
                    cco.setMessage_time(new Timestamp(date.getTime()));
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            cco.setStorged(true);
        } else {
            if (cco.getDetail_in() == null || "".equals(cco.getDetail_in()))
                cco.setDetail_in(msg.getLogRequestData());
            if (cco.getDetail() == null || "".equals(cco.getDetail()))
                cco.setDetail(msg.getLogRequestData());
        }
        if (cco.getDetail() != null)
            cco.setLog_size(new Long(cco.getDetail().length()));
        return cco;
    }
}