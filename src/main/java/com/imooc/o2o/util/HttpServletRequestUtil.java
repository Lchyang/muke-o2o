package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

// 解析request中传入的数据
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request, String key) {
        try{
            // Integer.decode 适用于数字，可以将其他进制的数字转化为十进制
            // Integer.valueof 适用于十进制，直接转化
            // Integer.parseInt 转化成int类型，而不是Integer
            return Integer.decode(request.getParameter(key));
        } catch(Exception e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key) {
        try{
            return Long.decode(request.getParameter(key));
        } catch(Exception e) {
            return -1;
        }
    }

    public static Double getDouble(HttpServletRequest request, String key) {
        try{
            return Double.valueOf(request.getParameter(key));
        } catch(Exception e) {
            return -1d;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request, String key) {
        try{
            return Boolean.valueOf(request.getParameter(key));
        } catch(Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request, String key){
        try{
            String result = request.getParameter(key);
            // 检查字符串是否为""还是null时先检查是否为null，如果在null值上调用方法为报错
            if(result != null){
                result = result.trim();
            }
            if("".equals(result)){
                result = null;
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }
}
