
package com.qihoo.wzws.rzb.routine.po;


import java.util.Comparator;


public class StatBeanComparator
        implements Comparator<StatBean> {

    @Override
    public int compare(StatBean o1, StatBean o2) {

        return o2.getVisit() - o1.getVisit();

    }

}