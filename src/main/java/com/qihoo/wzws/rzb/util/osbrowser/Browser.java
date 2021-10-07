
package com.qihoo.wzws.rzb.util.osbrowser;


import java.util.regex.Pattern;


public class Browser {
    public static final Pattern MQQBrowserPattern = Pattern.compile("MQQBrowser", 2);

    public static final Pattern UCWEBPattern = Pattern.compile("UCWEB|JUC|(IUC|JUC)UCWEB|UCBrowser|UC AppleWebKit", 2);

    public static final Pattern QIHOOPattern = Pattern.compile("360browser", 2);

    public static final Pattern OperaPattern = Pattern.compile("Opera (Mini|Mobi|Tablet)", 2);

    public static final Pattern OperaChinaPattern = Pattern.compile("Oupeng", 2);

    public static final Pattern baiduPattern = Pattern.compile("baidubrowser", 2);

    public static final Pattern MicroMessengerPattern = Pattern.compile("MicroMessenger", 2);


    public static final Pattern WeiboBrowserIOSDevicePattern = Pattern.compile("\\((iPhone\\d|iPad\\d|iPod\\d),\\d__weibo__.*\\)", 2);
    public static final Pattern WeiboBrowserPattern = Pattern.compile("__weibo__", 2);

    public static final Pattern IEMobilePattern = Pattern.compile("IEMobile", 2);

    public static final Pattern KJAVAPattern = Pattern.compile("MAUI WAP", 2);


    public static final Pattern MobileSafariPattern = Pattern.compile("AppleWebKit.*(Safari|Mobile)", 2);
    public static final Pattern andriodBrowserPattern = Pattern.compile("AppleWebkit.*Mobile", 2);

    public static final Pattern FirefoxPattern = Pattern.compile("Firefox", 2);
    public static final String MQQBrowser = "MQQBrowser";
    public static final String UCBrowser = "UCBrowser";
    public static final String QIHOOBrowser = "360browser";
    public static final String Opera = "Opera";
    public static final String BaiduBrowser = "baidubrowser";
    public static final String IEMobile = "IEMobile";
    public static final String AndriodBrowser = "AndriodBrowser";
    public static final String MobileSafariBrowser = "MobileSafari";
    public static final String MicroMessengerBrowser = "MicroMessenger";
    public static final String WeiboBrowser = "Weibo";
    public static final String KJAVABrowser = "KJAVABrowser";
    public static final String SymbianBrowser = "SymbianBrowser";
    public static final String chrome = "chrome";
    public static final String Firefox = "Firefox";
    public static final String Unknown = "Unknown";

}