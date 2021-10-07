
package com.qihoo.wzws.rzb.secure.po;


import java.util.Comparator;


public class CommBeanComparator
        implements Comparator<CommBean> {

    @Override
    public int compare(CommBean o1, CommBean o2) {

        long comp = (o2.getCount() - o1.getCount());


        if (comp > 0L) {
            return 1;
        }

        if (comp < 0L) {

            return -1;

        }


        return 0;

    }

}