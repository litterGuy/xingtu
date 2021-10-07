
package com.qihoo.wzws.rzb.cc;


import com.qihoo.wzws.rzb.util.DateUtil;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;


public class CCReqTimeComparator
        implements Comparator<CC> {

    @Override
    public int compare(CC o1, CC o2) {

        Date date1 = DateUtil.parseDate(o1.getTime());

        Date date2 = DateUtil.parseDate(o2.getTime());


        return date1.compareTo(date2);

    }


    public static void main(String[] args) {

        CC[] list = {new CC("A", 1), new CC("A", 1), new CC("A", 2), new CC("A", 5), new CC("A", 3), new CC("A", 4)};

        Arrays.sort(list, new CCReqTimeComparator());

        for (CC c : list) {
            System.out.println(c.getRequestKey() + " " + c.getCount());
        }

    }

}