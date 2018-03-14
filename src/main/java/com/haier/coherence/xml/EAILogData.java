package com.haier.coherence.xml;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by xiaoju on 2017/3/8.
 */
@XmlRootElement(namespace = NameSpace.ADMIN_URI)
public class EAILogData {

    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String MessageId;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String SvcId;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogUri;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogclientHost;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogHost;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogRequestTime;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogResponseTime;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogType;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogRequestData;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String LogResponseData;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String ErrorTitle;
    @XmlElement(namespace = NameSpace.ADMIN_URI)
    private String ErrorDetail;
    public EAILogData(){};
    public EAILogData(String MessageId, String SvcId, String LogUri, String LogclientHost, String LogHost, String LogRequestTime, String LogResponseTime, String LogType, String LogRequestData, String LogResponseData, String ErrorTitle, String ErrorDetail) {
        this.MessageId =MessageId;
        this.SvcId=SvcId;
        this.LogUri=LogUri;
        this.LogclientHost=LogclientHost;
        this.LogHost=LogHost;
        this.LogRequestTime=LogRequestTime;
        this.LogResponseTime=LogResponseTime;
        this.LogType=LogType;
        this.LogRequestData=LogRequestData;
        this.LogResponseData=LogResponseData;
        this.ErrorTitle=ErrorTitle;
        this.ErrorDetail=ErrorDetail;
    }

    public String getMessageId() {
        return MessageId;
    }

    public String getSvcId() {
        return SvcId;
    }

    public String getLogUri() {
        return LogUri;
    }

    public String getLogclientHost() {
        return LogclientHost;
    }

    public String getLogHost() {
        return LogHost;
    }

    public String getLogRequestTime() {
        return LogRequestTime;
    }

    public String getLogResponseTime() {
        return LogResponseTime;
    }

    public String getLogType() {
        return LogType;
    }

    public String getLogRequestData() {
        return LogRequestData;
    }

    public String getLogResponseData() {
        return LogResponseData;
    }

    public String getErrorTitle() {
        return ErrorTitle;
    }

    public String getErrorDetail() {
        return ErrorDetail;
    }
}
