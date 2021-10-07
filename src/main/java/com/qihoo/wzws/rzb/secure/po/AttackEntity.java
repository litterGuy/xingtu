
package com.qihoo.wzws.rzb.secure.po;


import java.io.Serializable;


public class AttackEntity
        implements Serializable {
    private static final long serialVersionUID = 6807835469251514642L;
    private long atdid;
    private String host;
    private String rule;
    private String ip;
    private String url;
    private String starttime;
    private String status;
    private int count;


    public AttackEntity(String host, String rule, String ip, String url, String starttime, String status, int count) {

        this.host = host;

        this.rule = rule;

        this.ip = ip;

        this.url = url;

        this.starttime = starttime;

        this.status = status;

        this.count = count;

    }


    public Long getAtdid() {

        return Long.valueOf(this.atdid);

    }


    public String getHost() {

        return this.host;

    }


    public String getRule() {

        return this.rule;

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


    public String getStatus() {

        return this.status;

    }


    public void setAtdid(Long atdid) {

        this.atdid = atdid.longValue();

    }


    public void setHost(String host) {

        this.host = host;

    }


    public void setRule(String rule) {

        this.rule = rule;

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


    public void setStatus(String status) {

        this.status = status;

    }


    public int getCount() {

        return this.count;

    }


    public void setCount(int count) {

        this.count = count;

    }


    public String output() {

        return new String(this.host + "\t" + this.rule + "\t" + this.ip + "\t" + this.url + "\t" + this.starttime + "\t" + this.status + "\t" + this.count + "\r\n");

    }

}