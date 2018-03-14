package com.oracle.mw.sc.haier.Controller;

import com.oracle.mw.sc.haier.Services.OTEAIDataCheckService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by xiaoju on 2017/3/8.
 */
@Controller("oTEAIDataCheckController")
public class OTEAIDataCheckController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="oTEAIDataCheckService")
    private OTEAIDataCheckService oTEAIDataCheckService;
    public void exec(){
        oTEAIDataCheckService.setBegin();
        oTEAIDataCheckService.schedueCheck();
    }
}
