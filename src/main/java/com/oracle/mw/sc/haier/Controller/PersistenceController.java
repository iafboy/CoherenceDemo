package com.oracle.mw.sc.haier.Controller;

import com.oracle.mw.sc.haier.Services.PersistenceService;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by xiaoju on 2017/1/23.
 */
@Controller("persistenceController")
public class PersistenceController {
    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    @Resource(name="PersistenceService")
    private PersistenceService persistenceService;
    public void exec(){
        persistenceService.setBegin();
        persistenceService.scheduePersistence();
    }
}
