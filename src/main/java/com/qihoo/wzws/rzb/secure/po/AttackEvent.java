
package com.qihoo.wzws.rzb.secure.po;


import java.util.Date;


public class AttackEvent {
    private String sumIP;
    private String sumAtt;
    private Date firstStartTime;
    private String firstStartTimeStr;
    private String firstIp;
    private String firstRegion;
    private String firstAttackType;
    private String firstUri;
    private int firstCount;
    private String firstCountStr;
    private String hotIP;
    private String hostRegion;
    private String hotDayHour;
    private int hotDayhourCount;
    private String hotDayhourCountStr;


    public Date getFirstStartTime() {

        return this.firstStartTime;

    }


    public String getFirstIp() {

        return this.firstIp;

    }


    public String getFirstRegion() {

        return this.firstRegion;

    }


    public String getFirstAttackType() {

        return this.firstAttackType;

    }


    public String getFirstUri() {

        return this.firstUri;

    }


    public int getFirstCount() {

        return this.firstCount;

    }


    public String getHotIP() {

        return this.hotIP;

    }


    public String getHotDayHour() {

        return this.hotDayHour;

    }


    public void setFirstStartTime(Date firstStartTime) {

        this.firstStartTime = firstStartTime;

    }


    public void setFirstIp(String firstIp) {

        this.firstIp = firstIp;

    }


    public void setFirstRegion(String firstRegion) {

        this.firstRegion = firstRegion;

    }


    public void setFirstAttackType(String firstAttackType) {

        this.firstAttackType = firstAttackType;

    }


    public void setFirstUri(String firstUri) {

        this.firstUri = firstUri;

    }


    public void setFirstCount(int firstCount) {

        this.firstCount = firstCount;

    }


    public void setHotIP(String hotIP) {

        this.hotIP = hotIP;

    }


    public void setHotDayHour(String hotDayHour) {

        this.hotDayHour = hotDayHour;

    }


    public String getHostRegion() {

        return this.hostRegion;

    }


    public void setHostRegion(String hostRegion) {

        this.hostRegion = hostRegion;

    }


    public int getHotDayhourCount() {

        return this.hotDayhourCount;

    }


    public void setHotDayhourCount(int hotDayhourCount) {

        this.hotDayhourCount = hotDayhourCount;

    }


    public String getFirstCountStr() {

        return this.firstCountStr;

    }


    public String getHotDayhourCountStr() {

        return this.hotDayhourCountStr;

    }


    public void setFirstCountStr(String firstCountStr) {

        this.firstCountStr = firstCountStr;

    }


    public void setHotDayhourCountStr(String hotDayhourCountStr) {

        this.hotDayhourCountStr = hotDayhourCountStr;

    }


    public String getSumIP() {

        return this.sumIP;

    }


    public String getSumAtt() {

        return this.sumAtt;

    }


    public void setSumIP(String sumIP) {

        this.sumIP = sumIP;

    }


    public void setSumAtt(String sumAtt) {

        this.sumAtt = sumAtt;

    }


    public String getFirstStartTimeStr() {

        return this.firstStartTimeStr;

    }


    public void setFirstStartTimeStr(String firstStartTimeStr) {

        this.firstStartTimeStr = firstStartTimeStr;

    }

}