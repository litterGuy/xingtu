
package com.qihoo.wzws.rzb.secure;


import java.io.Serializable;


public class RespBean
        implements Serializable {
    private static final long serialVersionUID = 1L;
    private String s;
    private String d;
    private String v;


    public String getS() {

        return this.s;

    }


    public String getD() {

        return this.d;

    }


    public void setS(String s) {

        this.s = s;

    }


    public void setD(String d) {

        this.d = d;

    }


    public String getV() {

        return this.v;

    }


    public void setV(String v) {

        this.v = v;

    }

}