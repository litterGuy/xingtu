
package com.qihoo.wzws.rzb.secure.po;


import java.io.Serializable;


public class CCEntity
        implements Serializable {
    private static final long serialVersionUID = 2893993256380110029L;
    private long ccdid;
    private String host;
    private String ip;
    private String url;
    private String starttime;
    private int count;


    public CCEntity() {
    }


    public CCEntity(String host, String ip, String url, String starttime, int count) {

        this.host = host;

        this.ip = ip;

        this.url = url;

        this.starttime = starttime;

        this.count = count;

    }


    public Long getCcdid() {

        return Long.valueOf(this.ccdid);

    }


    public String getHost() {

        return this.host;

    }


    public String getIp() {

        return this.ip;

    }


    public String getUrl() {

        return this.url;

    }


    public String getStarttime() {

        return this.starttime;

    }


    public void setCcdid(Long ccdid) {

        this.ccdid = ccdid.longValue();

    }


    public void setHost(String host) {

        this.host = host;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public void setUrl(String url) {

        this.url = url;

    }


    public void setStarttime(String starttime) {

        this.starttime = starttime;

    }


    public void setCount(int count) {

        this.count = count;

    }


    public int getCount() {

        return this.count;

    }


    public String output() {

        return new String(this.host + "\t" + this.ip + "\t" + this.url + "\t" + this.starttime + "\t" + this.count + "\r\n");

    }

}