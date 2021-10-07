
package com.qihoo.wzws.rzb.util.ip;


public class IPRegion {
    private String ip;
    private String country;
    private String province;
    private String city;


    public String getIp() {

        return this.ip;

    }


    public void setIp(String ip) {

        this.ip = ip;

    }


    public String getCountry() {

        return this.country;

    }


    public void setCountry(String country) {

        this.country = country;

    }


    public String getProvince() {

        return this.province;

    }


    public void setProvince(String province) {

        this.province = province;

    }


    public String getCity() {

        return this.city;

    }


    public void setCity(String city) {

        this.city = city;

    }


    @Override
    public String toString() {

        return "IPRegion [ip=" + this.ip + ", country=" + this.country + ", province=" + this.province + ", city=" + this.city + "]";

    }

}