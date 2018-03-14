package com.oracle.mw.sc.haier.Services;

import com.haier.coherence.entity.EAILogData;
import com.oracle.mw.sc.haier.DB.dao.EAILogDAO;
import com.oracle.mw.sc.haier.DB.dao.EAILogDataDAO;
import com.oracle.mw.sc.haier.DB.dao.OsbRroxyServiceCfgDAO;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.extractor.ReflectionExtractor;
import com.tangosol.util.filter.EqualsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;

/**
 * Created by xiaoju on 2017/1/20.
 */
@Service(value = "PersistenceService")
@Transactional
public class PersistenceService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    protected NamedCache<String, Object> cache = null;
    @Autowired
    protected EAILogDAO eAILogDao;
    @Autowired
    protected EAILogDataDAO eAILogDataDao;

    @Autowired
    protected OsbRroxyServiceCfgDAO osbRroxyServiceCfgDAO;

    @Value("${haier.coherence.cachename}")
    private String cachename;

    private boolean begin = false;

    public void setBegin() {
        begin = true;
        cache = CacheFactory.getCache(cachename);
        if (!cache.isEmpty()) {
            logger.info("Cache size " + cache.size());
        }
        cache.addIndex(new ReflectionExtractor("getMessage_id"), true, null);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Scheduled(cron = "${haier.schedule.cfg}")
    public void scheduePersistence() {
        if (!begin)
            return;
        if (cache == null)
            cache = CacheFactory.getCache(cachename);
        logger.info("Begin Persistence job ......");
        //将已经req与resp以及超时的进行处理设置为true,表示可以保存入数据库
        EqualsFilter equalsFilter = new EqualsFilter("isStorged", true);
        Set<Map.Entry<String, Object>> entries = cache.entrySet(equalsFilter);
        for (Map.Entry entry : entries) {
            try {
                if (entry.getValue() instanceof EAILogData) {
                    EAILogData obj = (EAILogData) entry.getValue();
                    if (obj == null) {
                        logger.info("got null EAILogData Obj ");
                        continue;
                    }
                    logger.info("try to save: "+obj.toString());
                    eAILogDataDao.insertEAILogDataDAO(obj.getLog_id(), obj.getDetail_in(), obj.getDetail_out(), new Timestamp(System.currentTimeMillis()));
                    logger.info("save[" + obj.getMessage_id() + "] to table [EAI_LogData]");
                    eAILogDao.insertEAILogDAO(obj.getLog_id(), obj.getSvc_id(), obj.getEntry_id(),
                            obj.getError_title(),
                            obj.getError_detail(), obj.getDest_url(), obj.getSource_url(), obj.getOsb_url(),
                            obj.getAtrtributes(), obj.getLog_time_id(), obj.getMessage_time(),
                            obj.getLog_time()==null?null:new BigDecimal(obj.getLog_time()), obj.getData_source(), obj.getGmt_create(), obj.getMessage_id(), new BigDecimal(obj.getDetail_in() == null ? 0 : obj.getDetail_in().length()), new BigDecimal(obj.getDetail_out() == null ? 0 : obj.getDetail_out().length()));
                    logger.info("save[" + obj.getMessage_id() + "] to table [EAI_Log]");

                    cache.remove(obj.getMessage_id());
                    logger.info("remove [" + obj.getMessage_id() + "] from cache");
                } else {
                    logger.info("unknown " + entry.getValue().getClass());
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                continue;
            }

        }
        logger.info("End   Persistence job ......");
    }
}
