package com.oracle.mw.sc.haier.Services;

import com.alibaba.fastjson.JSON;
import com.oracle.mw.sc.haier.Constants.CommonConstant;
import com.haier.coherence.entity.EAILogData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by xiaoju on 2016/12/27.
 */
@Service(value="KafkaSubService")
public class KafkaSubService {
    private Logger logger=Logger.getLogger(this.getClass());

    @Value("${haier.naming.provider.url}")
    private String provider_url;
    @Value("${haier.mq.jnditopic}")
    private String jnditopic;
    @Value("${haier.kafka.subsname}")
    private String kafkasubsname;

    public void consuemeJMSMsg() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, CommonConstant.INITIAL_CONTEXT_FACTORY);
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
            connection.setClientID("kafka");
            // 3.创建session
            session = connection.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
            // 4.创建Topic
            Topic topic = (Topic) ctx.lookup(jnditopic);
            // 5.创建订阅者
            TopicSubscriber tsubscriber = session.createDurableSubscriber(topic,kafkasubsname);
            connection.start();
            // 构造消息体
            Message message = tsubscriber.receiveNoWait();
            while(true) {
                if (message!=null&&message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    logger.info("RECV MESSAGE ---> " + tm.getText());
                    //persistence to DB
                    EAILogData eaiLogData= JSON.parseObject(tm.getText(),EAILogData.class);
                    if (eaiLogData!=null) {
                        //to be done
                    } else {
                        logger.info("filter the Object of requestID[ " + tm.getJMSMessageID() + " ] " + " Value Class type is " + tm.getClass());
                    }

                }else if(message!=null){
                    logger.info("filter the Object of requestID[ " + message.getJMSMessageID() + " ] " + " Value Class type is " + message.getClass());
                }
                message = tsubscriber.receiveNoWait();
            }
        } catch (NamingException ne) {
            ne.printStackTrace();
        } catch (Exception jse) {
            jse.printStackTrace();
        } finally {
            if (session != null)
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.error(e);
                }
            if(connection!=null){
                try{
                    connection.close();
                }catch (Exception e){
                    logger.error(e);
                }
            }
            if (ctx != null)
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error(e);
                }
        }
    }
}
