
package com.qihoo.wzws.rzb.util.keyword;


import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class KWUtils {
    private static final String keywordReg = "(?:.*yahoo.+?[\\?|&]p=|.*google.+?q=|.*zhongsou\\.com.+?word=|.*zhongsou\\.com.+?w=|.*chinaso\\.com.+?wd=|.*sogou.+?query=|.*sogou.+?keyword=|.*baidu.+?wd=|.*baidu.+?word=|.*soso.+?w=|.*soso.+?query=|.*so.+?q=|.*bing.+?q=|.*youdao.+?q=)([^&]*)";
    private static final String searchEngine = "http:\\/\\/.*\\.(google\\.com(:\\d{1,}){0,1}\\/|google\\.cn(:\\d{1,}){0,1}\\/|google\\.com\\.hk(:\\d{1,}){0,1}\\/|google\\.com\\.tw(:\\d{1,}){0,1}\\/|baidu\\.com(:\\d{1,}){0,1}\\/|yahoo\\.com(:\\d{1,}){0,1}\\/|sogou\\.com(:\\d{1,}){0,1}\\/|soso.com(:\\d{1,}){0,1}\\/|so.com(:\\d{1,}){0,1}\\/|zhongsou\\.com(:\\d{1,}){0,1}\\/|bing\\.com(:\\d{1,}){0,1}\\/|youdao\\.com(:\\d{1,}){0,1}\\/|chinaso\\.com(:\\d{1,}){0,1}\\/)";


    public static void main(String[] args) {

        String httpReferer = "http://www.google.cn/search?q=%E6%8F%90%E5%8F%96+%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E+%E5%85%B3%E9%94%AE%E5%AD%97&hl=zh-CN&newwindow=1&sa=2";

        System.out.println(getSearch(httpReferer));

    }

    private static final String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$";
    private static final String messyCodeReg = "^([\\u4e00-\\u9fa5\\w\\s\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b\\uff01\\u2014\\u3010\\u3011\\u2018\\u2019.,\"\\?!:'\\[\\]@#$%*\\(\\)\\{\\}<>;=-])+$";
    private static final String includeSearchReg = "//(.*\\.google\\.com|.*\\.google\\.com\\.hk|.*\\.google\\.com\\.tw|.*\\.google\\.cn|.*\\.baidu\\.com|.*\\.so\\.com|.*\\.soso\\.com|.*\\.sogou\\.com|.*\\.yahoo\\.com|.*\\.bing\\.com|.*\\.chinaso\\.com|.*\\.zhongsou\\.com|.*\\.youdao\\.com)";


    public static String getKW(String httpReferer) {

        try {

            boolean isIncludeSearch = isIncludeSearch(httpReferer);

            if (isIncludeSearch) {

                String kwEncode = getKeywordEncode(httpReferer);

                if (kwEncode != null) {

                    String keyword = getKeywordDecode(kwEncode);

                    if (isNotMessyCode(keyword)) {

                        return keyword;

                    }

                    try {

                        return URLDecoder.decode(keyword, "gbk");

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();

                    }


                }

            }

        } catch (Exception ex) {
        }


        return null;

    }


    public static SearchKeyWordBean getKWAndSE(String httpReferer) {

        try {

            SearchKeyWordBean bean = null;

            String seName = getSearch(httpReferer);

            if (seName != null) {

                String kwEncode = getKeywordEncode(httpReferer);

                if (kwEncode != null) {

                    String keyword = getKeywordDecode(kwEncode);

                    if (isNotMessyCode(keyword)) {

                        bean = new SearchKeyWordBean();

                        bean.setSe(seName);

                        bean.setKw(keyword);

                        return bean;

                    }

                    try {

                        String kw = URLDecoder.decode(keyword, "gbk");

                        bean = new SearchKeyWordBean();

                        bean.setSe(seName);

                        bean.setKw(kw);

                    } catch (UnsupportedEncodingException e) {

                        e.printStackTrace();

                    }


                }

            }

        } catch (Exception ex) {
        }


        return null;

    }


    private static Pattern keywordPatt = Pattern.compile("(?:.*yahoo.+?[\\?|&]p=|.*google.+?q=|.*zhongsou\\.com.+?word=|.*zhongsou\\.com.+?w=|.*chinaso\\.com.+?wd=|.*sogou.+?query=|.*sogou.+?keyword=|.*baidu.+?wd=|.*baidu.+?word=|.*soso.+?w=|.*soso.+?query=|.*so.+?q=|.*bing.+?q=|.*youdao.+?q=)([^&]*)");
    private static Pattern searchPatt = Pattern.compile("http:\\/\\/.*\\.(google\\.com(:\\d{1,}){0,1}\\/|google\\.cn(:\\d{1,}){0,1}\\/|google\\.com\\.hk(:\\d{1,}){0,1}\\/|google\\.com\\.tw(:\\d{1,}){0,1}\\/|baidu\\.com(:\\d{1,}){0,1}\\/|yahoo\\.com(:\\d{1,}){0,1}\\/|sogou\\.com(:\\d{1,}){0,1}\\/|soso.com(:\\d{1,}){0,1}\\/|so.com(:\\d{1,}){0,1}\\/|zhongsou\\.com(:\\d{1,}){0,1}\\/|bing\\.com(:\\d{1,}){0,1}\\/|youdao\\.com(:\\d{1,}){0,1}\\/|chinaso\\.com(:\\d{1,}){0,1}\\/)");
    private static Pattern encodePatt = Pattern.compile("^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$");
    private static Pattern messyCodePatt = Pattern.compile("^([\\u4e00-\\u9fa5\\w\\s\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b\\uff01\\u2014\\u3010\\u3011\\u2018\\u2019.,\"\\?!:'\\[\\]@#$%*\\(\\)\\{\\}<>;=-])+$");
    private static Pattern includeSearchPatt = Pattern.compile("//(.*\\.google\\.com|.*\\.google\\.com\\.hk|.*\\.google\\.com\\.tw|.*\\.google\\.cn|.*\\.baidu\\.com|.*\\.so\\.com|.*\\.soso\\.com|.*\\.sogou\\.com|.*\\.yahoo\\.com|.*\\.bing\\.com|.*\\.chinaso\\.com|.*\\.zhongsou\\.com|.*\\.youdao\\.com)");


    public static boolean isNotMessyCode(String str) {

        Matcher m = messyCodePatt.matcher(str);

        if (m.matches()) {

            return true;

        }

        return false;

    }


    public static boolean isIncludeSearch(String refererStr) {

        if (refererStr.length() <= 512) {

            Matcher m = includeSearchPatt.matcher(refererStr);

            if (m.find()) {

                return true;

            }

            return false;

        }


        return false;

    }


    public static String getSearch(String refererStr) {

        if (refererStr.length() <= 512) {

            Matcher m = includeSearchPatt.matcher(refererStr);

            if (m.find() && m.groupCount() >= 1) {

                return m.group(1);

            }

        }

        return null;

    }


    public static String getKeywordEncode(String referer) {

        StringBuffer keyword = new StringBuffer(20);

        try {

            Matcher keywordMat = keywordPatt.matcher(referer);

            while (keywordMat.find()) {

                keywordMat.appendReplacement(keyword, "$1");

            }

        } catch (Exception e) {


            return null;

        }


        if (!keyword.toString().equals("")) {

            return keyword.toString();

        }

        return null;

    }


    public static String getKeywordDecode(String kwEncode) {

        try {

            String unescapeString = unescape(kwEncode);

            if (unescapeString != null) {

                Matcher encodeMat = encodePatt.matcher(unescapeString);

                String encodeString = "gbk";

                if (encodeMat.matches())
                    encodeString = "utf-8";

                String keywordStr = URLDecoder.decode(kwEncode, encodeString);


                return keywordStr;

            }

        } catch (Exception e) {


            return null;

        }

        return null;

    }


    private static String unescape(String src) {

        StringBuffer tmp = new StringBuffer();

        tmp.ensureCapacity(src.length());

        int lastPos = 0, pos = 0;


        while (lastPos < src.length()) {

            try {

                pos = src.indexOf("%", lastPos);

                if (pos == lastPos) {

                    if (src.charAt(pos + 1) == 'u') {

                        char c = (char) Integer.parseInt(src.substring(pos + 2, pos + 6), 16);


                        tmp.append(c);

                        lastPos = pos + 6;
                        continue;

                    }

                    char ch = (char) Integer.parseInt(src.substring(pos + 1, pos + 3), 16);


                    tmp.append(ch);

                    lastPos = pos + 3;

                    continue;

                }

                if (pos == -1) {

                    tmp.append(src.substring(lastPos));

                    lastPos = src.length();
                    continue;

                }

                tmp.append(src.substring(lastPos, pos));

                lastPos = pos;


            } catch (Exception e) {


                return null;

            }

        }


        return tmp.toString();

    }


    private static String insteadCode(String str, String regEx, String code) {

        Pattern p = Pattern.compile(regEx);

        Matcher m = p.matcher(str);

        String s = m.replaceAll(code);

        return s;

    }


    public static String loadConvert(char[] in, int off, int len, char[] convtBuf) {

        if (convtBuf.length < len) {

            int newLen = len * 2;

            if (newLen < 0) {

                newLen = Integer.MAX_VALUE;

            }

            convtBuf = new char[newLen];

        }


        char[] out = convtBuf;

        int outLen = 0;

        int end = off + len;


        while (off < end) {

            char aChar = in[off++];

            if (aChar == '\\') {

                aChar = in[off++];

                if (aChar == 'u') {


                    int value = 0;

                    for (int i = 0; i < 4; i++) {

                        aChar = in[off++];

                        switch (aChar) {

                            case '0':

                            case '1':

                            case '2':

                            case '3':

                            case '4':

                            case '5':

                            case '6':

                            case '7':

                            case '8':

                            case '9':

                                value = (value << 4) + aChar - 48;

                                break;

                            case 'a':

                            case 'b':

                            case 'c':

                            case 'd':

                            case 'e':

                            case 'f':

                                value = (value << 4) + 10 + aChar - 97;

                                break;

                            case 'A':

                            case 'B':

                            case 'C':

                            case 'D':

                            case 'E':

                            case 'F':

                                value = (value << 4) + 10 + aChar - 65;

                                break;

                            default:

                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");

                        }


                    }

                    out[outLen++] = (char) value;
                    continue;

                }

                if (aChar == 't') {

                    aChar = '\t';

                } else if (aChar == 'r') {

                    aChar = '\r';

                } else if (aChar == 'n') {

                    aChar = '\n';

                } else if (aChar == 'f') {

                    aChar = '\f';

                }
                out[outLen++] = aChar;

                continue;

            }

            out[outLen++] = aChar;

        }


        return new String(out, 0, outLen);

    }

}