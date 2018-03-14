package com.haier.coherence.entity;

import java.io.Serializable;

/**
 * Created by xiaoju on 2017/3/8.
 */
public class EAILogMsg implements Serializable {
    private String MessageId;
    private String SvcId;
    private String LogUri;
    private String LogclientHost;
    private String LogHost;
    private String LogRequestTime;
    private String LogResponseTime;
    private String LogType;
    private String LogRequestData;
    private String LogResponseData;
    private String ErrorTitle;
    private String ErrorDetail;

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String messageId) {
        MessageId = messageId;
    }

    public String getSvcId() {
        return SvcId;
    }

    public void setSvcId(String svcId) {
        SvcId = svcId;
    }

    public String getLogUri() {
        return LogUri;
    }

    public void setLogUri(String logUri) {
        LogUri = logUri;
    }

    public String getLogclientHost() {
        return LogclientHost;
    }

    public void setLogclientHost(String logclientHost) {
        LogclientHost = logclientHost;
    }

    public String getLogHost() {
        return LogHost;
    }

    public void setLogHost(String logHost) {
        LogHost = logHost;
    }

    public String getLogRequestTime() {
        return LogRequestTime;
    }

    public void setLogRequestTime(String logRequestTime) {
        LogRequestTime = logRequestTime;
    }

    public String getLogResponseTime() {
        return LogResponseTime;
    }

    public void setLogResponseTime(String logResponseTime) {
        LogResponseTime = logResponseTime;
    }

    public String getLogType() {
        return LogType;
    }

    public void setLogType(String logType) {
        LogType = logType;
    }

    public String getLogRequestData() {
        return LogRequestData;
    }

    public void setLogRequestData(String logRequestData) {
        LogRequestData = logRequestData;
    }

    public String getLogResponseData() {
        return LogResponseData;
    }

    public void setLogResponseData(String logResponseData) {
        LogResponseData = logResponseData;
    }

    public String getErrorTitle() {
        return ErrorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        ErrorTitle = errorTitle;
    }

    public String getErrorDetail() {
        return ErrorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        ErrorDetail = errorDetail;
    }

    @Override
    public String toString() {
        return "EAILogMsg{" +
                "MessageId='" + MessageId + '\'' +
                ", SvcId='" + SvcId + '\'' +
                ", LogUri='" + LogUri + '\'' +
                ", LogclientHost='" + LogclientHost + '\'' +
                ", LogHost='" + LogHost + '\'' +
                ", LogRequestTime='" + LogRequestTime + '\'' +
                ", LogResponseTime='" + LogResponseTime + '\'' +
                ", LogType='" + LogType + '\'' +
                ", LogRequestData='" + LogRequestData + '\'' +
                ", LogResponseData='" + LogResponseData + '\'' +
                ", ErrorTitle='" + ErrorTitle + '\'' +
                ", ErrorDetail='" + ErrorDetail + '\'' +
                '}';
    }
}
