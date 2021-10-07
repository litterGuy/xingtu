
package com.qihoo.wzws.rzb.secure.po;


import java.util.Comparator;


public class AttackEntityComparator
        implements Comparator<AttackEntity> {

    @Override
    public int compare(AttackEntity o1, AttackEntity o2) {

        return o2.getCount() - o1.getCount();

    }

}