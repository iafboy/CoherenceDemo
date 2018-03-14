package com.oracle.mw.sc.haier.Controller;

import com.alibaba.fastjson.JSON;
import com.haier.coherence.xml.EAILogData;
import com.oracle.mw.sc.haier.Services.JmsPublisher;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaoju on 2017/1/23.
 */
@Controller("jmsPublishController")
public class JmsPublishController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name = "JmsPublisher")
    private JmsPublisher jmsPublisher;
    private ExecutorService executor;

    public void execTest() {
        executor = Executors.newFixedThreadPool(200);
        //define schedule;
        long cnt = 0;
        while (cnt < 10) {
            /*
            EAILogData data = new EAILogData();
            data.setStorged(false);
            data.setAtrtributes(""+cnt);
            data.setData_source(""+cnt);
            data.setDest_url("http://test.com/"+cnt);
            data.setDetail("");
            data.setDetail_in(""+cnt);
            data.setDetail_out(""+cnt);
            data.setEntry_id(""+cnt);
            data.setError_detail("");
            data.setError_title("");
            data.setGmt_create(new Timestamp(System.currentTimeMillis()));
            data.setLog_id(""+cnt);
            data.setLog_size(cnt);
            data.setLog_time(cnt);
            data.setLog_time_id(""+cnt);
            data.setMessage_id("msgid"+cnt);
            data.setMessage_time(new Timestamp(System.currentTimeMillis()));
            data.setOsb_url("http://osb/"+cnt);
            data.setSvc_id(""+cnt);
            executor.submit(new PubMsgThreed(jmsPublisher, data));
            */
            EAILogData data = null;
            if (cnt % 2 == 0) {
                data = new EAILogData("msg" + cnt / 2, "" + cnt, "http://osb" + cnt, "http://client/" + cnt, "http://host" + cnt / 2, "2017-03-08 10:00:00", "", "" + cnt % 2, "" + cnt, "", "", "");
            } else {
                data = new EAILogData("msg" + cnt / 2, "" + cnt, "http://osb" + cnt, "http://client/" + cnt, "http://host" + cnt / 2, "2017-03-08 10:00:00", "2017-03-08 10:05:00", "" + cnt % 2, "" + cnt, "" + cnt, "", "");
            }

            ByteArrayOutputStream baos = null;
            try {
                JAXBContext context = JAXBContext.newInstance(EAILogData.class);
                Marshaller marshaller = context.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
                marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNamespacePrefixMapper());
                baos = new ByteArrayOutputStream();
                marshaller.marshal(data, baos);
                String xmlObj = new String(baos.toByteArray());
                executor.submit(new PubMsgThreed(jmsPublisher, xmlObj));
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            } finally {
                if (baos != null)
                    try {
                        baos.close();
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
            }

            cnt++;
        }
        logger.info("Message sent!");
    }
}

class MyNamespacePrefixMapper extends NamespacePrefixMapper {
    @Override
    public String getPreferredPrefix(String namespaceUri,
                                     String suggestion, boolean requirePrefix) {
        return "eail";
    }
}

class PubMsgThreed implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String data;
    private JmsPublisher jmsPublisher;

    public PubMsgThreed(JmsPublisher _jmsPublisher, String _data) {
        jmsPublisher = _jmsPublisher;
        data = _data;
    }

    @Override
    public void run() {
        //jmsPublisher.publish(JSON.toJSONString(data));
        jmsPublisher.publish(data);

    }

}