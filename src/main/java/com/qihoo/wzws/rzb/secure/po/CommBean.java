
package com.qihoo.wzws.rzb.secure.po;


public class CommBean {
    private String name;
    private int count;
    private String countDisplay;
    private String countRate;
    private int count2;


    public CommBean(String name, String countDisplay, String countRate) {

        this.name = name;

        this.countDisplay = countDisplay;

        this.countRate = countRate;

    }


    public CommBean(String name, String countDisplay) {

        this.name = name;

        this.countDisplay = countDisplay;

    }


    public CommBean(String name, int count, int count2) {

        this.name = name;

        this.count = count;

        this.count2 = count2;

    }


    public CommBean(String name, int count) {

        this.name = name;

        this.count = count;

    }


    public String getName() {

        return this.name;

    }


    public int getCount() {

        return this.count;

    }


    public void setName(String name) {

        this.name = name;

    }


    public void setCount(int count) {

        this.count = count;

    }


    public String getCountDisplay() {

        return this.countDisplay;

    }


    public void setCountDisplay(String countDisplay) {

        this.countDisplay = countDisplay;

    }


    public int getCount2() {

        return this.count2;

    }


    public void setCount2(int count2) {

        this.count2 = count2;

    }


    public String getCountRate() {

        return this.countRate;

    }


    public void setCountRate(String countRate) {

        this.countRate = countRate;

    }

}