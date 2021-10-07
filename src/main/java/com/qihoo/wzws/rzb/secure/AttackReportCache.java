/*     */ package com.qihoo.wzws.rzb.secure;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.qihoo.wzws.rzb.routine.po.CommonStatBean;
/*     */ import com.qihoo.wzws.rzb.routine.po.StatBean;
/*     */ import com.qihoo.wzws.rzb.routine.po.StatBeanComparator;
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackEntity;
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackEntityComparator;
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackEvent;
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackStatBean;
/*     */ import com.qihoo.wzws.rzb.secure.po.AttackTypeObject;
/*     */ import com.qihoo.wzws.rzb.secure.po.CCEntity;
/*     */ import com.qihoo.wzws.rzb.secure.po.CCEntityComparator;
/*     */ import com.qihoo.wzws.rzb.secure.po.CommBean;
/*     */ import com.qihoo.wzws.rzb.secure.po.CommBeanComparator;
/*     */ import com.qihoo.wzws.rzb.secure.po.OverViewEntity;
/*     */ import com.qihoo.wzws.rzb.util.DateUtil;
/*     */ import com.qihoo.wzws.rzb.util.SecurityConstants;
/*     */ import com.qihoo.wzws.rzb.util.ip.IPRegion;
/*     */ import com.qihoo.wzws.rzb.util.ip.IPUtil;
/*     */ import java.text.DateFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AttackReportCache
/*     */ {
/*  46 */   public static Map<String, Long> overviewMap = new HashMap<String, Long>();
/*     */ 
/*     */   
/*  49 */   public static AtomicLong records = new AtomicLong(0L);
/*     */   public static Date startTime;
/*     */   public static AttackEntity firstAttack;
/*     */   public static Date endTime;
/*  53 */   public static List<AttackEntity> attackList = new ArrayList<AttackEntity>();
/*  54 */   public static AtomicLong attackCount = new AtomicLong(0L);
/*  55 */   public static AtomicLong ccCount = new AtomicLong(0L);
/*  56 */   public static AtomicLong longUrlCount = new AtomicLong(0L);
/*     */ 
/*     */   
/*  59 */   public static List<Date> dataFrom = new ArrayList<Date>();
/*     */   
/*  61 */   public static Map<String, Long> dayAttackMap = new HashMap<String, Long>();
/*     */   
/*  63 */   public static Map<String, Long> hourAttackMap = new HashMap<String, Long>();
/*     */   
/*  65 */   public static Map<String, Integer> dayCCAttackMap = new HashMap<String, Integer>();
/*     */   
/*  67 */   public static Map<String, Integer> hourCCAttackMap = new HashMap<String, Integer>();
/*     */   
/*  69 */   public static List<CommBean> attackIPList = new ArrayList<CommBean>();
/*  70 */   public static List<CommBean> ccIPList = new ArrayList<CommBean>();
/*  71 */   public static Map<String, Integer> ccIPMap = new HashMap<String, Integer>();
/*     */   
/*  73 */   public static List<AttackTypeObject> allAttackTypelist = new ArrayList<AttackTypeObject>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   private static Map<String, AttackEntity> attackMaps = new ConcurrentHashMap<String, AttackEntity>();
/*  79 */   public static List<CCEntity> ccList = new ArrayList<CCEntity>();
/*     */   
/*  81 */   public static CCEntity firstCC = new CCEntity();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void initData() {
/*  88 */     Set<String> set = dayAttackMap.keySet();
/*  89 */     DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
/*     */     try {
/*  91 */       for (String date : set) {
/*  92 */         Date myDate1 = dateFormat1.parse(date);
/*  93 */         dataFrom.add(myDate1);
/*     */       } 
/*  95 */     } catch (ParseException e) {
/*     */       
/*  97 */       e.printStackTrace();
/*     */     } 
/*  99 */     Collections.sort(dataFrom);
/*     */ 
/*     */     
/* 102 */     Map<String, Long> attackip = new HashMap<String, Long>();
/* 103 */     for (AttackEntity entity : attackList) {
/* 104 */       if (!attackip.containsKey(entity.getIp())) {
/*     */         
/* 106 */         attackip.put(entity.getIp(), Long.valueOf(1L)); continue;
/*     */       } 
/* 108 */       attackip.put(entity.getIp(), Long.valueOf(((Long)attackip.get(entity.getIp())).longValue() + 1L));
/*     */     } 
/*     */     
/* 111 */     for (Map.Entry<String, Long> entry : attackip.entrySet()) {
/* 112 */       CommBean c = new CommBean(entry.getKey(), ((Long)entry.getValue()).intValue());
/* 113 */       attackIPList.add(c);
/*     */     } 
/* 115 */     for (CCEntity entity : ccList) {
/* 116 */       if (!ccIPMap.containsKey(entity.getIp())) {
/*     */         
/* 118 */         ccIPMap.put(entity.getIp(), Integer.valueOf(1)); continue;
/*     */       } 
/* 120 */       ccIPMap.put(entity.getIp(), Integer.valueOf(((Integer)ccIPMap.get(entity.getIp())).intValue() + 1));
/*     */     } 
/*     */     
/* 123 */     for (Map.Entry<String, Integer> entry : ccIPMap.entrySet()) {
/* 124 */       CommBean c = new CommBean(entry.getKey(), ((Integer)entry.getValue()).intValue());
/* 125 */       ccIPList.add(c);
/*     */     } 
/*     */     
/* 128 */     Map<String, List<AttackEntity>> allAttackMap = new HashMap<String, List<AttackEntity>>();
/* 129 */     for (AttackEntity i : attackList) {
/* 130 */       if (allAttackMap.containsKey(i.getRule())) {
/* 131 */         ((List<AttackEntity>)allAttackMap.get(i.getRule())).add(i); continue;
/*     */       } 
/* 133 */       List<AttackEntity> tmp = new ArrayList<AttackEntity>();
/* 134 */       tmp.add(i);
/* 135 */       allAttackMap.put(i.getRule(), tmp);
/*     */     } 
/*     */ 
/*     */     
/* 139 */     for (Map.Entry<String, List<AttackEntity>> entry : allAttackMap.entrySet()) {
/* 140 */       AttackTypeObject temp = new AttackTypeObject();
/* 141 */       temp.setTypeName(entry.getKey());
/* 142 */       temp.setTypeList(entry.getValue());
/* 143 */       temp.setTypeId((String)SecurityConstants.attackTypeNameIdMap.get(entry.getKey()));
/* 144 */       allAttackTypelist.add(temp);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttackEvent getAttackEvt(NumberFormat intformat) {
/* 157 */     AttackEvent attackEvent = new AttackEvent();
/* 158 */     if (attackList.size() > 0) {
/* 159 */       attackEvent.setFirstAttackType(firstAttack.getRule());
/* 160 */       attackEvent.setFirstIp(firstAttack.getIp());
/* 161 */       attackEvent.setFirstCount(firstAttack.getCount());
/* 162 */       attackEvent.setFirstStartTimeStr(firstAttack.getStarttime());
/* 163 */       attackEvent.setFirstStartTime(DateUtil.parseDateTime(firstAttack.getStarttime()));
/* 164 */       attackEvent.setFirstCountStr(intformat.format(attackEvent.getFirstCount()));
/*     */       
/* 166 */       attackEvent.setSumAtt(intformat.format(attackCount));
/* 167 */       attackEvent.setSumIP(intformat.format(attackIPList.size()));
/*     */       
/* 169 */       attackEvent.setFirstRegion(IPUtil.getRegion(attackEvent.getFirstIp()));
/*     */       
/* 171 */       String Maxtime = null;
/* 172 */       int Maxcouns = 0;
/* 173 */       for (Map.Entry<String, Long> entry : hourAttackMap.entrySet()) {
/* 174 */         if (Maxtime == null || Maxcouns < ((Long)entry.getValue()).intValue()) {
/* 175 */           Maxtime = entry.getKey();
/* 176 */           Maxcouns = ((Long)entry.getValue()).intValue();
/*     */         } 
/*     */       } 
/* 179 */       attackEvent.setHotDayHour(Maxtime);
/* 180 */       attackEvent.setHotDayhourCount(Maxcouns);
/* 181 */       attackEvent.setHotDayhourCountStr(intformat.format(Maxcouns));
/* 182 */       Collections.sort(attackIPList, (Comparator<? super CommBean>)new CommBeanComparator());
/* 183 */       attackEvent.setHotIP(((CommBean)attackIPList.get(0)).getName());
/* 184 */       attackEvent.setHostRegion(IPUtil.getRegion(((CommBean)attackIPList.get(0)).getName()));
/*     */     } 
/* 186 */     return attackEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AttackEvent getCcEvt(NumberFormat intformat) {
/* 193 */     AttackEvent CcEvent = new AttackEvent();
/* 194 */     if (AnalyzeSingle.useCC && ccList.size() > 0) {
/*     */       
/* 196 */       CcEvent.setHotDayHour(((CCEntity)ccList.get(0)).getStarttime());
/* 197 */       CcEvent.setHotDayhourCount(((CCEntity)ccList.get(0)).getCount());
/* 198 */       CcEvent.setHotDayhourCountStr(intformat.format(CcEvent.getHotDayhourCount()));
/* 199 */       Collections.sort(ccIPList, (Comparator<? super CommBean>)new CommBeanComparator());
/* 200 */       CcEvent.setHotIP(((CommBean)ccIPList.get(0)).getName());
/* 201 */       CcEvent.setHostRegion(IPUtil.getRegion(((CommBean)ccIPList.get(0)).getName()));
/*     */       
/* 203 */       String Maxtime = null;
/* 204 */       int Maxcouns = 0;
/* 205 */       for (Map.Entry<String, Integer> entry : hourCCAttackMap.entrySet()) {
/* 206 */         if (Maxtime == null || Maxcouns < ((Integer)entry.getValue()).intValue()) {
/* 207 */           Maxtime = entry.getKey();
/* 208 */           Maxcouns = ((Integer)entry.getValue()).intValue();
/*     */         } 
/*     */       } 
/* 211 */       CcEvent.setHotDayHour(Maxtime);
/* 212 */       CcEvent.setHotDayhourCount(Maxcouns);
/* 213 */       CcEvent.setHotDayhourCountStr(intformat.format(Maxcouns));
/* 214 */       CcEvent.setSumAtt(intformat.format(ccCount));
/* 215 */       CcEvent.setSumIP(intformat.format(ccIPList.size()));
/*     */       
/* 217 */       CcEvent.setFirstIp(firstCC.getIp());
/* 218 */       CcEvent.setFirstUri(firstCC.getUrl());
/* 219 */       CcEvent.setFirstCount(firstCC.getCount());
/* 220 */       CcEvent.setFirstStartTimeStr(firstCC.getStarttime());
/* 221 */       CcEvent.setFirstCountStr(intformat.format(CcEvent.getFirstCount()));
/* 222 */       CcEvent.setFirstRegion(IPUtil.getRegion(CcEvent.getFirstIp()));
/*     */     } 
/*     */ 
/*     */     
/* 226 */     return CcEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2OverViewMap(String key) {
/* 235 */     if (overviewMap.containsKey(key)) {
/* 236 */       overviewMap.put(key, Long.valueOf(((Long)overviewMap.get(key)).longValue() + 1L));
/*     */     } else {
/* 238 */       overviewMap.put(key, Long.valueOf(1L));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2dayAttackMap(String key) {
/* 249 */     if (dayAttackMap.containsKey(key)) {
/* 250 */       dayAttackMap.put(key, Long.valueOf(((Long)dayAttackMap.get(key)).longValue() + 1L));
/*     */     } else {
/* 252 */       dayAttackMap.put(key, Long.valueOf(1L));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2dayCCAttackMap(String key, Integer value) {
/* 264 */     if (dayCCAttackMap.containsKey(key)) {
/* 265 */       dayCCAttackMap.put(key, Integer.valueOf(((Integer)dayCCAttackMap.get(key)).intValue() + value.intValue()));
/*     */     } else {
/* 267 */       dayCCAttackMap.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2hourCCAttackMap(String key, Integer value) {
/* 279 */     if (hourCCAttackMap.containsKey(key)) {
/* 280 */       hourCCAttackMap.put(key, Integer.valueOf(((Integer)hourCCAttackMap.get(key)).intValue() + value.intValue()));
/*     */     } else {
/* 282 */       hourCCAttackMap.put(key, value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void put2hourAttackMap(String key) {
/* 288 */     if (hourAttackMap.containsKey(key)) {
/* 289 */       hourAttackMap.put(key, Long.valueOf(((Long)hourAttackMap.get(key)).longValue() + 1L));
/*     */     } else {
/* 291 */       hourAttackMap.put(key, Long.valueOf(1L));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String outputOverViewForMail() {
/* 302 */     List<OverViewEntity> list = new ArrayList<OverViewEntity>();
/* 303 */     for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {
/* 304 */       list.add(new OverViewEntity(entry.getKey(), ((Long)entry.getValue()).longValue()));
/*     */     }
/*     */     
/* 307 */     if (longUrlCount.get() > 0L) {
/* 308 */       list.add(new OverViewEntity("超长URL", longUrlCount.get()));
/*     */     }
/* 310 */     if (ccCount.get() > 0L) {
/* 311 */       list.add(new OverViewEntity("CC攻击", ccCount.get()));
/*     */     }
/*     */     
/* 314 */     Collections.sort(list);
/* 315 */     StringBuilder sb = new StringBuilder();
/* 316 */     for (int i = 0; i < list.size(); i++) {
/* 317 */       OverViewEntity entity = list.get(i);
/* 318 */       sb.append(entity.getName() + ":" + entity.getCount());
/* 319 */       if (i != list.size() - 1) {
/* 320 */         sb.append("|");
/*     */       }
/*     */     } 
/*     */     
/* 324 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public static List<OverViewEntity> outputOverViewListForMail() {
/* 328 */     List<OverViewEntity> list = new ArrayList<OverViewEntity>();
/* 329 */     for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {
/* 330 */       list.add(new OverViewEntity(entry.getKey(), ((Long)entry.getValue()).longValue()));
/*     */     }
/*     */     
/* 333 */     if (longUrlCount.get() > 0L) {
/* 334 */       list.add(new OverViewEntity("超长URL", longUrlCount.get()));
/*     */     }
/* 336 */     if (ccCount.get() > 0L) {
/* 337 */       list.add(new OverViewEntity("CC攻击", ccCount.get()));
/*     */     }
/*     */     
/* 340 */     Collections.sort(list);
/*     */     
/* 342 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2AttMap(String key, AttackEntity entity) {
/* 352 */     if (attackMaps.containsKey(key)) {
/* 353 */       AttackEntity att = attackMaps.get(key);
/* 354 */       att.setCount(att.getCount() + 1);
/*     */     } else {
/* 356 */       attackMaps.put(key, entity);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void put2CCList(CCEntity entity) {
/* 366 */     ccList.add(entity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getattackTypeSum() {
/* 375 */     int attack = overviewMap.size();
/* 376 */     return attack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ForallDaysDetailsjson() {
/* 386 */     Map<String, Object> allDaysDetailsObject = new HashMap<String, Object>();
/* 387 */     int days = dataFrom.size();
/* 388 */     String day = null;
/* 389 */     List<AttackStatBean> attackDayHourList = new ArrayList<AttackStatBean>();
/* 390 */     if (days > 2) {
/* 391 */       List<AttackStatBean> attackDayList = new ArrayList<AttackStatBean>();
/* 392 */       for (int i = 0; i < dataFrom.size(); i++) {
/* 393 */         Date date = dataFrom.get(i);
/* 394 */         int attackC = 0;
/* 395 */         int ccC = 0;
/* 396 */         String dateStr = DateUtil.formatDate(date);
/*     */         
/* 398 */         if (dayAttackMap.containsKey(dateStr)) {
/* 399 */           attackC = ((Long)dayAttackMap.get(dateStr)).intValue();
/*     */         }
/* 401 */         if (dayCCAttackMap.containsKey(dateStr)) {
/* 402 */           ccC = ((Integer)dayCCAttackMap.get(dateStr)).intValue();
/*     */         }
/* 404 */         AttackStatBean asb = new AttackStatBean(dateStr, attackC, ccC);
/* 405 */         attackDayList.add(asb);
/*     */       } 
/* 407 */       String[] allDaysDetailsKeys = new String[attackDayList.size()];
/* 408 */       String[] allDaysDetailsAttackVals = new String[attackDayList.size()];
/* 409 */       String[] allDaysDetailsCCVals = new String[attackDayList.size()];
/* 410 */       for (int j = 0; j < attackDayList.size(); j++) {
/* 411 */         AttackStatBean bean = attackDayList.get(j);
/* 412 */         allDaysDetailsKeys[j] = bean.getDate();
/* 413 */         allDaysDetailsAttackVals[j] = String.valueOf(bean.getAttackCount());
/* 414 */         allDaysDetailsCCVals[j] = String.valueOf(bean.getCcCount());
/*     */       } 
/* 416 */       attackDayList.clear();
/* 417 */       allDaysDetailsObject.put("attackDeatilKey", allDaysDetailsKeys);
/* 418 */       allDaysDetailsObject.put("attackAttackDeatilVal", allDaysDetailsAttackVals);
/*     */       
/* 420 */       allDaysDetailsObject.put("attackCCDeatilVal", allDaysDetailsCCVals);
/*     */     } else {
/*     */       
/* 423 */       for (int i = 0; i < dataFrom.size(); i++) {
/*     */         
/* 425 */         day = DateUtil.formatDate(dataFrom.get(i));
/* 426 */         for (int k = 0; k < 24; k++) {
/* 427 */           String hourStr = "";
/* 428 */           if (k < 10) {
/* 429 */             hourStr = "0" + String.valueOf(k);
/*     */           } else {
/* 431 */             hourStr = String.valueOf(k);
/*     */           } 
/* 433 */           int attackC = 0;
/* 434 */           int ccC = 0;
/* 435 */           String dayHourStr = day + " " + hourStr;
/* 436 */           if (hourAttackMap.containsKey(dayHourStr)) {
/* 437 */             attackC = ((Long)hourAttackMap.get(dayHourStr)).intValue();
/*     */           }
/* 439 */           if (hourCCAttackMap.containsKey(dayHourStr)) {
/* 440 */             ccC = ((Integer)hourCCAttackMap.get(dayHourStr)).intValue();
/*     */           }
/* 442 */           AttackStatBean asb = new AttackStatBean(dayHourStr, attackC, ccC);
/* 443 */           attackDayHourList.add(asb);
/*     */         } 
/*     */       } 
/* 446 */       String[] allDaysDetailsKeys = new String[attackDayHourList.size()];
/* 447 */       String[] allDaysDetailsAttackVals = new String[attackDayHourList.size()];
/* 448 */       String[] allDaysDetailsCCVals = new String[attackDayHourList.size()];
/* 449 */       for (int j = 0; j < attackDayHourList.size(); j++) {
/* 450 */         AttackStatBean bean = attackDayHourList.get(j);
/* 451 */         allDaysDetailsKeys[j] = bean.getDate();
/* 452 */         allDaysDetailsAttackVals[j] = String.valueOf(bean.getAttackCount());
/* 453 */         allDaysDetailsCCVals[j] = String.valueOf(bean.getCcCount());
/*     */       } 
/*     */       
/* 456 */       attackDayHourList.clear();
/* 457 */       allDaysDetailsObject.put("attackDeatilKey", allDaysDetailsKeys);
/* 458 */       allDaysDetailsObject.put("attackAttackDeatilVal", allDaysDetailsAttackVals);
/*     */       
/* 460 */       allDaysDetailsObject.put("attackCCDeatilVal", allDaysDetailsCCVals);
/*     */     } 
/*     */     
/* 463 */     Gson gson = new Gson();
/* 464 */     String allDaysDetailsjson = gson.toJson(allDaysDetailsObject);
/*     */     
/* 466 */     return allDaysDetailsjson;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ForattackVObjectjson() {
/* 476 */     List<CommonStatBean> typeList = new ArrayList<CommonStatBean>();
/* 477 */     for (String key : overviewMap.keySet()) {
/* 478 */       CommonStatBean tmp = new CommonStatBean();
/* 479 */       tmp.setKey(key);
/* 480 */       tmp.setCount(((Long)overviewMap.get(key)).intValue());
/* 481 */       typeList.add(tmp);
/*     */     } 
/*     */     
/* 484 */     Map<String, Integer> secDimCountMap = new HashMap<String, Integer>();
/* 485 */     secDimCountMap.put("数据", Integer.valueOf(0));
/* 486 */     secDimCountMap.put("服务器", Integer.valueOf(0));
/* 487 */     secDimCountMap.put("应用", Integer.valueOf(0));
/* 488 */     secDimCountMap.put("文件", Integer.valueOf(0));
/* 489 */     secDimCountMap.put("其他", Integer.valueOf(0));
/*     */     
/* 491 */     if (typeList != null && typeList.size() > 0) {
/* 492 */       for (int i = 0; i < typeList.size(); i++) {
/* 493 */         CommonStatBean c = typeList.get(i);
/*     */ 
/*     */         
/* 496 */         String key = (String)SecurityConstants.secDimMap.get(c.getKey());
/* 497 */         if (secDimCountMap.containsKey(key)) {
/* 498 */           int count = ((Integer)secDimCountMap.get(key)).intValue() + c.getCount();
/* 499 */           secDimCountMap.put(key, Integer.valueOf(count));
/*     */         } else {
/* 501 */           secDimCountMap.put(key, Integer.valueOf(c.getCount()));
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 506 */     int max = 0;
/* 507 */     int sum = 0;
/*     */     
/* 509 */     for (Map.Entry<String, Integer> entry : secDimCountMap.entrySet()) {
/* 510 */       if (((Integer)entry.getValue()).intValue() > max) {
/* 511 */         sum += ((Integer)entry.getValue()).intValue();
/*     */       }
/*     */     } 
/* 514 */     double avg = (sum / secDimCountMap.size());
/*     */     
/* 516 */     Map<String, Object> attackVObject = new HashMap<String, Object>();
/* 517 */     List<Integer> attackVList = new ArrayList<Integer>();
/* 518 */     List<Map> listMap = new ArrayList<Map>();
/* 519 */     for (Map.Entry<String, Integer> entry : secDimCountMap.entrySet()) {
/*     */       
/* 521 */       double val = ((Integer)entry.getValue()).intValue() / avg;
/* 522 */       if (val >= 1.0D) {
/* 523 */         val = 1.0D;
/* 524 */       } else if (val < 0.08D) {
/* 525 */         val *= 10.0D;
/*     */       } 
/*     */       
/* 528 */       attackVList.add(Integer.valueOf((int)(val * 100.0D)));
/*     */       
/* 530 */       HashMap<String, Object> typeListMap = new HashMap<String, Object>();
/* 531 */       typeListMap.put("text", entry.getKey());
/* 532 */       typeListMap.put("max", Integer.valueOf(100));
/* 533 */       listMap.add(typeListMap);
/*     */     } 
/* 535 */     Object[] attackArrays = attackVList.toArray();
/* 536 */     attackVObject.put("attackVKeyList", listMap);
/* 537 */     attackVObject.put("attackVValList", attackArrays);
/*     */     
/* 539 */     Gson gson = new Gson();
/* 540 */     return gson.toJson(attackVObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String ForAttackDatajson() {
/* 549 */     Map<String, Object> attackTypeObject = new HashMap<String, Object>();
/* 550 */     if (overviewMap != null && overviewMap.size() > 0) {
/* 551 */       String[] typeKeyList = new String[overviewMap.size()];
/* 552 */       List<Map> listMap = new ArrayList<Map>();
/* 553 */       int i = 0;
/* 554 */       for (Map.Entry<String, Long> entry : overviewMap.entrySet()) {
/*     */         
/* 556 */         typeKeyList[i] = entry.getKey();
/*     */         
/* 558 */         HashMap<String, Object> typeListMap = new HashMap<String, Object>();
/* 559 */         typeListMap.put("name", entry.getKey());
/* 560 */         typeListMap.put("value", entry.getValue());
/* 561 */         listMap.add(typeListMap);
/* 562 */         i++;
/*     */       } 
/* 564 */       attackTypeObject.put("typeKeyList", typeKeyList);
/* 565 */       attackTypeObject.put("typeList", listMap);
/*     */     } 
/*     */     
/* 568 */     Gson gson = new Gson();
/* 569 */     return gson.toJson(attackTypeObject);
/*     */   }
/*     */   
/*     */   public static void sortAttlist() {
/* 573 */     for (Map.Entry<String, AttackEntity> entry : attackMaps.entrySet()) {
/* 574 */       attackList.add(entry.getValue());
/*     */     }
/* 576 */     Collections.sort(attackList, (Comparator<? super AttackEntity>)new AttackEntityComparator());
/*     */   }
/*     */   
/*     */   public static void sortCClist() {
/* 580 */     Collections.sort(ccList, (Comparator<? super CCEntity>)new CCEntityComparator());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<StatBean> GetCcIPRegionList(NumberFormat intformat, NumberFormat performat) {
/* 591 */     Long ccCount = new Long(ccList.size());
/*     */     
/* 593 */     List<StatBean> ccIPRegionList = new ArrayList<StatBean>();
/*     */     
/* 595 */     for (int i = 0; i < ccIPList.size(); i++) {
/* 596 */       CommBean cb = ccIPList.get(i);
/* 597 */       IPRegion region = IPUtil.getIPRegionLocal(cb.getName());
/* 598 */       if (region != null) {
/*     */ 
/*     */ 
/*     */         
/* 602 */         String ipRegion = region.getCountry();
/* 603 */         if (region.getCountry().indexOf("中国") > -1) {
/* 604 */           if (region.getCity() != null && !region.getProvince().equals(region.getCity())) {
/* 605 */             ipRegion = ipRegion + "-" + region.getProvince() + region.getCity();
/*     */           } else {
/* 607 */             ipRegion = ipRegion + "-" + region.getProvince();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 612 */         StatBean sb = new StatBean();
/* 613 */         sb.setIp(cb.getName());
/* 614 */         sb.setCountry(ipRegion);
/* 615 */         sb.setVisit(cb.getCount());
/* 616 */         sb.setVisitStr(intformat.format(cb.getCount()));
/* 617 */         sb.setVisitRate(performat.format(Long.valueOf(cb.getCount()).longValue() / ccCount.longValue()));
/*     */         
/* 619 */         ccIPRegionList.add(sb);
/*     */       } 
/*     */     } 
/* 622 */     return ccIPRegionList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<StatBean> GetAttackIPRegionList(NumberFormat intformat, NumberFormat performat) {
/* 634 */     Long attackCount = new Long(AttackReportCache.attackCount.intValue());
/* 635 */     List<StatBean> attackIPRegionList = new ArrayList<StatBean>();
/* 636 */     for (int i = 0; i < attackIPList.size(); i++) {
/* 637 */       CommBean cb = attackIPList.get(i);
/* 638 */       IPRegion region = IPUtil.getIPRegionLocal(cb.getName());
/* 639 */       if (region != null) {
/*     */ 
/*     */ 
/*     */         
/* 643 */         String ipRegion = region.getCountry();
/* 644 */         if (region.getCountry().indexOf("中国") > -1) {
/* 645 */           if (region.getCity() != null && !region.getProvince().equals(region.getCity())) {
/* 646 */             ipRegion = ipRegion + "-" + region.getProvince() + region.getCity();
/*     */           } else {
/* 648 */             ipRegion = ipRegion + "-" + region.getProvince();
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 653 */         StatBean sb = new StatBean();
/* 654 */         sb.setIp(cb.getName());
/* 655 */         sb.setCountry(ipRegion);
/* 656 */         sb.setVisit(cb.getCount());
/* 657 */         sb.setVisitStr(intformat.format(cb.getCount()));
/* 658 */         sb.setVisitRate(performat.format(Long.valueOf(cb.getCount()).longValue() / attackCount.longValue()));
/*     */         
/* 660 */         attackIPRegionList.add(sb);
/*     */       } 
/* 662 */     }  Collections.sort(attackIPRegionList, (Comparator<? super StatBean>)new StatBeanComparator());
/* 663 */     return attackIPRegionList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<CommBean> GetAttackCountryRegionList(NumberFormat intformat) {
/* 675 */     Map<String, Integer> attackCountryMap = new HashMap<String, Integer>();
/* 676 */     for (int i = 0; i < attackIPList.size(); i++) {
/* 677 */       CommBean cb = attackIPList.get(i);
/* 678 */       IPRegion region = IPUtil.getIPRegionLocal(cb.getName());
/* 679 */       if (region != null) {
/*     */ 
/*     */ 
/*     */         
/* 683 */         int ipCC = 0;
/* 684 */         if (ccIPMap.containsKey(cb.getName())) {
/* 685 */           ipCC = ((Integer)ccIPMap.get(cb.getName())).intValue();
/* 686 */           ccIPMap.remove(cb.getName());
/*     */         } 
/*     */         
/* 689 */         int count = cb.getCount() + ipCC;
/*     */ 
/*     */         
/* 692 */         if (region.getCountry().indexOf("局域网") <= -1 && region.getCountry().indexOf("骨干网") <= -1)
/*     */         {
/*     */           
/* 695 */           if (attackCountryMap.containsKey(region.getCountry())) {
/* 696 */             attackCountryMap.put(region.getCountry(), Integer.valueOf(((Integer)attackCountryMap.get(region.getCountry())).intValue() + count));
/*     */           } else {
/* 698 */             attackCountryMap.put(region.getCountry(), Integer.valueOf(count));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 704 */     for (Map.Entry<String, Integer> entry : ccIPMap.entrySet()) {
/* 705 */       IPRegion region = IPUtil.getIPRegionLocal(entry.getKey());
/* 706 */       if (region == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 710 */       int count = ((Integer)entry.getValue()).intValue();
/*     */ 
/*     */       
/* 713 */       if (region.getCountry().indexOf("局域网") > -1 || region.getCountry().indexOf("骨干网") > -1) {
/*     */         continue;
/*     */       }
/* 716 */       if (attackCountryMap.containsKey(region.getCountry())) {
/* 717 */         attackCountryMap.put(region.getCountry(), Integer.valueOf(((Integer)attackCountryMap.get(region.getCountry())).intValue() + count)); continue;
/*     */       } 
/* 719 */       attackCountryMap.put(region.getCountry(), Integer.valueOf(count));
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 725 */     List<CommBean> attackCountryRegionSortList = new ArrayList<CommBean>();
/* 726 */     for (Map.Entry<String, Integer> entry : attackCountryMap.entrySet()) {
/* 727 */       attackCountryRegionSortList.add(new CommBean(entry.getKey(), ((Integer)entry.getValue()).intValue()));
/*     */     }
/* 729 */     attackCountryMap.clear();
/* 730 */     Collections.sort(attackCountryRegionSortList, (Comparator<? super CommBean>)new CommBeanComparator());
/*     */ 
/*     */     
/* 733 */     List<CommBean> attackCountryRegionList = new ArrayList<CommBean>();
/* 734 */     for (CommBean bean : attackCountryRegionSortList) {
/* 735 */       attackCountryRegionList.add(new CommBean(bean.getName(), intformat.format(bean.getCount())));
/*     */     }
/* 737 */     attackCountryRegionSortList.clear();
/*     */     
/* 739 */     return attackCountryRegionList;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\AttackReportCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */