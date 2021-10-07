
package com.qihoo.wzws.rzb.routine.po;


public class StatBean {
    private String ip;
    private String country;
    private int visit;
    private String visitStr;
    private String visitRate;
    private long band;
    private String bandStr = "-";
    private String bandRate = "-";

    private int stat200count;

    private int stat404count;
    private int visitflag = 0;
    private int bandflag = 0;


    private String url;


    public int getVisit() {

        return this.visit;

    }


    public long getBand() {

        return this.band;

    }


    public int getStat404count() {

        return this.stat404count;

    }


    public void setVisit(int visit) {

        this.visit = visit;

    }


    public void setBand(long band) {

        this.band = band;

    }


    public void setStat404count(int stat404count) {

        this.stat404count = stat404count;

    }


    public int getStat200count() {

        return this.stat200count;

    }


    public void setStat200count(int stat200count) {

        this.stat200count = stat200count;

    }


    public String getIp() {

        return this.ip;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public String getCountry() {

        return this.country;

    }


    public String getVisitRate() {

        return this.visitRate;

    }


    public String getBandRate() {

        return this.bandRate;

    }


    public void setCountry(String country) {

        this.country = country;

    }


    public void setVisitRate(String visitRate) {

        this.visitRate = visitRate;

    }


    public void setBandRate(String bandRate) {

        this.bandRate = bandRate;

    }


    public String getBandStr() {

        return this.bandStr;

    }


    public void setBandStr(String bandStr) {

        this.bandStr = bandStr;

    }


    public String getUrl() {

        return this.url;

    }


    public void setUrl(String url) {

        this.url = url;

    }


    public int getVisitflag() {

        return this.visitflag;

    }


    public int getBandflag() {

        return this.bandflag;

    }


    public void setVisitflag(int visitflag) {

        this.visitflag = visitflag;

    }


    public void setBandflag(int bandflag) {

        this.bandflag = bandflag;

    }


    public String getVisitStr() {

        return this.visitStr;

    }


    public void setVisitStr(String visitStr) {

        this.visitStr = visitStr;

    }

}