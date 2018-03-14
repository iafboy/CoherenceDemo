package com.oracle.mw.sc.haier;

import com.oracle.mw.sc.haier.Controller.*;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;
@Configuration
@EnableAutoConfiguration
@ComponentScan
@EnableScheduling
@SpringBootApplication
public class Starter implements CommandLineRunner {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Resource(name="jmsPublishController")
    private JmsPublishController jmsPublishController;
    @Resource(name="coherenceConsuController")
    private CoherenceConsuController coherenceConsuController;
    @Resource(name="oTEAIDataCheckController")
    private OTEAIDataCheckController oTEAIDataCheckController;
    @Resource(name="persistenceController")
    private PersistenceController persistenceController;

    public static void main(String[] args) {
        System.setProperty("coherence.distributed.localstorage","false");
        SpringApplication.run(Starter.class, args);
	}
    protected static void usage() {
        System.out.println("Usage: java -jar ... <operation-name> ");
        System.out.println(
                "     where operation-name is 'pub', " + "'coh-sub, db' or 'ot'");
    }

    @Override
    public void run(String... args) throws Exception {

        String operation = "NO-VALUE";
        if(args.length>0) {
            operation = args[0].toLowerCase();
        }else{
            usage();
            return;
        }
        //begin services
        logger.debug(" operation is: "+operation);
        if("pub".equals(operation)){
            jmsPublishController.execTest();
            //jmsPublisher.publish(null);
        }else if("coh-sub".equals(operation)){
            coherenceConsuController.exec();
        }else if("ot".equals(operation)){
            oTEAIDataCheckController.exec();
        }else if("db".equals(operation)){
            persistenceController.exec();
        }
        else{
            System.out.println("Unknown operation input: " + operation);
            usage();
        }
    }
}
