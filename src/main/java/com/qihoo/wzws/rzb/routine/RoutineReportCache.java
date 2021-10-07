
package com.qihoo.wzws.rzb.routine;


import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;
import com.qihoo.wzws.rzb.routine.po.StatBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RoutineReportCache {
    private static int pageCountLimit = 100;

    public static int visits;

    public static int PV;
    public static long totalband = 0L;


    public static long logSize;

    public static List<Date> dataFrom = new ArrayList<Date>();


    public static Map<String, StatBean> UVMap = new HashMap<String, StatBean>();

    public static Map<String, Integer> browserMap = new HashMap<String, Integer>();

    public static Map<String, Integer> osMap = new HashMap<String, Integer>();

    public static Map<String, Integer> countryMap = new HashMap<String, Integer>();

    public static int osCount;

    public static Map<String, StatBean> ipStaMap = new HashMap<String, StatBean>();


    public static Map<String, StatBean> pageVisitMap = new HashMap<String, StatBean>();

    public static Map<String, StatBean> staticPageVisitMap = new HashMap<String, StatBean>();

    public static Map<String, StatBean> _404pageVisitMap = new HashMap<String, StatBean>();


    public static Map<String, Integer> refererMap = new HashMap<String, Integer>();

    public static Map<String, SearchKeyWordBean> keyWordsMap = new HashMap<String, SearchKeyWordBean>();

    public static int stat2XXcount;

    public static int stat3XXcount;
    public static int stat4XXcount;
    public static int stat5XXcount;
    public static Map<String, StatBean> soVisitMap = new HashMap<String, StatBean>();


    public static int googlebot;


    public static int qh360bot;


    public static int baidubot;


    public static int sogoubot;


    public static int bingbot;


    public static int yahoobot;


    public static int sosobot;


    public static int fromgoogle;


    public static int from360;


    public static int frombaidu;


    public static int fromsogou;


    public static int frombing;


    public static int fromyahoo;


    public static int fromsoso;


    public static int[] visithour_1h;


    public static int[] cachehit_1h;


    public static long[] cacheband_1h;


    public static long[] totalband_1h;


    public static String visithour;


    public static String cachehithour;


    public static String cachebandhour;


    public static String totalbandhour;


    public static void outputBasic() {
    }


    public static void main(String[] args) {

        visits = 5;

        totalband = 20890L;

        outputBasic();

    }

}