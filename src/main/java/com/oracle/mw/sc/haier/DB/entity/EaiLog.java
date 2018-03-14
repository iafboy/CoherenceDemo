package com.oracle.mw.sc.haier.DB.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by xiaoju on 2017/3/8.
 */
public class EaiLog {
    private String LOG_ID;
    private String SVC_ID;
	private String ENTRY_ID;
    private String ERROR_TITLE;
    private String ERROR_DETAIL;
    private String DEST_URL;
    private String SOURCE_URL;
    private String OSB_URI;
    private String ATTRIBUTES;
    private String LOG_TIME_ID;
    private Timestamp MESSAGE_TIME;
    private BigDecimal LOG_TIME;
    private String DATA_SOURCE;
    private Timestamp GMT_CREATE;
    private String MESSAGE_ID;
    private String DETAIL="";
    private BigDecimal REQ_SIZE;
    private BigDecimal RESP_SIZE;

    public String getLOG_ID() {
        return LOG_ID;
    }

    public void setLOG_ID(String LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    public String getSVC_ID() {
        return SVC_ID;
    }

    public void setSVC_ID(String SVC_ID) {
        this.SVC_ID = SVC_ID;
    }

    public String getENTRY_ID() {
        return ENTRY_ID;
    }

    public void setENTRY_ID(String ENTRY_ID) {
        this.ENTRY_ID = ENTRY_ID;
    }

    public String getERROR_TITLE() {
        return ERROR_TITLE;
    }

    public void setERROR_TITLE(String ERROR_TITLE) {
        this.ERROR_TITLE = ERROR_TITLE;
    }

    public String getERROR_DETAIL() {
        return ERROR_DETAIL;
    }

    public void setERROR_DETAIL(String ERROR_DETAIL) {
        this.ERROR_DETAIL = ERROR_DETAIL;
    }

    public String getDEST_URL() {
        return DEST_URL;
    }

    public void setDEST_URL(String DEST_URL) {
        this.DEST_URL = DEST_URL;
    }

    public String getSOURCE_URL() {
        return SOURCE_URL;
    }

    public void setSOURCE_URL(String SOURCE_URL) {
        this.SOURCE_URL = SOURCE_URL;
    }

    public String getOSB_URI() {
        return OSB_URI;
    }

    public void setOSB_URI(String OSB_URI) {
        this.OSB_URI = OSB_URI;
    }

    public String getATTRIBUTES() {
        return ATTRIBUTES;
    }

    public void setATTRIBUTES(String ATTRIBUTES) {
        this.ATTRIBUTES = ATTRIBUTES;
    }

    public String getLOG_TIME_ID() {
        return LOG_TIME_ID;
    }

    public void setLOG_TIME_ID(String LOG_TIME_ID) {
        this.LOG_TIME_ID = LOG_TIME_ID;
    }

    public Timestamp getMESSAGE_TIME() {
        return MESSAGE_TIME;
    }

    public void setMESSAGE_TIME(Timestamp MESSAGE_TIME) {
        this.MESSAGE_TIME = MESSAGE_TIME;
    }

    public BigDecimal getLOG_TIME() {
        return LOG_TIME;
    }

    public void setLOG_TIME(BigDecimal LOG_TIME) {
        this.LOG_TIME = LOG_TIME;
    }

    public String getDATA_SOURCE() {
        return DATA_SOURCE;
    }

    public void setDATA_SOURCE(String DATA_SOURCE) {
        this.DATA_SOURCE = DATA_SOURCE;
    }

    public Timestamp getGMT_CREATE() {
        return GMT_CREATE;
    }

    public void setGMT_CREATE(Timestamp GMT_CREATE) {
        this.GMT_CREATE = GMT_CREATE;
    }

    public String getMESSAGE_ID() {
        return MESSAGE_ID;
    }

    public void setMESSAGE_ID(String MESSAGE_ID) {
        this.MESSAGE_ID = MESSAGE_ID;
    }

    public String getDETAIL() {
        return DETAIL;
    }

    public void setDETAIL(String DETAIL) {
        this.DETAIL = DETAIL;
    }

    public BigDecimal getREQ_SIZE() {
        return REQ_SIZE;
    }

    public void setREQ_SIZE(BigDecimal REQ_SIZE) {
        this.REQ_SIZE = REQ_SIZE;
    }

    public BigDecimal getRESP_SIZE() {
        return RESP_SIZE;
    }

    public void setRESP_SIZE(BigDecimal RESP_SIZE) {
        this.RESP_SIZE = RESP_SIZE;
    }
}
