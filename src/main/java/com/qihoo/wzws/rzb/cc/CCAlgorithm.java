/*     */
package com.qihoo.wzws.rzb.cc;
/*     */
/*     */

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
/*     */ public class CCAlgorithm
        /*     */ {
    /*  20 */   private CCDetail previous5Min = new CCDetail();
    /*  21 */   private CCDetail current5Min = new CCDetail();

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
    private void cc() {
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
    public static CC compareAndGetResult(CCDetail previous5Min, CCDetail current5Min, double request_growth, double request_rate) {
        /*  47 */
        if (((current5Min.getCount() - previous5Min.getCount()) / current5Min.getCount() >= request_growth && (current5Min.getBand() - previous5Min.getBand()) / current5Min.getBand() >= request_growth) || ((current5Min.getCount() - previous5Min.getCount()) / current5Min.getCount() >= request_growth && current5Min.getBand() == 0L && previous5Min.getBand() == 0L)) {
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */
            /*     */
            /*  54 */
            CC cc = Sort(current5Min.getList());
            /*     */
            /*     */
            /*  57 */
            if (cc.getCount() / current5Min.getCount() >= request_rate) {
                /*     */
                /*  59 */
                String[] strs = cc.getRequestKey().split(" ");
                /*  60 */
                CC c = new CC();
                /*  61 */
                c.setHost(strs[0]);
                /*  62 */
                c.setIp(strs[1]);
                /*  63 */
                c.seturi(strs[2]);
                /*  64 */
                c.setCount(cc.getCount());
                /*     */
                /*  66 */
                List<CC> ipccList = new ArrayList<CC>();
                /*  67 */
                for (CC s : current5Min.getList()) {
                    /*  68 */
                    if (s.getIp().equals(c.getIp()) && s.geturi().equals(c.geturi()) && s.getHost().equals(c.getHost()))
                        /*     */ {
                        /*     */
                        /*  71 */
                        ipccList.add(s);
                        /*     */
                    }
                    /*     */
                }
                /*     */
                /*  75 */
                Collections.sort(ipccList, new CCReqTimeComparator());
                /*  76 */
                c.setTime(((CC) ipccList.get(0)).getTime());
                /*     */
                /*  78 */
                return c;
                /*     */
            }
            /*     */
        }
        /*     */
        /*  82 */
        return null;
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
    private static CC Sort(List<CC> list) {
        /*  92 */
        Map<String, Integer> map = new HashMap<String, Integer>();
        /*  93 */
        for (CC c : list) {
            /*  94 */
            String requestKey = c.getHost() + " " + c.getIp() + " " + c.geturi();
            /*  95 */
            if (map.containsKey(requestKey)) {
                /*  96 */
                map.put(requestKey, Integer.valueOf(((Integer) map.get(requestKey)).intValue() + 1));
                continue;
                /*     */
            }
            /*  98 */
            map.put(requestKey, Integer.valueOf(1));
            /*     */
        }
        /*     */
        /*     */
        /* 102 */
        List<CC> sortList = new ArrayList<CC>();
        /* 103 */
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            /* 104 */
            sortList.add(new CC(entry.getKey(), ((Integer) entry.getValue()).intValue()));
            /*     */
        }
        /*     */
        /*     */
        /* 108 */
        map.clear();
        /*     */
        /* 110 */
        Collections.sort(sortList, new CCReqCountComparator());
        /*     */
        /* 112 */
        return sortList.get(0);
        /*     */
    }

    /*     */
    /*     */
    public static int getReduceSize(int hour, int numReduces) {
        /* 116 */
        int parttitionLength = 24 / numReduces;
        /* 117 */
        for (int i = 1; i <= 24; i++) {
            /* 118 */
            if (hour <= i * parttitionLength) {
                /* 119 */
                return i - 1;
                /*     */
            }
            /*     */
        }
        /*     */
        /* 123 */
        return 0;
        /*     */
    }

    /*     */
    /*     */
    /*     */
    public static int get5MinInterval(int hour, int min) {
        /* 128 */
        int mins = hour * 60 + min;
        /*     */
        /* 130 */
        int parttitionLength = 5;
        /* 131 */
        for (int i = 1; i <= 1440; i++) {
            /* 132 */
            if (mins <= i * parttitionLength) {
                /* 133 */
                return i - 1;
                /*     */
            }
            /*     */
        }
        /*     */
        /* 137 */
        return 0;
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
    public static void main(String[] args) {
        /* 222 */
        TreeMap<Integer, CCDetail> map = new TreeMap<Integer, CCDetail>();
        /* 223 */
        List<String> l = new ArrayList();
        /*     */
        /* 225 */
        l.add("116.255.194.45|2014-07-30 00:00:04|www.cn|/|05|123456");
        /* 226 */
        l.add("116.255.194.45|2014-07-30 00:01:04|www.cn|/|05|123456");
        /* 227 */
        l.add("116.255.194.45|2014-07-30 00:02:04|www.cn|/|05|123456");
        /*     */
        /* 229 */
        l.add("116.255.194.45|2014-07-30 00:06:01|www.cn|/|05|123456");
        /* 230 */
        l.add("116.255.194.45|2014-07-30 00:06:04|www.cn|/|05|123456");
        /* 231 */
        l.add("116.255.194.45|2014-07-30 00:07:04|www.cn|/|05|123456");
        /* 232 */
        l.add("116.255.194.45|2014-07-30 00:08:04|www.cn|/|05|123456");
        /* 233 */
        l.add("116.255.194.45|2014-07-30 00:08:14|www.cn|/|05|123456");
        /* 234 */
        l.add("116.255.194.45|2014-07-30 00:08:24|www.cn|/|05|123456");
        /* 235 */
        l.add("116.255.194.45|2014-07-30 00:09:04|www.cn|/|05|123456");
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
        /* 247 */
        String globalHost = "";
        /*     */
        /* 249 */
        Iterator<String> values = l.iterator();
        /*     */
        /* 251 */
        while (values.hasNext()) {
            /* 252 */
            String value = values.next().toString();
            /* 253 */
            System.out.println("reduce value:" + value);
            /*     */
            /* 255 */
            String[] fields = value.split("\\|");
            /*     */
            /* 257 */
            long band = Long.valueOf(fields[4]).longValue();
            /* 258 */
            String uri = fields[3];
            /* 259 */
            String ip = fields[0];
            /* 260 */
            String time = fields[1];
            /* 261 */
            String host = (fields[2] == null || fields[2].isEmpty()) ? globalHost : fields[2];
            /*     */
            /* 263 */
            Date date = null;
            /*     */
            try {
                /* 265 */
                DateFormat commondf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                /* 266 */
                date = commondf.parse(fields[1]);
                /* 267 */
            } catch (ParseException e) {
                /*     */
                return;
                /*     */
            }
            /*     */
            /* 271 */
            int minInterval = get5MinInterval(date.getHours(), date.getMinutes());
            /* 272 */
            if (map.containsKey(Integer.valueOf(minInterval))) {
                /* 273 */
                CCDetail cCDetail = map.get(Integer.valueOf(minInterval));
                /*     */
                /* 275 */
                cCDetail.setCount(cCDetail.getCount() + 1);
                /* 276 */
                cCDetail.setBand(cCDetail.getBand() + band);
                /* 277 */
                cCDetail.getList().add(new CC(host, uri, ip, time));
                continue;
                /*     */
            }
            /* 279 */
            CCDetail detail = new CCDetail();
            /* 280 */
            detail.setCount(1);
            /* 281 */
            detail.setBand(band);
            /* 282 */
            List<CC> ccList = new ArrayList<CC>();
            /* 283 */
            ccList.add(new CC(host, uri, ip, time));
            /* 284 */
            detail.setList(ccList);
            /* 285 */
            map.put(Integer.valueOf(minInterval), detail);
            /*     */
        }
        /*     */
        /*     */
        /* 289 */
        System.out.println("map size:" + map.size());
        /*     */
        /*     */
        /* 292 */
        Iterator<Map.Entry<Integer, CCDetail>> iterator = map.entrySet().iterator();
        /*     */
        /* 294 */
        Map.Entry previous = null;
        /* 295 */
        if (iterator.hasNext()) {
            /* 296 */
            previous = iterator.next();
            /*     */
            /* 298 */
            System.out.println("first:" + previous.getKey());
            /*     */
        }
        /*     */
        /*     */
        /* 302 */
        while (iterator.hasNext()) {
            /* 303 */
            Map.Entry current = iterator.next();
            /*     */
            /* 305 */
            CCDetail previous5Min = (CCDetail) previous.getValue();
            /* 306 */
            CCDetail current5Min = (CCDetail) current.getValue();
            /*     */
            /* 308 */
            System.out.println("current:" + current.getKey() + ",band=" + current5Min.getBand() + ",count=" + current5Min.getCount());
            /*     */
            /* 310 */
            if (current5Min.getCount() >= 5) {
                /* 311 */
                CC c = compareAndGetResult(previous5Min, current5Min, 0.5D, 0.5D);
                /* 312 */
                if (c != null)
                    /*     */ {
                    /*     */
                    /* 315 */
                    System.out.println("www.cn\t" + c.getIp() + "\t" + c.geturi() + "\t" + c.getTime() + "\t" + c.getCount());
                    /*     */
                }
                /*     */
                /*     */
                /*     */
                /* 320 */
                current5Min.getList().clear();
                /*     */
                /*     */
                /* 323 */
                previous = current;
                /*     */
            }
            /*     */
        }
        /*     */
    }
    /*     */
}


/* Location:              C:\Users\Administrator\Downloads\xingtu_full\jar\rzb-sa.jar!\com\qihoo\wzws\rzb\cc\CCAlgorithm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */