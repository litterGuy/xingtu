
package com.qihoo.wzws.rzb.secure.po;


import java.util.Comparator;


public class CCEntityComparator
        implements Comparator<CCEntity> {

    @Override
    public int compare(CCEntity o1, CCEntity o2) {

        return o2.getCount() - o1.getCount();

    }

}