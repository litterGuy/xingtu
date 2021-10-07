/*     */ package com.qihoo.wzws.rzb.util.keyword;
/*     */ import com.qihoo.wzws.rzb.routine.po.SearchKeyWordBean;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URLDecoder;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class KWUtils {
/*     */   private static final String keywordReg = "(?:.*yahoo.+?[\\?|&]p=|.*google.+?q=|.*zhongsou\\.com.+?word=|.*zhongsou\\.com.+?w=|.*chinaso\\.com.+?wd=|.*sogou.+?query=|.*sogou.+?keyword=|.*baidu.+?wd=|.*baidu.+?word=|.*soso.+?w=|.*soso.+?query=|.*so.+?q=|.*bing.+?q=|.*youdao.+?q=)([^&]*)";
/*     */   private static final String searchEngine = "http:\\/\\/.*\\.(google\\.com(:\\d{1,}){0,1}\\/|google\\.cn(:\\d{1,}){0,1}\\/|google\\.com\\.hk(:\\d{1,}){0,1}\\/|google\\.com\\.tw(:\\d{1,}){0,1}\\/|baidu\\.com(:\\d{1,}){0,1}\\/|yahoo\\.com(:\\d{1,}){0,1}\\/|sogou\\.com(:\\d{1,}){0,1}\\/|soso.com(:\\d{1,}){0,1}\\/|so.com(:\\d{1,}){0,1}\\/|zhongsou\\.com(:\\d{1,}){0,1}\\/|bing\\.com(:\\d{1,}){0,1}\\/|youdao\\.com(:\\d{1,}){0,1}\\/|chinaso\\.com(:\\d{1,}){0,1}\\/)";
/*     */   
/*     */   public static void main(String[] args) {
/*  13 */     String httpReferer = "http://www.google.cn/search?q=%E6%8F%90%E5%8F%96+%E6%90%9C%E7%B4%A2%E5%BC%95%E6%93%8E+%E5%85%B3%E9%94%AE%E5%AD%97&hl=zh-CN&newwindow=1&sa=2";
/*  14 */     System.out.println(getSearch(httpReferer));
/*     */   }
/*     */   private static final String encodeReg = "^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$"; private static final String messyCodeReg = "^([\\u4e00-\\u9fa5\\w\\s\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b\\uff01\\u2014\\u3010\\u3011\\u2018\\u2019.,\"\\?!:'\\[\\]@#$%*\\(\\)\\{\\}<>;=-])+$"; private static final String includeSearchReg = "//(.*\\.google\\.com|.*\\.google\\.com\\.hk|.*\\.google\\.com\\.tw|.*\\.google\\.cn|.*\\.baidu\\.com|.*\\.so\\.com|.*\\.soso\\.com|.*\\.sogou\\.com|.*\\.yahoo\\.com|.*\\.bing\\.com|.*\\.chinaso\\.com|.*\\.zhongsou\\.com|.*\\.youdao\\.com)";
/*     */   public static String getKW(String httpReferer) {
/*     */     try {
/*  19 */       boolean isIncludeSearch = isIncludeSearch(httpReferer);
/*  20 */       if (isIncludeSearch) {
/*  21 */         String kwEncode = getKeywordEncode(httpReferer);
/*  22 */         if (kwEncode != null) {
/*  23 */           String keyword = getKeywordDecode(kwEncode);
/*  24 */           if (isNotMessyCode(keyword)) {
/*  25 */             return keyword;
/*     */           }
/*     */           try {
/*  28 */             return URLDecoder.decode(keyword, "gbk");
/*  29 */           } catch (UnsupportedEncodingException e) {
/*  30 */             e.printStackTrace();
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*  35 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/*  39 */     return null;
/*     */   }
/*     */   
/*     */   public static SearchKeyWordBean getKWAndSE(String httpReferer) {
/*     */     try {
/*  44 */       SearchKeyWordBean bean = null;
/*  45 */       String seName = getSearch(httpReferer);
/*  46 */       if (seName != null) {
/*  47 */         String kwEncode = getKeywordEncode(httpReferer);
/*  48 */         if (kwEncode != null) {
/*  49 */           String keyword = getKeywordDecode(kwEncode);
/*  50 */           if (isNotMessyCode(keyword)) {
/*  51 */             bean = new SearchKeyWordBean();
/*  52 */             bean.setSe(seName);
/*  53 */             bean.setKw(keyword);
/*  54 */             return bean;
/*     */           } 
/*     */           try {
/*  57 */             String kw = URLDecoder.decode(keyword, "gbk");
/*  58 */             bean = new SearchKeyWordBean();
/*  59 */             bean.setSe(seName);
/*  60 */             bean.setKw(kw);
/*  61 */           } catch (UnsupportedEncodingException e) {
/*  62 */             e.printStackTrace();
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*  67 */     } catch (Exception ex) {}
/*     */ 
/*     */ 
/*     */     
/*  71 */     return null;
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
/* 109 */   private static Pattern keywordPatt = Pattern.compile("(?:.*yahoo.+?[\\?|&]p=|.*google.+?q=|.*zhongsou\\.com.+?word=|.*zhongsou\\.com.+?w=|.*chinaso\\.com.+?wd=|.*sogou.+?query=|.*sogou.+?keyword=|.*baidu.+?wd=|.*baidu.+?word=|.*soso.+?w=|.*soso.+?query=|.*so.+?q=|.*bing.+?q=|.*youdao.+?q=)([^&]*)");
/* 110 */   private static Pattern searchPatt = Pattern.compile("http:\\/\\/.*\\.(google\\.com(:\\d{1,}){0,1}\\/|google\\.cn(:\\d{1,}){0,1}\\/|google\\.com\\.hk(:\\d{1,}){0,1}\\/|google\\.com\\.tw(:\\d{1,}){0,1}\\/|baidu\\.com(:\\d{1,}){0,1}\\/|yahoo\\.com(:\\d{1,}){0,1}\\/|sogou\\.com(:\\d{1,}){0,1}\\/|soso.com(:\\d{1,}){0,1}\\/|so.com(:\\d{1,}){0,1}\\/|zhongsou\\.com(:\\d{1,}){0,1}\\/|bing\\.com(:\\d{1,}){0,1}\\/|youdao\\.com(:\\d{1,}){0,1}\\/|chinaso\\.com(:\\d{1,}){0,1}\\/)");
/* 111 */   private static Pattern encodePatt = Pattern.compile("^(?:[\\x00-\\x7f]|[\\xfc-\\xff][\\x80-\\xbf]{5}|[\\xf8-\\xfb][\\x80-\\xbf]{4}|[\\xf0-\\xf7][\\x80-\\xbf]{3}|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xc0-\\xdf][\\x80-\\xbf])+$");
/* 112 */   private static Pattern messyCodePatt = Pattern.compile("^([\\u4e00-\\u9fa5\\w\\s\\u3002\\uff1b\\uff0c\\uff1a\\u201c\\u201d\\uff08\\uff09\\u3001\\uff1f\\u300a\\u300b\\uff01\\u2014\\u3010\\u3011\\u2018\\u2019.,\"\\?!:'\\[\\]@#$%*\\(\\)\\{\\}<>;=-])+$");
/* 113 */   private static Pattern includeSearchPatt = Pattern.compile("//(.*\\.google\\.com|.*\\.google\\.com\\.hk|.*\\.google\\.com\\.tw|.*\\.google\\.cn|.*\\.baidu\\.com|.*\\.so\\.com|.*\\.soso\\.com|.*\\.sogou\\.com|.*\\.yahoo\\.com|.*\\.bing\\.com|.*\\.chinaso\\.com|.*\\.zhongsou\\.com|.*\\.youdao\\.com)");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isNotMessyCode(String str) {
/* 123 */     Matcher m = messyCodePatt.matcher(str);
/* 124 */     if (m.matches()) {
/* 125 */       return true;
/*     */     }
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isIncludeSearch(String refererStr) {
/* 138 */     if (refererStr.length() <= 512) {
/* 139 */       Matcher m = includeSearchPatt.matcher(refererStr);
/* 140 */       if (m.find()) {
/* 141 */         return true;
/*     */       }
/* 143 */       return false;
/*     */     } 
/*     */     
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getSearch(String refererStr) {
/* 157 */     if (refererStr.length() <= 512) {
/* 158 */       Matcher m = includeSearchPatt.matcher(refererStr);
/* 159 */       if (m.find() && m.groupCount() >= 1) {
/* 160 */         return m.group(1);
/*     */       }
/*     */     } 
/* 163 */     return null;
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
/*     */   public static String getKeywordEncode(String referer) {
/* 175 */     StringBuffer keyword = new StringBuffer(20);
/*     */     try {
/* 177 */       Matcher keywordMat = keywordPatt.matcher(referer);
/* 178 */       while (keywordMat.find()) {
/* 179 */         keywordMat.appendReplacement(keyword, "$1");
/*     */       }
/* 181 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 184 */       return null;
/*     */     } 
/*     */     
/* 187 */     if (!keyword.toString().equals("")) {
/* 188 */       return keyword.toString();
/*     */     }
/* 190 */     return null;
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
/*     */   public static String getKeywordDecode(String kwEncode) {
/*     */     try {
/* 203 */       String unescapeString = unescape(kwEncode);
/* 204 */       if (unescapeString != null) {
/* 205 */         Matcher encodeMat = encodePatt.matcher(unescapeString);
/* 206 */         String encodeString = "gbk";
/* 207 */         if (encodeMat.matches())
/* 208 */           encodeString = "utf-8"; 
/* 209 */         String keywordStr = URLDecoder.decode(kwEncode, encodeString);
/*     */         
/* 211 */         return keywordStr;
/*     */       } 
/* 213 */     } catch (Exception e) {
/*     */ 
/*     */       
/* 216 */       return null;
/*     */     } 
/* 218 */     return null;
/*     */   }
/*     */   
/*     */   private static String unescape(String src) {
/* 222 */     StringBuffer tmp = new StringBuffer();
/* 223 */     tmp.ensureCapacity(src.length());
/* 224 */     int lastPos = 0, pos = 0;
/*     */     
/* 226 */     while (lastPos < src.length()) {
/*     */       try {
/* 228 */         pos = src.indexOf("%", lastPos);
/* 229 */         if (pos == lastPos) {
/* 230 */           if (src.charAt(pos + 1) == 'u') {
/* 231 */             char c = (char)Integer.parseInt(src.substring(pos + 2, pos + 6), 16);
/*     */             
/* 233 */             tmp.append(c);
/* 234 */             lastPos = pos + 6; continue;
/*     */           } 
/* 236 */           char ch = (char)Integer.parseInt(src.substring(pos + 1, pos + 3), 16);
/*     */           
/* 238 */           tmp.append(ch);
/* 239 */           lastPos = pos + 3;
/*     */           continue;
/*     */         } 
/* 242 */         if (pos == -1) {
/* 243 */           tmp.append(src.substring(lastPos));
/* 244 */           lastPos = src.length(); continue;
/*     */         } 
/* 246 */         tmp.append(src.substring(lastPos, pos));
/* 247 */         lastPos = pos;
/*     */       
/*     */       }
/* 250 */       catch (Exception e) {
/*     */         
/* 252 */         return null;
/*     */       } 
/*     */     } 
/*     */     
/* 256 */     return tmp.toString();
/*     */   }
/*     */   
/*     */   private static String insteadCode(String str, String regEx, String code) {
/* 260 */     Pattern p = Pattern.compile(regEx);
/* 261 */     Matcher m = p.matcher(str);
/* 262 */     String s = m.replaceAll(code);
/* 263 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String loadConvert(char[] in, int off, int len, char[] convtBuf) {
/* 268 */     if (convtBuf.length < len) {
/* 269 */       int newLen = len * 2;
/* 270 */       if (newLen < 0) {
/* 271 */         newLen = Integer.MAX_VALUE;
/*     */       }
/* 273 */       convtBuf = new char[newLen];
/*     */     } 
/*     */     
/* 276 */     char[] out = convtBuf;
/* 277 */     int outLen = 0;
/* 278 */     int end = off + len;
/*     */     
/* 280 */     while (off < end) {
/* 281 */       char aChar = in[off++];
/* 282 */       if (aChar == '\\') {
/* 283 */         aChar = in[off++];
/* 284 */         if (aChar == 'u') {
/*     */           
/* 286 */           int value = 0;
/* 287 */           for (int i = 0; i < 4; i++) {
/* 288 */             aChar = in[off++];
/* 289 */             switch (aChar) {
/*     */               case '0':
/*     */               case '1':
/*     */               case '2':
/*     */               case '3':
/*     */               case '4':
/*     */               case '5':
/*     */               case '6':
/*     */               case '7':
/*     */               case '8':
/*     */               case '9':
/* 300 */                 value = (value << 4) + aChar - 48;
/*     */                 break;
/*     */               case 'a':
/*     */               case 'b':
/*     */               case 'c':
/*     */               case 'd':
/*     */               case 'e':
/*     */               case 'f':
/* 308 */                 value = (value << 4) + 10 + aChar - 97;
/*     */                 break;
/*     */               case 'A':
/*     */               case 'B':
/*     */               case 'C':
/*     */               case 'D':
/*     */               case 'E':
/*     */               case 'F':
/* 316 */                 value = (value << 4) + 10 + aChar - 65;
/*     */                 break;
/*     */               default:
/* 319 */                 throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
/*     */             } 
/*     */           
/*     */           } 
/* 323 */           out[outLen++] = (char)value; continue;
/*     */         } 
/* 325 */         if (aChar == 't') {
/* 326 */           aChar = '\t';
/* 327 */         } else if (aChar == 'r') {
/* 328 */           aChar = '\r';
/* 329 */         } else if (aChar == 'n') {
/* 330 */           aChar = '\n';
/* 331 */         } else if (aChar == 'f') {
/* 332 */           aChar = '\f';
/* 333 */         }  out[outLen++] = aChar;
/*     */         continue;
/*     */       } 
/* 336 */       out[outLen++] = aChar;
/*     */     } 
/*     */     
/* 339 */     return new String(out, 0, outLen);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rz\\util\keyword\KWUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */