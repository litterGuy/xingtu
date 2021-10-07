
package com.qihoo.wzws.rzb.secure.po;


import com.qihoo.wzws.rzb.util.DateUtil;

import java.util.Date;


public class AttackStatBean
        implements Comparable<AttackStatBean> {
    private String date;


    public AttackStatBean(String date, int attackCount, int ccCount) {

        this.date = date;

        this.attackCount = attackCount;

        this.ccCount = ccCount;

    }


    private int attackCount;

    private int ccCount;


    public int compareTo(AttackStatBean o) {

        Date a = DateUtil.parseDate(this.date);

        Date b = DateUtil.parseDate(o.getDate());


        if (a.before(b))
            return 1;

        if (a.after(b)) {

            return -1;

        }


        return 0;

    }


    public String getDate() {

        return this.date;

    }


    public void setDate(String date) {

        this.date = date;

    }


    public int getAttackCount() {

        return this.attackCount;

    }


    public int getCcCount() {

        return this.ccCount;

    }


    public void setAttackCount(int attackCount) {

        this.attackCount = attackCount;

    }


    public void setCcCount(int ccCount) {

        this.ccCount = ccCount;

    }

}