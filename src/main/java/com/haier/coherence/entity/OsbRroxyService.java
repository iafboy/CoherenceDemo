package com.haier.coherence.entity;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

import java.io.Serializable;

/**
 * Created by xiaoju on 2017/3/6.
 */
@Portable
public class OsbRroxyService implements Serializable{
    @PortableProperty(0)
    private String origin_service_seq;
    @PortableProperty(1)
    private String origin_service_link;
    @PortableProperty(2)
    private String sub_service_link;
    @PortableProperty(3)
    private Long service_switch;
    @PortableProperty(4)
    private boolean storged=false;

    public String getOrigin_service_seq() {
        return origin_service_seq;
    }

    public void setOrigin_service_seq(String origin_service_seq) {
        this.origin_service_seq = origin_service_seq;
    }

    public String getOrigin_service_link() {
        return origin_service_link;
    }

    public void setOrigin_service_link(String origin_service_link) {
        this.origin_service_link = origin_service_link;
    }

    public String getSub_service_link() {
        return sub_service_link;
    }

    public void setSub_service_link(String sub_service_link) {
        this.sub_service_link = sub_service_link;
    }

    public Long getService_switch() {
        return service_switch;
    }

    public void setService_switch(Long service_switch) {
        this.service_switch = service_switch;
    }

    public boolean isStorged() {
        return storged;
    }

    public void setStorged(boolean storged) {
        this.storged = storged;
    }

    @Override
    public String toString() {
        return "OsbRroxyService{" +
                "origin_service_seq='" + origin_service_seq + '\'' +
                ", origin_service_link='" + origin_service_link + '\'' +
                ", sub_service_link='" + sub_service_link + '\'' +
                ", service_switch='" + (service_switch==null?0:service_switch.longValue()) + '\'' +
                ", storged=" + storged +
                '}';
    }
}
