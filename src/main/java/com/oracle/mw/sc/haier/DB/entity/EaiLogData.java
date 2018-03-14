package com.oracle.mw.sc.haier.DB.entity;

import java.sql.Clob;
import java.sql.Timestamp;

/**
 * Created by xiaoju on 2017/3/8.
 */
public class EaiLogData {
    private String LOGID;
	private String REP_DATA;
    private String RESP_DATA;
    private Timestamp END_TIME;

    public String getLOGID() {
        return LOGID;
    }

    public void setLOGID(String LOGID) {
        this.LOGID = LOGID;
    }

    public String getREP_DATA() {
        return REP_DATA;
    }

    public void setREP_DATA(String REP_DATA) {
        this.REP_DATA = REP_DATA;
    }

    public String getRESP_DATA() {
        return RESP_DATA;
    }

    public void setRESP_DATA(String RESP_DATA) {
        this.RESP_DATA = RESP_DATA;
    }

    public Timestamp getEND_TIME() {
        return END_TIME;
    }

    public void setEND_TIME(Timestamp END_TIME) {
        this.END_TIME = END_TIME;
    }
}
