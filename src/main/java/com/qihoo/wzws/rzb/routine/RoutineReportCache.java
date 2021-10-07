/*     */ package com.qihoo.wzws.rzb.routine;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;
/*     */ import com.qihoo.wzws.rzb.routine.po.StatBean;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ public class RoutineReportCache
/*     */ {
/*  14 */   private static int pageCountLimit = 100;
/*     */   
/*     */   public static int visits;
/*     */   
/*     */   public static int PV;
/*  19 */   public static long totalband = 0L;
/*     */ 
/*     */   
/*     */   public static long logSize;
/*     */   
/*  24 */   public static List<Date> dataFrom = new ArrayList<Date>();
/*     */ 
/*     */   
/*  27 */   public static Map<String, StatBean> UVMap = new HashMap<String, StatBean>();
/*     */   
/*  29 */   public static Map<String, Integer> browserMap = new HashMap<String, Integer>();
/*     */   
/*  31 */   public static Map<String, Integer> osMap = new HashMap<String, Integer>();
/*     */   
/*  33 */   public static Map<String, Integer> countryMap = new HashMap<String, Integer>();
/*     */   
/*     */   public static int osCount;
/*     */   
/*  37 */   public static Map<String, StatBean> ipStaMap = new HashMap<String, StatBean>();
/*     */ 
/*     */   
/*  40 */   public static Map<String, StatBean> pageVisitMap = new HashMap<String, StatBean>();
/*     */   
/*  42 */   public static Map<String, StatBean> staticPageVisitMap = new HashMap<String, StatBean>();
/*     */   
/*  44 */   public static Map<String, StatBean> _404pageVisitMap = new HashMap<String, StatBean>();
/*     */ 
/*     */   
/*  47 */   public static Map<String, Integer> refererMap = new HashMap<String, Integer>();
/*     */   
/*  49 */   public static Map<String, SearchKeyWordBean> keyWordsMap = new HashMap<String, SearchKeyWordBean>();
/*     */   
/*     */   public static int stat2XXcount;
/*     */   
/*     */   public static int stat3XXcount;
/*     */   public static int stat4XXcount;
/*     */   public static int stat5XXcount;
/*  56 */   public static Map<String, StatBean> soVisitMap = new HashMap<String, StatBean>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int googlebot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int qh360bot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int baidubot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int sogoubot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int bingbot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int yahoobot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int sosobot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fromgoogle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int from360;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int frombaidu;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fromsogou;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int frombing;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fromyahoo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fromsoso;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] visithour_1h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] cachehit_1h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] cacheband_1h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long[] totalband_1h;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String visithour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String cachehithour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String cachebandhour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String totalbandhour;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void outputBasic() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 311 */     visits = 5;
/* 312 */     totalband = 20890L;
/* 313 */     outputBasic();
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\routine\RoutineReportCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */