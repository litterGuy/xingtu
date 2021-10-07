
package com.qihoo.wzws.rzb.routine.po;


public class CommonStatBean {
    private String key;
    private int count;
    private String countStr;
    private String rate;
    private int flag = 0;


    public CommonStatBean() {
    }


    public CommonStatBean(String key, int count) {

        this.key = key;

        this.count = count;

    }


    public CommonStatBean(String key, int count, String rate) {

        this.key = key;

        this.count = count;

        this.rate = rate;

    }


    public String getKey() {

        return this.key;

    }


    public int getCount() {

        return this.count;

    }


    public void setKey(String key) {

        this.key = key;

    }


    public void setCount(int count) {

        this.count = count;

    }


    public String getRate() {

        return this.rate;

    }


    public void setRate(String rate) {

        this.rate = rate;

    }


    public int getFlag() {

        return this.flag;

    }


    public void setFlag(int flag) {

        this.flag = flag;

    }


    public String getCountStr() {

        return this.countStr;

    }


    public void setCountStr(String countStr) {

        this.countStr = countStr;

    }

}