/*     */
package com.qihoo.wzws.rzb.secure;
/*     */
/*     */

import com.qihoo.wzws.rzb.cc.CC;
import com.qihoo.wzws.rzb.cc.CCAlgorithm;
import com.qihoo.wzws.rzb.cc.CCDetail;
import com.qihoo.wzws.rzb.secure.po.CCEntity;
import com.qihoo.wzws.rzb.util.ConfigUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
/*     */ public class CCAnalyzeSingle
        /*     */ {
    /*  26 */   private static int concurrent_request = 1000;
    /*  27 */   private static double request_growth = 0.5D;
    /*  28 */   private static double ip_rate = 0.5D;

    /*     */
    /*     */
    /*     */
    public static void initParas() {
        /*  32 */
        concurrent_request = Integer.valueOf((String) ConfigUtil.formatConfig.get("cc_concurrent_request")).intValue();
        /*  33 */
        request_growth = Double.valueOf((String) ConfigUtil.formatConfig.get("cc_request_growth")).doubleValue();
        /*  34 */
        ip_rate = Double.valueOf((String) ConfigUtil.formatConfig.get("cc_ip_rate")).doubleValue();
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void execute(HashMap<String, TreeMap<Long, CCDetail>> allHost) {
        /*  39 */
        if (allHost == null || allHost.size() == 0) {
            /*     */
            return;
            /*     */
        }
        /*     */
        /*     */
        /*     */
        /*     */
        /*  46 */
        for (Map.Entry<String, TreeMap<Long, CCDetail>> hostEntry : allHost.entrySet()) {
            /*     */
            /*  48 */
            TreeMap<Long, CCDetail> hMap = hostEntry.getValue();
            /*     */
            /*     */
            /*  51 */
            Iterator<Map.Entry<Long, CCDetail>> iterator = hMap.entrySet().iterator();
            /*     */
            /*  53 */
            Map.Entry previous = null;
            /*  54 */
            if (iterator.hasNext()) {
                /*  55 */
                previous = iterator.next();
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*     */
            /*  61 */
            while (iterator.hasNext()) {
                /*  62 */
                Map.Entry current = iterator.next();
                /*     */
                /*  64 */
                CCDetail previous5Min = (CCDetail) previous.getValue();
                /*  65 */
                CCDetail current5Min = (CCDetail) current.getValue();
                /*     */
                /*     */
                /*     */
                /*     */
                /*  70 */
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                /*  71 */
                if (current5Min.getCount() >= concurrent_request) {
                    /*  72 */
                    Date startime = null;
                    /*  73 */
                    CC c = CCAlgorithm.compareAndGetResult(previous5Min, current5Min, request_growth, ip_rate);
                    /*  74 */
                    if (c != null) {
                        /*     */
                        /*     */
                        /*  77 */
                        CCEntity entity = new CCEntity(c.getHost(), c.getIp(), c.geturi().replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;"), c.getTime(), c.getCount());
                        /*  78 */
                        AttackReportCache.put2CCList(entity);
                        /*  79 */
                        if (AnalyzeSingle.useRoutine) {
                            /*  80 */
                            String[] day = c.getTime().split(" ");
                            /*  81 */
                            String[] hour = c.getTime().split(":");
                            /*  82 */
                            AttackReportCache.put2dayCCAttackMap(day[0], Integer.valueOf(c.getCount()));
                            /*  83 */
                            AttackReportCache.put2hourCCAttackMap(hour[0], Integer.valueOf(c.getCount()));
                            /*     */
                            /*  85 */
                            AttackReportCache.ccCount.addAndGet(c.getCount());
                            /*     */
                            /*     */
                            try {
                                /*  88 */
                                Date CCtime = dateFormat.parse(c.getTime());
                                /*     */
                                /*  90 */
                                if (startime == null || startime.after(CCtime)) {
                                    /*  91 */
                                    AttackReportCache.firstCC = entity;
                                    /*     */
                                }
                                /*     */
                            }
                            /*  94 */ catch (ParseException e) {
                                /*     */
                                /*  96 */
                                e.printStackTrace();
                                /*     */
                            }
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                    /*     */
                    /*     */
                    /*     */
                    /* 104 */
                    current5Min.getList().clear();
                    /*     */
                    /*     */
                    /* 107 */
                    previous = current;
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\CCAnalyzeSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */