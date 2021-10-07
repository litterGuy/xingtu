
package com.qihoo.wzws.rzb.util.ip;


public class IPUtil {

    public static IPRegion getIPRegionLocal(String ip) {

        String line = IPDataHandler.findGeography(ip);

        if (line != null) {

            IPRegion region = new IPRegion();

            String[] strArray = line.split("\t");

            if (strArray.length >= 2) {

                region.setIp(ip);

                region.setCountry(strArray[0]);

                region.setProvince(strArray[1]);

            }

            if (strArray.length >= 3) {

                region.setCity(strArray[2]);

            }


            return region;

        }


        return null;

    }


    public static String getRegion(String ip) {

        String rv = "";

        IPRegion ipRegion = getIPRegionLocal(ip);

        if (ipRegion.getCountry().indexOf("中国") > -1) {

            if (ipRegion.getCity() != null && !ipRegion.getProvince().equals(ipRegion.getCity())) {

                rv = ipRegion.getProvince() + ipRegion.getCity();

            } else {

                rv = ipRegion.getProvince();

            }

        } else {

            rv = ipRegion.getCountry();

        }


        return rv;

    }


    public static void main(String[] args) {
    }

}