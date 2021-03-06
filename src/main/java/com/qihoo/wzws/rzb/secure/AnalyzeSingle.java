
package com.qihoo.wzws.rzb.secure;


import com.daima.common.exception.ErrorCode;
import com.daima.common.exception.NSException;
import com.qihoo.wzws.rzb.cc.CC;
import com.qihoo.wzws.rzb.cc.CCAlgorithm;
import com.qihoo.wzws.rzb.cc.CCDetail;
import com.qihoo.wzws.rzb.parse.AutomaticLogFormatParser;
import com.qihoo.wzws.rzb.routine.RoutineAnalyze;
import com.qihoo.wzws.rzb.routine.RoutineReportCache;
import com.qihoo.wzws.rzb.secure.po.LogEntity;
import com.qihoo.wzws.rzb.util.ConfigUtil;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.Utils;
import com.qihoo.wzws.rzb.util.ValidateConfig;
import com.qihoo.wzws.rzb.util.ip.IPDataHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class AnalyzeSingle {
    private static AnalyzeSingle instance;
    private static String globalHost = "";

    public static String currentFile = "";
    public static String currentTime = "";

    public static boolean useAttack = true;

    public static boolean useCC = false;
    public static boolean useRoutine = false;
    private RoutineAnalyze routine;
    private AttackAnalyzeSingle attack;
    private CCAnalyzeSingle cc;
    private String dir;


    public AnalyzeSingle(RoutineAnalyze routine, AttackAnalyzeSingle attack, CCAnalyzeSingle cc, String dir) {
        this.routine = routine;
        this.attack = attack;
        this.cc = cc;
        this.dir = dir;
    }


    public void execute() {
        String scheduleAnalysis = ConfigUtil.formatConfig.get("schedule_analysis");
        File fileDir = new File(this.dir);
        if (fileDir.isDirectory()) {
            File[] files = fileDir.listFiles();
            if (files.length >= 1) {
                if ("2".equals(scheduleAnalysis)) {
                    File file = Utils.getlastestFileFromDir(files);
                    RoutineReportCache.logSize += file.length();
                    currentTime = DateUtil.formatDateHour();
                    if (file.getName().indexOf(".") > -1) {
                        currentFile = file.getName().substring(0, file.getName().lastIndexOf("."));
                    } else {
                        currentFile = file.getName();
                    }
                    clearResultHistory();
                    System.out.println("??????????????????");
                    System.out.println("?????????????????????????????????" + file.getName() + ";??????????????????" + outputFileSize(file.length()));
                    HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
                    rsearch(file, allHost);
                    if (useCC) {
                        this.cc.execute(allHost);
                    }
                    System.gc();
                    allHost.clear();
                    allHost = null;
                } else {
                    currentTime = DateUtil.formatDateHour();
                    if (files[0].getName().indexOf(".") > -1) {
                        currentFile = files[0].getName().substring(0, files[0].getName().lastIndexOf("."));
                    } else {
                        currentFile = files[0].getName();
                    }
                    clearResultHistory();
                    for (File file : files) {
                        RoutineReportCache.logSize += file.length();
                        log.info("??????????????????" + file.getName() + ";??????????????????" + outputFileSize(file.length()));
                        HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
                        rsearch(file, allHost);
                        if (useCC) {
                            this.cc.execute(allHost);
                        }
                        allHost.clear();
                        allHost = null;
                        System.gc();
                    }
                }
            } else {
                log.error("????????????????????????????????????????????????");
                log.error("????????????????????????????????????????????????");
            }
        } else if (fileDir.isFile()) {
            RoutineReportCache.logSize = fileDir.length();
            currentTime = DateUtil.formatDateHour();
            if (fileDir.getName().indexOf(".") > -1) {
                currentFile = fileDir.getName().substring(0, fileDir.getName().lastIndexOf("."));
            } else {
                currentFile = fileDir.getName();
            }
            clearResultHistory();
            log.info("??????????????????" + fileDir.getName() + ";??????????????????" + outputFileSize(fileDir.length()));
            HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
            rsearch(fileDir, allHost);
            if (useCC) {
                this.cc.execute(allHost);
            }
            System.gc();
            allHost.clear();
            allHost = null;
        } else {
            System.err.println("???????????????????????????");
            System.out.println("????????????????????????????????????????????????");
        }
    }


    private static String outputFileSize(long length) {
        String size = "";
        if (length / 1024L / 1024L < 1L) {
            size = (length / 1024L) + "K";
        } else {
            size = (length / 1024L / 1024L) + "M";
        }
        return size;
    }


    private static void clearResultHistory() {
        File longf = new File(ReportOutput.getLongUrlFilePath());
        if (longf != null && longf.exists()) {
            longf.delete();
        }
        File attackf = new File(ReportOutput.getAttackFilePath());
        if (attackf != null && attackf.exists()) {
            attackf.delete();
        }
        File ccf = new File(ReportOutput.getCCFilePath());
        if (ccf != null && ccf.exists()) {
            ccf.delete();
        }
        File reportf = new File(ReportOutput.getReportFilePath());
        if (reportf != null && reportf.exists()) {
            reportf.delete();
        }
    }


    public synchronized static void run(String logPath, String outPath) throws NSException {
        log.info("=====?????????????????? =====");
        for (int i = 3; i > 0; i--) {
            try {
                Thread.currentThread();
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new NSException(new ErrorCode(1000, e.getMessage()));
            }
        }

        long start = System.currentTimeMillis();
        String binPath = (new File("")).getAbsolutePath();
        basePath = binPath;
        log.info("???????????????...");

        initPath();

        String sys = basePath + File.separator + "conf" + File.separator + "config.ini";
        String rules = basePath + File.separator + "conf" + File.separator + "rules.ini";
        String ipFilePath = basePath + File.separator + "bin" + File.separator + "ip.dat";
        File sysFile = new File(sys);
        if (sysFile == null || !sysFile.exists()) {
            log.error("conf/config.ini??????????????????: " + sys);
            throw new NSException(new ErrorCode(1000, "conf/config.ini???????????????"));
        }

        File rulesFile = new File(rules);
        if (rulesFile == null || !rulesFile.exists()) {
            log.error("conf/rule.ini??????????????????: " + rules);
            throw new NSException(new ErrorCode(1000, "conf/rule.ini???????????????"));
        }

        File ipFile = new File(ipFilePath);
        if (ipFile == null || !ipFile.exists()) {
            log.error("bin/ip.dat??????????????????: " + ipFilePath);
            throw new NSException(new ErrorCode(1000, "bin/ip.dat???????????????"));
        }

        if (!ValidateConfig.validateSysConf(new String[]{sys, rules, logPath})) {
            log.error("????????????????????????????????????????????????");
            throw new NSException(new ErrorCode(1000, "?????????????????????????????????"));
        }

        IPDataHandler.initIPData(ipFilePath);
        log.info("????????????????????????:" + sys);
        log.info("????????????????????????:" + rules);
        ValidateConfig.validateRuleConf(rules);
        long startM = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        log.info("?????????????????????????????????" + startM + "M");
        log.info("???????????????");
        log.info("---------------");

        if ("2".equals(ConfigUtil.formatConfig.get("common_analysis"))) {
            useRoutine = true;
        }
        if ("2".equals(ConfigUtil.formatConfig.get("cc_analysis"))) {
            CCAnalyzeSingle.initParas();
            useCC = true;
        }

        String tips = "[Web????????????";
        if (useRoutine) {
            tips = tips + "???????????????";
            if (useCC) {
                tips = tips + "???CC????????????";
            }
        } else if (useCC) {
            tips = tips + "???CC????????????";
        }
        tips = tips + "]";

        log.info("??????????????????????????????(??????1G??????????????????200???)...");
        log.info("---------------");
        ReportOutput.setOutputBasePath(outPath);
        File resultDir = new File(ReportOutput.getReportDir());
        if (!resultDir.exists()) {
            resultDir.mkdirs();
        }

        RoutineAnalyze routine = new RoutineAnalyze();
        AttackAnalyzeSingle attack = new AttackAnalyzeSingle();
        CCAnalyzeSingle cc = new CCAnalyzeSingle();
        instance = new AnalyzeSingle(routine, attack, cc, logPath);
        instance.execute();

        AttackReportCache.sortAttlist();
        if (useCC) {
            AttackReportCache.sortCClist();
        }
        ReportOutput.outputFile(AttackReportCache.attackList, AttackReportCache.ccList);

        long sum = AttackReportCache.attackCount.get() + AttackReportCache.longUrlCount.get();
        if (useCC) {
            sum += AttackReportCache.ccCount.get();
        }
        long end = System.currentTimeMillis();
        if (useRoutine) {
            System.gc();
            ReportOutput.outputHtml();
            ReportOutput.outputAttackHtml(basePath);
        }
        log.info("???????????????" + tips);
        long count = AttackReportCache.records.get();
        if (useRoutine) {
            count = RoutineReportCache.visits;
        }
        log.info("????????????" + count + "???????????????");
        long costTime = (end - start) / 1000L;
        if (costTime < 1L) {
            costTime = 1L;
        }
        log.info("????????????:" + costTime + "???");
        log.info("??????????????????" + sum + "???");
        if (useRoutine) {
            log.info("?????????????????????????????????");
        }
        log.info("---------------");
        log.info("?????????????????????[??????????????????result?????????]??????");
        log.info("=====?????????????????? =====");
    }


    private void rsearch(File file, HashMap<String, TreeMap<Long, CCDetail>> allHost) {
        TreeMap<Long, CCDetail> hostMap = null;
        BufferedReader reader = null;
        try {
            int CharBufferSize = 102400;
            if (file.length() > 209715200L) {
                CharBufferSize = 5242880;
            }
            reader = new BufferedReader(new FileReader(file), CharBufferSize);
            String tempString = null;
            int day = 0;
            while ((tempString = reader.readLine()) != null) {
                if (tempString.startsWith("#")) {
                    continue;
                }
                LogEntity log = AutomaticLogFormatParser.parse(tempString);
                if (log != null) {
                    if (log.getRequestUrl() == null || log.getRequestUrl().trim().length() == 0) {
                        continue;
                    }
                    if (useAttack) {
                        this.attack.execute(log);
                    }
                    if (useRoutine && log.getRequestUrl().length() < 512) {
                        this.routine.execute(log);
                    }
                    if (useCC) {
                        long s1 = System.currentTimeMillis();
                        String ip = log.getIp();
                        String time = log.getTime();
                        long band = log.getContentLength();
                        String host = (log.getHost() == null || log.getHost().trim().isEmpty()) ? globalHost : log.getHost();
                        String uri = log.getRequestUrl();
                        if (uri.indexOf("?") > 0) {
                            uri = uri.substring(0, uri.indexOf("?"));
                        }
                        Date date = null;
                        try {
                            DateFormat commondf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            date = commondf.parse(time);
                        } catch (ParseException e) {
                            continue;
                        }
                        if (allHost.containsKey(host)) {
                            hostMap = allHost.get(host);
                            try {
                                day = Integer.valueOf(DateUtil.formatDate(date, "yyyyMMdd")).intValue();
                            } catch (NumberFormatException nfe) {
                                continue;
                            }
                            long l = day * 1000L + CCAlgorithm.get5MinInterval(date.getHours(), date.getMinutes());
                            if (hostMap.containsKey(Long.valueOf(l))) {
                                CCDetail cCDetail = hostMap.get(Long.valueOf(l));
                                cCDetail.setCount(cCDetail.getCount() + 1);
                                cCDetail.setBand(cCDetail.getBand() + band);
                                cCDetail.getList().add(new CC(host, uri, ip, time));
                                continue;
                            }
                            CCDetail detail = new CCDetail();
                            detail.setCount(1);
                            detail.setBand(band);
                            List<CC> ccList = new ArrayList<CC>();
                            ccList.add(new CC(host, uri, ip, time));
                            detail.setList(ccList);
                            hostMap.put(Long.valueOf(l), detail);
                            continue;
                        }
                        hostMap = new TreeMap<Long, CCDetail>();
                        try {
                            day = Integer.valueOf(DateUtil.formatDate(date, "yyyyMMdd")).intValue();
                        } catch (NumberFormatException nfe) {
                            continue;
                        }
                        long minIntervalKey = day * 1000L + CCAlgorithm.get5MinInterval(date.getHours(), date.getMinutes());
                        if (hostMap.containsKey(Long.valueOf(minIntervalKey))) {
                            CCDetail detail = hostMap.get(Long.valueOf(minIntervalKey));
                            detail.setCount(detail.getCount() + 1);
                            detail.setBand(detail.getBand() + band);
                            detail.getList().add(new CC(host, uri, ip, time));
                        } else {
                            CCDetail detail = new CCDetail();
                            detail.setCount(1);
                            detail.setBand(band);
                            List<CC> ccList = new ArrayList<CC>();
                            ccList.add(new CC(host, uri, ip, time));
                            detail.setList(ccList);
                            hostMap.put(Long.valueOf(minIntervalKey), detail);
                        }
                        allHost.put(host, hostMap);
                    }
                }
            }
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
    }


    public static String basePath = "";
    public static String jarPath = "";
    public static boolean isJarExecute = false;


    private static void initPath() {
        File jarFile = new File(AnalyzeSingle.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        if (jarFile.getPath().endsWith(".jar")) {
            isJarExecute = true;
            jarPath = jarFile.getPath();
        } else if (jarFile.getPath().contains(".jar")) {
//            basePath = jarFile.getParent();
        } else {
            basePath = jarFile.getPath();
        }
    }


    public AttackAnalyzeSingle getAttack() {
        return this.attack;
    }


    public void setAttack(AttackAnalyzeSingle attack) {
        this.attack = attack;
    }

}