package com.example.module_base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    public static String getWeekOfDate2(Date dt) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }


    //格式化日期1
    public static String  StrToData(String time) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ft2 = new SimpleDateFormat("M月d日");
        try {
            Date date = ft.parse(time);
            return ft2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "转换错误";
    }

    //格式化日期2
    public static String  StrToData2(String time) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat ft2 = new SimpleDateFormat("MM/dd");
        try {
            Date date = ft.parse(time);
            return ft2.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "转换错误";
    }


    public  static String getDate2() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy年M月dd日");
        Date date = new Date();
        String format = ft.format(date);
        return format;
    }

    public  static String getDate() {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String format = ft.format(date);
        return format;
    }



    //格式化星期
    public static String getWeek(String time) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(time));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int wek=c.get(Calendar.DAY_OF_WEEK);

        if (wek == 1) {
            Week += "周日";
        }
        if (wek == 2) {
            Week += "周一";
        }
        if (wek == 3) {
            Week += "周二";
        }
        if (wek == 4) {
            Week += "周三";
        }
        if (wek == 5) {
            Week += "周四";
        }
        if (wek == 6) {
            Week += "周五";
        }
        if (wek == 7) {
            Week += "周六";
        }
        return Week;
    }



    /**
     * create date:2010-5-22下午03:40:44
     * 描述：取出日期字符串中的年份字符串
     * @param str 日期字符串
     * @return
     */
    public static String getYearStr(String str)
    {
        String yearStr = "";
        yearStr = str.substring(0,4);
        return yearStr;
    }

    /**
     * create date:2010-5-22下午03:40:47
     * 描述：取出日期字符串中的月份字符串
     * @param
     * @return
     */
    public static String getMonthStr(String str)
    {
        String m;
        int startIndex = str.indexOf("年");
        int endIndex = str.indexOf("月");
        m = str.substring(startIndex+1,endIndex);
        return m;
    }

    /**
     * create date:2010-5-22下午03:32:31
     * 描述：将源字符串中的阿拉伯数字格式化为汉字
     * @param sign 源字符串中的字符
     * @return
     */
    public static char formatDigit(char sign){
        if(sign == '0')
            sign = '0';
        if(sign == '1')
            sign = '一';
        if(sign == '2')
            sign = '二';
        if(sign == '3')
            sign = '三';
        if(sign == '4')
            sign = '四';
        if(sign == '5')
            sign = '五';
        if(sign == '6')
            sign = '六';
        if(sign == '7')
            sign = '七';
        if(sign == '8')
            sign = '八';
        if(sign == '9')
            sign = '九';
        return sign;
    }

    /**
     * create date:2010-5-22下午03:31:51
     * 描述： 获得月份字符串的长度
     * @param str  待转换的源字符串
     * @param pos1 第一个'-'的位置
     * @param pos2 第二个'-'的位置
     * @return
     */
    public static int getMidLen(String str,int pos1,int pos2){
        return str.substring(pos1+1, pos2).length();
    }
    /**
     * create date:2010-5-22下午03:32:17
     * 描述：获得日期字符串的长度
     * @param str  待转换的源字符串
     * @param pos2 第二个'-'的位置
     * @return
     */
    public static int getLastLen(String str,int pos2){
        return str.substring(pos2+1).length();
    }

    /**
     * create date:2010-5-22下午03:40:50
     * 描述：取出日期字符串中的日字符串
     * @param str 日期字符串
     * @return
     */
    public static String getDayStr(String str)
    {
        String dayStr = "";
        int startIndex = str.indexOf("月");
        int endIndex = str.indexOf("日");
        dayStr = str.substring(startIndex+1,endIndex);
        return dayStr;
    }
    /**
     * create date:2010-5-22下午03:32:46
     * 描述：格式化日期
     * @param str 源字符串中的字符
     * @return
     */
    public static String formatStr(String str){
        StringBuffer sb = new StringBuffer();
        int pos1 = str.indexOf("-");
        int pos2 = str.lastIndexOf("-");
        for(int i = 0; i < 4; i++){
            sb.append(formatDigit(str.charAt(i)));
        }
        sb.append('年');
        if(getMidLen(str,pos1,pos2) == 1){
            sb.append(formatDigit(str.charAt(5))+"月");
            if(str.charAt(7) != '0'){
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(7))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(7) != '1' && str.charAt(8) != '0'){
                        sb.append(formatDigit(str.charAt(7))+"十"+formatDigit(str.charAt(8))+"日");
                    }
                    else if(str.charAt(7) != '1' && str.charAt(8) == '0'){
                        sb.append(formatDigit(str.charAt(7))+"十日");
                    }
                    else if(str.charAt(7) == '1' && str.charAt(8) != '0'){
                        sb.append("十"+formatDigit(str.charAt(8))+"日");
                    }
                    else{
                        sb.append("十日");
                    }
                }
            }
            else{
                sb.append(formatDigit(str.charAt(8))+"日");
            }
        }
        if(getMidLen(str,pos1,pos2) == 2){
            if(str.charAt(5) != '0' && str.charAt(6) != '0'){
                sb.append("十"+formatDigit(str.charAt(6))+"月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
            else if(str.charAt(5) != '0' && str.charAt(6) == '0'){
                sb.append("十月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
            else{
                sb.append(formatDigit(str.charAt(6))+"月");
                if(getLastLen(str, pos2) == 1){
                    sb.append(formatDigit(str.charAt(8))+"日");
                }
                if(getLastLen(str, pos2) == 2){
                    if(str.charAt(8) != '0'){
                        if(str.charAt(8) != '1' && str.charAt(9) != '0'){
                            sb.append(formatDigit(str.charAt(8))+"十"+formatDigit(str.charAt(9))+"日");
                        }
                        else if(str.charAt(8) != '1' && str.charAt(9) == '0'){
                            sb.append(formatDigit(str.charAt(8))+"十日");
                        }
                        else if(str.charAt(8) == '1' && str.charAt(9) != '0'){
                            sb.append("十"+formatDigit(str.charAt(9))+"日");
                        }
                        else{
                            sb.append("十日");
                        }
                    }
                    else{
                        sb.append(formatDigit(str.charAt(9))+"日");
                    }
                }
            }
        }
        return sb.toString();
    }

}
