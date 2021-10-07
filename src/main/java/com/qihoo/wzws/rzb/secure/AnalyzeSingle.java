/*     */
package com.qihoo.wzws.rzb.secure;
/*     */
/*     */

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
/*     */
/*     */ public class AnalyzeSingle
        /*     */ {
    /*     */   public static final String clientVersion = "0.6.2";
    /*     */   public static final boolean product = true;
    /*     */   private static AnalyzeSingle instance;
    /*  57 */   private static String globalHost = "";
    /*     */
    /*  59 */   public static String currentFile = "";
    /*  60 */   public static String currentTime = "";
    /*     */
    /*     */   public static boolean useAttack = true;
    /*     */
    /*     */   public static boolean useCC = false;
    /*     */   public static boolean useRoutine = false;
    /*     */   private RoutineAnalyze routine;
    /*     */   private AttackAnalyzeSingle attack;
    /*     */   private CCAnalyzeSingle cc;
    /*     */   private String dir;

    /*     */
    /*     */
    public AnalyzeSingle(RoutineAnalyze routine, AttackAnalyzeSingle attack, CCAnalyzeSingle cc, String dir) {
        /*  72 */
        this.routine = routine;
        /*  73 */
        this.attack = attack;
        /*  74 */
        this.cc = cc;
        /*  75 */
        this.dir = dir;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public void execute() {
        /*  80 */
        String scheduleAnalysis = (String) ConfigUtil.formatConfig.get("schedule_analysis");
        /*     */
        /*  82 */
        File fileDir = new File(this.dir);
        /*     */
        /*     */
        /*  85 */
        if (fileDir.isDirectory()) {
            /*     */
            /*     */
            /*  88 */
            File[] files = fileDir.listFiles();
            /*  89 */
            if (files.length >= 1) {
                /*     */
                /*     */
                /*  92 */
                if ("2".equals(scheduleAnalysis)) {
                    /*     */
                    /*     */
                    /*  95 */
                    File file = Utils.getlastestFileFromDir(files);
                    /*     */
                    /*  97 */
                    RoutineReportCache.logSize += file.length();
                    /*     */
                    /*  99 */
                    currentTime = DateUtil.formatDateHour();
                    /*     */
                    /* 101 */
                    if (file.getName().indexOf(".") > -1) {
                        /* 102 */
                        currentFile = file.getName().substring(0, file.getName().lastIndexOf("."));
                        /*     */
                    } else {
                        /* 104 */
                        currentFile = file.getName();
                        /*     */
                    }
                    /*     */
                    /* 107 */
                    clearResultHistory();
                    /*     */
                    /* 109 */
                    System.out.println("定时任务模式");
                    /* 110 */
                    System.out.println("当前文件下最新文件为：" + file.getName() + ";文件大小为：" + outputFileSize(file.length()));
                    /*     */
                    /* 112 */
                    HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
                    /* 113 */
                    rsearch(file, allHost);
                    /*     */
                    /*     */
                    /* 116 */
                    if (useCC) {
                        /* 117 */
                        this.cc.execute(allHost);
                        /*     */
                    }
                    /*     */
                    /*     */
                    /*     */
                    /* 122 */
                    System.gc();
                    /*     */
                    /*     */
                    /*     */
                    /* 126 */
                    allHost.clear();
                    /* 127 */
                    allHost = null;
                    /*     */
                }
                /*     */
                else {
                    /*     */
                    /* 131 */
                    currentTime = DateUtil.formatDateHour();
                    /* 132 */
                    if (files[0].getName().indexOf(".") > -1) {
                        /* 133 */
                        currentFile = files[0].getName().substring(0, files[0].getName().lastIndexOf("."));
                        /*     */
                    } else {
                        /* 135 */
                        currentFile = files[0].getName();
                        /*     */
                    }
                    /* 137 */
                    clearResultHistory();
                    /*     */
                    /*     */
                    /* 140 */
                    for (File file : files)
                        /*     */ {
                        /* 142 */
                        RoutineReportCache.logSize += file.length();
                        /*     */
                        /* 144 */
                        System.out.println("当前文件为：" + file.getName() + ";文件大小为：" + outputFileSize(file.length()));
                        /*     */
                        /* 146 */
                        HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
                        /* 147 */
                        rsearch(file, allHost);
                        /*     */
                        /*     */
                        /* 150 */
                        if (useCC) {
                            /* 151 */
                            this.cc.execute(allHost);
                            /*     */
                        }
                        /*     */
                        /* 154 */
                        allHost.clear();
                        /* 155 */
                        allHost = null;
                        /*     */
                        /*     */
                        /*     */
                        /* 159 */
                        System.gc();
                        /*     */
                    }
                    /*     */
                    /*     */
                }
                /*     */
            } else {
                /*     */
                /* 165 */
                System.err.println("输入路径下没有文件，请重新选择！");
                /* 166 */
                System.out.println("配置无误后请关闭窗口，重新执行。");
                /*     */
            }
            /* 168 */
        } else if (fileDir.isFile()) {
            /*     */
            /*     */
            /* 171 */
            RoutineReportCache.logSize = fileDir.length();
            /*     */
            /* 173 */
            currentTime = DateUtil.formatDateHour();
            /* 174 */
            if (fileDir.getName().indexOf(".") > -1) {
                /* 175 */
                currentFile = fileDir.getName().substring(0, fileDir.getName().lastIndexOf("."));
                /*     */
            } else {
                /* 177 */
                currentFile = fileDir.getName();
                /*     */
            }
            /*     */
            /* 180 */
            clearResultHistory();
            /*     */
            /* 182 */
            System.out.println("当前文件为：" + fileDir.getName() + ";文件大小为：" + outputFileSize(fileDir.length()));
            /*     */
            /* 184 */
            HashMap<String, TreeMap<Long, CCDetail>> allHost = new HashMap<String, TreeMap<Long, CCDetail>>();
            /* 185 */
            rsearch(fileDir, allHost);
            /*     */
            /*     */
            /* 188 */
            if (useCC) {
                /* 189 */
                this.cc.execute(allHost);
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /* 194 */
            System.gc();
            /*     */
            /*     */
            /*     */
            /* 198 */
            allHost.clear();
            /* 199 */
            allHost = null;
            /*     */
        } else {
            /* 201 */
            System.err.println("输入文件路径有误！");
            /* 202 */
            System.out.println("配置无误后请关闭窗口，重新执行。");
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    private static String outputFileSize(long length) {
        /* 207 */
        String size = "";
        /* 208 */
        if (length / 1024L / 1024L < 1L) {
            /* 209 */
            size = (length / 1024L) + "K";
            /*     */
        } else {
            /* 211 */
            size = (length / 1024L / 1024L) + "M";
            /*     */
        }
        /*     */
        /* 214 */
        return size;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    private static void clearResultHistory() {
        /* 221 */
        File longf = new File(ReportOutput.getLongUrlFilePath());
        /* 222 */
        if (longf != null && longf.exists()) {
            /* 223 */
            longf.delete();
            /*     */
        }
        /* 225 */
        File attackf = new File(ReportOutput.getAttackFilePath());
        /* 226 */
        if (attackf != null && attackf.exists()) {
            /* 227 */
            attackf.delete();
            /*     */
        }
        /* 229 */
        File ccf = new File(ReportOutput.getCCFilePath());
        /* 230 */
        if (ccf != null && ccf.exists()) {
            /* 231 */
            ccf.delete();
            /*     */
        }
        /* 233 */
        File reportf = new File(ReportOutput.getReportFilePath());
        /* 234 */
        if (reportf != null && reportf.exists()) {
            /* 235 */
            reportf.delete();
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
    /*     */
    /*     */
    /*     */
    public static void main(String[] args) throws SystemConfigException {
        /* 252 */
        System.out.println("");
        /* 253 */
        for (int i = 3; i > 0; i--) {
            /*     */
            /*     */
            try {
                /* 256 */
                Thread.currentThread();
                Thread.sleep(1000L);
                /* 257 */
            } catch (InterruptedException e) {
                /* 258 */
                e.printStackTrace();
                /*     */
            }
            /*     */
        }
        /*     */
        /* 262 */
        long start = System.currentTimeMillis();
        /*     */
        /*     */
        /* 265 */
        String binPath = (new File("")).getAbsolutePath();
        /*     */
        System.out.println("默认路径：" + binPath);
        /* 267 */
        basePath = binPath.substring(0, binPath.length() - 4);
        /*     */
        /*     */
        /* 270 */
        System.out.println("运行前检查...");
        /*     */
        /* 272 */
        initPath();
        /*     */
        /*     */
        /* 275 */
        String sys = basePath + File.separator + "conf" + File.separator + "config.ini";

        System.out.println("config.ini：" + sys);
        /* 276 */
        String rules = basePath + File.separator + "conf" + File.separator + "rules.ini";
        /* 277 */
        String ipFilePath = basePath + File.separator + "bin" + File.separator + "ip.dat";
        /*     */
        /*     */
        /*     */
        /*     */
        /* 282 */
        File sysFile = new File(sys);
        /* 283 */
        if (sysFile == null || !sysFile.exists()) {
            /* 284 */
            System.out.println("conf/config.ini文件不存在。");
            /* 285 */
            System.out.println("确认无误后请关闭窗口，重新执行。");
            /*     */
            return;
            /*     */
        }
        /* 288 */
        File rulesFile = new File(rules);
        /* 289 */
        if (rulesFile == null || !rulesFile.exists()) {
            /* 290 */
            System.out.println("conf/rule.ini文件不存在。");
            /* 291 */
            System.out.println("确认无误后请关闭窗口，重新执行。");
            /*     */
            /*     */
            return;
            /*     */
        }
        /* 295 */
        File ipFile = new File(ipFilePath);
        /* 296 */
        if (ipFile == null || !ipFile.exists()) {
            /* 297 */
            System.out.println("bin/ip.dat文件不存在。");
            /* 298 */
            System.out.println("确认无误后请关闭窗口，重新执行。");
            /*     */
            /*     */
            return;
            /*     */
        }
        /* 302 */
        if (!ValidateConfig.validateSysConf(new String[]{sys, rules})) {
            /* 303 */
            System.out.println("配置无误后请关闭窗口，重新执行。");
            /*     */
            /*     */
            return;
            /*     */
        }
        /*     */
        /* 308 */
        IPDataHandler.initIPData(ipFilePath);
        /*     */
        /* 310 */
        System.out.println("加载系统配置文件:" + sys);
        /* 311 */
        System.out.println("加载分析规则文件:" + rules);
        /*     */
        /*     */
        /* 314 */
        String mac = (String) ConfigUtil.formatConfig.get("sign");
        /*     */
        /*     */
        try {
            /* 317 */
            ConnServerHandler.requestUpdate((String) ConfigUtil.formatConfig.get("sign"), "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"));
            /* 318 */
        } catch (Exception ex) {
        }
        /*     */
        /*     */
        /*     */
        /* 322 */
        ValidateConfig.validateRuleConf(rules);
        /*     */
        /*     */
        /* 325 */
        long startM = Runtime.getRuntime().freeMemory() / 1024L / 1024L;
        /* 326 */
        System.out.println("当前分配的系统内存为：" + startM + "M");
        /*     */
        /* 328 */
        System.out.println("检查完毕！");
        /* 329 */
        System.out.println("");
        /*     */
        /*     */
        /* 332 */
        if ("2".equals(ConfigUtil.formatConfig.get("common_analysis"))) {
            /* 333 */
            useRoutine = true;
            /*     */
        }
        /*     */
        /* 336 */
        if ("2".equals(ConfigUtil.formatConfig.get("cc_analysis"))) {
            /* 337 */
            CCAnalyzeSingle.initParas();
            /* 338 */
            useCC = true;
            /*     */
        }
        /*     */
        /*     */
        /* 342 */
        String tips = "[Web攻击分析";
        /* 343 */
        if (useRoutine) {
            /* 344 */
            tips = tips + "、日常分析";
            /* 345 */
            if (useCC) {
                /* 346 */
                tips = tips + "、CC攻击分析";
                /*     */
            }
            /*     */
        }
        /* 349 */
        else if (useCC) {
            /* 350 */
            tips = tips + "、CC攻击分析";
            /*     */
        }
        /*     */
        /* 353 */
        tips = tips + "]";
        /*     */
        /* 355 */
        System.out.println("开始分析，请耐心等待(分析1G日志大约需要200秒)...");
        /* 356 */
        System.out.println("");
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 362 */
        File resultDir = new File(ReportOutput.getReportDir());
        /* 363 */
        if (!resultDir.exists()) {
            /* 364 */
            resultDir.mkdirs();
            /*     */
        }
        /*     */
        /* 367 */
        RoutineAnalyze routine = new RoutineAnalyze();
        /*     */
        /* 369 */
        AttackAnalyzeSingle attack = new AttackAnalyzeSingle();
        /* 370 */
        CCAnalyzeSingle cc = new CCAnalyzeSingle();
        /* 371 */
        instance = new AnalyzeSingle(routine, attack, cc, (String) ConfigUtil.formatConfig.get("log_file"));
        /*     */
        /* 373 */
        instance.execute();
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 379 */
        AttackReportCache.sortAttlist();
        /* 380 */
        if (useCC) {
            /* 381 */
            AttackReportCache.sortCClist();
            /*     */
        }
        /*     */
        /*     */
        /* 385 */
        ReportOutput.outputFile(AttackReportCache.attackList, AttackReportCache.ccList);
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /*     */
        /* 392 */
        long sum = AttackReportCache.attackCount.get() + AttackReportCache.longUrlCount.get();
        /* 393 */
        if (useCC) {
            /* 394 */
            sum += AttackReportCache.ccCount.get();
            /*     */
        }
        /*     */
        /*     */
        /*     */
        try {
            /* 399 */
            if (AttackReportCache.records.get() > 0L) {
                /* 400 */
                ConnServerHandler.updateUseInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), AttackReportCache.records.get(), sum);
                /*     */
            }
            /* 402 */
        } catch (Exception ex) {
        }
        /*     */
        /*     */
        /*     */
        /*     */
        /* 407 */
        System.out.println("");
        /*     */
        /* 409 */
        long end = System.currentTimeMillis();
        /*     */
        /*     */
        /* 412 */
        String email = (String) ConfigUtil.formatConfig.get("xingtu_email");
        /* 413 */
        if (email != null) {
            /* 414 */
            ConnServerHandler.sendMail(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), email, (String) ConfigUtil.formatConfig.get("log_file"), AttackReportCache.records.get(), sum, (end - start) / 1000L, AttackReportCache.outputOverViewForMail());
            /*     */
        }
        /*     */
        /*     */
        /* 418 */
        Map<String, String> map = new HashMap<String, String>();
        /*     */
        /* 420 */
        for (AttackEntity entity : AttackReportCache.attackList) {
            /* 421 */
            if (!map.containsKey(entity.getIp() + "|" + entity.getUrl())) {
                /* 422 */
                map.put(entity.getIp() + "|" + entity.getUrl(), entity.getRule());
                /*     */
            }
            /*     */
        }
        /*     */
        /* 426 */
        if (useRoutine) {
            /*     */
            /* 428 */
            System.gc();
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */
            /* 434 */
            ReportOutput.outputHtml();
            /* 435 */
            ReportOutput.outputAttackHtml();
            /*     */
        }
        /*     */
        /*     */
        /* 439 */
        System.out.println("本次进行了" + tips);
        /*     */
        /* 441 */
        long count = AttackReportCache.records.get();
        /* 442 */
        if (useRoutine) {
            /* 443 */
            count = RoutineReportCache.visits;
            /*     */
        }
        /* 445 */
        System.out.println("共分析了" + count + "条访问日志");
        /* 446 */
        long costTime = (end - start) / 1000L;
        /* 447 */
        if (costTime < 1L) {
            /* 448 */
            costTime = 1L;
            /*     */
        }
        /* 450 */
        System.out.println("分析耗时:" + costTime + "秒");
        /* 451 */
        System.out.println("其中异常访问" + sum + "条");
        /* 452 */
        if (useRoutine) {
            /* 453 */
            System.out.println("常规日志分析报告已生成");
            /*     */
        }
        /* 455 */
        System.out.println("");
        /* 456 */
        System.out.println("分析结果请打开[星图安装目录result文件夹]查看");
        /* 457 */
        System.out.println("本次分析完成！");
        /* 458 */
        System.out.println("");
        /*     */
        /*     */
        /* 461 */
        int leftsize = map.size();
        /* 462 */
        int index = 0;
        /* 463 */
        StringBuilder sb = new StringBuilder();
        /* 464 */
        for (Map.Entry<String, String> entry : map.entrySet()) {
            /* 465 */
            sb.append((String) entry.getKey() + "|" + (String) entry.getValue());
            /* 466 */
            sb.append(";");
            /*     */
            /* 468 */
            index++;
            /* 469 */
            leftsize--;
            /*     */
            /* 471 */
            if (index > 500 && leftsize > 0) {
                /*     */
                /* 473 */
                ConnServerHandler.updateAttackInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), sb.toString());
                /* 474 */
                sb = null;
                /* 475 */
                sb = new StringBuilder();
                /* 476 */
                index = 0;
                continue;
                /* 477 */
            }
            if (leftsize == 0) {
                /* 478 */
                ConnServerHandler.updateAttackInfo(mac, "0.6.2", (String) ConfigUtil.formatConfig.get("rule_ver"), sb.toString());
                /*     */
            }
            /*     */
        }
        /*     */
    }

    /*     */
    /*     */
    /*     */
    private void rsearch(File file, HashMap<String, TreeMap<Long, CCDetail>> allHost) {
        /* 485 */
        TreeMap<Long, CCDetail> hostMap = null;
        /* 486 */
        BufferedReader reader = null;
        /*     */
        try {
            /* 488 */
            int CharBufferSize = 102400;
            /* 489 */
            if (file.length() > 209715200L) {
                /* 490 */
                CharBufferSize = 5242880;
                /*     */
            }
            /* 492 */
            reader = new BufferedReader(new FileReader(file), CharBufferSize);
            /* 493 */
            String tempString = null;
            /* 494 */
            int day = 0;
            /*     */
            /* 496 */
            while ((tempString = reader.readLine()) != null)
                /*     */ {
                /* 498 */
                if (tempString.startsWith("#")) {
                    /*     */
                    continue;
                    /*     */
                }
                /*     */
                /* 502 */
                LogEntity log = AutomaticLogFormatParser.parse(tempString);
                /*     */
                /* 504 */
                if (log != null)
                    /*     */ {
                    /*     */
                    /* 507 */
                    if (log.getRequestUrl() == null || log.getRequestUrl().trim().length() == 0) {
                        /*     */
                        continue;
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 512 */
                    if (useAttack) {
                        /* 513 */
                        this.attack.execute(log);
                        /*     */
                    }
                    /*     */
                    /*     */
                    /* 517 */
                    if (useRoutine && log.getRequestUrl().length() < 512) {
                        /* 518 */
                        this.routine.execute(log);
                        /*     */
                    }
                    /*     */
                    /*     */
                    /*     */
                    /* 523 */
                    if (useCC) {
                        /* 524 */
                        long s1 = System.currentTimeMillis();
                        /*     */
                        /* 526 */
                        String ip = log.getIp();
                        /* 527 */
                        String time = log.getTime();
                        /* 528 */
                        long band = log.getContentLength();
                        /*     */
                        /* 530 */
                        String host = (log.getHost() == null || log.getHost().trim().isEmpty()) ? globalHost : log.getHost();
                        /* 531 */
                        String uri = log.getRequestUrl();
                        /* 532 */
                        if (uri.indexOf("?") > 0) {
                            /* 533 */
                            uri = uri.substring(0, uri.indexOf("?"));
                            /*     */
                        }
                        /*     */
                        /* 536 */
                        Date date = null;
                        /*     */
                        try {
                            /* 538 */
                            DateFormat commondf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            /* 539 */
                            date = commondf.parse(time);
                            /* 540 */
                        } catch (ParseException e) {
                            /*     */
                            continue;
                            /*     */
                        }
                        /*     */
                        /* 544 */
                        if (allHost.containsKey(host)) {
                            /* 545 */
                            hostMap = allHost.get(host);
                            /*     */
                            /*     */
                            try {
                                /* 548 */
                                day = Integer.valueOf(DateUtil.formatDate(date, "yyyyMMdd")).intValue();
                                /* 549 */
                            } catch (NumberFormatException nfe) {
                                /*     */
                                continue;
                                /*     */
                            }
                            /*     */
                            /* 553 */
                            long l = day * 1000L + CCAlgorithm.get5MinInterval(date.getHours(), date.getMinutes());
                            /* 554 */
                            if (hostMap.containsKey(Long.valueOf(l))) {
                                /* 555 */
                                CCDetail cCDetail = hostMap.get(Long.valueOf(l));
                                /* 556 */
                                cCDetail.setCount(cCDetail.getCount() + 1);
                                /* 557 */
                                cCDetail.setBand(cCDetail.getBand() + band);
                                /* 558 */
                                cCDetail.getList().add(new CC(host, uri, ip, time));
                                continue;
                                /*     */
                            }
                            /* 560 */
                            CCDetail detail = new CCDetail();
                            /* 561 */
                            detail.setCount(1);
                            /* 562 */
                            detail.setBand(band);
                            /* 563 */
                            List<CC> ccList = new ArrayList<CC>();
                            /* 564 */
                            ccList.add(new CC(host, uri, ip, time));
                            /* 565 */
                            detail.setList(ccList);
                            /* 566 */
                            hostMap.put(Long.valueOf(l), detail);
                            /*     */
                            /*     */
                            continue;
                            /*     */
                        }
                        /*     */
                        /* 571 */
                        hostMap = new TreeMap<Long, CCDetail>();
                        /*     */
                        /*     */
                        try {
                            /* 574 */
                            day = Integer.valueOf(DateUtil.formatDate(date, "yyyyMMdd")).intValue();
                            /* 575 */
                        } catch (NumberFormatException nfe) {
                            /*     */
                            continue;
                            /*     */
                        }
                        /*     */
                        /* 579 */
                        long minIntervalKey = day * 1000L + CCAlgorithm.get5MinInterval(date.getHours(), date.getMinutes());
                        /* 580 */
                        if (hostMap.containsKey(Long.valueOf(minIntervalKey))) {
                            /* 581 */
                            CCDetail detail = hostMap.get(Long.valueOf(minIntervalKey));
                            /* 582 */
                            detail.setCount(detail.getCount() + 1);
                            /* 583 */
                            detail.setBand(detail.getBand() + band);
                            /* 584 */
                            detail.getList().add(new CC(host, uri, ip, time));
                            /*     */
                        } else {
                            /* 586 */
                            CCDetail detail = new CCDetail();
                            /* 587 */
                            detail.setCount(1);
                            /* 588 */
                            detail.setBand(band);
                            /* 589 */
                            List<CC> ccList = new ArrayList<CC>();
                            /* 590 */
                            ccList.add(new CC(host, uri, ip, time));
                            /* 591 */
                            detail.setList(ccList);
                            /* 592 */
                            hostMap.put(Long.valueOf(minIntervalKey), detail);
                            /*     */
                        }
                        /*     */
                        /* 595 */
                        allHost.put(host, hostMap);
                        /*     */
                    }
                    /*     */
                    /*     */
                }
                /*     */
                /*     */
            }
            /*     */
            /* 602 */
        } catch (IOException e) {
            /* 603 */
            e.printStackTrace();
            /*     */
        } finally {
            /* 605 */
            if (reader != null) {
                /*     */
                try {
                    /* 607 */
                    reader.close();
                    /* 608 */
                } catch (IOException e1) {
                }
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
    /* 693 */   public static String basePath = "";
    /* 694 */   public static String jarPath = "";
    public static boolean isJarExecute = false;

    /*     */
    /*     */
    private static void initPath() {
        /* 697 */
        File jarFile = new File(AnalyzeSingle.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        /*     */
        /* 699 */
        if (jarFile.getPath().endsWith(".jar")) {
            /* 700 */
            isJarExecute = true;
            /*     */
            /* 702 */
            jarPath = jarFile.getPath();
            /*     */
        } else {
            /* 704 */
//            basePath = jarFile.getParent();
            basePath = jarFile.getPath();
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
    public AttackAnalyzeSingle getAttack() {
        /* 715 */
        return this.attack;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public void setAttack(AttackAnalyzeSingle attack) {
        /* 722 */
        this.attack = attack;
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\secure\AnalyzeSingle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */