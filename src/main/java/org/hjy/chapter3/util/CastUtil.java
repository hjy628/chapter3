package org.hjy.chapter3.util;

/**
 * Created by hjy on 15-12-26.
 * 转型操作工具类
 */
public final class CastUtil {
    /**
     * 转为String型
     */
    public static String catsString(Object obj){
        return catsString(obj,"");
    }

    /**
     * 转为String型(提供默认值)
     */
    public static String catsString(Object obj,String defaultValue){
        return obj!=null ? String.valueOf(obj) : defaultValue;
    }

    /**
     * 转为double型
     */
    public static double catsDouble(Object obj){
        return catsDouble(obj,0);
    }

    /**
     * 转为double型(提供默认值)
     */
    public static double catsDouble(Object obj,double defaultValue){
        double doubleValue = defaultValue;
        if (obj!=null){
            String strValue = catsString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    doubleValue=Double.parseDouble(strValue);
                }catch (NumberFormatException e){
                    doubleValue=defaultValue;
                }
            }
        }
        return doubleValue;
    }


    /**
     * 转为long型
     */
    public static long catsLong(Object obj){
        return catsLong(obj, 0);
    }

    /**
     * 转为long型(提供默认值)
     */
    public static long catsLong(Object obj,long defaultValue){
        long longValue = defaultValue;
        if (obj!=null){
            String strValue = catsString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    longValue=Long.parseLong(strValue);
                }catch (NumberFormatException e){
                    longValue=defaultValue;
                }
            }
        }
        return longValue;
    }

    /**
     * 转为int型
     */
    public static int catsInt(Object obj){
        return catsInt(obj, 0);
    }

    /**
     * 转为int型(提供默认值)
     */
    public static int catsInt(Object obj,int defaultValue){
        int intValue = defaultValue;
        if (obj!=null){
            String strValue = catsString(obj);
            if (StringUtil.isNotEmpty(strValue)){
                try {
                    intValue=Integer.parseInt(strValue);
                }catch (NumberFormatException e){
                    intValue=defaultValue;
                }
            }
        }
        return intValue;
    }


    /**
     * 转为boolean型
     */
    public static boolean catsBoolean(Object obj){
        return catsBoolean(obj, false);
    }

    /**
     * 转为boolean型(提供默认值)
     */
    public static boolean catsBoolean(Object obj,boolean defaultValue){
        boolean booleanValue = defaultValue;
        if (obj!=null){
            booleanValue=Boolean.parseBoolean(catsString(obj));
        }
        return booleanValue;
    }

}
