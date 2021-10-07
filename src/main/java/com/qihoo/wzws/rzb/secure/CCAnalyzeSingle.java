
package com.qihoo.wzws.rzb.secure;


import com.qihoo.wzws.rzb.cc.CC;
import com.qihoo.wzws.rzb.cc.CCAlgorithm;
import com.qihoo.wzws.rzb.cc.CCDetail;
import com.qihoo.wzws.rzb.secure.po.CCEntity;
import com.qihoo.wzws.rzb.util.ConfigUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CCAnalyzeSingle {
    private static int concurrent_request = 1000;
    private static double request_growth = 0.5D;
    private static double ip_rate = 0.5D;


    public static void initParas() {

        concurrent_request = Integer.valueOf((String) ConfigUtil.formatConfig.get("cc_concurrent_request")).intValue();

        request_growth = Double.valueOf((String) ConfigUtil.formatConfig.get("cc_request_growth")).doubleValue();

        ip_rate = Double.valueOf((String) ConfigUtil.formatConfig.get("cc_ip_rate")).doubleValue();

    }


    public void execute(HashMap<String, TreeMap<Long, CCDetail>> allHost) {

        if (allHost == null || allHost.size() == 0) {

            return;

        }


        for (Map.Entry<String, TreeMap<Long, CCDetail>> hostEntry : allHost.entrySet()) {


            TreeMap<Long, CCDetail> hMap = hostEntry.getValue();


            Iterator<Map.Entry<Long, CCDetail>> iterator = hMap.entrySet().iterator();


            Map.Entry previous = null;

            if (iterator.hasNext()) {

                previous = iterator.next();

            }


            while (iterator.hasNext()) {

                Map.Entry current = iterator.next();


                CCDetail previous5Min = (CCDetail) previous.getValue();

                CCDetail current5Min = (CCDetail) current.getValue();


                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                if (current5Min.getCount() >= concurrent_request) {

                    Date startime = null;

                    CC c = CCAlgorithm.compareAndGetResult(previous5Min, current5Min, request_growth, ip_rate);

                    if (c != null) {


                        CCEntity entity = new CCEntity(c.getHost(), c.getIp(), c.geturi().replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;"), c.getTime(), c.getCount());

                        AttackReportCache.put2CCList(entity);

                        if (AnalyzeSingle.useRoutine) {

                            String[] day = c.getTime().split(" ");

                            String[] hour = c.getTime().split(":");

                            AttackReportCache.put2dayCCAttackMap(day[0], Integer.valueOf(c.getCount()));

                            AttackReportCache.put2hourCCAttackMap(hour[0], Integer.valueOf(c.getCount()));


                            AttackReportCache.ccCount.addAndGet(c.getCount());


                            try {

                                Date CCtime = dateFormat.parse(c.getTime());


                                if (startime == null || startime.after(CCtime)) {

                                    AttackReportCache.firstCC = entity;

                                }

                            } catch (ParseException e) {


                                e.printStackTrace();

                            }

                        }

                    }


                    current5Min.getList().clear();


                    previous = current;

                }

            }

        }

    }

}