
package com.qihoo.wzws.rzb.secure.po;


public class LogEntity {
    private String ip;
    private String time;
    private String host;
    private String requestUrl;
    private String responseCode;
    private long contentLength;
    private String referer;
    private String ua;


    public LogEntity(String ip, String time, String host, String requestUrl, String responseCode, long contentLength, String referer, String ua) {

        this.ip = ip;

        this.time = time;

        this.host = host;

        this.requestUrl = requestUrl;

        this.responseCode = responseCode;

        this.contentLength = contentLength;

        this.referer = referer;

        this.ua = ua;

    }


    public String getIp() {

        return this.ip;

    }


    public String getTime() {

        return this.time;

    }


    public String getHost() {

        return this.host;

    }


    public String getRequestUrl() {

        return this.requestUrl;

    }


    public String getResponseCode() {

        return this.responseCode;

    }


    public long getContentLength() {

        return this.contentLength;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public void setTime(String time) {

        this.time = time;

    }


    public void setHost(String host) {

        this.host = host;

    }


    public void setRequestUrl(String requestUrl) {

        this.requestUrl = requestUrl;

    }


    public void setResponseCode(String responseCode) {

        this.responseCode = responseCode;

    }


    public void setContentLength(long contentLength) {

        this.contentLength = contentLength;

    }


    @Override
    public String toString() {

        return "LogEntity [ip=" + this.ip + ", time=" + this.time + ", host=" + this.host + ", requestUrl=" + this.requestUrl + ", responseCode=" + this.responseCode + ", contentLength=" + this.contentLength + "]";

    }


    public String getReferer() {

        return this.referer;

    }


    public String getUa() {

        return this.ua;

    }


    public void setReferer(String referer) {

        this.referer = referer;

    }


    public void setUa(String ua) {

        this.ua = ua;

    }

}