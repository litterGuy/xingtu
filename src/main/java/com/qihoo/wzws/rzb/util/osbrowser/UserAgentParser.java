
package com.qihoo.wzws.rzb.util.osbrowser;


import com.qihoo.wzws.rzb.util.osbrowser.pc.PCUserAgentParser;

import java.util.regex.Pattern;


public class UserAgentParser {

    public UserAgent parse(String userAgentString) {

        UserAgent userAgent = new UserAgent();

        userAgent.setPlatform(platform(userAgentString));

        browser(userAgentString, userAgent);


        return userAgent;

    }


    public static String platform(String userAgentString) {

        if (matches(Platform.AndroidPattern, userAgentString)) {
            return "Android";
        }

        if (matches(Platform.iPadPattern, userAgentString)) {
            return "iOS";
        }

        if (matches(Platform.iPodPattern, userAgentString)) {
            return "iOS";
        }

        if (matches(Platform.iPhonePattern, userAgentString)) {
            return "iOS";
        }

        if (matches(Platform.WindowsPhonePattern, userAgentString)) {
            return "WindowsPhone";
        }

        if (matches(Platform.SymbianPattern, userAgentString)) {
            return "Symbian";
        }

        if (matches(Platform.JavaPattern, userAgentString)) {
            return "Java";
        }

        if (matches(Platform.BlackberryPattern, userAgentString)) {

            return "Blackberry";

        }

        return "Unknown";

    }


    public void browser(String userAgentString, UserAgent userAgent) {

        if (matches(Browser.MQQBrowserPattern, userAgentString)) {

            userAgent.setBrowser("MQQBrowser");

        } else if (matches(Browser.UCWEBPattern, userAgentString)) {

            userAgent.setBrowser("UCBrowser");

        } else if (matches(Browser.QIHOOPattern, userAgentString)) {

            userAgent.setBrowser("360browser");

        } else if (matches(Browser.baiduPattern, userAgentString)) {

            userAgent.setBrowser("baidubrowser");

        } else if (matches(Browser.OperaPattern, userAgentString) || matches(Browser.OperaChinaPattern, userAgentString)) {

            userAgent.setBrowser("Opera");

        } else if (matches(Browser.MicroMessengerPattern, userAgentString)) {

            userAgent.setBrowser("MicroMessenger");

        } else if (matches(Browser.WeiboBrowserPattern, userAgentString)) {

            userAgent.setBrowser("Weibo");

        } else if (matches(Browser.KJAVAPattern, userAgentString)) {

            userAgent.setBrowser("KJAVABrowser");

        } else if (matches(Browser.IEMobilePattern, userAgentString)) {

            userAgent.setBrowser("IEMobile");


        } else if ("Android".equals(userAgent.getPlatform())) {


            userAgent.setBrowser("AndriodBrowser");

        } else if ("iOS".equals(userAgent.getPlatform())) {

            userAgent.setBrowser("MobileSafari");

        } else if ("Symbian".equals(userAgent.getPlatform())) {

            userAgent.setBrowser("SymbianBrowser");

        } else if ("WindowsPhone".equals(userAgent.getPlatform())) {


        }

    }


    public static boolean matches(Pattern pattern, String userAgentStr) {

        return pattern.matcher(userAgentStr).find();

    }


    public static boolean isMobileAccess(String userAgentStr) {

        if ("Unknown".equals(platform(userAgentStr))) {

            return false;

        }

        return true;

    }


    public static PCUserAgentParser pcUserAgentParser = new PCUserAgentParser();


    public static UserAgent getUA(String userAgentStr) {

        UserAgent ua = null;

        try {

            if (isMobileAccess(userAgentStr)) {

                ua = new UserAgent();

                ua.setPlatform("移动端-操作系统");

                ua.setBrowser("移动端-浏览器");

            } else {

                ua = pcUserAgentParser.parse(userAgentStr);

            }

        } catch (Exception ex) {
        }


        return ua;

    }

}