/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;

/**
 *
 * @author sits_lahirupr
 */
public class APPUtills {

    private static Logger dedLog = LogManager.getLogger(APPUtills.class);

    public final static String DATE_TIME_DEFAULT_FORMAT = "dd/MM/yyyy HH:mm:ss";
    public final static String DATE_TIME_SHORT_FORMAT = "dd/MM/yyyy";

    public static void setLog4jUserParams() {
        dedLog.info("ENTERED | DEDUtills.setLog4jUserParams()");
        try {
            ThreadContext.put("app_loger_code", "DE_Directry");
            ThreadContext.put("request_id", Long.toString(getUniqueNumber()));
            ThreadContext.put("ws_method_name", "Y");
        } catch (Exception ex) {
            ex.printStackTrace();
            dedLog.error("ERROR   | " + ex.getMessage());
        }
        dedLog.info("LEFT    | DEDUtills.setLog4jUserParams()");
    }

    private static long getUniqueNumber() {
        long timeInMiliseconds = 0;
        try {
            Calendar calCurrentTime = Calendar.getInstance();
            timeInMiliseconds = calCurrentTime.getTimeInMillis();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return timeInMiliseconds;
    }

    /**
     *
     * @param date as String
     * @param formatString as String
     * @return Date
     */
    public static Date parseDate(String date, String formatString) {
        Date dt = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat(formatString);
            sdf1.setLenient(false);
            dt = sdf1.parse(date);
        } catch (Exception e) {
            dedLog.error("ERROR   | In DEDUtills.parseDate \n" + e.getMessage());
        }
        return dt;
    }

    /**
     * Returns the formatted Date or Time as a String
     *
     * @param pDate	java.util.Date
     * @param sFormatPattern	java.lang.String - General java date formats
     * @return	String
     */
    public static String formatDate(Date pDate, String sFormatPattern) {

        String sDateFormatter = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(sFormatPattern);
            sDateFormatter = sdf.format(pDate);
        } catch (Exception ex) {
            dedLog.error("ERROR   | In DEDUtills.formatDate \n" + ex.getMessage());
        }

        return sDateFormatter;
    }

    public static Calendar getCurrentDatePart() {
        Calendar calToday = Calendar.getInstance();
        calToday.clear(Calendar.HOUR_OF_DAY);
        calToday.clear(Calendar.HOUR);
        calToday.clear(Calendar.AM_PM);
        calToday.clear(Calendar.MINUTE);
        calToday.clear(Calendar.SECOND);
        calToday.clear(Calendar.MILLISECOND);

        return calToday;
    }

    public static Date getCurrentDate() {
        Date date = new Date();
        Date ISTDate = new Date();

        try {
            SimpleDateFormat sd = new SimpleDateFormat(DATE_TIME_DEFAULT_FORMAT);//  dd/MM/yyyy HH:mm:ss
            sd.setTimeZone(TimeZone.getTimeZone("IST"));
            String sDate = sd.format(date);

            sd.setTimeZone(TimeZone.getTimeZone("GMT"));
            ISTDate = sd.parse(sDate);
        } catch (Exception ex) {
            dedLog.error("ERROR   | In DEDUtills.getCurrentDate \n" + ex.getMessage());
        }
        return ISTDate;
    }
/*
    public static String getRandomToken() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase(Locale.ROOT);
        String digits = "0123456789";
        String alphanum = upper + lower + digits;
        Random random = new Random();
        char[] symbols = alphanum.toCharArray();;
        char[] buf = new char[10];
        String randomToken = "";

        try {
            for (int a = 0; a < buf.length; ++a) {
                buf[a] = symbols[random.nextInt(symbols.length)];
            }
            randomToken = new String(buf);
        } catch (Exception ex) {
            dedLog.error("ERROR   | In DEDUtills.getRandomToken \n" + ex.getMessage());
        }
        return randomToken;
    }

    public static void setProxySettings() {
        //Set proxy if neccessary
        if (ApplicationConstants.USE_PROXY == Boolean.TRUE) {
            dedLog.info("MESSAGE | Enable proxy setting");
            System.setProperty("https.proxySet", "true");
            System.setProperty("https.proxyHost", ApplicationConstants.PROXY_HOST);
            System.setProperty("https.proxyPort", ApplicationConstants.PROXY_PORT);
            //System.setProperty("https.proxyHost", "proxy2.sampath.lk");
            //System.setProperty("https.proxyPort", "8095");

            if (!ApplicationConstants.APP_ENV.equalsIgnoreCase("LIVE")) {
                System.setProperty("http.proxySet", "true");
                System.setProperty("http.proxyHost", ApplicationConstants.PROXY_HOST);
                System.setProperty("http.proxyPort", ApplicationConstants.PROXY_PORT);
            }
        }
    }

    public static void removeProxySettings() {
        if (ApplicationConstants.USE_PROXY == Boolean.TRUE) {
            dedLog.info("MESSAGE | Removing proxy setting");
            System.clearProperty("https.proxySet");
            System.clearProperty("https.proxyHost");
            System.clearProperty("https.proxyPort");

            System.clearProperty("http.proxySet");
            System.clearProperty("http.proxyHost");
            System.clearProperty("http.proxyPort");
        }
    }
*/
    public static boolean isThisStringValid(String strTovalidate) {
        return !(strTovalidate == null || strTovalidate.trim().equalsIgnoreCase(""));
    }

    public static boolean isEqual(String str1, String str2) {
        //System.out.println("str1 :"+str1+".  |  str2 :"+str2+".");
        if (str1 == null && str2 == null) {
            return true;
        } else if (str1 == null || str2 == null) {
            return false;
        } else return str1.equalsIgnoreCase(str2);
    }
    
    public static String getString(String str) {
        return ((str == null)?"":str);
    }


    /*public static String getStringId(BigDecimal tempIdBigDecml,int stgIdLength) {
        dedLog.info("MESSAGE | getStringId");
        String tempId = tempIdBigDecml != null ? tempIdBigDecml.toString() : "";
        int i = tempId.length();
        while (i < stgIdLength) {
            tempId = "0" + tempId;
            i++;
        }
        dedLog.info("MESSAGE | generated tempId : "+tempId);
        return tempId;
    }*/
}
