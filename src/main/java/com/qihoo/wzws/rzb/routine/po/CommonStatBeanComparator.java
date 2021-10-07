
package com.qihoo.wzws.rzb.routine.po;


import java.util.Comparator;


public class CommonStatBeanComparator
        implements Comparator<CommonStatBean> {

    @Override
    public int compare(CommonStatBean o1, CommonStatBean o2) {

        return o2.getCount() - o1.getCount();

    }

}