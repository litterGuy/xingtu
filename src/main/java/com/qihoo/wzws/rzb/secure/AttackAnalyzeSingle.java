
package com.qihoo.wzws.rzb.secure;


import com.qihoo.wzws.rzb.secure.po.AttackEntity;
import com.qihoo.wzws.rzb.secure.po.LogEntity;
import com.qihoo.wzws.rzb.util.ConfigUtil;
import com.qihoo.wzws.rzb.util.MD5Builder;
import com.qihoo.wzws.rzb.util.UAUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AttackAnalyzeSingle {

    public void execute(LogEntity log) {

        long start = System.currentTimeMillis();


        if (log == null) {

            return;

        }


        AttackReportCache.records.incrementAndGet();


        String remote_addr = log.getIp();

        String time_local = log.getTime();

        String host = log.getHost();

        String request_uri = log.getRequestUrl();

        String status = log.getResponseCode();


        try {

            String xingtu_pagetype_particular, xingtu_httpcode_particular;


            switch (((Integer) ConfigUtil.m_config.get("xingtu_pagetype")).intValue()) {


                case 2:

                    if (request_uri.contains(".html") || request_uri.contains(".js") || request_uri.contains(".css") || request_uri.contains(".jpg") || request_uri.contains(".png") || request_uri.contains(".gif")) {

                        return;

                    }

                    break;


                case 3:

                    xingtu_pagetype_particular = (String) ConfigUtil.formatConfig.get("xingtu_pagetype_particular");

                    if (xingtu_pagetype_particular != null && xingtu_pagetype_particular.length() >= 3) {

                        String[] xingtu_pagetype_particulars = xingtu_pagetype_particular.split(",");

                        boolean isContains = false;

                        for (String s : xingtu_pagetype_particulars) {

                            if (request_uri.contains(s)) {

                                isContains = true;

                                break;

                            }

                        }

                        if (!isContains) {

                            return;

                        }

                        break;

                    }

                    if (request_uri.contains(".php") || request_uri.contains(".aspx")) {

                        break;

                    }

                    return;

            }


            switch (((Integer) ConfigUtil.m_config.get("xingtu_httpcode")).intValue()) {


                case 2:

                    if (status.equals("200")) {

                        break;

                    }

                    return;


                case 3:

                    xingtu_httpcode_particular = (String) ConfigUtil.formatConfig.get("xingtu_httpcode_particular");

                    if (xingtu_httpcode_particular != null && xingtu_httpcode_particular.length() >= 3) {

                        String[] xingtu_httpcode_particulars = xingtu_httpcode_particular.split(",");

                        boolean isContains = false;

                        for (String s : xingtu_httpcode_particulars) {

                            if (status.contains(s)) {

                                isContains = true;

                                break;

                            }

                        }

                        if (!isContains) {

                            return;

                        }

                        break;

                    }

                    if (status.equals("302") || status.equals("502")) {

                        break;

                    }

                    return;

            }


            switch (((Integer) ConfigUtil.m_config.get("xingtu_urltype")).intValue()) {


                case 2:

                    if (request_uri.contains("?")) {

                        break;

                    }

                    return;


                case 3:

                    if (!request_uri.contains("?")) {

                        break;

                    }

                    return;

            }


            if (request_uri.length() >= 512) {


                ReportOutput.writeFile(new String(host + "\t" + "超长URL" + "\t" + remote_addr + "\t" + request_uri + "\t" + time_local + "\t" + status + "\t" + '\001'));

                AttackReportCache.longUrlCount.incrementAndGet();


                return;

            }


            if (AnalyzeSingle.useCC && AttackReportCache.records.get() > 0L && AttackReportCache.records.get() % 500000L == 0L) {


                System.gc();

            }


            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            for (String rule : ConfigUtil.rules) {

                String[] ritems = rule.split(":", 2);

                if (ritems.length == 2) {

                    String rname = ritems[0];

                    String rfeatures = ritems[1];

                    String[] features = rfeatures.split("\\|");

                    for (String feature : features) {

                        if (request_uri.contains(feature)) {


                            AttackEntity entity = new AttackEntity(host, rname, remote_addr, request_uri.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;"), time_local, status, 1);

                            AttackReportCache.put2AttMap(MD5Builder.getMD5String(new String(host + remote_addr + request_uri.replaceAll("&", "&amp;").replaceAll(">", "&gt;").replaceAll("<", "&lt;").replaceAll("\"", "&quot;") + time_local + status)), entity);


                            if (AnalyzeSingle.useRoutine) {

                                AttackReportCache.attackCount.incrementAndGet();

                                AttackReportCache.put2OverViewMap(rname);

                                String[] day = time_local.split(" ");

                                String[] hour = time_local.split(":");

                                AttackReportCache.put2dayAttackMap(day[0]);

                                AttackReportCache.put2hourAttackMap(hour[0]);


                                Date attacktime = dateFormat.parse(time_local);

                                if (AttackReportCache.startTime == null || AttackReportCache.startTime.after(attacktime)) {

                                    AttackReportCache.startTime = attacktime;

                                    AttackReportCache.firstAttack = entity;

                                }

                                if (AttackReportCache.endTime == null || AttackReportCache.endTime.before(attacktime)) {

                                    AttackReportCache.endTime = attacktime;

                                }

                            }


                            return;

                        }

                    }

                }

            }


            if (log.getUa() != null) {

                String ua = log.getUa();

                for (String feature : UAUtils.uaList) {

                    if (ua.contains(feature)) {


                        AttackEntity entity = new AttackEntity(host, "非法UA", remote_addr, request_uri, time_local, status, 1);

                        AttackReportCache.put2AttMap(MD5Builder.getMD5String(new String(host + remote_addr + request_uri + time_local + status)), entity);

                        AttackReportCache.attackCount.incrementAndGet();

                        AttackReportCache.put2OverViewMap("非法UA");


                        return;

                    }

                }

            }

        } catch (Exception ex) {

            ex.printStackTrace();

            return;

        }

        long then = System.currentTimeMillis();

    }

}