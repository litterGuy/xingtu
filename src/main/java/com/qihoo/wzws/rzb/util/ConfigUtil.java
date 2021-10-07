/*     */
package com.qihoo.wzws.rzb.util;
/*     */
/*     */

import com.qihoo.wzws.rzb.exception.SystemConfigException;
/*     */ import com.qihoo.wzws.rzb.parse.W3CFormatParser;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureFactory;
/*     */ import com.qihoo.wzws.rzb.util.security.SignatureManager;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.codec.binary.Base64;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class ConfigUtil
        /*     */ {
    /*  30 */   private static Set<String> radioSet = new HashSet<String>();

    /*     */   static {
        /*  32 */
        radioSet.add("xingtu_logtype");
        /*  33 */
        radioSet.add("xingtu_pagetype");
        /*  34 */
        radioSet.add("xingtu_httpcode");
        /*  35 */
        radioSet.add("xingtu_urltype");
        /*  36 */
        radioSet.add("xingtu_uniq");
        /*  37 */
        radioSet.add("xingtu_rules");
        /*  38 */
        radioSet.add("xingtu_output");
        /*     */
    }

    /*     */
    /*  41 */   public static List<String> rules = new ArrayList<String>();
    /*     */
    /*     */
    /*     */
    /*  45 */   public static LinkedHashMap<String, Integer> m_config = new LinkedHashMap<String, Integer>();
    /*     */
    /*     */
    /*     */
    /*  49 */   public static LinkedHashMap<String, String> formatConfig = new LinkedHashMap<String, String>();

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static boolean initSysConf(String configPath) {
        /*  57 */
        m_config.clear();
        /*  58 */
        formatConfig.clear();
        /*     */
        /*  60 */
        File file = new File(configPath);
        /*  61 */
        BufferedReader reader = null;
        /*     */
        try {
            /*  63 */
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            /*  64 */
            String line = null;
            /*  65 */
            String[] items = null;
            /*  66 */
            while ((line = reader.readLine()) != null) {
                /*  67 */
                if (!line.startsWith("#") && !line.trim().isEmpty()) {
                    /*  68 */
                    items = line.split(":");
                    /*  69 */
                    if (radioSet.contains(items[0].trim())) {
                        /*     */
                        try {
                            /*  71 */
                            m_config.put(items[0].trim(), new Integer(items[1].trim()));
                            /*  72 */
                        } catch (Exception ex) {
                            /*  73 */
                            System.out.println("请在配置文件config.ini中设置[" + items[0].trim() + "配置项]");
                            /*  74 */
                            return false;
                            /*     */
                        }
                        /*     */
                        continue;
                        /*     */
                    }
                    /*     */
                    try {
                        /*  79 */
                        if (items.length >= 2) {
                            /*  80 */
                            formatConfig.put(items[0].trim(), line.substring(line.indexOf(":") + 1));
                            /*     */
                        }
                        /*  82 */
                    } catch (Exception ex) {
                        /*  83 */
                        System.out.println("请在配置文件config.ini中设置[" + items[0].trim() + "配置项]");
                        /*  84 */
                        return false;
                        /*     */
                    }
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /*     */
            /*  91 */
            reader.close();
            /*     */
        }
        /*  93 */ catch (IOException e) {
            /*  94 */
            e.printStackTrace();
            /*     */
        } finally {
            /*  96 */
            if (reader != null) {
                /*     */
                try {
                    /*  98 */
                    reader.close();
                    /*  99 */
                } catch (IOException e1) {
                }
                /*     */
            }
            /*     */
        }
        /*     */
        /*     */
        /* 104 */
        return true;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    /*     */
    public static void initRuleConf(String configPath) {
        /* 112 */
        File file = new File(configPath);
        /*     */
        /* 114 */
        SignatureManager signatureManager = SignatureFactory.getSignature("0001");
        /* 115 */
        BufferedReader reader = null;
        /*     */
        /*     */
        try {
            /* 118 */
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            /* 119 */
            String line = null;
            /* 120 */
            while ((line = reader.readLine()) != null) {
                /*     */
                /*     */
                try {
                    /* 123 */
                    byte[] decryptData = signatureManager.decrypt(Base64.decodeBase64(line));
                    /* 124 */
                    String source = new String(decryptData, "utf-8");
                    /*     */
                    /*     */
                    /* 127 */
                    if (!source.startsWith("#")) {
                        /* 128 */
                        rules.add(source);
                        /*     */
                    }
                    /* 130 */
                } catch (Exception e) {
                    /* 131 */
                    e.printStackTrace();
                    /*     */
                }
                /*     */
            }
            /*     */
            /*     */
            /* 136 */
            reader.close();
            /*     */
        }
        /* 138 */ catch (IOException e) {
            /* 139 */
            e.printStackTrace();
            /*     */
            /*     */
            /*     */
            /*     */
        }
        /*     */ finally {
            /*     */
            /*     */
            /*     */
            /* 148 */
            if (reader != null) {
                /*     */
                try {
                    /* 150 */
                    reader.close();
                    /* 151 */
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
    public static void initCustomRuleConf(String configPath) {
        /* 162 */
        File file = new File(configPath);
        /* 163 */
        BufferedReader reader = null;
        /*     */
        try {
            /* 165 */
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            /* 166 */
            String line = null;
            /* 167 */
            while ((line = reader.readLine()) != null) {
                /* 168 */
                if (!line.startsWith("#")) {
                    /* 169 */
                    rules.add(line);
                    /*     */
                }
                /*     */
            }
            /*     */
            /* 173 */
            reader.close();
            /*     */
        }
        /* 175 */ catch (IOException e) {
            /* 176 */
            e.printStackTrace();
            /*     */
        } finally {
            /* 178 */
            if (reader != null) {
                /*     */
                try {
                    /* 180 */
                    reader.close();
                    /* 181 */
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
    /* 187 */   private static Properties logformatProps = null;

    /*     */
    public static void initLogFormat(String configPath) {
        /*     */
        try {
            /* 190 */
            File logformatFile = new File(configPath);
            /* 191 */
            logformatProps = new Properties();
            /* 192 */
            logformatProps.load(new FileInputStream(logformatFile));
            /* 193 */
        } catch (FileNotFoundException e) {
            /* 194 */
            e.printStackTrace();
            /* 195 */
        } catch (IOException e) {
            /* 196 */
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
    public static String getConfig(String key) {
        /* 249 */
        return logformatProps.getProperty(key);
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static void main(String[] args) throws SystemConfigException {
///* 254 */     initSysConf("C:\\Users\\wangpeng3-s\\Desktop\\rzb\\single\\conf\\config.ini");
///*     */
///*     */
///*     */
///* 258 */     W3CFormatParser.initW3CFormat();
///* 259 */     String log = "2010-08-14 02:07:18 W3SVC21393 NNIWEB 216.107.0.72 GET /styles.css - 80 - 24.102.134.20 HTTP/1.1 Mozilla/5.0+(X11;+U;+Linux+x86_64;+en-US)+AppleWebKit/533.4+(KHTML,+like+Gecko)+Chrome/5.0.375.125+Safari/533.4 - http://royalelectric.com/ royalelectric.com 200 0 0 858 383 375";
///* 260 */     String log1 = "2014-08-13 05:00:00 W3SVC417834866 W11625519445 116.255.194.45 GET /forum.php mod=forumdisplay&fid=59&filter=digest&digest=1&typeid=25&sortid=4 80 - 61.147.92.99 HTTP/1.1 Mozilla/5.0+(X11;+Linux+i686;+rv:18.0)+Gecko/20100101+Firefox/18.0+Iceweasel/18.0.1 bbKL_2132_saltkey=H3y787ap;+bbKL_2132_lastvisit=1407902267;+bbKL_2132_sid=Nff75Z;+bbKL_2132_lastact=1407905984%09forum.php%09forumdisplay;+bbKL_2132_st_t=0%7C1407905984%7Cef29e8d16d3f92c58235edb7e713a688;+bbKL_2132_forum_lastvisit=D_59_1407905984;+bbKL_2132_visitedfid=59 http://www.chinapcu.com/forum.php?mod=forumdisplay&fid=59&filter=digest&digest=1&typeid=25&sortid=4 www.chinapcu.com 200 0 0 32264 873 11968";
///* 261 */     System.out.println(W3CFormatParser.parse(log, "testsite"));
        ConfigUtil.initRuleConf("F:\\otherwork\\java\\xingtu\\src\\main\\resources\\conf\\rules.ini");
        for (String line : ConfigUtil.rules) {
            System.out.println(line);
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\ConfigUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */