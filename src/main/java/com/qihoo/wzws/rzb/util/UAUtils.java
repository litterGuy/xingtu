
package com.qihoo.wzws.rzb.util;


import java.util.ArrayList;
import java.util.List;


public class UAUtils {
    public static final String LABEL = "非法UA";
    public static List<String> uaList = new ArrayList<String>();


    static {

        uaList.add("ddos");

        uaList.add("Ddos");

        uaList.add("Referer");

        uaList.add("Python-urllib/2.7");

        uaList.add("_USERAGENT_");

        uaList.add("CustomAgent");

        uaList.add("Mozilla/5.0 ()");

        uaList.add("jakarta commons-httpclient");

        uaList.add("Apache-HttpClient");

    }

}