/*     */ package com.qihoo.wzws.rzb.util.osbrowser.pc;
/*     */ 
/*     */ import com.qihoo.wzws.rzb.util.osbrowser.UserAgent;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PCUserAgentParser
/*     */ {
/*     */   private static final String REGEX_PC_WIN = "((?i:windows)\\s?\\w*)+?\\s?((\\d.\\d)+?)";
/*  16 */   private static Pattern PATTERN_PC_WIN = Pattern.compile("((?i:windows)\\s?\\w*)+?\\s?((\\d.\\d)+?)");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String REGEX_PC_MAC = "((?i:mac os)\\s?(?i:x)?)+?\\s?((\\d+.?\\d*[\\._]?\\d*)?)";
/*     */ 
/*     */   
/*  23 */   private static Pattern PATTERN_PC_MAC = Pattern.compile("((?i:mac os)\\s?(?i:x)?)+?\\s?((\\d+.?\\d*[\\._]?\\d*)?)");
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String REGEX_PC_LINUX = "((?i:linux|freebsd|SunOS|cros)+?)";
/*     */ 
/*     */   
/*  30 */   private static Pattern PATTERN_PC_LINUX = Pattern.compile("((?i:linux|freebsd|SunOS|cros)+?)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  38 */   private static final Pattern Windows8Pattern = Pattern.compile("windows nt 6\\.(2|3)", 2);
/*  39 */   private static final Pattern Windows7Pattern = Pattern.compile("windows nt 6\\.1", 2);
/*  40 */   private static final Pattern WindowsVistaPattern = Pattern.compile("windows nt 6\\.0", 2);
/*  41 */   private static final Pattern Windows2003Pattern = Pattern.compile("windows nt 5\\.2", 2);
/*  42 */   private static final Pattern WindowsXPPattern = Pattern.compile("windows nt 5\\.1", 2);
/*  43 */   private static final Pattern Windows2000Pattern = Pattern.compile("windows nt 5\\.0", 2);
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String REGEX_PC_OPERA = "((?i:opera)+)/[\\s?\\S*]+?((?i:windows\\s?nt)|(?i:mac\\s?os\\s?x)|(?i:freebsd|sunos|linux))+?\\s?(\\d+.?\\d*[_\\.]?\\d*)?";
/*     */ 
/*     */   
/*  50 */   private static Pattern PATTERN_PC_OPERA = Pattern.compile("((?i:opera)+)/[\\s?\\S*]+?((?i:windows\\s?nt)|(?i:mac\\s?os\\s?x)|(?i:freebsd|sunos|linux))+?\\s?(\\d+.?\\d*[_\\.]?\\d*)?");
/*     */   
/*     */   private static final String WIN = "Windows";
/*     */   
/*     */   private static final String MAC = "Mac OS";
/*     */   
/*     */   private static final String LINUX = "Linux";
/*     */   
/*     */   private static final String IE = "IE";
/*     */   
/*     */   private static final String _360SE = "360SE";
/*     */   
/*     */   private static final String theworldBrowser = "theworld";
/*     */   
/*     */   private static final String sougouBrowser = "Sougoubrowser";
/*     */   
/*     */   private static final String QQBrowser = "QQbrowser";
/*     */   
/*     */   private static final String TTBrowser = "TTbrowser";
/*     */   
/*     */   private static final String Maxthon = "Maxthon";
/*     */   
/*     */   private static final String TaoBrowser = "TaoBrowser";
/*     */   
/*     */   private static final String LBBROWSER = "LBBROWSER";
/*     */   
/*     */   private static final String UCWEB = "UCWEB";
/*     */   
/*     */   private static final String _2345Explorer = "2345Explorer";
/*     */   
/*     */   private static final String CoolNovo = "CoolNovo";
/*     */   
/*     */   private static final String BaiduBrowser = "Baidubrowser";
/*     */   
/*     */   private static final String Opera = "Opera";
/*     */   
/*     */   private static final String Chrome = "Chrome";
/*     */   
/*     */   private static final String Safari = "Safari";
/*     */   private static final String Firefox = "Firefox";
/*     */   private static final String Unknown = "Unknown";
/*  91 */   private static final Pattern _360SEPattern = Pattern.compile("360SE", 2);
/*     */   
/*  93 */   private static final Pattern theworldPattern = Pattern.compile("qihu theworld", 2);
/*     */   
/*  95 */   private static final Pattern SougouPattern = Pattern.compile(" SE |MetaSr", 2);
/*     */   
/*  97 */   private static final Pattern QQBrowserPattern = Pattern.compile("QQBrowser", 2);
/*     */   
/*  99 */   private static final Pattern TTPattern = Pattern.compile("TencentTraveler", 2);
/*     */   
/* 101 */   private static final Pattern MaxthonPattern = Pattern.compile("Maxthon", 2);
/*     */   
/* 103 */   private static final Pattern TaoBrowserPattern = Pattern.compile("TaoBrowser", 2);
/*     */   
/* 105 */   private static final Pattern LBBROWSERPattern = Pattern.compile("LBBROWSER", 2);
/*     */   
/* 107 */   private static final Pattern UCWEBPattern = Pattern.compile("UCWEB", 2);
/*     */   
/* 109 */   private static final Pattern _2345ExplorerPattern = Pattern.compile("2345Explorer", 2);
/*     */   
/* 111 */   private static final Pattern baidubrowserPattern = Pattern.compile("baidubrowser", 2);
/*     */ 
/*     */   
/* 114 */   private static final Pattern CoolNovoPattern = Pattern.compile("CoolNovo", 2);
/*     */   
/* 116 */   private static final Pattern ChromePattern = Pattern.compile("Chrome", 2);
/*     */   
/* 118 */   private static final Pattern SafariPattern = Pattern.compile("Safari", 2);
/*     */   
/* 120 */   private static final Pattern FirefoxPattern = Pattern.compile("Firefox", 2);
/*     */   
/* 122 */   private static final Pattern OperaPattern = Pattern.compile("Opera", 2);
/*     */ 
/*     */   
/* 125 */   private static final Pattern IEPattern = Pattern.compile("MSIE ?(\\d+\\.\\d+\\w?+);", 2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   private static final Pattern IEOTHERPattern = Pattern.compile("MSIE ?(\\d+\\.\\d+\\w?+);", 2);
/* 131 */   private static final Pattern IE11Pattern = Pattern.compile("rv:(\\d+\\.\\d+)", 2);
/*     */   
/*     */   private static boolean matches(Pattern pattern, String userAgentStr) {
/* 134 */     return pattern.matcher(userAgentStr).find();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyBrowser(String userAgentString, UserAgent ua) {
/* 142 */     if (matches(_360SEPattern, userAgentString)) {
/* 143 */       ua.setBrowser("360SE");
/* 144 */     } else if (matches(theworldPattern, userAgentString)) {
/* 145 */       ua.setBrowser("theworld");
/* 146 */     } else if (matches(SougouPattern, userAgentString)) {
/* 147 */       ua.setBrowser("Sougoubrowser");
/* 148 */     } else if (matches(QQBrowserPattern, userAgentString)) {
/* 149 */       ua.setBrowser("QQbrowser");
/* 150 */     } else if (matches(TTPattern, userAgentString)) {
/* 151 */       ua.setBrowser("TTbrowser");
/* 152 */     } else if (matches(MaxthonPattern, userAgentString)) {
/* 153 */       ua.setBrowser("Maxthon");
/* 154 */     } else if (matches(TaoBrowserPattern, userAgentString)) {
/* 155 */       ua.setBrowser("TaoBrowser");
/* 156 */     } else if (matches(LBBROWSERPattern, userAgentString)) {
/* 157 */       ua.setBrowser("LBBROWSER");
/* 158 */     } else if (matches(UCWEBPattern, userAgentString)) {
/* 159 */       ua.setBrowser("UCWEB");
/* 160 */     } else if (matches(_2345ExplorerPattern, userAgentString)) {
/* 161 */       ua.setBrowser("2345Explorer");
/* 162 */     } else if (matches(baidubrowserPattern, userAgentString)) {
/* 163 */       ua.setBrowser("Baidubrowser");
/* 164 */     } else if (matches(CoolNovoPattern, userAgentString)) {
/* 165 */       ua.setBrowser("CoolNovo");
/* 166 */     } else if (matches(ChromePattern, userAgentString)) {
/* 167 */       ua.setBrowser("Chrome");
/* 168 */     } else if (matches(SafariPattern, userAgentString)) {
/* 169 */       ua.setBrowser("Safari");
/* 170 */     } else if (matches(FirefoxPattern, userAgentString)) {
/* 171 */       ua.setBrowser("Firefox");
/* 172 */     } else if (matches(OperaPattern, userAgentString)) {
/* 173 */       ua.setBrowser("Opera");
/*     */     } else {
/* 175 */       Matcher IEMatcher = IEPattern.matcher(userAgentString);
/* 176 */       Matcher IE11Matcher = IE11Pattern.matcher(userAgentString);
/* 177 */       if (IEMatcher.find() && IEMatcher.groupCount() >= 1) {
/* 178 */         ua.setBrowser("IE");
/* 179 */         ua.setBrowserVersion(IEMatcher.group(1));
/* 180 */       } else if (IE11Matcher.find() && IE11Matcher.groupCount() >= 1) {
/* 181 */         ua.setBrowser("IE");
/* 182 */         ua.setBrowserVersion(IE11Matcher.group(1));
/* 183 */       } else if (userAgentString.indexOf("MSIE;") > 0) {
/* 184 */         ua.setBrowser("IE");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void applyWinVersion(String userAgentString, UserAgent ua) {
/* 192 */     if (matches(Windows2000Pattern, userAgentString)) {
/* 193 */       ua.setOsVersion("2000");
/* 194 */     } else if (matches(Windows2003Pattern, userAgentString)) {
/* 195 */       ua.setOsVersion("2003");
/* 196 */     } else if (matches(Windows7Pattern, userAgentString)) {
/* 197 */       ua.setOsVersion("7");
/* 198 */     } else if (matches(Windows8Pattern, userAgentString)) {
/* 199 */       ua.setOsVersion("8");
/* 200 */     } else if (matches(WindowsVistaPattern, userAgentString)) {
/* 201 */       ua.setOsVersion("Vista");
/* 202 */     } else if (matches(WindowsXPPattern, userAgentString)) {
/* 203 */       ua.setOsVersion("XP");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UserAgent parse(String userAgentString) {
/*     */     UserAgent ua;
/* 215 */     if ((ua = parsePCWin(userAgentString)) != null || (ua = parsePCOpera(userAgentString)) != null || (ua = parsePCLinux(userAgentString)) != null || (ua = parsePCMac(userAgentString)) != null)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 220 */       return ua;
/*     */     }
/*     */     
/* 223 */     if (userAgentString.indexOf("MSIE") > -1) {
/* 224 */       ua = new UserAgent();
/* 225 */       ua.setPlatform("Windows");
/* 226 */       ua.setBrowser("IE");
/*     */     } 
/*     */     
/* 229 */     return ua;
/*     */   }
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
/*     */   private UserAgent parsePCWin(String userAgentString) {
/* 242 */     Matcher match = PATTERN_PC_WIN.matcher(userAgentString);
/* 243 */     if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Opera")) {
/* 244 */       UserAgent ua = new UserAgent();
/*     */       
/* 246 */       ua.setPlatform("Windows");
/*     */       
/* 248 */       applyWinVersion(userAgentString, ua);
/* 249 */       applyBrowser(userAgentString, ua);
/*     */       
/* 251 */       return ua;
/* 252 */     }  if (userAgentString.indexOf("Microsoft Windows 7") > 0) {
/* 253 */       UserAgent ua = new UserAgent();
/* 254 */       ua.setPlatform("Windows");
/* 255 */       ua.setOsVersion("7");
/* 256 */       applyBrowser(userAgentString, ua);
/*     */     } 
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UserAgent parsePCMac(String userAgentString) {
/* 268 */     Matcher match = PATTERN_PC_MAC.matcher(userAgentString);
/* 269 */     if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Opera") && !userAgentString.contains("iPhone") && !userAgentString.contains("iPod") && !userAgentString.contains("iPad") && !userAgentString.contains("iPod touch")) {
/*     */ 
/*     */       
/* 272 */       UserAgent ua = new UserAgent();
/* 273 */       ua.setPlatform("Mac OS");
/*     */       
/* 275 */       applyBrowser(userAgentString, ua);
/* 276 */       return ua;
/*     */     } 
/* 278 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UserAgent parsePCLinux(String userAgentString) {
/* 288 */     Matcher match = PATTERN_PC_LINUX.matcher(userAgentString);
/* 289 */     if (match.find() && match.groupCount() >= 1 && !userAgentString.contains("Android") && !userAgentString.contains("Opera")) {
/* 290 */       UserAgent ua = new UserAgent();
/* 291 */       ua.setPlatform("Linux");
/*     */ 
/*     */ 
/*     */       
/* 295 */       applyBrowser(userAgentString, ua);
/* 296 */       return ua;
/*     */     } 
/* 298 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private UserAgent parsePCOpera(String userAgentString) {
/* 308 */     Matcher match = PATTERN_PC_OPERA.matcher(userAgentString);
/* 309 */     if (match.find() && match.groupCount() >= 2) {
/* 310 */       UserAgent ua = new UserAgent();
/* 311 */       ua.setBrowser("Opera");
/*     */       
/* 313 */       if (match.group(2).equalsIgnoreCase("windows"))
/*     */       {
/* 315 */         ua.setPlatform("Windows");
/*     */       }
/*     */       
/* 318 */       if (match.group(2).equalsIgnoreCase("mac os x")) {
/* 319 */         ua.setPlatform("Mac OS");
/*     */       }
/*     */       
/* 322 */       if (userAgentString.indexOf("Windows") > 0) {
/* 323 */         ua.setPlatform("Windows");
/* 324 */         applyWinVersion(userAgentString, ua);
/*     */       } 
/*     */       
/* 327 */       applyBrowser(userAgentString, ua);
/*     */       
/* 329 */       return ua;
/*     */     } 
/* 331 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/* 336 */     String userAgentString = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)";
/*     */     
/* 338 */     PCUserAgentParser parse = new PCUserAgentParser();
/* 339 */     System.out.println(parse.parsePCWin(userAgentString));
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\osbrowser\pc\PCUserAgentParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */