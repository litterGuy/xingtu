
package com.qihoo.wzws.rzb.cc;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class CCAlgorithm {
    private CCDetail previous5Min = new CCDetail();
    private CCDetail current5Min = new CCDetail();


    private void cc() {
    }


    public static CC compareAndGetResult(CCDetail previous5Min, CCDetail current5Min, double request_growth, double request_rate) {

        if (((current5Min.getCount() - previous5Min.getCount()) / current5Min.getCount() >= request_growth && (current5Min.getBand() - previous5Min.getBand()) / current5Min.getBand() >= request_growth) || ((current5Min.getCount() - previous5Min.getCount()) / current5Min.getCount() >= request_growth && current5Min.getBand() == 0L && previous5Min.getBand() == 0L)) {


            CC cc = Sort(current5Min.getList());


            if (cc.getCount() / current5Min.getCount() >= request_rate) {


                String[] strs = cc.getRequestKey().split(" ");

                CC c = new CC();

                c.setHost(strs[0]);

                c.setIp(strs[1]);

                c.seturi(strs[2]);

                c.setCount(cc.getCount());


                List<CC> ipccList = new ArrayList<CC>();

                for (CC s : current5Min.getList()) {

                    if (s.getIp().equals(c.getIp()) && s.geturi().equals(c.geturi()) && s.getHost().equals(c.getHost())) {


                        ipccList.add(s);

                    }

                }


                Collections.sort(ipccList, new CCReqTimeComparator());

                c.setTime(((CC) ipccList.get(0)).getTime());


                return c;

            }

        }


        return null;

    }


    private static CC Sort(List<CC> list) {

        Map<String, Integer> map = new HashMap<String, Integer>();

        for (CC c : list) {

            String requestKey = c.getHost() + " " + c.getIp() + " " + c.geturi();

            if (map.containsKey(requestKey)) {

                map.put(requestKey, Integer.valueOf(((Integer) map.get(requestKey)).intValue() + 1));
                continue;

            }

            map.put(requestKey, Integer.valueOf(1));

        }


        List<CC> sortList = new ArrayList<CC>();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {

            sortList.add(new CC(entry.getKey(), ((Integer) entry.getValue()).intValue()));

        }


        map.clear();


        Collections.sort(sortList, new CCReqCountComparator());


        return sortList.get(0);

    }


    public static int getReduceSize(int hour, int numReduces) {

        int parttitionLength = 24 / numReduces;

        for (int i = 1; i <= 24; i++) {

            if (hour <= i * parttitionLength) {

                return i - 1;

            }

        }


        return 0;

    }


    public static int get5MinInterval(int hour, int min) {

        int mins = hour * 60 + min;


        int parttitionLength = 5;

        for (int i = 1; i <= 1440; i++) {

            if (mins <= i * parttitionLength) {

                return i - 1;

            }

        }


        return 0;

    }


    public static void main(String[] args) {

        TreeMap<Integer, CCDetail> map = new TreeMap<Integer, CCDetail>();

        List<String> l = new ArrayList();


        l.add("116.255.194.45|2014-07-30 00:00:04|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:01:04|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:02:04|www.cn|/|05|123456");


        l.add("116.255.194.45|2014-07-30 00:06:01|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:06:04|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:07:04|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:08:04|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:08:14|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:08:24|www.cn|/|05|123456");

        l.add("116.255.194.45|2014-07-30 00:09:04|www.cn|/|05|123456");


        String globalHost = "";


        Iterator<String> values = l.iterator();


        while (values.hasNext()) {

            String value = values.next().toString();

            System.out.println("reduce value:" + value);


            String[] fields = value.split("\\|");


            long band = Long.valueOf(fields[4]).longValue();

            String uri = fields[3];

            String ip = fields[0];

            String time = fields[1];

            String host = (fields[2] == null || fields[2].isEmpty()) ? globalHost : fields[2];


            Date date = null;

            try {

                DateFormat commondf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                date = commondf.parse(fields[1]);

            } catch (ParseException e) {

                return;

            }


            int minInterval = get5MinInterval(date.getHours(), date.getMinutes());

            if (map.containsKey(Integer.valueOf(minInterval))) {

                CCDetail cCDetail = map.get(Integer.valueOf(minInterval));


                cCDetail.setCount(cCDetail.getCount() + 1);

                cCDetail.setBand(cCDetail.getBand() + band);

                cCDetail.getList().add(new CC(host, uri, ip, time));
                continue;

            }

            CCDetail detail = new CCDetail();

            detail.setCount(1);

            detail.setBand(band);

            List<CC> ccList = new ArrayList<CC>();

            ccList.add(new CC(host, uri, ip, time));

            detail.setList(ccList);

            map.put(Integer.valueOf(minInterval), detail);

        }


        System.out.println("map size:" + map.size());


        Iterator<Map.Entry<Integer, CCDetail>> iterator = map.entrySet().iterator();


        Map.Entry previous = null;

        if (iterator.hasNext()) {

            previous = iterator.next();


            System.out.println("first:" + previous.getKey());

        }


        while (iterator.hasNext()) {

            Map.Entry current = iterator.next();


            CCDetail previous5Min = (CCDetail) previous.getValue();

            CCDetail current5Min = (CCDetail) current.getValue();


            System.out.println("current:" + current.getKey() + ",band=" + current5Min.getBand() + ",count=" + current5Min.getCount());


            if (current5Min.getCount() >= 5) {

                CC c = compareAndGetResult(previous5Min, current5Min, 0.5D, 0.5D);

                if (c != null) {


                    System.out.println("www.cn\t" + c.getIp() + "\t" + c.geturi() + "\t" + c.getTime() + "\t" + c.getCount());

                }


                current5Min.getList().clear();


                previous = current;

            }

        }

    }

}