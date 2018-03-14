package com.oracle.mw.sc.haier.DB.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.sql.Timestamp;

@Mapper
public interface EAILogDataDAO {
    @Insert("insert into eai_logdata(logid,rep_data,resp_data,end_time) values(#{logid,jdbcType=VARCHAR},#{rep_data},#{resp_data},#{end_time,jdbcType=TIMESTAMP})")
    public void insertEAILogDataDAO(@Param("logid") String logid,@Param("rep_data") String rep_data,@Param("resp_data") String resp_data,@Param("end_time") Timestamp end_time);
}
