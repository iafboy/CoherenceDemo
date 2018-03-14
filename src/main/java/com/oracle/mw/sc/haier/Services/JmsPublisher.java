package com.oracle.mw.sc.haier.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Created by xiaoju on 2016/12/27.
 */
@Service(value="JmsPublisher")
public class JmsPublisher {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${haier.naming.provider.url}")
    private String provider_url;

    @Value("${haier.mq.jnditopic}")
    private String jnditopic;

    public void publish(String jsonMessage) {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"weblogic.jndi.WLInitialContextFactory");
        System.setProperty(Context.PROVIDER_URL,provider_url);
        TopicConnection connection = null;
        TopicSession session = null;
        Context ctx = null;
        try {
            ctx = new InitialContext();
            //1.通过JNDI查询ConnectionFactory。JMS中连接工厂分QueueConnectionFactory和 TopicConnectionFactory两种，Weblogic不区分这两种类型。
            TopicConnectionFactory factory = (TopicConnectionFactory) ctx.lookup("jndiConnectionFactory");
            //2.通过工厂建立连接connection
            connection = factory.createTopicConnection();
            //3.创建session
            session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            //4.创建Topic,必须新建一个Topic JNDI。
            Topic topic = (Topic) ctx.lookup(jnditopic);
            //5.创建TopicPublisher
            TopicPublisher tp = session.createPublisher(topic);
            //构造消息体
            TextMessage tm=session.createTextMessage();
            tm.setText(jsonMessage);
            tp.publish(tm);
            logger.info("Send Message from Weblogic JMS Topic ------> " +jsonMessage);
        } catch (NamingException ne) {
            logger.error(ne.getMessage(),ne);
        } catch (Exception jse) {
            logger.error(jse.getMessage(),jse);
        } finally {
            if (session != null)
                try {
                    session.close();
                } catch (JMSException e) {
                    logger.error(e.getErrorCode(),e);
                }
            if (connection != null)
                try {
                    connection.close();
                } catch (JMSException e) {
                    logger.error(e.getErrorCode(),e);
                }
            if (ctx != null)
                try {
                    ctx.close();
                } catch (NamingException e) {
                    logger.error(e.getMessage(),e);
                }
            logger.info("Message publish task finished!");
        }
    }
}
