package com.oracle.mw.sc.haier.DB.dao;

import com.haier.coherence.entity.OsbRroxyService;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by xiaoju on 2017/3/3.
 */
@Mapper
public interface OsbRroxyServiceCfgDAO {
    @Select("select * from OSB_PROXY_SERVICE_CONFIG where origin_service_seq = #{seq}")
    public List getOsbRroxyServiceCfgBySeq(@Param("seq")String seq);
    @Insert("insert into OSB_PROXY_SERVICE_CONFIG values(#{origin_service_seq},#{origin_service_link},#{sub_service_link},#{service_switch})")
    public void insertOsbRroxyServiceCfg(OsbRroxyService osbRroxyService);
    @Delete("delete from OSB_PROXY_SERVICE_CONFIG where origin_service_seq = #{seq}")
    public void deleteOsbRroxyServiceCfg(@Param("seq")String seq);
}
