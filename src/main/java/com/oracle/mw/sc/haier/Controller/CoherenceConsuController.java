package com.oracle.mw.sc.haier.Controller;

import com.oracle.mw.sc.haier.Services.CoherenceConsuService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by xiaoju on 2017/1/23.
 */
@Controller("coherenceConsuController")
public class CoherenceConsuController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="CoherenceConsuService")
    private CoherenceConsuService coherenceConsuService;
    public void exec(){
        coherenceConsuService.setBegin();
        coherenceConsuService.processWork();
    }
}
