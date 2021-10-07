
package com.qihoo.wzws.rzb.secure.po;


public class OverViewEntity
        implements Comparable {
    private String name;
    private long count;


    public OverViewEntity(String name, long count) {

        this.name = name;

        this.count = count;

    }


    public String getName() {

        return this.name;

    }


    public long getCount() {

        return this.count;

    }


    public void setName(String name) {

        this.name = name;

    }


    public void setCount(long count) {

        this.count = count;

    }


    @Override
    public int compareTo(Object o) {

        return (int) (((OverViewEntity) o).getCount() - getCount());

    }

}