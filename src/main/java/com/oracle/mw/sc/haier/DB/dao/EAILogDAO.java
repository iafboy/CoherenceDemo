package com.oracle.mw.sc.haier.DB.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Mapper
public interface EAILogDAO {
    @Insert("Insert into EAI_LOG(" +
            "log_id,svc_id,entry_id,error_title,error_detail,dest_url,source_url,osb_uri," +
            "ATTRIBUTES,log_time_id,message_time,log_time,data_source,gmt_create,message_id,req_size,resp_size" +
            ") values(" +
            "#{log_id,jdbcType=VARCHAR}," +
            "#{svc_id,jdbcType=VARCHAR}," +
            "#{entry_id,jdbcType=VARCHAR}," +
            "#{error_title,jdbcType=VARCHAR}," +
            "#{error_detail,jdbcType=VARCHAR}," +
            "#{dest_url,jdbcType=VARCHAR}," +
            "#{source_url,jdbcType=VARCHAR}," +
            "#{osb_url,jdbcType=VARCHAR}," +
            "#{atrtributes,jdbcType=VARCHAR}," +
            "#{log_time_id,jdbcType= VARCHAR}," +
            "#{message_time,jdbcType=TIMESTAMP }," +
            "#{log_time,jdbcType=NUMERIC}," +
            "#{data_source,jdbcType=VARCHAR}," +
            "#{gmt_create,jdbcType=TIMESTAMP}," +
            "#{message_id,jdbcType=VARCHAR}," +
            "#{req_size,jdbcType=NUMERIC}," +
            "#{resp_size,jdbcType=NUMERIC}" +
            ")")
    public void insertEAILogDAO(@Param("log_id") String log_id,
                                    @Param("svc_id") String svc_id,
                                    @Param("entry_id") String entry_id,
                                    @Param("error_title") String error_title,
                                    @Param("error_detail") String error_detail,
                                    @Param("dest_url") String dest_url,
                                    @Param("source_url") String source_url,
                                    @Param("osb_url") String osb_url,
                                    @Param("atrtributes") String atrtributes,
                                    @Param("log_time_id") String log_time_id,
                                    @Param("message_time") Timestamp message_time,
                                    @Param("log_time") BigDecimal time,
                                    @Param("data_source") String data_source,
                                    @Param("gmt_create") Timestamp gmt_create,
                                    @Param("message_id") String message_id,
                                    @Param("req_size") BigDecimal req_size,
                                    @Param("resp_size") BigDecimal resp_size
                                );
}
