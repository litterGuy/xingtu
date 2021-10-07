
package com.qihoo.wzws.rzb.cc;


public class CC {
    private String requestKey;
    private int count;
    private String host;
    private String uri;
    private String ip;
    private String time;


    public CC() {
    }


    public CC(String requestKey, int count) {

        this.requestKey = requestKey;

        this.count = count;

    }


    public CC(String host, String uri, String ip, String time) {

        this.host = host;

        this.uri = uri;

        this.ip = ip;

        this.time = time;

    }


    public String getRequestKey() {

        return this.requestKey;

    }


    public void setRequestKey(String requestKey) {

        this.requestKey = requestKey;

    }


    public int getCount() {

        return this.count;

    }


    public void setCount(int count) {

        this.count = count;

    }


    public String geturi() {

        return this.uri;

    }


    public String getIp() {

        return this.ip;

    }


    public void seturi(String uri) {

        this.uri = uri;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public String getTime() {

        return this.time;

    }


    public void setTime(String time) {

        this.time = time;

    }


    public String getHost() {

        return this.host;

    }


    public String getUri() {

        return this.uri;

    }


    public void setHost(String host) {

        this.host = host;

    }


    public void setUri(String uri) {

        this.uri = uri;

    }

}