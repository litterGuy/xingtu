/*     */
package com.qihoo.wzws.rzb.parse;
/*     */
/*     */

import com.qihoo.wzws.rzb.exception.LogParserException;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.util.DateUtil;
import com.qihoo.wzws.rzb.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
/*     */ public class ApacheDefaultFormatParser
        /*     */ {
    /*  31 */   public static FormatType current_apache_logType = null;
    /*     */
    /*     */   private static final String prefix = "^";
    /*     */
    /*     */   private static final String ipPattern = "([0-9.,]+)\\s";
    /*     */
    /*     */   private static final String identityPattern = "([\\w\\.-]+)\\s";
    /*     */
    /*     */   private static final String useridPattern = "([\\w\\.-]+)\\s";
    /*     */
    /*     */   private static final String timePattern = "\\[([/a-zA-Z0-9+-: ]+)\\]\\s";
    /*     */
    /*     */   private static final String requestPattern = "\"(.+)\"\\s";
    /*     */
    /*     */   private static final String responseCodePattern = "(\\d{3})\\s";
    /*     */
    /*     */   private static final String contentLength = "(\\d+|-)";
    /*     */
    /*     */   private static final String ipCommon = "(-|(\\d{1,3}\\.){3}\\d{1,3})";
    /*     */
    /*     */   private static final String refererPattern = "\"(-|http\\S+)\"\\s";
    /*     */   private static final String useragentPattern = "\"(-|.*)\"";
    /*     */   private static final String allPattern = ".*";
    /*     */   private static final String blackPattern = "\\s";
    /*     */   private static final String suffix = "$";
    /*     */   private static final String quote = "\"{0,1}";
    /*     */   private static final String anmp_identityPattern = "-\\s";
    /*     */   private static final String anmp_useridPattern = "-\\s";
    /*     */   private static final String anmp_requestPattern = "(.+)\"\\s";
    /*  60 */   private static StringBuilder commonPatternStr = new StringBuilder();
    /*  61 */   private static StringBuilder apacheCommonPatternStr = new StringBuilder();
    /*  62 */   private static StringBuilder apacheCombinedPatternStr = new StringBuilder();
    /*  63 */   private static StringBuilder apacheCombinedExtPatternStr = new StringBuilder();
    /*  64 */   private static StringBuilder cnpcPatternStr = new StringBuilder();
    /*     */
    /*  66 */   private static Pattern apacheCommonPattern = null;
    /*  67 */   private static Pattern apacheCombinedPattern = null;
    /*  68 */   private static Pattern apacheCombinedExtPattern = null;
    /*  69 */   private static Pattern cnpcPattern = null;
    /*     */
    /*     */   protected static EnumMap<FormatType, Pattern> formatPattern;

    /*     */
    /*     */   static {
        /*  72 */
        commonPatternStr.append("([0-9.,]+)\\s");
        /*  73 */
        commonPatternStr.append("([\\w\\.-]+)\\s");
        /*  74 */
        commonPatternStr.append("([\\w\\.-]+)\\s");
        /*  75 */
        commonPatternStr.append("\\[([/a-zA-Z0-9+-: ]+)\\]\\s");
        /*  76 */
        commonPatternStr.append("\"(.+)\"\\s");
        /*  77 */
        commonPatternStr.append("(\\d{3})\\s");
        /*  78 */
        commonPatternStr.append("(\\d+|-)");
        /*     */
        /*     */
        /*     */
        /*  82 */
        apacheCommonPatternStr.append("^");
        /*  83 */
        apacheCommonPatternStr.append(commonPatternStr);
        /*  84 */
        apacheCommonPatternStr.append(" ?.*");
        /*     */
        /*  86 */
        apacheCommonPattern = Pattern.compile(apacheCommonPatternStr.toString());
        /*     */
        /*     */
        /*     */
        /*     */
        /*  91 */
        apacheCombinedPatternStr.append("^");
        /*  92 */
        apacheCombinedPatternStr.append(commonPatternStr);
        /*  93 */
        apacheCombinedPatternStr.append("\\s");
        /*  94 */
        apacheCombinedPatternStr.append("\"(-|http\\S+)\"\\s");
        /*  95 */
        apacheCombinedPatternStr.append("\"(-|.*)\"");
        /*  96 */
        apacheCombinedPatternStr.append(".*");
        /*     */
        /*  98 */
        apacheCombinedPattern = Pattern.compile(apacheCombinedPatternStr.toString());
        /*     */
        /* 100 */
        apacheCombinedExtPatternStr.append("^");
        /* 101 */
        apacheCombinedExtPatternStr.append(commonPatternStr);
        /* 102 */
        apacheCombinedExtPatternStr.append("\\s");
        /* 103 */
        apacheCombinedExtPatternStr.append("\"(-|http\\S+)\"\\s");
        /* 104 */
        apacheCombinedExtPatternStr.append("\"(-|.*)\"");
        /* 105 */
        apacheCombinedExtPatternStr.append("\\s");
        /* 106 */
        apacheCombinedExtPatternStr.append("\"{0,1}");
        /* 107 */
        apacheCombinedExtPatternStr.append("(-|(\\d{1,3}\\.){3}\\d{1,3})");
        /* 108 */
        apacheCombinedExtPatternStr.append("\"{0,1}");
        /* 109 */
        apacheCombinedExtPatternStr.append(".*");
        /*     */
        /* 111 */
        apacheCombinedExtPattern = Pattern.compile(apacheCombinedExtPatternStr.toString());
        /*     */
        /*     */
        /* 114 */
        cnpcPatternStr.append("^");
        /* 115 */
        cnpcPatternStr.append("([0-9.,]+)\\s");
        /* 116 */
        cnpcPatternStr.append("([\\w\\.-]+)\\s");
        /* 117 */
        cnpcPatternStr.append("([\\w\\.-]+)\\s");
        /* 118 */
        cnpcPatternStr.append("\\[([/a-zA-Z0-9+-: ]+)\\]\\s");
        /* 119 */
        cnpcPatternStr.append("\"(.+)\"\\s");
        /* 120 */
        cnpcPatternStr.append("(\\d{3})\\s");
        /* 121 */
        cnpcPatternStr.append("(\\d+|-)");
        /* 122 */
        cnpcPatternStr.append(".*");
        /*     */
        /* 124 */
        cnpcPattern = Pattern.compile(cnpcPatternStr.toString());
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
        /* 146 */
        formatPattern = new EnumMap<FormatType, Pattern>(FormatType.class);
        /*     */
        /* 148 */
        formatPattern.put(FormatType.Apache_Common, apacheCommonPattern);
        /* 149 */
        formatPattern.put(FormatType.Apache_Combined, apacheCombinedPattern);
        /* 150 */
        formatPattern.put(FormatType.Apache_CombinedExt, apacheCombinedExtPattern);
        /* 151 */
        formatPattern.put(FormatType.CNPC, cnpcPattern);
        /*     */
    }

    /*     */
    /*     */   public enum FormatType
            /*     */ {
        /*     */     Agent_Log, Apache_Common, Apache_Combined, Apache_CombinedExt, Nginx_ANMP, CNPC, W3C_ALL, LNMP, Apache_Custom, IIS, NULL;
        /*     */
    }

    /*     */
    /*     */
    public static void matcherLogFormatType(String dir) throws SystemConfigException {
        /* 162 */
        File file = null;
        /* 163 */
        File fileDir = new File(dir);
        /* 164 */
        if (fileDir.isDirectory()) {
            /* 165 */
            File[] files = fileDir.listFiles();
            /* 166 */
            if (files.length >= 1) {
                /* 167 */
                file = Utils.getlastestFileFromDir(files);
                /*     */
            }
            /* 169 */
            fileDir = null;
            /*     */
        } else {
            /* 171 */
            file = fileDir;
            /*     */
        }
        /*     */
        /* 174 */
        List<String> list = new ArrayList<String>();
        /* 175 */
        BufferedReader reader = null;
        /*     */
        try {
            /* 177 */
            reader = new BufferedReader(new FileReader(file));
            /* 178 */
            String tempString = null;
            /* 179 */
            while ((tempString = reader.readLine()) != null) {
                /*     */
                /* 181 */
                if (!tempString.startsWith("#")) {
                    /* 182 */
                    list.add(tempString);
                    /* 183 */
                    if (list.size() >= 5) {
                        /*     */
                        break;
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /* 188 */
        } catch (IOException e) {
            /* 189 */
            e.printStackTrace();
            /*     */
        } finally {
            /* 191 */
            if (reader != null) {
                /*     */
                try {
                    /* 193 */
                    reader.close();
                    /* 194 */
                } catch (IOException e1) {
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /*     */
        /* 199 */
        if (list == null || list.size() == 0) {
            /* 200 */
            throw new SystemConfigException("请确保设置的日志存放路径config.ini中[log_file配置项]是有效的文件或者文件夹");
            /*     */
        }
        /*     */
        /* 203 */
        String first = list.get(0);
        /* 204 */
        FormatType type = null;
        /*     */
        /* 206 */
        if (matches(apacheCombinedExtPattern, first)) {
            /* 207 */
            type = FormatType.Apache_CombinedExt;
            /* 208 */
        } else if (matches(apacheCombinedPattern, first)) {
            /* 209 */
            type = FormatType.Apache_Combined;
            /* 210 */
        } else if (matches(apacheCommonPattern, first)) {
            /* 211 */
            type = FormatType.Apache_Common;
            /*     */
        }
        /*     */
        /*     */
        /* 215 */
        if (type != null) {
            /* 216 */
            int sum = 1;
            /* 217 */
            for (int i = 1; i < list.size(); i++) {
                /* 218 */
                if (matches(formatPattern.get(type), first)) {
                    /* 219 */
                    sum++;
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 224 */
            if (list.size() == sum) {
                /* 225 */
                current_apache_logType = type;
                /*     */
            }
            /*     */
        } else {
            /*     */
            /* 229 */
            throw new SystemConfigException(String.format("请确认一下config.ini中日志文件类型【xingtu_logtype参数项】设置是否正确，如仍有问题，请反馈到星图官方群。谢谢！[%s]", new Object[]{first}));
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
    public static boolean mattcherApacheLogFormatTypeByTemplate(List<String> list) throws SystemConfigException {
        /* 240 */
        if (list == null || list.size() == 0) {
            /* 241 */
            return false;
            /*     */
        }
        /*     */
        /* 244 */
        String first = Utils.getLongestFromList(list);
        /*     */
        /* 246 */
        FormatType type = null;
        /*     */
        /* 248 */
        if (matches(apacheCombinedExtPattern, first)) {
            /* 249 */
            type = FormatType.Apache_CombinedExt;
            /* 250 */
        } else if (matches(apacheCombinedPattern, first)) {
            /* 251 */
            type = FormatType.Apache_Combined;
            /* 252 */
        } else if (matches(apacheCommonPattern, first)) {
            /* 253 */
            type = FormatType.Apache_Common;
            /*     */
        }
        /*     */
        /*     */
        /* 257 */
        if (type != null) {
            /* 258 */
            int sum = 0;
            /* 259 */
            for (int i = 0; i < list.size(); i++) {
                /* 260 */
                if (matches(formatPattern.get(type), first)) {
                    /* 261 */
                    sum++;
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 266 */
            if (sum > list.size() / 2) {
                /* 267 */
                current_apache_logType = type;
                /* 268 */
                return true;
                /*     */
            }
            /*     */
        }
        /*     */
        /* 272 */
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
    /*     */
    public static boolean matches(Pattern pattern, String str) {
        /*     */
        try {
            /* 284 */
            return pattern.matcher(str).find();
            /* 285 */
        } catch (Exception ex) {
            /* 286 */
            return false;
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
    public static String parse(String line, FormatType type, String host) throws LogParserException {
        /* 299 */
        StringBuilder sb = new StringBuilder("");
        /* 300 */
        Matcher m = ((Pattern) formatPattern.get(type)).matcher(line);
        /* 301 */
        if (m.matches())
            /*     */ {
            /* 303 */
            switch (type) {
                /*     */
                /*     */
                case Apache_Common:
                    /*     */
                case Apache_Combined:
                    /*     */
                case Apache_CombinedExt:
                    /* 308 */
                    if (m.groupCount() >= 7) {
                        /*     */
                        /* 310 */
                        if (m.groupCount() >= 10 && !m.group(10).equals("-")) {
                            /* 311 */
                            sb.append(Utils.getRemoteIp(m.group(10)));
                            /*     */
                        } else {
                            /* 313 */
                            sb.append(Utils.getRemoteIp(m.group(1)));
                            /*     */
                        }
                        /* 315 */
                        sb.append("\t");
                        /*     */
                        /* 317 */
                        String timeZone = m.group(4);
                        /* 318 */
                        if (timeZone != null && !timeZone.isEmpty()) {
                            /* 319 */
                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                            /* 320 */
                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);
                            /* 321 */
                            if (strDate != null) {
                                /* 322 */
                                sb.append(strDate);
                                /*     */
                            }
                            /*     */
                        }
                        /* 325 */
                        sb.append("\t");
                        /*     */
                        /* 327 */
                        sb.append(host);
                        /* 328 */
                        sb.append("\t");
                        /*     */
                        /* 330 */
                        String rquestLine = m.group(5);
                        /* 331 */
                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {
                            /* 332 */
                            sb.append(rquestLine.split("\\s")[1]);
                            /*     */
                        } else {
                            /* 334 */
                            sb.append(rquestLine);
                            /*     */
                        }
                        /* 336 */
                        sb.append("\t");
                        /*     */
                        /* 338 */
                        sb.append(m.group(6));
                        /* 339 */
                        sb.append("\t");
                        /*     */
                        /* 341 */
                        sb.append(m.group(7));
                        /* 342 */
                        if (m.groupCount() >= 9) {
                            /* 343 */
                            sb.append("\t");
                            /*     */
                            /* 345 */
                            sb.append(m.group(8));
                            /* 346 */
                            sb.append("\t");
                            /*     */
                            /* 348 */
                            sb.append(m.group(9));
                            /* 349 */
                            sb.append("\t");
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                    break;
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
        }
        /* 363 */
        return sb.toString();
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
    public static String parseNginxANMP(String line, FormatType type, String host) throws LogParserException {
        /* 376 */
        StringBuilder sb = new StringBuilder("");
        /* 377 */
        Matcher m = ((Pattern) formatPattern.get(type)).matcher(line);
        /* 378 */
        if (m.matches())
            /*     */ {
            /* 380 */
            switch (type) {
                /*     */
                /*     */
                case Apache_Common:
                    /*     */
                case Apache_Combined:
                    /*     */
                case Apache_CombinedExt:
                    /* 385 */
                    if (m.groupCount() >= 7) {
                        /*     */
                        /* 387 */
                        if (m.groupCount() >= 10) {
                            /* 388 */
                            sb.append(Utils.getRemoteIp(m.group(10)));
                            /*     */
                        } else {
                            /* 390 */
                            sb.append(Utils.getRemoteIp(m.group(1)));
                            /*     */
                        }
                        /* 392 */
                        sb.append("\t");
                        /*     */
                        /* 394 */
                        String timeZone = m.group(4);
                        /* 395 */
                        if (timeZone != null && !timeZone.isEmpty()) {
                            /* 396 */
                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                            /* 397 */
                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);
                            /* 398 */
                            if (strDate != null) {
                                /* 399 */
                                sb.append(strDate);
                                /*     */
                            }
                            /*     */
                        }
                        /* 402 */
                        sb.append("\t");
                        /*     */
                        /* 404 */
                        sb.append(host);
                        /* 405 */
                        sb.append("\t");
                        /*     */
                        /* 407 */
                        String rquestLine = m.group(5);
                        /* 408 */
                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {
                            /* 409 */
                            sb.append(rquestLine.split("\\s")[1]);
                            /*     */
                        } else {
                            /* 411 */
                            sb.append(rquestLine);
                            /*     */
                        }
                        /* 413 */
                        sb.append("\t");
                        /*     */
                        /* 415 */
                        sb.append(m.group(6));
                        /* 416 */
                        sb.append("\t");
                        /*     */
                        /* 418 */
                        sb.append(m.group(7));
                        /* 419 */
                        if (m.groupCount() >= 9) {
                            /* 420 */
                            sb.append("\t");
                            /*     */
                            /* 422 */
                            sb.append(m.group(8));
                            /* 423 */
                            sb.append("\t");
                            /*     */
                            /* 425 */
                            sb.append(m.group(9));
                            /* 426 */
                            sb.append("\t");
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                    break;
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
        }
        /* 440 */
        return sb.toString();
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
    public static String parseByMatcher(String line, String host) throws LogParserException {
        /* 452 */
        StringBuilder sb = new StringBuilder("");
        /* 453 */
        Matcher m = ((Pattern) formatPattern.get(current_apache_logType)).matcher(line);
        /* 454 */
        if (m.matches())
            /*     */ {
            /* 456 */
            switch (current_apache_logType) {
                /*     */
                /*     */
                case Apache_Common:
                    /*     */
                case Apache_Combined:
                    /*     */
                case Apache_CombinedExt:
                    /* 461 */
                    if (m.groupCount() >= 7) {
                        /*     */
                        /* 463 */
                        if (m.groupCount() >= 10) {
                            /* 464 */
                            sb.append(Utils.getRemoteIp(m.group(10)));
                            /*     */
                        } else {
                            /* 466 */
                            sb.append(Utils.getRemoteIp(m.group(1)));
                            /*     */
                        }
                        /* 468 */
                        sb.append("\t");
                        /*     */
                        /* 470 */
                        String timeZone = m.group(4);
                        /* 471 */
                        if (timeZone != null && !timeZone.isEmpty()) {
                            /* 472 */
                            DateFormat dateFormatter = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);
                            /* 473 */
                            String strDate = DateUtil.formatStrDate(timeZone, dateFormatter);
                            /* 474 */
                            if (strDate != null) {
                                /* 475 */
                                sb.append(strDate);
                                /*     */
                            }
                            /*     */
                        }
                        /* 478 */
                        sb.append("\t");
                        /*     */
                        /* 480 */
                        sb.append(host);
                        /* 481 */
                        sb.append("\t");
                        /*     */
                        /* 483 */
                        String rquestLine = m.group(5);
                        /* 484 */
                        if (rquestLine != null && (rquestLine.split("\\s")).length == 3) {
                            /* 485 */
                            sb.append(rquestLine.split("\\s")[1]);
                            /*     */
                        } else {
                            /* 487 */
                            sb.append(rquestLine);
                            /*     */
                        }
                        /* 489 */
                        sb.append("\t");
                        /*     */
                        /* 491 */
                        sb.append(m.group(6));
                        /* 492 */
                        sb.append("\t");
                        /*     */
                        /* 494 */
                        sb.append(m.group(7));
                        /* 495 */
                        if (m.groupCount() >= 9) {
                            /* 496 */
                            sb.append("\t");
                            /*     */
                            /* 498 */
                            sb.append(m.group(8));
                            /* 499 */
                            sb.append("\t");
                            /*     */
                            /* 501 */
                            sb.append(m.group(9));
                            /* 502 */
                            sb.append("\t");
                            /*     */
                        }
                        /*     */
                    }
                    /*     */
                    break;
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
        }
        /* 516 */
        return sb.toString();
        /*     */
    }

    /*     */
    /*     */
    public static String formatDateTime(Date date) {
        /* 520 */
        return DateUtil.formatDateTime(date);
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
    public static void main(String[] args) throws LogParserException {
        /* 530 */
        String referer = "\"(-|\\S+)\"\\s.*";
        /* 531 */
        String line = "\"http://www.swanksalon.info/\" \"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_1 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Version/7.0 Mobile/11D201 Safari/9537.53\"";
        /* 532 */
        System.out.println(line.matches(referer));
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\parse\ApacheDefaultFormatParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */