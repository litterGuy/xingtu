
package com.qihoo.wzws.rzb.cc;


import java.util.Arrays;
import java.util.Comparator;


public class CCReqCountComparator
        implements Comparator<CC> {

    public int compare(CC o1, CC o2) {

        return o2.getCount() - o1.getCount();

    }


    public static void main(String[] args) {

        CC[] list = {new CC("A", 1), new CC("A", 1), new CC("A", 2), new CC("A", 5), new CC("A", 3), new CC("A", 4)};

        Arrays.sort(list, new CCReqCountComparator());

        for (CC c : list)

            System.out.println(c.getRequestKey() + " " + c.getCount());

    }

}