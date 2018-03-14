package com.oracle.mw.sc.haier.Controller;

import com.oracle.mw.sc.haier.Services.KafkaSubService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by xiaoju on 2017/1/23.
 */
@Controller("kafkaSubController")
public class KafkaSubController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="KafkaSubService")
    private KafkaSubService kafkaSubService;
    public void exec(){
        kafkaSubService.consuemeJMSMsg();
    }

}
