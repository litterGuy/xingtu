
package com.qihoo.wzws.rzb.secure;


import com.google.gson.Gson;
import com.qihoo.wzws.rzb.routine.po.CommonStatBean;
import com.qihoo.wzws.rzb.routine.po.StatBean;
import com.qihoo.wzws.rzb.routine.po.StatBeanComparator;
import com.qihoo.wzws.rzb.secure.po.AttackEntity;
import com.qihoo.wzws.rzb.secure.po.AttackEntityComparator;
import com.qihoo.wzws.rzb.secure.po.AttackEvent;
import com.qihoo.wzws.rzb.secure.po.AttackStatBean;
import com.qihoo.wzws.rzb.secure.po.AttackTypeObject;
import com.qihoo.wzws.rzb.secure.po.CCEntity;
import com.qihoo.wzws.rzb.secure.po.CCEntityComparator;
import com.qihoo.wzws.rzb.secure.po.CommBean;
import com.qihoo.wzws.rzb.secure.po.CommBeanComparator;
import com.qihoo.wzws.rzb.secure.po.OverViewEntity;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.SecurityConstants;
import com.qihoo.wzws.rzb.util.ip.IPRegion;
import com.qihoo.wzws.rzb.util.ip.IPUtil;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class AttackReportCache {
    public static Map<String, Long> overviewMap = new HashMap<String, Long>();


    public static AtomicLong records = new AtomicLong(0L);
    public static Date startTime;
    public static AttackEntity firstAttack;
    public static Date endTime;
    public static List<AttackEntity> attackList = new ArrayList<AttackEntity>();
    public static AtomicLong attackCount = new AtomicLong(0L);
    public static AtomicLong ccCount = new AtomicLong(0L);
    public static AtomicLong longUrlCount = new AtomicLong(0L);


    public static List<Date> dataFrom = new ArrayList<Date>();

    public static Map<String, Long> dayAttackMap = new HashMap<String, Long>();

    public static Map<String, Long> hourAttackMap = new HashMap<String, Long>();

    public static Map<String, Integer> dayCCAttackMap = new HashMap<String, Integer>();

    public static Map<String, Integer> hourCCAttackMap = new HashMap<String, Integer>();

    public static List<CommBean> attackIPList = new ArrayList<CommBean>();
    public static List<CommBean> ccIPList = new ArrayList<CommBean>();
    public static Map<String, Integer> ccIPMap = new HashMap<String, Integer>();

    public static List<AttackTypeObject> allAttackTypelist = new ArrayList<AttackTypeObject>();


    private static Map<String, AttackEntity> attackMaps = new ConcurrentHashMap<String, AttackEntity>();
    public static List<CCEntity> ccList = new ArrayList<CCEntity>();

    public static CCEntity firstCC = new CCEntity();


    public static void initData() {

        Set<String> set = dayAttackMap.keySet();

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");

        try {

            for (String date : set) {

                Date myDate1 = dateFormat1.parse(date);

                dataFrom.add(myDate1);

            }

        } catch (ParseException e) {


            e.printStackTrace();

        }

        Collections.sort(dataFrom);


        Map<String, Long> attackip = new HashMap<String, Long>();

        for (AttackEntity entity : attackList) {

            if (!attackip.containsKey(entity.getIp())) {


                attackip.put(entity.getIp(), Long.valueOf(1L));
                continue;

            }

            attackip.put(entity.getIp(), Long.valueOf(((Long) attackip.get(entity.getIp())).longValue() + 1L));

        }


        for (Map.Entry<String, Long> entry : attackip.entrySet()) {

            CommBean c = new CommBean(entry.getKey(), ((Long) entry.getValue()).intValue());

            attackIPList.add(c);

        }

        for (CCEntity entity : ccList) {

            if (!ccIPMap.containsKey(entity.getIp())) {


                ccIPMap.put(entity.getIp(), Integer.valueOf(1));
                continue;

            }

            ccIPMap.put(entity.getIp(), Integer.valueOf(((Integer) ccIPMap.get(entity.getIp())).intValue() + 1));

        }


        for (Map.Entry<String, Integer> entry : ccIPMap.entrySet()) {

            CommBean c = new CommBean(entry.getKey(), ((Integer) entry.getValue()).intValue());

            ccIPList.add(c);

        }


        Map<String, List<AttackEntity>> allAttackMap = new HashMap<String, List<AttackEntity>>();

        for (AttackEntity i : attackList) {

            if (allAttackMap.containsKey(i.getRule())) {

                ((List<AttackEntity>) allAttackMap.get(i.getRule())).add(i);
                continue;

            }

            List<AttackEntity> tmp = new ArrayList<AttackEntity>();

            tmp.add(i);

            allAttackMap.put(i.getRule(), tmp);

        }


        for (Map.Entry<String, List<AttackEntity>> entry : allAttackMap.entrySet()) {

            AttackTypeObject temp = new AttackTypeObject();

            temp.setTypeName(entry.getKey());

            temp.setTypeList(entry.getValue());

            temp.setTypeId((String) SecurityConstants.attackTypeNameIdMap.get(entry.getKey()));

            allAttackTypelist.add(temp);

        }

    }


    public static AttackEvent getAttackEvt(NumberFormat intformat) {

        AttackEvent attackEvent = new AttackEvent();

        if (attackList.size() > 0) {

            attackEvent.setFirstAttackType(firstAttack.getRule());

            attackEvent.setFirstIp(firstAttack.getIp());

            attackEvent.setFirstCount(firstAttack.getCount());

            attackEvent.setFirstStartTimeStr(firstAttack.getStarttime());

            attackEvent.setFirstStartTime(DateUtil.parseDateTime(firstAttack.getStarttime()));

            attackEvent.setFirstCountStr(intformat.format(attackEvent.getFirstCount()));


            attackEvent.setSumAtt(intformat.format(attackCount));

            attackEvent.setSumIP(intformat.format(attackIPList.size()));


            attackEvent.setFirstRegion(IPUtil.getRegion(attackEvent.getFirstIp()));


            String Maxtime = null;

            int Maxcouns = 0;

            for (Map.Entry<String, Long> entry : hourAttackMap.entrySet()) {

                if (Maxtime == null || Maxcouns < ((Long) entry.getValue()).intValue()) {

                    Maxtime = entry.getKey();

                    Maxcouns = ((Long) entry.getValue()).intValue();

                }

            }

            attackEvent.setHotDayHour(Maxtime);

            attackEvent.setHotDayhourCount(Maxcouns);

            attackEvent.setHotDayhourCountStr(intformat.format(Maxcouns));

            Collections.sort(attackIPList, (Comparator<? super CommBean>) new CommBeanComparator());

            attackEvent.setHotIP(((CommBean) attackIPList.get(0)).getName());

            attackEvent.setHostRegion(IPUtil.getRegion(((CommBean) attackIPList.get(0)).getName()));

        }

        return attackEvent;

    }


    public static AttackEvent getCcEvt(NumberFormat intformat) {

        AttackEvent CcEvent = new AttackEvent();

        if (AnalyzeSingle.useCC && ccList.size() > 0) {


            CcEvent.setHotDayHour(((CCEntity) ccList.get(0)).getStarttime());

            CcEvent.setHotDayhourCount(((CCEntity) ccList.get(0)).getCount());

            CcEvent.setHotDayhourCountStr(intformat.format(CcEvent.getHotDayhourCount()));

            Collections.sort(ccIPList, (Comparator<? super CommBean>) new CommBeanComparator());

            CcEvent.setHotIP(((CommBean) ccIPList.get(0)).getName());

            CcEvent.setHostRegion(IPUtil.getRegion(((CommBean) ccIPList.get(0)).getName()));


            String Maxtime = null;

            int Maxcouns = 0;

            for (Map.Entry<String, Integer> entry : hourCCAttackMap.entrySet()) {

                if (Maxtime == null || Maxcouns < ((Integer) entry.getValue()).intValue()) {

                    Maxtime = entry.getKey();

                    Maxcouns = ((Integer) entry.getValue()).intValue();

                }

            }

            CcEvent.setHotDayHour(Maxtime);

            CcEvent.setHotDayhourCount(Maxcouns);

            CcEvent.setHotDayhourCountStr(intformat.format(Maxcouns));

            CcEvent.setSumAtt(intformat.format(ccCount));

            CcEvent.setSumIP(intformat.format(ccIPList.size()));


            CcEvent.setFirstIp(firstCC.getIp());

            CcEvent.setFirstUri(firstCC.getUrl());

            CcEvent.setFirstCount(firstCC.getCount());

            CcEvent.setFirstStartTimeStr(firstCC.getStarttime());

            CcEvent.setFirstCountStr(intformat.format(CcEvent.getFirstCount()));

            CcEvent.setFirstRegion(IPUtil.getRegion(CcEvent.getFirstIp()));

        }


        return CcEvent;

    }


    public static void put2OverViewMap(String key) {

        if (overviewMap.containsKey(key)) {

            overviewMap.put(key, Long.valueOf(((Long) overviewMap.get(key)).longValue() + 1L));

        } else {

            overviewMap.put(key, Long.valueOf(1L));

        }

    }


    public static void put2dayAttackMap(String key) {

        if (dayAttackMap.containsKey(key)) {

            dayAttackMap.put(key, Long.valueOf(((Long) dayAttackMap.get(key)).longValue() + 1L));

        } else {

            dayAttackMap.put(key, Long.valueOf(1L));

        }

    }


    public static void put2dayCCAttackMap(String key, Integer value) {

        if (dayCCAttackMap.containsKey(key)) {

            dayCCAttackMap.put(key, Integer.valueOf(((Integer) dayCCAttackMap.get(key)).intValue() + value.intValue()));

        } else {

            dayCCAttackMap.put(key, value);

        }

    }


    public static void put2hourCCAttackMap(String key, Integer value) {

        if (hourCCAttackMap.containsKey(key)) {

            hourCCAttackMap.put(key, Integer.valueOf(((Integer) hourCCAttackMap.get(key)).intValue() + value.intValue()));

        } else {

            hourCCAttackMap.put(key, value);

        }

    }


    public static void put2hourAttackMap(String key) {

        if (hourAttackMap.containsKey(key)) {

            hourAttackMap.put(key, Long.valueOf(((Long) hourAttackMap.get(key)).longValue() + 1L));

        } else {

            hourAttackMap.put(key, Long.valueOf(1L));

        }

    }


    public static String outputOverViewForMail() {

        List<OverViewEntity> list = new ArrayList<OverViewEntity>();

        for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {

            list.add(new OverViewEntity(entry.getKey(), ((Long) entry.getValue()).longValue()));

        }


        if (longUrlCount.get() > 0L) {

            list.add(new OverViewEntity("超长URL", longUrlCount.get()));

        }

        if (ccCount.get() > 0L) {

            list.add(new OverViewEntity("CC攻击", ccCount.get()));

        }


        Collections.sort(list);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {

            OverViewEntity entity = list.get(i);

            sb.append(entity.getName() + ":" + entity.getCount());

            if (i != list.size() - 1) {

                sb.append("|");

            }

        }


        return sb.toString();

    }


    public static List<OverViewEntity> outputOverViewListForMail() {

        List<OverViewEntity> list = new ArrayList<OverViewEntity>();

        for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {

            list.add(new OverViewEntity(entry.getKey(), ((Long) entry.getValue()).longValue()));

        }


        if (longUrlCount.get() > 0L) {

            list.add(new OverViewEntity("超长URL", longUrlCount.get()));

        }

        if (ccCount.get() > 0L) {

            list.add(new OverViewEntity("CC攻击", ccCount.get()));

        }


        Collections.sort(list);


        return list;

    }


    public static void put2AttMap(String key, AttackEntity entity) {

        if (attackMaps.containsKey(key)) {

            AttackEntity att = attackMaps.get(key);

            att.setCount(att.getCount() + 1);

        } else {

            attackMaps.put(key, entity);

        }

    }


    public static void put2CCList(CCEntity entity) {

        ccList.add(entity);

    }


    public static int getattackTypeSum() {

        int attack = overviewMap.size();

        return attack;

    }


    public static String ForallDaysDetailsjson() {

        Map<String, Object> allDaysDetailsObject = new HashMap<String, Object>();

        int days = dataFrom.size();

        String day = null;

        List<AttackStatBean> attackDayHourList = new ArrayList<AttackStatBean>();

        if (days > 2) {

            List<AttackStatBean> attackDayList = new ArrayList<AttackStatBean>();

            for (int i = 0; i < dataFrom.size(); i++) {

                Date date = dataFrom.get(i);

                int attackC = 0;

                int ccC = 0;

                String dateStr = DateUtil.formatDate(date);


                if (dayAttackMap.containsKey(dateStr)) {

                    attackC = ((Long) dayAttackMap.get(dateStr)).intValue();

                }

                if (dayCCAttackMap.containsKey(dateStr)) {

                    ccC = ((Integer) dayCCAttackMap.get(dateStr)).intValue();

                }

                AttackStatBean asb = new AttackStatBean(dateStr, attackC, ccC);

                attackDayList.add(asb);

            }

            String[] allDaysDetailsKeys = new String[attackDayList.size()];

            String[] allDaysDetailsAttackVals = new String[attackDayList.size()];

            String[] allDaysDetailsCCVals = new String[attackDayList.size()];

            for (int j = 0; j < attackDayList.size(); j++) {

                AttackStatBean bean = attackDayList.get(j);

                allDaysDetailsKeys[j] = bean.getDate();

                allDaysDetailsAttackVals[j] = String.valueOf(bean.getAttackCount());

                allDaysDetailsCCVals[j] = String.valueOf(bean.getCcCount());

            }

            attackDayList.clear();

            allDaysDetailsObject.put("attackDeatilKey", allDaysDetailsKeys);

            allDaysDetailsObject.put("attackAttackDeatilVal", allDaysDetailsAttackVals);


            allDaysDetailsObject.put("attackCCDeatilVal", allDaysDetailsCCVals);

        } else {


            for (int i = 0; i < dataFrom.size(); i++) {


                day = DateUtil.formatDate(dataFrom.get(i));

                for (int k = 0; k < 24; k++) {

                    String hourStr = "";

                    if (k < 10) {

                        hourStr = "0" + String.valueOf(k);

                    } else {

                        hourStr = String.valueOf(k);

                    }

                    int attackC = 0;

                    int ccC = 0;

                    String dayHourStr = day + " " + hourStr;

                    if (hourAttackMap.containsKey(dayHourStr)) {

                        attackC = ((Long) hourAttackMap.get(dayHourStr)).intValue();

                    }

                    if (hourCCAttackMap.containsKey(dayHourStr)) {

                        ccC = ((Integer) hourCCAttackMap.get(dayHourStr)).intValue();

                    }

                    AttackStatBean asb = new AttackStatBean(dayHourStr, attackC, ccC);

                    attackDayHourList.add(asb);

                }

            }

            String[] allDaysDetailsKeys = new String[attackDayHourList.size()];

            String[] allDaysDetailsAttackVals = new String[attackDayHourList.size()];

            String[] allDaysDetailsCCVals = new String[attackDayHourList.size()];

            for (int j = 0; j < attackDayHourList.size(); j++) {

                AttackStatBean bean = attackDayHourList.get(j);

                allDaysDetailsKeys[j] = bean.getDate();

                allDaysDetailsAttackVals[j] = String.valueOf(bean.getAttackCount());

                allDaysDetailsCCVals[j] = String.valueOf(bean.getCcCount());

            }


            attackDayHourList.clear();

            allDaysDetailsObject.put("attackDeatilKey", allDaysDetailsKeys);

            allDaysDetailsObject.put("attackAttackDeatilVal", allDaysDetailsAttackVals);


            allDaysDetailsObject.put("attackCCDeatilVal", allDaysDetailsCCVals);

        }


        Gson gson = new Gson();

        String allDaysDetailsjson = gson.toJson(allDaysDetailsObject);


        return allDaysDetailsjson;

    }


    public static String ForattackVObjectjson() {

        List<CommonStatBean> typeList = new ArrayList<CommonStatBean>();

        for (String key : overviewMap.keySet()) {

            CommonStatBean tmp = new CommonStatBean();

            tmp.setKey(key);

            tmp.setCount(((Long) overviewMap.get(key)).intValue());

            typeList.add(tmp);

        }


        Map<String, Integer> secDimCountMap = new HashMap<String, Integer>();

        secDimCountMap.put("数据", Integer.valueOf(0));

        secDimCountMap.put("服务器", Integer.valueOf(0));

        secDimCountMap.put("应用", Integer.valueOf(0));

        secDimCountMap.put("文件", Integer.valueOf(0));

        secDimCountMap.put("其他", Integer.valueOf(0));


        if (typeList != null && typeList.size() > 0) {

            for (int i = 0; i < typeList.size(); i++) {

                CommonStatBean c = typeList.get(i);


                String key = (String) SecurityConstants.secDimMap.get(c.getKey());

                if (secDimCountMap.containsKey(key)) {

                    int count = ((Integer) secDimCountMap.get(key)).intValue() + c.getCount();

                    secDimCountMap.put(key, Integer.valueOf(count));

                } else {

                    secDimCountMap.put(key, Integer.valueOf(c.getCount()));

                }

            }

        }


        int max = 0;

        int sum = 0;


        for (Map.Entry<String, Integer> entry : secDimCountMap.entrySet()) {

            if (((Integer) entry.getValue()).intValue() > max) {

                sum += ((Integer) entry.getValue()).intValue();

            }

        }

        double avg = (sum / secDimCountMap.size());


        Map<String, Object> attackVObject = new HashMap<String, Object>();

        List<Integer> attackVList = new ArrayList<Integer>();

        List<Map> listMap = new ArrayList<Map>();

        for (Map.Entry<String, Integer> entry : secDimCountMap.entrySet()) {


            double val = ((Integer) entry.getValue()).intValue() / avg;

            if (val >= 1.0D) {

                val = 1.0D;

            } else if (val < 0.08D) {

                val *= 10.0D;

            }


            attackVList.add(Integer.valueOf((int) (val * 100.0D)));


            HashMap<String, Object> typeListMap = new HashMap<String, Object>();

            typeListMap.put("text", entry.getKey());

            typeListMap.put("max", Integer.valueOf(100));

            listMap.add(typeListMap);

        }

        Object[] attackArrays = attackVList.toArray();

        attackVObject.put("attackVKeyList", listMap);

        attackVObject.put("attackVValList", attackArrays);


        Gson gson = new Gson();

        return gson.toJson(attackVObject);

    }


    public static String ForAttackDatajson() {

        Map<String, Object> attackTypeObject = new HashMap<String, Object>();

        if (overviewMap != null && overviewMap.size() > 0) {

            String[] typeKeyList = new String[overviewMap.size()];

            List<Map> listMap = new ArrayList<Map>();

            int i = 0;

            for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {


                typeKeyList[i] = entry.getKey();


                HashMap<String, Object> typeListMap = new HashMap<String, Object>();

                typeListMap.put("name", entry.getKey());

                typeListMap.put("value", entry.getValue());

                listMap.add(typeListMap);

                i++;

            }

            attackTypeObject.put("typeKeyList", typeKeyList);

            attackTypeObject.put("typeList", listMap);

        }


        Gson gson = new Gson();

        return gson.toJson(attackTypeObject);

    }


    public static void sortAttlist() {

        for (Map.Entry<String, AttackEntity> entry : attackMaps.entrySet()) {

            attackList.add(entry.getValue());

        }

        Collections.sort(attackList, (Comparator<? super AttackEntity>) new AttackEntityComparator());

    }


    public static void sortCClist() {

        Collections.sort(ccList, (Comparator<? super CCEntity>) new CCEntityComparator());

    }


    public static List<StatBean> GetCcIPRegionList(NumberFormat intformat, NumberFormat performat) {

        Long ccCount = new Long(ccList.size());


        List<StatBean> ccIPRegionList = new ArrayList<StatBean>();


        for (int i = 0; i < ccIPList.size(); i++) {

            CommBean cb = ccIPList.get(i);

            IPRegion region = IPUtil.getIPRegionLocal(cb.getName());

            if (region != null) {


                String ipRegion = region.getCountry();

                if (region.getCountry().indexOf("中国") > -1) {

                    if (region.getCity() != null && !region.getProvince().equals(region.getCity())) {

                        ipRegion = ipRegion + "-" + region.getProvince() + region.getCity();

                    } else {

                        ipRegion = ipRegion + "-" + region.getProvince();

                    }

                }


                StatBean sb = new StatBean();

                sb.setIp(cb.getName());

                sb.setCountry(ipRegion);

                sb.setVisit(cb.getCount());

                sb.setVisitStr(intformat.format(cb.getCount()));

                sb.setVisitRate(performat.format(Long.valueOf(cb.getCount()).longValue() / ccCount.longValue()));


                ccIPRegionList.add(sb);

            }

        }

        return ccIPRegionList;

    }


    public static List<StatBean> GetAttackIPRegionList(NumberFormat intformat, NumberFormat performat) {

        Long attackCount = new Long(AttackReportCache.attackCount.intValue());

        List<StatBean> attackIPRegionList = new ArrayList<StatBean>();

        for (int i = 0; i < attackIPList.size(); i++) {

            CommBean cb = attackIPList.get(i);

            IPRegion region = IPUtil.getIPRegionLocal(cb.getName());

            if (region != null) {


                String ipRegion = region.getCountry();

                if (region.getCountry().indexOf("中国") > -1) {

                    if (region.getCity() != null && !region.getProvince().equals(region.getCity())) {

                        ipRegion = ipRegion + "-" + region.getProvince() + region.getCity();

                    } else {

                        ipRegion = ipRegion + "-" + region.getProvince();

                    }

                }


                StatBean sb = new StatBean();

                sb.setIp(cb.getName());

                sb.setCountry(ipRegion);

                sb.setVisit(cb.getCount());

                sb.setVisitStr(intformat.format(cb.getCount()));

                sb.setVisitRate(performat.format(Long.valueOf(cb.getCount()).longValue() / attackCount.longValue()));


                attackIPRegionList.add(sb);

            }

        }
        Collections.sort(attackIPRegionList, (Comparator<? super StatBean>) new StatBeanComparator());

        return attackIPRegionList;

    }


    public static List<CommBean> GetAttackCountryRegionList(NumberFormat intformat) {

        Map<String, Integer> attackCountryMap = new HashMap<String, Integer>();

        for (int i = 0; i < attackIPList.size(); i++) {

            CommBean cb = attackIPList.get(i);

            IPRegion region = IPUtil.getIPRegionLocal(cb.getName());

            if (region != null) {


                int ipCC = 0;

                if (ccIPMap.containsKey(cb.getName())) {

                    ipCC = ((Integer) ccIPMap.get(cb.getName())).intValue();

                    ccIPMap.remove(cb.getName());

                }


                int count = cb.getCount() + ipCC;


                if (region.getCountry().indexOf("局域网") <= -1 && region.getCountry().indexOf("骨干网") <= -1) {


                    if (attackCountryMap.containsKey(region.getCountry())) {

                        attackCountryMap.put(region.getCountry(), Integer.valueOf(((Integer) attackCountryMap.get(region.getCountry())).intValue() + count));

                    } else {

                        attackCountryMap.put(region.getCountry(), Integer.valueOf(count));

                    }

                }

            }

        }


        for (Map.Entry<String, Integer> entry : ccIPMap.entrySet()) {

            IPRegion region = IPUtil.getIPRegionLocal(entry.getKey());

            if (region == null) {

                continue;

            }


            int count = ((Integer) entry.getValue()).intValue();


            if (region.getCountry().indexOf("局域网") > -1 || region.getCountry().indexOf("骨干网") > -1) {

                continue;

            }

            if (attackCountryMap.containsKey(region.getCountry())) {

                attackCountryMap.put(region.getCountry(), Integer.valueOf(((Integer) attackCountryMap.get(region.getCountry())).intValue() + count));
                continue;

            }

            attackCountryMap.put(region.getCountry(), Integer.valueOf(count));

        }


        List<CommBean> attackCountryRegionSortList = new ArrayList<CommBean>();

        for (Map.Entry<String, Integer> entry : attackCountryMap.entrySet()) {

            attackCountryRegionSortList.add(new CommBean(entry.getKey(), ((Integer) entry.getValue()).intValue()));

        }

        attackCountryMap.clear();

        Collections.sort(attackCountryRegionSortList, (Comparator<? super CommBean>) new CommBeanComparator());


        List<CommBean> attackCountryRegionList = new ArrayList<CommBean>();

        for (CommBean bean : attackCountryRegionSortList) {

            attackCountryRegionList.add(new CommBean(bean.getName(), intformat.format(bean.getCount())));

        }

        attackCountryRegionSortList.clear();


        return attackCountryRegionList;

    }

}