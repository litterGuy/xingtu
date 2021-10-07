
package com.qihoo.wzws.rzb.secure;


import com.qihoo.wzws.rzb.cc.CC;
import com.qihoo.wzws.rzb.cc.CCAlgorithm;
import com.qihoo.wzws.rzb.cc.CCDetail;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.parse.AutomaticLogFormatParser;
import com.qihoo.wzws.rzb.routine.RoutineAnalyze;
import com.qihoo.wzws.rzb.routine.RoutineReportCache;
import com.qihoo.wzws.rzb.secure.po.AttackEntity;
import com.qihoo.wzws.rzb.secure.po.LogEntity;
import com.qihoo.wzws.rzb.util.*;
import com.qihoo.wzws.rzb.util.ip.IPDataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class AnalyzeSingle {
    public static final String clientVersion = "0.6.2";
    public static final boolean product = true;
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

        String scheduleAnalysis = (String) ConfigUtil.formatConfig.get("schedule_analysis");


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


                    System.out.println("定时任务模式");

                    System.out.println("当前文件下最新文件为：" + file.getName() + ";文件大小为：" + outputFileSize(file.length()));


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


                        System.out.println("当前文件为：" + file.getName() + ";文件大小为：" + outputFileSize(file.length()));


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


                System.err.println("输入路径下没有文件，请重新选择！");

                System.out.println("配置无误后请关闭窗口，重新执行。");

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


            System.out.println("当前文件为：" + fileDir.getName() + ";文件大小为：" + outputFileSize(fileDir.length()));


            HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();

            rsearch(fileDir, allHost);


            if (useCC) {

                this.cc.execute(allHost);

            }


            System.gc();


            allHost.clear();

            allHost = null;

        } else {

            System.err.println("输入文件路径有误！");

            System.out.println("配置无误后请关闭窗口，重新执行。");

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


    public static void main(String[] args) throws SystemConfigException {

        System.out.println("");

        for (int i = 3; i > 0; i--) {


            try {

                Thread.currentThread();
                Thread.sleep(1000L);

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

        }


        long start = System.currentTimeMillis();


        String binPath = (new File("")).getAbsolutePath();

        System.out.println("默认路径：" + binPath);

        basePath = binPath.substring(0, binPath.length() - 4);


        System.out.println("运行前检查...");


        initPath();


        String sys = basePath + File.separator + "conf" + File.separator + "config.ini";

        System.out.println("config.ini：" + sys);

        String rules = basePath + File.separator + "conf" + File.separator + "rules.ini";

        String ipFilePath = basePath + File.separator + "bin" + File.separator + "ip.dat";


        File sysFile = new File(sys);

        if (sysFile == null || !sysFile.exists()) {

            System.out.println("conf/config.ini文件不存在。");

            System.out.println("确认无误后请关闭窗口，重新执行。");

            return;

        }

        File rulesFile = new File(rules);

        if (rulesFile == null || !rulesFile.exists()) {

            System.out.println("conf/rule.ini文件不存在。");

            System.out.println("确认无误后请关闭窗口，重新执行。");


            return;

        }

        File ipFile = new File(ipFilePath);

        if (ipFile == null || !ipFile.exists()) {

            System.out.println("bin/ip.dat文件不存在。");

            System.out.println("确认无误后请关闭窗口，重新执行。");


            return;

        }

        if (!ValidateConfig.validateSysConf(new String[]{sys, rules})) {

            System.out.println("配置无误后请关闭窗口，重新执行。");


            return;

        }


        IPDataHandler.initIPData(ipFilePath);


        System.out.println("加载系统配置文件:" + sys);

        System.out.println("加载分析规则文件:" + rules);


        String mac = (String) ConfigUtil.formatConfig.get("sign");


        try {

            ConnServerHandler.requestUpdate((String) ConfigUtil.formatConfig.get("sign"), "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"));

        } catch (Exception ex) {
        }


        ValidateConfig.validateRuleConf(rules);


        long startM = Runtime.getRuntime().freeMemory() / 1024L / 1024L;

        System.out.println("当前分配的系统内存为：" + startM + "M");


        System.out.println("检查完毕！");

        System.out.println("");


        if ("2".equals(ConfigUtil.formatConfig.get("common_analysis"))) {

            useRoutine = true;

        }


        if ("2".equals(ConfigUtil.formatConfig.get("cc_analysis"))) {

            CCAnalyzeSingle.initParas();

            useCC = true;

        }


        String tips = "[Web攻击分析";

        if (useRoutine) {

            tips = tips + "、日常分析";

            if (useCC) {

                tips = tips + "、CC攻击分析";

            }

        } else if (useCC) {

            tips = tips + "、CC攻击分析";

        }


        tips = tips + "]";


        System.out.println("开始分析，请耐心等待(分析1G日志大约需要200秒)...");

        System.out.println("");


        File resultDir = new File(ReportOutput.getReportDir());

        if (!resultDir.exists()) {

            resultDir.mkdirs();

        }


        RoutineAnalyze routine = new RoutineAnalyze();


        AttackAnalyzeSingle attack = new AttackAnalyzeSingle();

        CCAnalyzeSingle cc = new CCAnalyzeSingle();

        instance = new AnalyzeSingle(routine, attack, cc, (String) ConfigUtil.formatConfig.get("log_file"));


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


        try {

            if (AttackReportCache.records.get() > 0L) {

                ConnServerHandler.updateUseInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), AttackReportCache.records.get(), sum);

            }

        } catch (Exception ex) {
        }


        System.out.println("");


        long end = System.currentTimeMillis();


        String email = (String) ConfigUtil.formatConfig.get("xingtu_email");

        if (email != null) {

            ConnServerHandler.sendMail(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), email, (String) ConfigUtil.formatConfig.get("log_file"), AttackReportCache.records.get(), sum, (end - start) / 1000L, AttackReportCache.outputOverViewForMail());

        }


        Map<String, String> map = new HashMap<String, String>();


        for (AttackEntity entity : AttackReportCache.attackList) {

            if (!map.containsKey(entity.getIp() + "|" + entity.getUrl())) {

                map.put(entity.getIp() + "|" + entity.getUrl(), entity.getRule());

            }

        }


        if (useRoutine) {


            System.gc();


            ReportOutput.outputHtml();

            ReportOutput.outputAttackHtml();

        }


        System.out.println("本次进行了" + tips);


        long count = AttackReportCache.records.get();

        if (useRoutine) {

            count = RoutineReportCache.visits;

        }

        System.out.println("共分析了" + count + "条访问日志");

        long costTime = (end - start) / 1000L;

        if (costTime < 1L) {

            costTime = 1L;

        }

        System.out.println("分析耗时:" + costTime + "秒");

        System.out.println("其中异常访问" + sum + "条");

        if (useRoutine) {

            System.out.println("常规日志分析报告已生成");

        }

        System.out.println("");

        System.out.println("分析结果请打开[星图安装目录result文件夹]查看");

        System.out.println("本次分析完成！");

        System.out.println("");


        int leftsize = map.size();

        int index = 0;

        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> entry : map.entrySet()) {

            sb.append((String) entry.getKey() + "|" + (String) entry.getValue());

            sb.append(";");


            index++;

            leftsize--;


            if (index > 500 && leftsize > 0) {


                ConnServerHandler.updateAttackInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), sb.toString());

                sb = null;

                sb = new StringBuilder();

                index = 0;
                continue;

            }
            if (leftsize == 0) {

                ConnServerHandler.updateAttackInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), sb.toString());

            }

        }

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

        } else {

//            basePath = jarFile.getParent();
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