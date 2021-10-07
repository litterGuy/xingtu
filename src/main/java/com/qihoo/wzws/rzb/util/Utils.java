
package com.qihoo.wzws.rzb.util;


import com.qihoo.wzws.rzb.util.ip.IPDataHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utils {
    public static Map<String, String> sosuoMap = new HashMap<String, String>();
    private static final String TIME_LOCAL = "time_local";
    private static final String REMOTE_ADDR = "remote_addr";

    static {

        sosuoMap.put("googlebot", "google");

        sosuoMap.put("qh360bot", "360");

        sosuoMap.put("baidubot", "baidu");

        sosuoMap.put("sogou", "sogou");

        sosuoMap.put("bingbot", "bing");

        sosuoMap.put("yahoobot", "yahoo");

        sosuoMap.put("sosobot", "soso");

    }

    private static final String STATUS = "status";
    private static final String HOST = "host";
    private static final String REQUEST_URI = "request_uri";


    public enum LineCounters {
        ALL_LINES,
        VALID_LINES,
        BAD_LINES,


        MOBILE_LINES,
        NOMOBILE_LINES,
        MATCH_LINES,
        NOMATCH_LINES,


        PC_LINES,
        OTHER_LINES,


        GETREGION_LINES,
        NO_REGION_LINES,
        CN_REGION_LINES,
        NO_CNREGION_LINES;
    }


    protected enum CustomFormatFields {
        time_local,
        remote_addr,
        status,
        host,
        request_uri;

    }


    private static int timeIndex = 0;
    private static int ipIndex = 0;
    private static int statusIndex = 0;
    private static int hostIndex = 0;
    private static int urlIndex = 0;


    private static Map<String, Integer> map = new HashMap<String, Integer>();


    public static void loadSystemConfig(String sysConfigPath) {

        InputStream is = null;

        BufferedReader br = null;


        try {

            is = Utils.class.getResourceAsStream(sysConfigPath);

            br = new BufferedReader(new InputStreamReader(is));

            String s1 = null;

            String[] items = null;


            while ((s1 = br.readLine()) != null) {

                if (!s1.startsWith("#")) {

                    items = s1.split("\\|");

                    int size = items.length;

                    if (items.length > 0) {

                        for (int i = 0; i < items.length - 1; i++) {

                            map.put(items[i], Integer.valueOf(i));

                        }

                    }


                    timeIndex = ((Integer) map.get("time_local")).intValue();

                    ipIndex = ((Integer) map.get("remote_addr")).intValue();

                    statusIndex = ((Integer) map.get("status")).intValue();

                    hostIndex = ((Integer) map.get("host")).intValue();

                    urlIndex = ((Integer) map.get("request_uri")).intValue();

                }

            }


            br.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }


    private static final Set<String> set = new HashSet<String>();
    private static final String emailPatternStr = "^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    static {

        set.add("|");

        set.add("*");

        set.add("+");

    }


    public static String[] parseLog(String log, String separator) {

        if (set.contains(separator)) {

            separator = "\\" + separator;

        }


        String[] items = log.split(separator);

        return items;

    }


    public static String getMac() {

        StringBuilder sb = new StringBuilder();

        try {

            InetAddress address = InetAddress.getLocalHost();


            NetworkInterface ni = NetworkInterface.getByInetAddress(address);

            if (ni != null) {

                byte[] mac = ni.getHardwareAddress();

                if (mac != null) {


                    for (int i = 0; i < mac.length; i++) {

                        String s = String.format("%02X%s", new Object[]{Byte.valueOf(mac[i]), (i < mac.length - 1) ? "-" : ""});

                        sb.append(s.toLowerCase());

                    }

                } else {

                    System.out.println("Address doesn't exist or is not accessible.");

                }

            } else {

                System.out.println("Network Interface for the specified address is not found.");

            }

        } catch (UnknownHostException e) {


            e.printStackTrace();

        } catch (SocketException se) {

            se.printStackTrace();

        }


        return sb.toString();

    }


    private static final Pattern emailPattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.|_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");


    public static boolean checkEmail(String email) {

        if (null == email || "".equals(email) || email.trim().length() > 50) {

            return false;

        }

        Matcher m = emailPattern.matcher(email);

        return m.matches();

    }


    public static boolean isPVbyContentType(String sent_http_content_type, String status) {

        if ((sent_http_content_type.contains("text/html") || sent_http_content_type.contains("text/plain") || sent_http_content_type.contains("text/xml") || sent_http_content_type.contains("text/json")) && status.equals("200")) {


            return true;

        }


        return false;

    }


    public static boolean isPV(String request_uri, String status) {

        if ("200".equals(status) && !request_uri.contains(".js") && !request_uri.contains(".css") && !request_uri.contains(".jpg") && !request_uri.contains(".png") && !request_uri.contains(".gif") && !request_uri.contains(".ico") && !request_uri.contains(".txt") && !request_uri.contains(".log")) {


            return true;

        }


        return false;

    }


    public static boolean isPage(String request_uri) {

        if (!request_uri.contains(".js") && !request_uri.contains(".css") && !request_uri.contains(".jpg") && !request_uri.contains(".png") && !request_uri.contains(".gif") && !request_uri.contains(".ico") && !request_uri.contains(".txt") && !request_uri.contains(".log")) {


            return true;

        }


        return false;

    }


    public static File getlastestFileFromDir(File[] files) {

        if (files.length == 1) {

            return files[0];

        }


        List<Long> fileUpdateList = new ArrayList<Long>();

        Map<Long, Integer> fileListIndexMap = new HashMap<Long, Integer>();

        for (int i = 0; i < files.length; i++) {

            fileUpdateList.add(Long.valueOf(files[i].lastModified()));

            fileListIndexMap.put(Long.valueOf(files[i].lastModified()), Integer.valueOf(i));

        }


        Collections.sort(fileUpdateList);

        int lastestIndex = fileUpdateList.size() - 1;


        return files[((Integer) fileListIndexMap.get(fileUpdateList.get(lastestIndex))).intValue()];

    }


    public static String getIPRegionLocal(String ip) {

        String ipLocation = "";

        String line = IPDataHandler.findGeography(ip);

        if (line != null) {

            String[] strArray = line.split("\t");

            if (strArray.length >= 2) {

                if ("中国".equals(strArray[0])) {

                    ipLocation = "中国-" + strArray[1];

                    if (strArray.length >= 3) {

                        if (!strArray[1].equals(strArray[2])) {

                            ipLocation = ipLocation + strArray[2];

                        }

                        if (strArray.length == 4) {

                            ipLocation = ipLocation + "-" + strArray[3];

                        }

                    }

                } else {

                    ipLocation = strArray[0];

                }

            }

        }


        return ipLocation;

    }


    public static String getRemoteIp(String ips) {

        if (ips.indexOf(",") > 1) {

            int index = ips.indexOf(",");

            if (index > 1 && index < ips.length()) {

                return ips.substring(0, index);

            }

        }

        return ips;

    }


    public static String getLongestFromList(List<String> list) {

        int maxlength = ((String) list.get(0)).length();

        int maxlengthIndex = 0;


        for (int i = 1; i < list.size(); i++) {

            int length = ((String) list.get(i)).length();

            if (length > maxlength) {

                maxlength = length;

                maxlengthIndex = i;

            }

        }


        return list.get(maxlengthIndex);

    }


    public static void main(String[] args) {

        System.out.println(getRemoteIp("192.168.1.1,192.168.1.2,192.168.1.3"));

        System.out.println(getRemoteIp("192.168.1.5"));

    }

}