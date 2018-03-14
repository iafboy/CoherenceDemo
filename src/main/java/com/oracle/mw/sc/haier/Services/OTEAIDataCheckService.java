package com.oracle.mw.sc.haier.Services;

import com.haier.coherence.entity.EAILogData;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.EqualsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiaoju on 2017/3/8.
 */
@Service(value = "oTEAIDataCheckService")
public class OTEAIDataCheckService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected NamedCache<String, Object> cache = null;

    @Value("${haier.coherence.cachename}")
    private String cachename;

    private String timeout="1";

    private boolean begin = false;

    public void setBegin() {
        begin = true;
        cache = CacheFactory.getCache(cachename);
        if (cache.isEmpty()) {
            logger.info("Cache is empty");
        }
    }
    @Scheduled(cron = "${haier.schedule.timeout}")
    public void schedueCheck() {
        if(!begin)
            return;
        if (cache == null)
            cache = CacheFactory.getCache(cachename);
        java.util.Date  nows=new  java.util.Date();   ;
        EqualsFilter equalsFilter = new EqualsFilter("isStorged", false);
        Set<Map.Entry<String, Object>> entries = cache.entrySet(equalsFilter);
        logger.info("got "+entries.size()+" to be update");
        for (Map.Entry entry : entries) {
            if (entry.getValue() instanceof EAILogData) {
                EAILogData obj = (EAILogData) entry.getValue();
                long gmt=obj.getGmt_create().getTime();
                long hours=(nows.getTime()-gmt)/(60*60*1000);
                logger.info("Msg["+obj.getMessage_id()+"] expr "+hours+" hours to be update");
                if(hours>Long.parseLong(timeout)) {
                    obj.setError_detail("Timeout");
                    obj.setError_title("Timeout");
                    obj.setMessage_time(new Timestamp(nows.getTime()));
                    obj.setStorged(true);
                    cache.put(obj.getMessage_id(), obj);
                }
            }
        }
    }
}
