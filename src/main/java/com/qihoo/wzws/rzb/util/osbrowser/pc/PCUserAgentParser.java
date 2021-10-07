
package com.qihoo.wzws.rzb.util.osbrowser.pc;


import com.qihoo.wzws.rzb.util.osbrowser.UserAgent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class PCUserAgentParser {
    private static final String REGEX_PC_WIN = "((?i:windows)\\s?\\w*)+?\\s?((\\d.\\d)+?)";
    private static Pattern PATTERN_PC_WIN = Pattern.compile("((?i:windows)\\s?\\w*)+?\\s?((\\d.\\d)+?)");


    private static final String REGEX_PC_MAC = "((?i:mac os)\\s?(?i:x)?)+?\\s?((\\d+.?\\d*[\\._]?\\d*)?)";


    private static Pattern PATTERN_PC_MAC = Pattern.compile("((?i:mac os)\\s?(?i:x)?)+?\\s?((\\d+.?\\d*[\\._]?\\d*)?)");


    private static final String REGEX_PC_LINUX = "((?i:linux|freebsd|SunOS|cros)+?)";


    private static Pattern PATTERN_PC_LINUX = Pattern.compile("((?i:linux|freebsd|SunOS|cros)+?)");


    private static final Pattern Windows8Pattern = Pattern.compile("windows nt 6\\.(2|3)", 2);
    private static final Pattern Windows7Pattern = Pattern.compile("windows nt 6\\.1", 2);
    private static final Pattern WindowsVistaPattern = Pattern.compile("windows nt 6\\.0", 2);
    private static final Pattern Windows2003Pattern = Pattern.compile("windows nt 5\\.2", 2);
    private static final Pattern WindowsXPPattern = Pattern.compile("windows nt 5\\.1", 2);
    private static final Pattern Windows2000Pattern = Pattern.compile("windows nt 5\\.0", 2);


    private static final String REGEX_PC_OPERA = "((?i:opera)+)/[\\s?\\S*]+?((?i:windows\\s?nt)|(?i:mac\\s?os\\s?x)|(?i:freebsd|sunos|linux))+?\\s?(\\d+.?\\d*[_\\.]?\\d*)?";


    private static Pattern PATTERN_PC_OPERA = Pattern.compile("((?i:opera)+)/[\\s?\\S*]+?((?i:windows\\s?nt)|(?i:mac\\s?os\\s?x)|(?i:freebsd|sunos|linux))+?\\s?(\\d+.?\\d*[_\\.]?\\d*)?");

    private static final String WIN = "Windows";

    private static final String MAC = "Mac OS";

    private static final String LINUX = "Linux";

    private static final String IE = "IE";

    private static final String _360SE = "360SE";

    private static final String theworldBrowser = "theworld";

    private static final String sougouBrowser = "Sougoubrowser";

    private static final String QQBrowser = "QQbrowser";

    private static final String TTBrowser = "TTbrowser";

    private static final String Maxthon = "Maxthon";

    private static final String TaoBrowser = "TaoBrowser";

    private static final String LBBROWSER = "LBBROWSER";

    private static final String UCWEB = "UCWEB";

    private static final String _2345Explorer = "2345Explorer";

    private static final String CoolNovo = "CoolNovo";

    private static final String BaiduBrowser = "Baidubrowser";

    private static final String Opera = "Opera";

    private static final String Chrome = "Chrome";

    private static final String Safari = "Safari";
    private static final String Firefox = "Firefox";
    private static final String Unknown = "Unknown";
    private static final Pattern _360SEPattern = Pattern.compile("360SE", 2);

    private static final Pattern theworldPattern = Pattern.compile("qihu theworld", 2);

    private static final Pattern SougouPattern = Pattern.compile(" SE |MetaSr", 2);

    private static final Pattern QQBrowserPattern = Pattern.compile("QQBrowser", 2);

    private static final Pattern TTPattern = Pattern.compile("TencentTraveler", 2);

    private static final Pattern MaxthonPattern = Pattern.compile("Maxthon", 2);

    private static final Pattern TaoBrowserPattern = Pattern.compile("TaoBrowser", 2);

    private static final Pattern LBBROWSERPattern = Pattern.compile("LBBROWSER", 2);

    private static final Pattern UCWEBPattern = Pattern.compile("UCWEB", 2);

    private static final Pattern _2345ExplorerPattern = Pattern.compile("2345Explorer", 2);

    private static final Pattern baidubrowserPattern = Pattern.compile("baidubrowser", 2);


    private static final Pattern CoolNovoPattern = Pattern.compile("CoolNovo", 2);

    private static final Pattern ChromePattern = Pattern.compile("Chrome", 2);

    private static final Pattern SafariPattern = Pattern.compile("Safari", 2);

    private static final Pattern FirefoxPattern = Pattern.compile("Firefox", 2);

    private static final Pattern OperaPattern = Pattern.compile("Opera", 2);


    private static final Pattern IEPattern = Pattern.compile("MSIE ?(\\d+\\.\\d+\\w?+);", 2);


    private static final Pattern IEOTHERPattern = Pattern.compile("MSIE ?(\\d+\\.\\d+\\w?+);", 2);
    private static final Pattern IE11Pattern = Pattern.compile("rv:(\\d+\\.\\d+)", 2);


    private static boolean matches(Pattern pattern, String userAgentStr) {

        return pattern.matcher(userAgentStr).find();

    }


    private void applyBrowser(String userAgentString, UserAgent ua) {

        if (matches(_360SEPattern, userAgentString)) {

            ua.setBrowser("360SE");

        } else if (matches(theworldPattern, userAgentString)) {

            ua.setBrowser("theworld");

        } else if (matches(SougouPattern, userAgentString)) {

            ua.setBrowser("Sougoubrowser");

        } else if (matches(QQBrowserPattern, userAgentString)) {

            ua.setBrowser("QQbrowser");

        } else if (matches(TTPattern, userAgentString)) {

            ua.setBrowser("TTbrowser");

        } else if (matches(MaxthonPattern, userAgentString)) {

            ua.setBrowser("Maxthon");

        } else if (matches(TaoBrowserPattern, userAgentString)) {

            ua.setBrowser("TaoBrowser");

        } else if (matches(LBBROWSERPattern, userAgentString)) {

            ua.setBrowser("LBBROWSER");

        } else if (matches(UCWEBPattern, userAgentString)) {

            ua.setBrowser("UCWEB");

        } else if (matches(_2345ExplorerPattern, userAgentString)) {

            ua.setBrowser("2345Explorer");

        } else if (matches(baidubrowserPattern, userAgentString)) {

            ua.setBrowser("Baidubrowser");

        } else if (matches(CoolNovoPattern, userAgentString)) {

            ua.setBrowser("CoolNovo");

        } else if (matches(ChromePattern, userAgentString)) {

            ua.setBrowser("Chrome");

        } else if (matches(SafariPattern, userAgentString)) {

            ua.setBrowser("Safari");

        } else if (matches(FirefoxPattern, userAgentString)) {

            ua.setBrowser("Firefox");

        } else if (matches(OperaPattern, userAgentString)) {

            ua.setBrowser("Opera");

        } else {

            Matcher IEMatcher = IEPattern.matcher(userAgentString);

            Matcher IE11Matcher = IE11Pattern.matcher(userAgentString);

            if (IEMatcher.find() && IEMatcher.groupCount() >= 1) {

                ua.setBrowser("IE");

                ua.setBrowserVersion(IEMatcher.group(1));

            } else if (IE11Matcher.find() && IE11Matcher.groupCount() >= 1) {

                ua.setBrowser("IE");

                ua.setBrowserVersion(IE11Matcher.group(1));

            } else if (userAgentString.indexOf("MSIE;") > 0) {

                ua.setBrowser("IE");

            }

        }

    }


    private void applyWinVersion(String userAgentString, UserAgent ua) {

        if (matches(Windows2000Pattern, userAgentString)) {

            ua.setOsVersion("2000");

        } else if (matches(Windows2003Pattern, userAgentString)) {

            ua.setOsVersion("2003");

        } else if (matches(Windows7Pattern, userAgentString)) {

            ua.setOsVersion("7");

        } else if (matches(Windows8Pattern, userAgentString)) {

            ua.setOsVersion("8");

        } else if (matches(WindowsVistaPattern, userAgentString)) {

            ua.setOsVersion("Vista");

        } else if (matches(WindowsXPPattern, userAgentString)) {

            ua.setOsVersion("XP");

        }

    }


    public UserAgent parse(String userAgentString) {

        UserAgent ua;

        if ((ua = parsePCWin(userAgentString)) != null || (ua = parsePCOpera(userAgentString)) != null || (ua = parsePCLinux(userAgentString)) != null || (ua = parsePCMac(userAgentString)) != null) {


            return ua;

        }


        if (userAgentString.indexOf("MSIE") > -1) {

            ua = new UserAgent();

            ua.setPlatform("Windows");

            ua.setBrowser("IE");

        }


        return ua;

    }


    private UserAgent parsePCWin(String userAgentString) {

        Matcher match = PATTERN_PC_WIN.matcher(userAgentString);

        if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Opera")) {

            UserAgent ua = new UserAgent();


            ua.setPlatform("Windows");


            applyWinVersion(userAgentString, ua);

            applyBrowser(userAgentString, ua);


            return ua;

        }
        if (userAgentString.indexOf("Microsoft Windows 7") > 0) {

            UserAgent ua = new UserAgent();

            ua.setPlatform("Windows");

            ua.setOsVersion("7");

            applyBrowser(userAgentString, ua);

        }

        return null;

    }


    private UserAgent parsePCMac(String userAgentString) {

        Matcher match = PATTERN_PC_MAC.matcher(userAgentString);

        if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Opera") && !userAgentString.contains("iPhone") && !userAgentString.contains("iPod") && !userAgentString.contains("iPad") && !userAgentString.contains("iPod touch")) {


            UserAgent ua = new UserAgent();

            ua.setPlatform("Mac OS");


            applyBrowser(userAgentString, ua);

            return ua;

        }

        return null;

    }


    private UserAgent parsePCLinux(String userAgentString) {

        Matcher match = PATTERN_PC_LINUX.matcher(userAgentString);

        if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Android") && !userAgentString.contains("Opera")) {

            UserAgent ua = new UserAgent();

            ua.setPlatform("Linux");


            applyBrowser(userAgentString, ua);

            return ua;

        }

        return null;

    }


    private UserAgent parsePCOpera(String userAgentString) {

        Matcher match = PATTERN_PC_OPERA.matcher(userAgentString);

        if (match.find() && match.groupCount() >= 2) {

            UserAgent ua = new UserAgent();

            ua.setBrowser("Opera");


            if (match.group(2).equalsIgnoreCase("windows")) {

                ua.setPlatform("Windows");

            }


            if (match.group(2).equalsIgnoreCase("mac os x")) {

                ua.setPlatform("Mac OS");

            }


            if (userAgentString.indexOf("Windows") > 0) {

                ua.setPlatform("Windows");

                applyWinVersion(userAgentString, ua);

            }


            applyBrowser(userAgentString, ua);


            return ua;

        }

        return null;

    }


    public static void main(String[] args) {

        String userAgentString = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)";


        PCUserAgentParser parse = new PCUserAgentParser();

        System.out.println(parse.parsePCWin(userAgentString));

    }

}