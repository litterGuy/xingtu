
package com.qihoo.wzws.rzb.util;


import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.parse.W3CFormatParser;
import com.qihoo.wzws.rzb.util.security.SignatureFactory;
import com.qihoo.wzws.rzb.util.security.SignatureManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;


public class ConfigUtil {
    private static Set<String> radioSet = new HashSet<String>();

    static {

        radioSet.add("xingtu_logtype");

        radioSet.add("xingtu_pagetype");

        radioSet.add("xingtu_httpcode");

        radioSet.add("xingtu_urltype");

        radioSet.add("xingtu_uniq");

        radioSet.add("xingtu_rules");

        radioSet.add("xingtu_output");

    }


    public static List<String> rules = new ArrayList<String>();


    public static LinkedHashMap<String, Integer> m_config = new LinkedHashMap<String, Integer>();


    public static LinkedHashMap<String, String> formatConfig = new LinkedHashMap<String, String>();


    public static boolean initSysConf(String configPath) {

        m_config.clear();

        formatConfig.clear();


        File file = new File(configPath);

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

            String line = null;

            String[] items = null;

            while ((line = reader.readLine()) != null) {

                if (!line.startsWith("#") && !line.trim().isEmpty()) {

                    items = line.split(":");

                    if (radioSet.contains(items[0].trim())) {

                        try {

                            m_config.put(items[0].trim(), new Integer(items[1].trim()));

                        } catch (Exception ex) {

                            System.out.println("请在配置文件config.ini中设置[" + items[0].trim() + "配置项]");

                            return false;

                        }

                        continue;

                    }

                    try {

                        if (items.length >= 2) {

                            formatConfig.put(items[0].trim(), line.substring(line.indexOf(":") + 1));

                        }

                    } catch (Exception ex) {

                        System.out.println("请在配置文件config.ini中设置[" + items[0].trim() + "配置项]");

                        return false;

                    }

                }

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


        return true;

    }


    public static void initRuleConf(String configPath) {

        File file = new File(configPath);


        SignatureManager signatureManager = SignatureFactory.getSignature("0001");

        BufferedReader reader = null;


        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

            String line = null;

            while ((line = reader.readLine()) != null) {


                try {

                    byte[] decryptData = signatureManager.decrypt(Base64.decodeBase64(line));

                    String source = new String(decryptData, "utf-8");


                    if (!source.startsWith("#")) {

                        rules.add(source);

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                }

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

    }


    public static void initCustomRuleConf(String configPath) {

        File file = new File(configPath);

        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));

            String line = null;

            while ((line = reader.readLine()) != null) {

                if (!line.startsWith("#")) {

                    rules.add(line);

                }

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

    }


    private static Properties logformatProps = null;


    public static void initLogFormat(String configPath) {

        try {

            File logformatFile = new File(configPath);

            logformatProps = new Properties();

            logformatProps.load(new FileInputStream(logformatFile));

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    public static String getConfig(String key) {

        return logformatProps.getProperty(key);

    }


    public static void main(String[] args) throws SystemConfigException {
//     initSysConf("C:\\Users\\wangpeng3-s\\Desktop\\rzb\\single\\conf\\config.ini");
//
//
//
//     W3CFormatParser.initW3CFormat();
//     String log = "2010-08-14 02:07:18 W3SVC21393 NNIWEB 216.107.0.72 GET /styles.css - 80 - 24.102.134.20 HTTP/1.1 Mozilla/5.0+(X11;+U;+Linux+x86_64;+en-US)+AppleWebKit/533.4+(KHTML,+like+Gecko)+Chrome/5.0.375.125+Safari/533.4 - http://royalelectric.com/ royalelectric.com 200 0 0 858 383 375";
//     String log1 = "2014-08-13 05:00:00 W3SVC417834866 W11625519445 116.255.194.45 GET /forum.php mod=forumdisplay&fid=59&filter=digest&digest=1&typeid=25&sortid=4 80 - 61.147.92.99 HTTP/1.1 Mozilla/5.0+(X11;+Linux+i686;+rv:18.0)+Gecko/20100101+Firefox/18.0+Iceweasel/18.0.1 bbKL_2132_saltkey=H3y787ap;+bbKL_2132_lastvisit=1407902267;+bbKL_2132_sid=Nff75Z;+bbKL_2132_lastact=1407905984%09forum.php%09forumdisplay;+bbKL_2132_st_t=0%7C1407905984%7Cef29e8d16d3f92c58235edb7e713a688;+bbKL_2132_forum_lastvisit=D_59_1407905984;+bbKL_2132_visitedfid=59 http://www.chinapcu.com/forum.php?mod=forumdisplay&fid=59&filter=digest&digest=1&typeid=25&sortid=4 www.chinapcu.com 200 0 0 32264 873 11968";
//     System.out.println(W3CFormatParser.parse(log, "testsite"));
        ConfigUtil.initRuleConf("F:\\otherwork\\java\\xingtu\\src\\main\\resources\\conf\\rules.ini");
        for (String line : ConfigUtil.rules) {
            System.out.println(line);
        }

    }

}