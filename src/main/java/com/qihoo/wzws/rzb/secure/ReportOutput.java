
package com.qihoo.wzws.rzb.secure;


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


public class ReportOutput {

    private static String outputBasePath;

    public static String getOutputBasePath() {
        return outputBasePath;
    }

    public static void setOutputBasePath(String outputBasePath) {
        ReportOutput.outputBasePath = outputBasePath;
    }

    public static void outputAttackHtml(String projectPath) {

        try {

            String ccCounts, cc_ip, fromDate;

            if (AttackReportCache.startTime == null) {

                return;

            }


            Properties properties = new Properties();

            properties.setProperty("ISO-8859-1", "UTF-8");

            properties.setProperty("input.encoding", "UTF-8");

            properties.setProperty("output.encoding", "UTF-8");


            if (AnalyzeSingle.isJarExecute) {


                properties.setProperty("resource.loader", "jar");


                properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");


                properties.setProperty("jar.resource.loader.path", "jar:file:" + AnalyzeSingle.jarPath);

            } else {


                properties.setProperty("resource.loader", "class");


                properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            }


            VelocityEngine velocityEngine = new VelocityEngine(properties);


            Template template = velocityEngine.getTemplate("com/qihoo/wzws/rzb/template/security-data.html", "UTF-8");


            VelocityContext context = new VelocityContext();


            NumberFormat performat = NumberFormat.getPercentInstance();

            performat.setMinimumFractionDigits(3);


            NumberFormat intformat = NumberFormat.getNumberInstance();

            intformat.setMaximumFractionDigits(0);


            NumberFormat decimalformat = NumberFormat.getNumberInstance();

            decimalformat.setRoundingMode(RoundingMode.HALF_UP);

            decimalformat.setMaximumFractionDigits(2);


            AttackReportCache.initData();


            int attackCounts = AttackReportCache.attackCount.intValue();

            if (AnalyzeSingle.useCC) {

                ccCounts = intformat.format(AttackReportCache.ccCount);

                cc_ip = intformat.format(AttackReportCache.ccIPList.size());

            } else {

                ccCounts = "-";

                cc_ip = "-";

            }


            int attackTypeSum = AttackReportCache.getattackTypeSum();

            int visits = RoutineReportCache.visits;


            int UIP = AttackReportCache.attackIPList.size() + AttackReportCache.ccIPList.size();


            if (AttackReportCache.dataFrom.size() > 1) {

                fromDate = DateUtil.formatDate(RoutineReportCache.dataFrom.get(0)) + "至" + DateUtil.formatDate(RoutineReportCache.dataFrom.get(RoutineReportCache.dataFrom.size() - 1));

            } else {

                fromDate = DateUtil.formatDate(RoutineReportCache.dataFrom.get(0));

            }

            String attackDatajson = AttackReportCache.ForAttackDatajson();

            String allDaysDetailsjson = AttackReportCache.ForallDaysDetailsjson();

            String attackVObjectjson = AttackReportCache.ForattackVObjectjson();


            List<CommBean> attackCountryRegionList = AttackReportCache.GetAttackCountryRegionList(intformat);

            List<StatBean> attackIPRegionList = AttackReportCache.GetAttackIPRegionList(intformat, performat);

            List<StatBean> ccIPRegionList = AttackReportCache.GetCcIPRegionList(intformat, performat);


            Date startTime = AttackReportCache.startTime;

            Date endTime = AttackReportCache.endTime;

            int longs = DateUtil.diffDate(11, startTime, endTime);

            int days = longs / 24;

            int hours = longs % 24;

            String longsStr = "";

            if (days > 0) {

                longsStr = String.valueOf(days) + "天";

            }

            if (hours > 0) {

                longsStr = longsStr + String.valueOf(hours) + "小时";

            }


            context.put("datetime", DateUtil.formatDateTime());

            context.put("attackCounts", intformat.format(attackCounts));

            context.put("ccCounts", ccCounts);

            context.put("uip", intformat.format(UIP));

            context.put("attackTypeSum", intformat.format(attackTypeSum));

            context.put("Visits", intformat.format(visits));

            context.put("fromDate", fromDate);


            context.put("attackDatajson", attackDatajson);

            context.put("allDaysDetailsjson", allDaysDetailsjson);

            context.put("attackVObjectjson", attackVObjectjson);


            context.put("attackCountryRegionList", (attackCountryRegionList.size() > 200) ? attackCountryRegionList.subList(0, 200) : attackCountryRegionList);

            context.put("attackIPRegionList", (attackIPRegionList.size() > 200) ? attackIPRegionList.subList(0, 200) : attackIPRegionList);

            context.put("ccIPRegionList", (ccIPRegionList.size() > 200) ? ccIPRegionList.subList(0, 200) : ccIPRegionList);

            context.put("ccList", (AttackReportCache.ccList.size() > 200) ? AttackReportCache.ccList.subList(0, 200) : AttackReportCache.ccList);


            context.put("attack_ip", intformat.format(AttackReportCache.attackIPList.size()));

            context.put("cc_ip", cc_ip);


            context.put("attackTypeMap", AttackReportCache.overviewMap);

            context.put("allAttackTypeList", AttackReportCache.allAttackTypelist);


            context.put("evt_hostSum", intformat.format(1L));

            context.put("evt_datePeriod", intformat.format(AttackReportCache.dataFrom.size()));

            if (AnalyzeSingle.useCC && AttackReportCache.ccCount.intValue() != 0) {

                context.put("evt_cc", AttackReportCache.getCcEvt(intformat));

                context.put("evt_allAttackSum", intformat.format((AttackReportCache.attackCount.intValue() + AttackReportCache.ccCount.intValue())));

            } else {

                context.put("evt_allAttackSum", intformat.format(AttackReportCache.attackCount.intValue()));

            }


            context.put("evt_attackLongs", longsStr);

            context.put("evt_attackEndTime", DateUtil.formatDateTime(endTime));

            context.put("evt_attack", AttackReportCache.getAttackEvt(intformat));


            PrintWriter pw = new PrintWriter(getReportattackFilePath(), "UTF-8");

            template.merge((Context) context, pw);


            pw.close();

            // copy需要的js文件夹
            File srcFile = new File(projectPath + File.separator + "bin" + File.separator + "js");
            File destFile = new File(getReportDir() + File.separator);
            FileUtils.copyDirectoryToDirectory(srcFile, destFile);
        } catch (ResourceNotFoundException e) {

            e.printStackTrace();

        } catch (ParseErrorException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public static void outputHtml() {

        try {

            Properties properties = new Properties();

            properties.setProperty("ISO-8859-1", "UTF-8");

            properties.setProperty("input.encoding", "UTF-8");

            properties.setProperty("output.encoding", "UTF-8");


            if (AnalyzeSingle.isJarExecute) {


                properties.setProperty("resource.loader", "jar");


                properties.setProperty("jar.resource.loader.class", "org.apache.velocity.runtime.resource.loader.JarResourceLoader");


                properties.setProperty("jar.resource.loader.path", "jar:file:" + AnalyzeSingle.jarPath);

            } else {


                properties.setProperty("resource.loader", "class");


                properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

            }


            VelocityEngine velocityEngine = new VelocityEngine(properties);


            Template template = velocityEngine.getTemplate("com/qihoo/wzws/rzb/template/black360report.html", "UTF-8");


            VelocityContext context = new VelocityContext();


            NumberFormat performat = NumberFormat.getPercentInstance();

            performat.setMinimumFractionDigits(3);


            NumberFormat intformat = NumberFormat.getNumberInstance();

            intformat.setMaximumFractionDigits(0);


            NumberFormat decimalformat = NumberFormat.getNumberInstance();

            decimalformat.setRoundingMode(RoundingMode.HALF_UP);

            decimalformat.setMaximumFractionDigits(2);


            context.put("datetime", DateUtil.formatDateTime());


            int UV = RoutineReportCache.UVMap.size();

            long BAND = RoutineReportCache.totalband;

            int VISITS = RoutineReportCache.visits;

            int UIP = RoutineReportCache.ipStaMap.size();

            long LOGSIZE = RoutineReportCache.logSize;


            context.put("Visits", intformat.format(VISITS));

            context.put("PV", intformat.format(RoutineReportCache.PV));

            context.put("CUV", Integer.valueOf(UV));

            if (UV > 0) {

                context.put("UV", intformat.format(UV));

            }


            context.put("UIP", intformat.format(UIP));

            if (BAND > 0L) {

                if (BAND <= 1048576L) {

                    context.put("Band", decimalformat.format(BAND / 1024.0D) + " K");

                } else if (BAND >= 1073741824L) {

                    context.put("Band", decimalformat.format(BAND / 1024.0D / 1024.0D / 1024.0D) + " G");

                } else {

                    context.put("Band", decimalformat.format(BAND / 1024.0D / 1024.0D) + " M");

                }

            }


            context.put("Errors", intformat.format((RoutineReportCache.stat4XXcount + RoutineReportCache.stat5XXcount)));


            if (LOGSIZE > 0L) {

                if (LOGSIZE <= 1048576L) {

                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D) + " K");

                } else if (LOGSIZE >= 1073741824L) {

                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D / 1024.0D / 1024.0D) + " G");

                } else {

                    context.put("LogSize", decimalformat.format(LOGSIZE / 1024.0D / 1024.0D) + " M");

                }

            }


            List<StatBean> ipVisitsSortList = new ArrayList<StatBean>();

            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.ipStaMap.entrySet()) {

                StatBean iPStatBean = entry.getValue();

                if (iPStatBean.getBand() > 0L) {

                    if (iPStatBean.getBand() <= 1048576L) {

                        iPStatBean.setBandStr(decimalformat.format(iPStatBean.getBand() / 1024.0D) + " K");

                    } else {

                        iPStatBean.setBandStr(decimalformat.format(iPStatBean.getBand() / 1024.0D / 1024.0D) + " M");

                    }

                    iPStatBean.setBandRate(performat.format(iPStatBean.getBand() / BAND));


                    if (iPStatBean.getBand() / BAND > 0.5D) {

                        iPStatBean.setBandflag(1);

                    }

                }


                iPStatBean.setVisitStr(intformat.format(iPStatBean.getVisit()));

                iPStatBean.setVisitRate(performat.format(iPStatBean.getVisit() / VISITS));

                if (iPStatBean.getVisit() / VISITS > 0.5D) {

                    iPStatBean.setVisitflag(1);

                }


                iPStatBean.setCountry(Utils.getIPRegionLocal(iPStatBean.getIp()));

                ipVisitsSortList.add(entry.getValue());

            }

            Collections.sort(ipVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());

            RoutineReportCache.ipStaMap.clear();

            if (ipVisitsSortList.size() > 200) {

                ipVisitsSortList = ipVisitsSortList.subList(0, 200);

            }

            context.put("ipVisitsSortList", ipVisitsSortList);


            List<StatBean> pageVisitsSortList = new ArrayList<StatBean>();

            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.pageVisitMap.entrySet()) {

                StatBean statBean = entry.getValue();

                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);

                pageVisitsSortList.add(statBean);

            }

            Collections.sort(pageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());

            RoutineReportCache.pageVisitMap.clear();

            if (pageVisitsSortList.size() > 200) {

                pageVisitsSortList = pageVisitsSortList.subList(0, 200);

            }

            context.put("pageVisitsSortList", pageVisitsSortList);


            List<StatBean> staticPageVisitsSortList = new ArrayList<StatBean>();

            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.staticPageVisitMap.entrySet()) {

                StatBean statBean = entry.getValue();

                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);

                staticPageVisitsSortList.add(statBean);

            }

            Collections.sort(staticPageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());

            RoutineReportCache.staticPageVisitMap.clear();

            if (staticPageVisitsSortList.size() > 200) {

                staticPageVisitsSortList = staticPageVisitsSortList.subList(0, 200);

            }

            context.put("staticPageVisitsSortList", staticPageVisitsSortList);


            List<StatBean> _404PageVisitsSortList = new ArrayList<StatBean>();

            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache._404pageVisitMap.entrySet()) {

                StatBean statBean = entry.getValue();

                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);

                _404PageVisitsSortList.add(statBean);

            }

            Collections.sort(_404PageVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());

            RoutineReportCache._404pageVisitMap.clear();

            if (_404PageVisitsSortList.size() > 200) {

                _404PageVisitsSortList = _404PageVisitsSortList.subList(0, 200);

            }

            context.put("_404PageVisitsSortList", _404PageVisitsSortList);


            List<CommonStatBean> referersVisitsSortList = new ArrayList<CommonStatBean>();

            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.refererMap.entrySet()) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey(entry.getKey());

                statBean.setCount(((Integer) entry.getValue()).intValue());

                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / VISITS));

                statBean.setCountStr(intformat.format(statBean.getCount()));


                if (statBean.getCount() / VISITS > 0.5D) {

                    statBean.setFlag(1);

                }


                referersVisitsSortList.add(statBean);

            }

            Collections.sort(referersVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());

            RoutineReportCache.refererMap.clear();

            if (referersVisitsSortList.size() > 200) {

                referersVisitsSortList = referersVisitsSortList.subList(0, 200);

            }

            context.put("referersVisitsSortList", referersVisitsSortList);


            List<StatBean> soVisitsSortList = new ArrayList<StatBean>();

            for (Map.Entry<String, StatBean> entry : (Iterable<Map.Entry<String, StatBean>>) RoutineReportCache.soVisitMap.entrySet()) {

                StatBean statBean = entry.getValue();

                bandCompute(statBean, performat, decimalformat, BAND, VISITS, intformat);

                soVisitsSortList.add(statBean);

            }

            Collections.sort(soVisitsSortList, (Comparator<? super StatBean>) new StatBeanComparator());

            RoutineReportCache.soVisitMap.clear();

            if (soVisitsSortList.size() > 200) {

                soVisitsSortList = soVisitsSortList.subList(0, 200);

            }

            context.put("soVisitsSortList", soVisitsSortList);


            List<SearchKeyWordBean> keywordSortList = new ArrayList<SearchKeyWordBean>();

            for (Map.Entry<String, SearchKeyWordBean> entry : (Iterable<Map.Entry<String, SearchKeyWordBean>>) RoutineReportCache.keyWordsMap.entrySet()) {

                SearchKeyWordBean kwBean = entry.getValue();

                kwBean.setCountStr(intformat.format(kwBean.getCount()));


                keywordSortList.add(kwBean);

            }

            Collections.sort(keywordSortList, (Comparator<? super SearchKeyWordBean>) new SearchKeyWordComparator());

            RoutineReportCache.keyWordsMap.clear();

            if (keywordSortList.size() > 10) {

                keywordSortList = keywordSortList.subList(0, 10);

            }

            context.put("keywordSortList", keywordSortList);


            List<CommonStatBean> regionVisitsSortList = new ArrayList<CommonStatBean>();

            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.countryMap.entrySet()) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey(entry.getKey());

                statBean.setCount(((Integer) entry.getValue()).intValue());

                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / UV));

                statBean.setCountStr(intformat.format(statBean.getCount()));


                if (statBean.getCount() / UV > 0.5D) {

                    statBean.setFlag(1);

                }


                regionVisitsSortList.add(statBean);

            }

            Collections.sort(regionVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());

            RoutineReportCache.countryMap.clear();

            if (regionVisitsSortList.size() > 200) {

                regionVisitsSortList = regionVisitsSortList.subList(0, 200);

            }

            context.put("regionVisitsSortList", regionVisitsSortList);


            List<CommonStatBean> pcOSVisitsSortList = new ArrayList<CommonStatBean>();

            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.osMap.entrySet()) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey(entry.getKey());

                statBean.setCount(((Integer) entry.getValue()).intValue());

                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / RoutineReportCache.osCount));

                statBean.setCountStr(intformat.format(statBean.getCount()));


                if (statBean.getCount() / UV > 0.5D) {

                    statBean.setFlag(1);

                }


                pcOSVisitsSortList.add(statBean);

            }

            Collections.sort(pcOSVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());

            RoutineReportCache.osMap.clear();

            if (pcOSVisitsSortList.size() > 200) {

                pcOSVisitsSortList = pcOSVisitsSortList.subList(0, 200);

            }

            context.put("pcOSVisitsSortList", pcOSVisitsSortList);


            List<CommonStatBean> pcBrowserVisitsSortList = new ArrayList<CommonStatBean>();

            for (Map.Entry<String, Integer> entry : (Iterable<Map.Entry<String, Integer>>) RoutineReportCache.browserMap.entrySet()) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey(entry.getKey());

                statBean.setCount(((Integer) entry.getValue()).intValue());

                statBean.setRate(performat.format(((Integer) entry.getValue()).intValue() / RoutineReportCache.osCount));

                statBean.setCountStr(intformat.format(statBean.getCount()));


                if (statBean.getCount() / UV > 0.5D) {

                    statBean.setFlag(1);

                }


                pcBrowserVisitsSortList.add(statBean);

            }

            Collections.sort(pcBrowserVisitsSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());

            RoutineReportCache.browserMap.clear();

            if (pcBrowserVisitsSortList.size() > 200) {

                pcBrowserVisitsSortList = pcBrowserVisitsSortList.subList(0, 200);

            }

            context.put("pcBrowserVisitsSortList", pcBrowserVisitsSortList);


            List<CommonStatBean> httpStatusSortList = new ArrayList<CommonStatBean>();

            if (RoutineReportCache.stat2XXcount > 0) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey("2xx 成功");

                statBean.setCount(RoutineReportCache.stat2XXcount);

                statBean.setRate(performat.format(RoutineReportCache.stat2XXcount / VISITS));

                statBean.setCountStr(intformat.format(statBean.getCount()));

                httpStatusSortList.add(statBean);

            }


            if (RoutineReportCache.stat3XXcount > 0) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey("3xx 重定向");

                statBean.setCount(RoutineReportCache.stat3XXcount);

                statBean.setRate(performat.format(RoutineReportCache.stat3XXcount / VISITS));

                statBean.setCountStr(intformat.format(statBean.getCount()));

                httpStatusSortList.add(statBean);

            }


            if (RoutineReportCache.stat4XXcount > 0) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey("4xx 客户端请求错误");

                statBean.setCount(RoutineReportCache.stat4XXcount);

                statBean.setRate(performat.format(RoutineReportCache.stat4XXcount / VISITS));

                statBean.setCountStr(intformat.format(statBean.getCount()));

                httpStatusSortList.add(statBean);

            }


            if (RoutineReportCache.stat5XXcount > 0) {

                CommonStatBean statBean = new CommonStatBean();

                statBean.setKey("5xx 服务器内部错误");

                statBean.setCount(RoutineReportCache.stat5XXcount);

                statBean.setRate(performat.format(RoutineReportCache.stat5XXcount / VISITS));

                statBean.setCountStr(intformat.format(statBean.getCount()));

                httpStatusSortList.add(statBean);

            }

            Collections.sort(httpStatusSortList, (Comparator<? super CommonStatBean>) new CommonStatBeanComparator());

            context.put("httpStatusSortList", httpStatusSortList);


            PrintWriter pw = new PrintWriter(getReportFilePath(), "UTF-8");

            template.merge((Context) context, pw);


            pw.close();

        } catch (ResourceNotFoundException e) {

            e.printStackTrace();

        } catch (ParseErrorException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    private static void bandCompute(StatBean statBean, NumberFormat performat, NumberFormat decimalformat, long BAND, int VISITS, NumberFormat intformat) {

        if (statBean.getBand() > 0L) {

            if (statBean.getBand() <= 1048576L) {

                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D) + " K");

            } else if (statBean.getBand() > 1073741824L) {

                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D / 1024.0D / 1024.0D) + " G");

            } else {

                statBean.setBandStr(decimalformat.format(statBean.getBand() / 1024.0D / 1024.0D) + " M");

            }

            statBean.setBandRate(performat.format(statBean.getBand() / BAND));


            if (statBean.getBand() / BAND > 0.5D) {

                statBean.setBandflag(1);

            }

        }


        statBean.setVisitRate(performat.format(statBean.getVisit() / VISITS));

        if (statBean.getVisit() / VISITS > 0.5D) {

            statBean.setVisitflag(1);

        }

        statBean.setVisitStr(intformat.format(statBean.getVisit()));

    }


    public static void outputFile(List<AttackEntity> attackList, List<CCEntity> ccList) {

        if (attackList.size() > 0) {

            File attFile = new File(getAttackFilePath());

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(attFile));

                for (AttackEntity entity : attackList) {

                    writer.write(entity.output());

                }

                writer.flush();

                writer.close();

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }


        if (ccList.size() > 0) {

            File ccFile = new File(getCCFilePath());

            try {

                BufferedWriter writer = new BufferedWriter(new FileWriter(ccFile));

                for (CCEntity entity : ccList) {

                    writer.write(entity.output());

                }

                writer.flush();

                writer.close();

            } catch (FileNotFoundException e) {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }


    public static String getReportDir() {

        return getOutputBasePath();

    }


    public static String getConfDir() {

        return AnalyzeSingle.basePath + File.separator + "conf" + File.separator;

    }


    public static File file = null;


    public static void writeFile(String data) {

        if (file == null) {

            file = new File(getLongUrlFilePath());

        }


        try {

            FileUtils.write(file, data + "\r\n", "utf-8", true);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public static String getLongUrlFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "可疑访问" + ".txt");

    }


    public static String getReportFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "常规分析报告" + ".html");

    }


    public static String getReportattackFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "安全分析报告" + ".html");

    }


    public static String getAttackFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "漏洞攻击" + ".txt");

    }


    public static String getBasicStatFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "日常统计" + ".txt");

    }


    public static String getRuleFilePath() {

        return new String(getConfDir() + "rules.ini");

    }


    public static String getCCFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "CC攻击" + ".txt");

    }


    public static String getOverViewFilePath() {

        return new String(getReportDir() + AnalyzeSingle.currentFile + "-" + AnalyzeSingle.currentTime + "-" + "分析概览" + ".txt");

    }


    public static boolean updateRuleFile(String rules) {

        String newRulePath = getRuleFilePath() + "." + System.currentTimeMillis();


        File newRuleFile = new File(newRulePath);


        if (newRuleFile != null) {

            String[] array = rules.split("\t");

            for (String s : array) {


                try {


                    FileUtils.write(newRuleFile, s + "\n", "utf-8", true);

                } catch (IOException e) {

                    e.printStackTrace();

                } catch (Exception e) {

                    e.printStackTrace();

                }

            }

        }


        File rule = new File(getRuleFilePath());


        if (newRuleFile != null && rule != null) {

            try {

                FileUtils.copyFile(newRuleFile, rule);

                newRuleFile.delete();

                return true;

            } catch (IOException e) {


                e.printStackTrace();

            }

        }


        return false;

    }


    public static void generateUid() {

        String configPath = AnalyzeSingle.basePath + File.separator + "conf" + File.separator + "config.ini";

        File configNew = new File(configPath);


        try {

            String s = Utils.getMac();

            if (s == null || s.length() != 17) {

                s = UUID.randomUUID().toString();

            }


            String sign = "sign:" + Base64.encodeBase64String(s.getBytes("utf-8"));


            FileUtils.write(configNew, sign + "\r\n", "utf-8", true);

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public static boolean updateRuleVersion(String newVersion) {

        String configPath = AnalyzeSingle.basePath + File.separator + "conf" + File.separator + "config.ini";


        File file = new File(configPath);

        List<String> lines = new ArrayList<String>();

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

            String line = null;

            boolean updateRuleVer = false;

            while ((line = reader.readLine()) != null) {

                if (!line.startsWith("rule_ver")) {

                    lines.add(line);
                    continue;

                }

                lines.add("rule_ver:" + newVersion);

                updateRuleVer = true;

            }


            if (!updateRuleVer) {

                lines.add("rule_ver:" + newVersion);

            }

            reader.close();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (reader != null) {

                try {

                    reader.close();

                } catch (IOException e1) {
                }

            }

        }


        File configNew = new File(configPath + ".new");

        try {

            for (String s : lines) {

                FileUtils.write(configNew, s + "\r\n", "utf-8", true);

            }


            FileUtils.copyFile(configNew, file);


            configNew.delete();


            return true;

        } catch (IOException e) {

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }


        return false;

    }


    public static void main(String[] args) {
    }

}