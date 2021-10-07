
package com.qihoo.wzws.rzb.routine;


import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;
import com.qihoo.wzws.rzb.routine.po.StatBean;
import com.qihoo.wzws.rzb.secure.po.LogEntity;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.MD5Builder;
import com.qihoo.wzws.rzb.util.Utils;
import com.qihoo.wzws.rzb.util.keyword.KWUtils;
import com.qihoo.wzws.rzb.util.osbrowser.UserAgent;
import com.qihoo.wzws.rzb.util.osbrowser.UserAgentParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class RoutineAnalyze {

    public void execute(LogEntity log) {

        if (RoutineReportCache.visits > 0 && RoutineReportCache.visits % 500000 == 0) {

            System.out.println(String.valueOf(DateUtil.formatDateTime()) + ":已处理" + RoutineReportCache.visits + "条访问日志...");

        }


        RoutineReportCache.visits++;

        RoutineReportCache.totalband += log.getContentLength();


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String[] day = log.getTime().split(" ");


        try {

            if (!RoutineReportCache.dataFrom.contains(dateFormat.parse(day[0]))) {

                RoutineReportCache.dataFrom.add(dateFormat.parse(day[0]));

            }

        } catch (ParseException e) {


            e.printStackTrace();

        }


        if (RoutineReportCache.ipStaMap.containsKey(log.getIp())) {

            StatBean ipStatBean = RoutineReportCache.ipStaMap.get(log.getIp());

            ipStatBean.setBand(ipStatBean.getBand() + log.getContentLength());

            ipStatBean.setVisit(ipStatBean.getVisit() + 1);


            RoutineReportCache.ipStaMap.put(log.getIp(), ipStatBean);

        } else {


            StatBean ipStatBean = new StatBean();

            ipStatBean.setIp(log.getIp());

            ipStatBean.setBand(log.getContentLength());

            ipStatBean.setVisit(1);


            RoutineReportCache.ipStaMap.put(log.getIp(), ipStatBean);

        }


        if (Utils.isPage(log.getRequestUrl())) {

            if ("200".equals(log.getResponseCode())) {

                RoutineReportCache.PV++;

            }


            StatBean statBean = RoutineReportCache.pageVisitMap.get(log.getRequestUrl());

            if (statBean != null) {

                statBean.setVisit(statBean.getVisit() + 1);

                statBean.setBand(statBean.getBand() + log.getContentLength());

            } else {

                statBean = new StatBean();

                statBean.setUrl(log.getRequestUrl());

                statBean.setVisit(1);

                statBean.setBand(log.getContentLength());

                RoutineReportCache.pageVisitMap.put(log.getRequestUrl(), statBean);

            }


        } else {


            StatBean statBean = RoutineReportCache.staticPageVisitMap.get(log.getRequestUrl());

            if (statBean != null) {

                statBean.setVisit(statBean.getVisit() + 1);

                statBean.setBand(statBean.getBand() + log.getContentLength());

            } else {

                statBean = new StatBean();

                statBean.setUrl(log.getRequestUrl());

                statBean.setVisit(1);

                statBean.setBand(log.getContentLength());

                RoutineReportCache.staticPageVisitMap.put(log.getRequestUrl(), statBean);

            }

        }


        if ("404".equals(log.getResponseCode())) {

            StatBean statBean = RoutineReportCache._404pageVisitMap.get(log.getRequestUrl());

            if (statBean != null) {

                statBean.setVisit(statBean.getVisit() + 1);

                statBean.setBand(statBean.getBand() + log.getContentLength());

            } else {

                statBean = new StatBean();

                statBean.setUrl(log.getRequestUrl());

                statBean.setVisit(1);

                statBean.setBand(log.getContentLength());

                RoutineReportCache._404pageVisitMap.put(log.getRequestUrl(), statBean);

            }

        }


        if (!"-".equals(log.getUa())) {

            String key = MD5Builder.getMD5String(new String(String.valueOf(log.getIp()) + log.getUa()));

            StatBean statBean = RoutineReportCache.UVMap.get(key);

            if (statBean == null) {


                statBean = new StatBean();


                statBean.setVisit(1);


                RoutineReportCache.UVMap.put(key, statBean);


                UserAgent ua = UserAgentParser.getUA(log.getUa());

                if (ua != null && ua.getPlatform() != null && ua.getBrowser() != null) {


                    RoutineReportCache.osCount++;


                    String osKey = ua.getPlatform();

                    if (ua.getOsVersion() != null) {

                        osKey = String.valueOf(osKey) + ua.getOsVersion();

                    }

                    Integer osCount = RoutineReportCache.osMap.get(osKey);

                    if (osCount != null) {

                        RoutineReportCache.osMap.put(osKey, Integer.valueOf(osCount.intValue() + 1));

                    } else {

                        RoutineReportCache.osMap.put(osKey, Integer.valueOf(1));

                    }


                    Integer browerCount = RoutineReportCache.browserMap.get(ua.getBrowser());

                    if (browerCount != null) {

                        RoutineReportCache.browserMap.put(ua.getBrowser(), Integer.valueOf(browerCount.intValue() + 1));

                    } else {

                        RoutineReportCache.browserMap.put(ua.getBrowser(), Integer.valueOf(1));

                    }

                }


                if (!log.getIp().equals("-")) {

                    String countryKey = Utils.getIPRegionLocal(log.getIp());

                    Integer countryCount = RoutineReportCache.countryMap.get(countryKey);

                    if (countryCount != null) {

                        RoutineReportCache.countryMap.put(countryKey, Integer.valueOf(countryCount.intValue() + 1));

                    } else {

                        RoutineReportCache.countryMap.put(countryKey, Integer.valueOf(1));

                    }

                }

            }


            String soKey = "";

            if (log.getUa().toLowerCase().contains("googlebot")) {

                soKey = "googlebot";

            } else if (log.getUa().toLowerCase().contains("360spider")) {

                soKey = "qh360bot";

            } else if (log.getUa().toLowerCase().contains("baiduspider")) {

                soKey = "baidubot";

            } else if (log.getUa().toLowerCase().contains("sogou")) {

                soKey = "sogou";

            } else if (log.getUa().toLowerCase().contains("bing")) {

                soKey = "bingbot";

            } else if (log.getUa().toLowerCase().contains("slurp")) {

                soKey = "yahoobot";

            } else if (log.getUa().toLowerCase().contains("sosospider")) {

                soKey = "sosobot";

            } else if (log.getUa().toLowerCase().contains("bingbot")) {

                soKey = "bingbot";

            }


            if (soKey.length() > 0) {

                if (RoutineReportCache.soVisitMap.containsKey(soKey)) {

                    StatBean soStatBean = RoutineReportCache.soVisitMap.get(soKey);

                    soStatBean.setVisit(soStatBean.getVisit() + 1);

                    soStatBean.setBand(soStatBean.getBand() + log.getContentLength());

                } else {

                    StatBean soStatBean = new StatBean();

                    soStatBean.setVisit(1);

                    soStatBean.setUrl((String) Utils.sosuoMap.get(soKey));

                    soStatBean.setBand(log.getContentLength());

                    RoutineReportCache.soVisitMap.put(soKey, soStatBean);

                }

            }

        }


        if (!"-".equals(log.getReferer())) {

            String refererKey = "";


            if (log.getReferer().startsWith("http://") || log.getReferer().startsWith("https://")) {

                int index = log.getReferer().indexOf("?");

                if (index > 0 && index <= log.getReferer().length() - 1) {

                    refererKey = log.getReferer().substring(0, index);

                } else {

                    refererKey = log.getReferer();

                }


                Integer refererCount = RoutineReportCache.refererMap.get(refererKey);

                if (refererCount != null) {

                    RoutineReportCache.refererMap.put(refererKey, Integer.valueOf(refererCount.intValue() + 1));

                } else {

                    RoutineReportCache.refererMap.put(refererKey, Integer.valueOf(1));

                }


                SearchKeyWordBean kwBean = KWUtils.getKWAndSE(log.getReferer());

                if (kwBean != null) {

                    SearchKeyWordBean bean = RoutineReportCache.keyWordsMap.get(String.valueOf(kwBean.getSe()) + "@" + kwBean.getKw());

                    if (bean != null) {

                        bean.setCount(bean.getCount() + 1);

                    } else {

                        bean = new SearchKeyWordBean();

                        bean.setSe(kwBean.getSe());

                        bean.setKw(kwBean.getKw());

                        bean.setCount(1);

                        RoutineReportCache.keyWordsMap.put(String.valueOf(kwBean.getSe()) + "@" + kwBean.getKw(), bean);

                    }

                }

            }

        }


        if (log.getResponseCode().startsWith("2")) {

            RoutineReportCache.stat2XXcount++;

        } else if (log.getResponseCode().startsWith("3")) {

            RoutineReportCache.stat3XXcount++;

        } else if (log.getResponseCode().startsWith("4")) {

            RoutineReportCache.stat4XXcount++;

        } else if (log.getResponseCode().startsWith("5")) {

            RoutineReportCache.stat5XXcount++;

        }

    }

}