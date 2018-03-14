package com.haier.coherence.entity;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

import java.io.Serializable;
import java.sql.Timestamp;


@Portable
public class EAILogData implements Serializable {
    @PortableProperty(0)
    private String log_id="";
    @PortableProperty(1)
    private String svc_id="";
    @PortableProperty(2)
    private String entry_id="";
    @PortableProperty(3)
    private String detail="";
    @PortableProperty(4)
    private String detail_in="";
    @PortableProperty(5)
    private String detail_out="";
    @PortableProperty(6)
    private String error_title="";
    @PortableProperty(7)
    private String error_detail="";
    @PortableProperty(8)
    private String dest_url="";
    @PortableProperty(9)
    private String source_url="";
    @PortableProperty(10)
    private String osb_url="";
    @PortableProperty(11)
    private String atrtributes="";
    @PortableProperty(12)
    private String log_time_id="";
    @PortableProperty(13)
    private Timestamp message_time;
    @PortableProperty(14)
    private Long log_time;
    @PortableProperty(15)
    private Long log_size;
    @PortableProperty(16)
    private String data_source="";
    @PortableProperty(17)
    private Timestamp gmt_create;
    @PortableProperty(18)
    private String message_id="";
    @PortableProperty(19)
    private boolean storged=false;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getSvc_id() {
        return svc_id;
    }

    public void setSvc_id(String svc_id) {
        this.svc_id = svc_id;
    }

    public String getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(String entry_id) {
        this.entry_id = entry_id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetail_in() {
        return detail_in;
    }

    public void setDetail_in(String detail_in) {
        this.detail_in = detail_in;
    }

    public String getDetail_out() {
        return detail_out;
    }

    public void setDetail_out(String detail_out) {
        this.detail_out = detail_out;
    }

    public String getError_title() {
        return error_title;
    }

    public void setError_title(String error_title) {
        this.error_title = error_title;
    }

    public String getError_detail() {
        return error_detail;
    }

    public void setError_detail(String error_detail) {
        this.error_detail = error_detail;
    }

    public String getDest_url() {
        return dest_url;
    }

    public void setDest_url(String dest_url) {
        this.dest_url = dest_url;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getOsb_url() {
        return osb_url;
    }

    public void setOsb_url(String osb_url) {
        this.osb_url = osb_url;
    }

    public String getAtrtributes() {
        return atrtributes;
    }

    public void setAtrtributes(String atrtributes) {
        this.atrtributes = atrtributes;
    }

    public String getLog_time_id() {
        return log_time_id;
    }

    public void setLog_time_id(String log_time_id) {
        this.log_time_id = log_time_id;
    }

    public Timestamp getMessage_time() {
        return message_time;
    }

    public void setMessage_time(Timestamp message_time) {
        this.message_time = message_time;
    }

    public Long getLog_time() {
        return log_time;
    }

    public void setLog_time(Long log_time) {
        this.log_time = log_time;
    }

    public Long getLog_size() {
        return log_size;
    }

    public void setLog_size(Long log_size) {
        this.log_size = log_size;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public Timestamp getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(Timestamp gmt_create) {
        this.gmt_create = gmt_create;
    }

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public boolean isStorged() {
        return storged;
    }

    public void setStorged(boolean storged) {
        this.storged = storged;
    }

    @Override
    public String toString() {
        return "EAILogData{" +
                "log_id=" + log_id+
                ", svc_id=" + svc_id +
                ", entry_id=" + entry_id +
                ", detail=" + detail.toString() +
                ", detail_in='" + detail_in + '\'' +
                ", detail_out='" + detail_out + '\'' +
                ", error_title='" + error_title + '\'' +
                ", error_detail='" + error_detail + '\'' +
                ", dest_url='" + dest_url + '\'' +
                ", source_url='" + source_url + '\'' +
                ", osb_url='" + osb_url + '\'' +
                ", atrtributes=" + atrtributes +
                ", log_time_id=" + log_time_id +
                ", message_time=" + (message_time==null?"":message_time.toString()) +
                ", log_time=" + (log_time==null?"":log_time.longValue()) +
                ", log_size=" + (log_size==null?"":log_size.longValue()) +
                ", data_source=" + data_source +
                ", gmt_create=" + (gmt_create==null?"":gmt_create.toString()) +
                ", message_id='" + message_id + '\'' +
                '}';
    }
}
