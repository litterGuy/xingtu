
package com.qihoo.wzws.rzb.routine.po;


import java.util.Comparator;


public class SearchKeyWordComparator
        implements Comparator<SearchKeyWordBean> {

    @Override
    public int compare(SearchKeyWordBean o1, SearchKeyWordBean o2) {

        return o2.getCount() - o1.getCount();

    }

}