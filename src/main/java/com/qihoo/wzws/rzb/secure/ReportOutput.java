/*     */
package com.qihoo.wzws.rzb.secure;
/*     */
/*     */

import com.qihoo.wzws.rzb.routine.RoutineReportCache;
import com.qihoo.wzws.rzb.routine.po.*;
import com.qihoo.wzws.rzb.secure.po.AttackEntity;
import com.qihoo.wzws.rzb.secure.po.CCEntity;
import com.qihoo.wzws.rzb.secure.po.CommBean;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.Utils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

import java.io.*;
import java.math.RoundingMode;
import java.text.NumberFormat;
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
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class ReportOutput
        /*     */ {
    /*     */
    public static void outputAttackHtml() {
        /*     */
        try {
            /*     */
            String ccCounts, cc_ip, fromDate;
            /*  56 */
            if (AttackReportCache.startTime == null) {
                /*     */
                return;
                /*     */
            }
            /*     */
            /*  60 */
            Properties properties = new Properties();
            /*  61 */
            properties.setProperty("ISO-8859-1", "UTF-8");
            /*  62 */
            properties.setProperty("input.encoding", "UTF-8");
            /*  63 */
            properties.setProperty("output.encoding", "UTF-8");
            /*     */
            /*  65 */
            if (AnalyzeSingle.isJarExecute) {
                /*     */
                /*  67 */
                properties.setProperty("resource.loader", "jar");
                /*     */
                /*  69 */
                properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
                /*     */
                /*  71 */
                properties.setProperty("jar.resource.loader.path", "jar:file:" + AnalyzeSingle.jarPath);
                /*     */
            } else {
                /*     */
                /*  74 */
                properties.setProperty("resource.loader", "class");
                /*     */
                /*  76 */
                properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*  81 */
            VelocityEngine velocityEngine = new VelocityEngine(properties);
            /*     */
            /*  83 */
            Template template = velocityEngine.getTemplate("com/qihoo/wzws/rzb/template/security-data.html", "UTF-8");
            /*     */
            /*  85 */
            VelocityContext context = new VelocityContext();
            /*     */
            /*     */
            /*     */
            /*  89 */
            NumberFormat performat = NumberFormat.getPercentInstance();
            /*  90 */
            performat.setMinimumFractionDigits(3);
            /*     */
            /*  92 */
            NumberFormat intformat = NumberFormat.getNumberInstance();
            /*  93 */
            intformat.setMaximumFractionDigits(0);
            /*     */
            /*  95 */
            NumberFormat decimalformat = NumberFormat.getNumberInstance();
            /*  96 */
            decimalformat.setRoundingMode(RoundingMode.HALF_UP);
            /*  97 */
            decimalformat.setMaximumFractionDigits(2);
            /*     */
            /*  99 */
            AttackReportCache.initData();
            /*     */
            /*     */
            /*     */
            /* 103 */
            int attackCounts = AttackReportCache.attackCount.intValue();
            /* 104 */
            if (AnalyzeSingle.useCC) {
                /* 105 */
                ccCounts = intformat.format(AttackReportCache.ccCount);
                /* 106 */
                cc_ip = intformat.format(AttackReportCache.ccIPList.size());
                /*     */
            } else {
                /* 108 */
                ccCounts = "-";
                /* 109 */
                cc_ip = "-";
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*     */
            /* 115 */
            int attackTypeSum = AttackReportCache.getattackTypeSum();
            /* 116 */
            int visits = RoutineReportCache.visits;
            /*     */
            /* 118 */
            int UIP = AttackReportCache.attackIPList.size() + AttackReportCache.ccIPList.size();
            /*     */
            /*     */
            /* 121 */
            if (AttackReportCache.dataFrom.size() > 1) {
                /* 122 */
                fromDate = DateUtil.formatDate(RoutineReportCache.dataFrom.get(0)) + "至" + DateUtil.formatDate(RoutineReportCache.dataFrom.get(RoutineReportCache.dataFrom.size() - 1));
                /*     */
            } else {
                /* 124 */
                fromDate = DateUtil.formatDate(RoutineReportCache.dataFrom.get(0));
                /*     */
            }
            /* 126 */
            String attackDatajson = AttackReportCache.ForAttackDatajson();
            /* 127 */
            String allDaysDetailsjson = AttackReportCache.ForallDaysDetailsjson();
            /* 128 */
            String attackVObjectjson = AttackReportCache.ForattackVObjectjson();
            /*     */
            /* 130 */
            List<CommBean> attackCountryRegionList = AttackReportCache.GetAttackCountryRegionList(intformat);
            /* 131 */
            List<StatBean> attackIPRegionList = AttackReportCache.GetAttackIPRegionList(intformat, performat);
            /* 132 */
            List<StatBean> ccIPRegionList = AttackReportCache.GetCcIPRegionList(intformat, performat);
            /*     */
            /*     */
            /* 135 */
            Date startTime = AttackReportCache.startTime;
            /* 136 */
            Date endTime = AttackReportCache.endTime;
            /* 137 */
            int longs = DateUtil.diffDate(11, startTime, endTime);
            /* 138 */
            int days = longs / 24;
            /* 139 */
            int hours = longs % 24;
            /* 140 */
            String longsStr = "";
            /* 141 */
            if (days > 0) {
                /* 142 */
                longsStr = String.valueOf(days) + "天";
                /*     */
            }
            /* 144 */
            if (hours > 0) {
                /* 145 */
                longsStr = longsStr + String.valueOf(hours) + "小时";
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */
            /* 152 */
            context.put("datetime", DateUtil.formatDateTime());
            /* 153 */
            context.put("attackCounts", intformat.format(attackCounts));
            /* 154 */
            context.put("ccCounts", ccCounts);
            /* 155 */
            context.put("uip", intformat.format(UIP));
            /* 156 */
            context.put("attackTypeSum", intformat.format(attackTypeSum));
            /* 157 */
            context.put("Visits", intformat.format(visits));
            /* 158 */
            context.put("fromDate", fromDate);
            /*     */
            /* 160 */
            context.put("attackDatajson", attackDatajson);
            /* 161 */
            context.put("allDaysDetailsjson", allDaysDetailsjson);
            /* 162 */
            context.put("attackVObjectjson", attackVObjectjson);
            /*     */
            /* 164 */
            context.put("attackCountryRegionList", (attackCountryRegionList.size() > 200) ? attackCountryRegionList.subList(0, 200) : attackCountryRegionList);
            /* 165 */
            context.put("attackIPRegionList", (attackIPRegionList.size() > 200) ? attackIPRegionList.subList(0, 200) : attackIPRegionList);
            /* 166 */
            context.put("ccIPRegionList", (ccIPRegionList.size() > 200) ? ccIPRegionList.subList(0, 200) : ccIPRegionList);
            /* 167 */
            context.put("ccList", (AttackReportCache.ccList.size() > 200) ? AttackReportCache.ccList.subList(0, 200) : AttackReportCache.ccList);
            /*     */
            /* 169 */
            context.put("attack_ip", intformat.format(AttackReportCache.attackIPList.size()));
            /* 170 */
            context.put("cc_ip", cc_ip);
            /*     */
            /* 172 */
            context.put("attackTypeMap", AttackReportCache.overviewMap);
            /* 173 */
            context.put("allAttackTypeList", AttackReportCache.allAttackTypelist);
            /*     */
            /* 175 */
            context.put("evt_hostSum", intformat.format(1L));
            /* 176 */
            context.put("evt_datePeriod", intformat.format(AttackReportCache.dataFrom.size()));
            /* 177 */
            if (AnalyzeSingle.useCC && AttackReportCache.ccCount.intValue() != 0) {
                /* 178 */
                context.put("evt_cc", AttackReportCache.getCcEvt(intformat));
                /* 179 */
                context.put("evt_allAttackSum", intformat.format((AttackReportCache.attackCount.intValue() + AttackReportCache.ccCount.intValue())));
                /*     */
            } else {
                /* 181 */
                context.put("evt_allAttackSum", intformat.format(AttackReportCache.attackCount.intValue()));
                /*     */
            }
            /*     */
            /* 184 */
            context.put("evt_attackLongs", longsStr);
            /* 185 */
            context.put("evt_attackEndTime", DateUtil.formatDateTime(endTime));
            /* 186 */
            context.put("evt_attack", AttackReportCache.getAttackEvt(intformat));
            /*     */
            /*     */
            /*     */
            /*     */
            /* 191 */
            PrintWriter pw = new PrintWriter(getReportattackFilePath(), "UTF-8");
            /* 192 */
            template.merge((Context) context, pw);
            /*     */
            /*     */
            /* 195 */
            pw.close();
            /* 196 */
        } catch (ResourceNotFoundException e) {
            /* 197 */
            e.printStackTrace();
            /* 198 */
        } catch (ParseErrorException e) {
            /* 199 */
            e.printStackTrace();
            /* 200 */
        } catch (Exception e) {
            /* 201 */
            e.printStackTrace();
            /*     */
        }
        /*     */
    }

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
    public static void outputHtml() {
        /*     */
        try {
            /* 216 */
            Properties properties = new Properties();
            /* 217 */
            properties.setProperty("ISO-8859-1", "UTF-8");
            /* 218 */
            properties.setProperty("input.encoding", "UTF-8");
            /* 219 */
            properties.setProperty("output.encoding", "UTF-8");
            /*     */
            /* 221 */
            if (AnalyzeSingle.isJarExecute) {
                /*     */
                /* 223 */
                properties.setProperty("resource.loader", "jar");
                /*     */
                /* 225 */
                properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");
                /*     */
                /* 227 */
                properties.setProperty("jar.resource.loader.path", "jar:file:" + AnalyzeSingle.jarPath);
                /*     */
            } else {
                /*     */
                /* 230 */
                properties.setProperty("resource.loader", "class");
                /*     */
                /* 232 */
                properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /* 237 */
            VelocityEngine velocityEngine = new VelocityEngine(properties);
            /*     */
            /* 239 */
            Template template = velocityEngine.getTemplate("com/qihoo/wzws/rzb/template/black360report.html", "UTF-8");
            /*     */
            /* 241 */
            VelocityContext context = new VelocityContext();
            /*     */
            /*     */
            /*     */
            /* 245 */
            NumberFormat performat = NumberFormat.getPercentInstance();
            /* 246 */
            performat.setMinimumFractionDigits(3);
            /*     */
            /* 248 */
            NumberFormat intformat = NumberFormat.getNumberInstance();
            /* 249 */
            intformat.setMaximumFractionDigits(0);
            /*     */
            /* 251 */
            NumberFormat decimalformat = NumberFormat.getNumberInstance();
            /* 252 */
            decimalformat.setRoundingMode(RoundingMode.HALF_UP);
            /* 253 */
            decimalformat.setMaximumFractionDigits(2);
            /*     */
            /* 255 */
            context.put("datetime", DateUtil.formatDateTime());
            /*     */
            /*     */
            /* 258 */
            int UV = RoutineReportCache.UVMap.size();
            /* 259 */
            long BAND = RoutineReportCache.totalband;
            /* 260 */
            int VISITS = RoutineReportCache.visits;
            /* 261 */
            int UIP = RoutineReportCache.ipStaMap.size();
            /* 262 */
            long LOGSIZE = RoutineReportCache.logSize;
            /*     */
            /* 264 */
            context.put("Visits", intformat.format(VISITS));
            /* 265 */
            context.put("PV", intformat.format(RoutineReportCache.PV));
            /* 266 */
            context.put("CUV", Integer.valueOf(UV));
            /* 267 */
            if (UV > 0) {
                /* 268 */
                context.put("UV", intformat.format(UV));
                /*     */
            }
            /*     */
            /* 271 */
            context.put("UIP", intformat.format(UIP));
            /* 272 */
            if (BAND > 0L) {
                /* 273 */
                if (BAND <= 1048576L) {
                    /* 274 */
                    context.put("Band", decimalformat.format(BAND / 1024.0D) + " K");
                    /* 275 */
                } else if (BAND >= 1073741824L) {
                    /* 276 */
                    context.put("Band", decimalformat.format(BAND / 1024.0D / 1024.0D / 1024.0D) + " G");
                    /*     */
                } else {
                    /* 278 */
                    context.put("Band", decimalformat.format(BAND / 1024.0D / 1024.0D) + " M");
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 283 */
            context.put("Errors", intformat.format((RoutineReportCache.stat4XXcount + RoutineReportCache.stat5XXcount)));
            /*     */
            /*     */
            /* 286 */
            if (LOGSIZE > 0L) {
                /* 287 */
                if (LOGSIZE <= 1048576L) {
                    /* 288 */
                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D) + " K");
                    /* 289 */
                } else if (LOGSIZE >= 1073741824L) {
                    /* 290 */
                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D / 1024.0D / 1024.0D) + " G");
                    /*     */
                } else {
                    /* 292 */
                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D / 1024.0D) + " M");
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 297 */
            List<StatBean> ipVisitsSortList = new ArrayList<StatBean>();
            /* 298 */
            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.ipStaMap.entrySet()) {
                /* 299 */
                StatBean iPStatBean = entry.getValue();
                /* 300 */
                if (iPStatBean.getBand() > 0L) {
                    /* 301 */
                    if (iPStatBean.getBand() <= 1048576L) {
                        /* 302 */
                        iPStatBean.setBandStr(decimalformat.format(iPStatBean.getBand() / 1024.0D) + " K");
                        /*     */
                    } else {
                        /* 304 */
                        iPStatBean.setBandStr(decimalformat.format(iPStatBean.getBand() / 1024.0D / 1024.0D) + " M");
                        /*     */
                    }
                    /* 306 */
                    iPStatBean.setBandRate(performat.format(iPStatBean.getBand() / BAND));
                    /*     */
                    /* 308 */
                    if (iPStatBean.getBand() / BAND > 0.5D) {
                        /* 309 */
                        iPStatBean.setBandflag(1);
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /* 313 */
                iPStatBean.setVisitStr(intformat.format(iPStatBean.getVisit()));
                /* 314 */
                iPStatBean.setVisitRate(performat.format(iPStatBean.getVisit() / VISITS));
                /* 315 */
                if (iPStatBean.getVisit() / VISITS > 0.5D) {
                    /* 316 */
                    iPStatBean.setVisitflag(1);
                    /*     */
                }
                /*     */
                /* 319 */
                iPStatBean.setCountry(Utils.getIPRegionLocal(iPStatBean.getIp()));
                /* 320 */
                ipVisitsSortList.add(entry.getValue());
                /*     */
            }
            /* 322 */
            Collections.sort(ipVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());
            /* 323 */
            RoutineReportCache.ipStaMap.clear();
            /* 324 */
            if (ipVisitsSortList.size() > 200) {
                /* 325 */
                ipVisitsSortList = ipVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 327 */
            context.put("ipVisitsSortList", ipVisitsSortList);
            /*     */
            /*     */
            /*     */
            /*     */
            /* 332 */
            List<StatBean> pageVisitsSortList = new ArrayList<StatBean>();
            /* 333 */
            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.pageVisitMap.entrySet()) {
                /* 334 */
                StatBean statBean = entry.getValue();
                /* 335 */
                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);
                /* 336 */
                pageVisitsSortList.add(statBean);
                /*     */
            }
            /* 338 */
            Collections.sort(pageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());
            /* 339 */
            RoutineReportCache.pageVisitMap.clear();
            /* 340 */
            if (pageVisitsSortList.size() > 200) {
                /* 341 */
                pageVisitsSortList = pageVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 343 */
            context.put("pageVisitsSortList", pageVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 347 */
            List<StatBean> staticPageVisitsSortList = new ArrayList<StatBean>();
            /* 348 */
            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.staticPageVisitMap.entrySet()) {
                /* 349 */
                StatBean statBean = entry.getValue();
                /* 350 */
                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);
                /* 351 */
                staticPageVisitsSortList.add(statBean);
                /*     */
            }
            /* 353 */
            Collections.sort(staticPageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());
            /* 354 */
            RoutineReportCache.staticPageVisitMap.clear();
            /* 355 */
            if (staticPageVisitsSortList.size() > 200) {
                /* 356 */
                staticPageVisitsSortList = staticPageVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 358 */
            context.put("staticPageVisitsSortList", staticPageVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 362 */
            List<StatBean> _404PageVisitsSortList = new ArrayList<StatBean>();
            /* 363 */
            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache._404pageVisitMap.entrySet()) {
                /* 364 */
                StatBean statBean = entry.getValue();
                /* 365 */
                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);
                /* 366 */
                _404PageVisitsSortList.add(statBean);
                /*     */
            }
            /* 368 */
            Collections.sort(_404PageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());
            /* 369 */
            RoutineReportCache._404pageVisitMap.clear();
            /* 370 */
            if (_404PageVisitsSortList.size() > 200) {
                /* 371 */
                _404PageVisitsSortList = _404PageVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 373 */
            context.put("_404PageVisitsSortList", _404PageVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 377 */
            List<CommonStatBean> referersVisitsSortList = new ArrayList<CommonStatBean>();
            /* 378 */
            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.refererMap.entrySet()) {
                /* 379 */
                CommonStatBean statBean = new CommonStatBean();
                /* 380 */
                statBean.setKey(entry.getKey());
                /* 381 */
                statBean.setCount(((Integer) entry.getValue()).intValue());
                /* 382 */
                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / VISITS));
                /* 383 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /*     */
                /* 385 */
                if (statBean.getCount() / VISITS > 0.5D) {
                    /* 386 */
                    statBean.setFlag(1);
                    /*     */
                }
                /*     */
                /* 389 */
                referersVisitsSortList.add(statBean);
                /*     */
            }
            /* 391 */
            Collections.sort(referersVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());
            /* 392 */
            RoutineReportCache.refererMap.clear();
            /* 393 */
            if (referersVisitsSortList.size() > 200) {
                /* 394 */
                referersVisitsSortList = referersVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 396 */
            context.put("referersVisitsSortList", referersVisitsSortList);
            /*     */
            /*     */
            /* 399 */
            List<StatBean> soVisitsSortList = new ArrayList<StatBean>();
            /* 400 */
            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.soVisitMap.entrySet()) {
                /* 401 */
                StatBean statBean = entry.getValue();
                /* 402 */
                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);
                /* 403 */
                soVisitsSortList.add(statBean);
                /*     */
            }
            /* 405 */
            Collections.sort(soVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());
            /* 406 */
            RoutineReportCache.soVisitMap.clear();
            /* 407 */
            if (soVisitsSortList.size() > 200) {
                /* 408 */
                soVisitsSortList = soVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 410 */
            context.put("soVisitsSortList", soVisitsSortList);
            /*     */
            /*     */
            /* 413 */
            List<SearchKeyWordBean> keywordSortList = new ArrayList<SearchKeyWordBean>();
            /* 414 */
            for (Map.Entry<String, SearchKeyWordBean> entry : (Iterable<Map.Entry<String, SearchKeyWordBean>>) RoutineReportCache.keyWordsMap.entrySet()) {
                /* 415 */
                SearchKeyWordBean kwBean = entry.getValue();
                /* 416 */
                kwBean.setCountStr(intformat.format(kwBean.getCount()));
                /*     */
                /* 418 */
                keywordSortList.add(kwBean);
                /*     */
            }
            /* 420 */
            Collections.sort(keywordSortList, (Comparator<? super SearchKeyWordBean>) new SearchKeyWordComparator());
            /* 421 */
            RoutineReportCache.keyWordsMap.clear();
            /* 422 */
            if (keywordSortList.size() > 10) {
                /* 423 */
                keywordSortList = keywordSortList.subList(0, 10);
                /*     */
            }
            /* 425 */
            context.put("keywordSortList", keywordSortList);
            /*     */
            /*     */
            /*     */
            /*     */
            /* 430 */
            List<CommonStatBean> regionVisitsSortList = new ArrayList<CommonStatBean>();
            /* 431 */
            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.countryMap.entrySet()) {
                /* 432 */
                CommonStatBean statBean = new CommonStatBean();
                /* 433 */
                statBean.setKey(entry.getKey());
                /* 434 */
                statBean.setCount(((Integer) entry.getValue()).intValue());
                /* 435 */
                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / UV));
                /* 436 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /*     */
                /* 438 */
                if (statBean.getCount() / UV > 0.5D) {
                    /* 439 */
                    statBean.setFlag(1);
                    /*     */
                }
                /*     */
                /* 442 */
                regionVisitsSortList.add(statBean);
                /*     */
            }
            /* 444 */
            Collections.sort(regionVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());
            /* 445 */
            RoutineReportCache.countryMap.clear();
            /* 446 */
            if (regionVisitsSortList.size() > 200) {
                /* 447 */
                regionVisitsSortList = regionVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 449 */
            context.put("regionVisitsSortList", regionVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 453 */
            List<CommonStatBean> pcOSVisitsSortList = new ArrayList<CommonStatBean>();
            /* 454 */
            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.osMap.entrySet()) {
                /* 455 */
                CommonStatBean statBean = new CommonStatBean();
                /* 456 */
                statBean.setKey(entry.getKey());
                /* 457 */
                statBean.setCount(((Integer) entry.getValue()).intValue());
                /* 458 */
                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / RoutineReportCache.osCount));
                /* 459 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /*     */
                /* 461 */
                if (statBean.getCount() / UV > 0.5D) {
                    /* 462 */
                    statBean.setFlag(1);
                    /*     */
                }
                /*     */
                /* 465 */
                pcOSVisitsSortList.add(statBean);
                /*     */
            }
            /* 467 */
            Collections.sort(pcOSVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());
            /* 468 */
            RoutineReportCache.osMap.clear();
            /* 469 */
            if (pcOSVisitsSortList.size() > 200) {
                /* 470 */
                pcOSVisitsSortList = pcOSVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 472 */
            context.put("pcOSVisitsSortList", pcOSVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 476 */
            List<CommonStatBean> pcBrowserVisitsSortList = new ArrayList<CommonStatBean>();
            /* 477 */
            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.browserMap.entrySet()) {
                /* 478 */
                CommonStatBean statBean = new CommonStatBean();
                /* 479 */
                statBean.setKey(entry.getKey());
                /* 480 */
                statBean.setCount(((Integer) entry.getValue()).intValue());
                /* 481 */
                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / RoutineReportCache.osCount));
                /* 482 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /*     */
                /* 484 */
                if (statBean.getCount() / UV > 0.5D) {
                    /* 485 */
                    statBean.setFlag(1);
                    /*     */
                }
                /*     */
                /* 488 */
                pcBrowserVisitsSortList.add(statBean);
                /*     */
            }
            /* 490 */
            Collections.sort(pcBrowserVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());
            /* 491 */
            RoutineReportCache.browserMap.clear();
            /* 492 */
            if (pcBrowserVisitsSortList.size() > 200) {
                /* 493 */
                pcBrowserVisitsSortList = pcBrowserVisitsSortList.subList(0, 200);
                /*     */
            }
            /* 495 */
            context.put("pcBrowserVisitsSortList", pcBrowserVisitsSortList);
            /*     */
            /*     */
            /*     */
            /* 499 */
            List<CommonStatBean> httpStatusSortList = new ArrayList<CommonStatBean>();
            /* 500 */
            if (RoutineReportCache.stat2XXcount > 0) {
                /* 501 */
                CommonStatBean statBean = new CommonStatBean();
                /* 502 */
                statBean.setKey("2xx 成功");
                /* 503 */
                statBean.setCount(RoutineReportCache.stat2XXcount);
                /* 504 */
                statBean.setRate(performat.format(RoutineReportCache.stat2XXcount / VISITS));
                /* 505 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /* 506 */
                httpStatusSortList.add(statBean);
                /*     */
            }
            /*     */
            /* 509 */
            if (RoutineReportCache.stat3XXcount > 0) {
                /* 510 */
                CommonStatBean statBean = new CommonStatBean();
                /* 511 */
                statBean.setKey("3xx 重定向");
                /* 512 */
                statBean.setCount(RoutineReportCache.stat3XXcount);
                /* 513 */
                statBean.setRate(performat.format(RoutineReportCache.stat3XXcount / VISITS));
                /* 514 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /* 515 */
                httpStatusSortList.add(statBean);
                /*     */
            }
            /*     */
            /* 518 */
            if (RoutineReportCache.stat4XXcount > 0) {
                /* 519 */
                CommonStatBean statBean = new CommonStatBean();
                /* 520 */
                statBean.setKey("4xx 客户端请求错误");
                /* 521 */
                statBean.setCount(RoutineReportCache.stat4XXcount);
                /* 522 */
                statBean.setRate(performat.format(RoutineReportCache.stat4XXcount / VISITS));
                /* 523 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /* 524 */
                httpStatusSortList.add(statBean);
                /*     */
            }
            /*     */
            /* 527 */
            if (RoutineReportCache.stat5XXcount > 0) {
                /* 528 */
                CommonStatBean statBean = new CommonStatBean();
                /* 529 */
                statBean.setKey("5xx 服务器内部错误");
                /* 530 */
                statBean.setCount(RoutineReportCache.stat5XXcount);
                /* 531 */
                statBean.setRate(performat.format(RoutineReportCache.stat5XXcount / VISITS));
                /* 532 */
                statBean.setCountStr(intformat.format(statBean.getCount()));
                /* 533 */
                httpStatusSortList.add(statBean);
                /*     */
            }
            /* 535 */
            Collections.sort(httpStatusSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());
            /* 536 */
            context.put("httpStatusSortList", httpStatusSortList);
            /*     */
            /*     */
            /*     */
            /*     */
            /* 541 */
            PrintWriter pw = new PrintWriter(getReportFilePath(), "UTF-8");
            /* 542 */
            template.merge((Context) context, pw);
            /*     */
            /*     */
            /* 545 */
            pw.close();
            /* 546 */
        } catch (ResourceNotFoundException e) {
            /* 547 */
            e.printStackTrace();
            /* 548 */
        } catch (ParseErrorException e) {
            /* 549 */
            e.printStackTrace();
            /* 550 */
        } catch (Exception e) {
            /* 551 */
            e.printStackTrace();
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    private static void bandCompute(StatBean statBean, NumberFormat performat, NumberFormat decimalformat, long BAND, int VISITS, NumberFormat intformat) {
        /* 556 */
        if (statBean.getBand() > 0L) {
            /* 557 */
            if (statBean.getBand() <= 1048576L) {
                /* 558 */
                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D) + " K");
                /* 559 */
            } else if (statBean.getBand() > 1073741824L) {
                /* 560 */
                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D / 1024.0D / 1024.0D) + " G");
                /*     */
            } else {
                /* 562 */
                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D / 1024.0D) + " M");
                /*     */
            }
            /* 564 */
            statBean.setBandRate(performat.format(statBean.getBand() / BAND));
            /*     */
            /* 566 */
            if (statBean.getBand() / BAND > 0.5D) {
                /* 567 */
                statBean.setBandflag(1);
                /*     */
            }
            /*     */
        }
        /*     */
        /* 571 */
        statBean.setVisitRate(performat.format(statBean.getVisit() / VISITS));
        /* 572 */
        if (statBean.getVisit() / VISITS > 0.5D) {
            /* 573 */
            statBean.setVisitflag(1);
            /*     */
        }
        /* 575 */
        statBean.setVisitStr(intformat.format(statBean.getVisit()));
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static void outputFile(List<AttackEntity> attackList, List<CCEntity> ccList) {
        /* 585 */
        if (attackList.size() > 0) {
            /* 586 */
            File attFile = new File(getAttackFilePath());
            /*     */
            try {
                /* 588 */
                BufferedWriter writer = new BufferedWriter(new FileWriter(attFile));
                /* 589 */
                for (AttackEntity entity : attackList) {
                    /* 590 */
                    writer.write(entity.output());
                    /*     */
                }
                /* 592 */
                writer.flush();
                /* 593 */
                writer.close();
                /* 594 */
            } catch (FileNotFoundException e) {
                /* 595 */
                e.printStackTrace();
                /* 596 */
            } catch (IOException e) {
                /* 597 */
                e.printStackTrace();
                /*     */
            }
            /*     */
        }
        /*     */
        /* 601 */
        if (ccList.size() > 0) {
            /* 602 */
            File ccFile = new File(getCCFilePath());
            /*     */
            try {
                /* 604 */
                BufferedWriter writer = new BufferedWriter(new FileWriter(ccFile));
                /* 605 */
                for (CCEntity entity : ccList) {
                    /* 606 */
                    writer.write(entity.output());
                    /*     */
                }
                /* 608 */
                writer.flush();
                /* 609 */
                writer.close();
                /* 610 */
            } catch (FileNotFoundException e) {
                /* 611 */
                e.printStackTrace();
                /* 612 */
            } catch (IOException e) {
                /* 613 */
                e.printStackTrace();
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public static String getReportDir() {
        /* 619 */
        return AnalyzeSingle.basePath + File.separator + "result" + File.separator;
        /*     */
    }

    /*     */
    /*     */
    public static String getConfDir() {
        /* 623 */
        return AnalyzeSingle.basePath + File.separator + "conf" + File.separator;
        /*     */
    }

    /*     */
    /* 626 */   public static File file = null;

    /*     */
    public static void writeFile(String data) {
        /* 628 */
        if (file == null) {
            /* 629 */
            file = new File(getLongUrlFilePath());
            /*     */
        }
        /*     */
        /*     */
        try {
            /* 633 */
            FileUtils.write(file, data + "\r\n", "utf-8", true);
            /* 634 */
        } catch (IOException e) {
            /* 635 */
            e.printStackTrace();
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static String getLongUrlFilePath() {
        /* 641 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "可疑访问" + ".txt");
        /*     */
    }

    /*     */
    /*     */
    public static String getReportFilePath() {
        /* 645 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "常规分析报告" + ".html");
        /*     */
    }

    /*     */
    /*     */
    public static String getReportattackFilePath() {
        /* 649 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "安全分析报告" + ".html");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static String getAttackFilePath() {
        /* 654 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "漏洞攻击" + ".txt");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static String getBasicStatFilePath() {
        /* 659 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "日常统计" + ".txt");
        /*     */
    }

    /*     */
    /*     */
    public static String getRuleFilePath() {
        /* 663 */
        return new String(getConfDir() + "rules.ini");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static String getCCFilePath() {
        /* 668 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "CC攻击" + ".txt");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static String getOverViewFilePath() {
        /* 673 */
        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "分析概览" + ".txt");
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static boolean updateRuleFile(String rules) {
        /* 680 */
        String newRulePath = getRuleFilePath() + "." + System.currentTimeMillis();
        /*     */
        /* 682 */
        File newRuleFile = new File(newRulePath);
        /*     */
        /* 684 */
        if (newRuleFile != null) {
            /* 685 */
            String[] array = rules.split("\t");
            /* 686 */
            for (String s : array) {
                /*     */
                /*     */
                /*     */
                try {
                    /*     */
                    /* 691 */
                    FileUtils.write(newRuleFile, s + "\n", "utf-8", true);
                    /* 692 */
                } catch (IOException e) {
                    /* 693 */
                    e.printStackTrace();
                    /* 694 */
                } catch (Exception e) {
                    /* 695 */
                    e.printStackTrace();
                    /*     */
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /* 700 */
        File rule = new File(getRuleFilePath());
        /*     */
        /* 702 */
        if (newRuleFile != null && rule != null) {
            /*     */
            try {
                /* 704 */
                FileUtils.copyFile(newRuleFile, rule);
                /* 705 */
                newRuleFile.delete();
                /* 706 */
                return true;
                /*     */
            }
            /* 708 */ catch (IOException e) {
                /*     */
                /* 710 */
                e.printStackTrace();
                /*     */
            }
            /*     */
        }
        /*     */
        /* 714 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static void generateUid() {
        /* 724 */
        String configPath = AnalyzeSingle.basePath + File.separator + "conf" + File.separator + "config.ini";
        /* 725 */
        File configNew = new File(configPath);
        /*     */
        /*     */
        try {
            /* 728 */
            String s = Utils.getMac();
            /* 729 */
            if (s == null || s.length() != 17) {
                /* 730 */
                s = UUID.randomUUID().toString();
                /*     */
            }
            /*     */
            /*     */
            /* 734 */
            String sign = "sign:" + Base64.encodeBase64String(s.getBytes("utf-8"));
            /*     */
            /* 736 */
            FileUtils.write(configNew, sign + "\r\n", "utf-8", true);
            /*     */
        }
        /* 738 */ catch (IOException e) {
            /* 739 */
            e.printStackTrace();
            /* 740 */
        } catch (Exception e) {
            /* 741 */
            e.printStackTrace();
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    public static boolean updateRuleVersion(String newVersion) {
        /* 746 */
        String configPath = AnalyzeSingle.basePath + File.separator + "conf" + File.separator + "config.ini";
        /*     */
        /* 748 */
        File file = new File(configPath);
        /* 749 */
        List<String> lines = new ArrayList<String>();
        /* 750 */
        BufferedReader reader = null;
        /*     */
        try {
            /* 752 */
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            /* 753 */
            String line = null;
            /* 754 */
            boolean updateRuleVer = false;
            /* 755 */
            while ((line = reader.readLine()) != null) {
                /* 756 */
                if (!line.startsWith("rule_ver")) {
                    /* 757 */
                    lines.add(line);
                    continue;
                    /*     */
                }
                /* 759 */
                lines.add("rule_ver:" + newVersion);
                /* 760 */
                updateRuleVer = true;
                /*     */
            }
            /*     */
            /*     */
            /* 764 */
            if (!updateRuleVer) {
                /* 765 */
                lines.add("rule_ver:" + newVersion);
                /*     */
            }
            /* 767 */
            reader.close();
            /*     */
        }
        /* 769 */ catch (IOException e) {
            /* 770 */
            e.printStackTrace();
            /*     */
        } finally {
            /* 772 */
            if (reader != null) {
                /*     */
                try {
                    /* 774 */
                    reader.close();
                    /* 775 */
                } catch (IOException e1) {
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /*     */
        /* 780 */
        File configNew = new File(configPath + ".new");
        /*     */
        try {
            /* 782 */
            for (String s : lines)
                /*     */ {
                /* 784 */
                FileUtils.write(configNew, s + "\r\n", "utf-8", true);
                /*     */
            }
            /*     */
            /* 787 */
            FileUtils.copyFile(configNew, file);
            /*     */
            /* 789 */
            configNew.delete();
            /*     */
            /* 791 */
            return true;
            /*     */
        }
        /* 793 */ catch (IOException e) {
            /* 794 */
            e.printStackTrace();
            /* 795 */
        } catch (Exception e) {
            /* 796 */
            e.printStackTrace();
            /*     */
        }
        /*     */
        /*     */
        /* 800 */
        return false;
        /*     */
    }

    /*     */
    /*     */
    public static void main(String[] args) {
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\ReportOutput.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */