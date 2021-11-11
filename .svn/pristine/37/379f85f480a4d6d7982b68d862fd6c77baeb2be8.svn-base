package com.nrib.common;

/**
 * @ClassName: StringUtil
 * @Description: 字符串转换共通
 * @author 赵峰剑
 * @date 2014年8月22日 下午3:54:26
 * @version 1.0
 */
public class StringUtil
{

    /**
     * 指定长度的random生成
     * 
     * @param length
     *            长度
     * @return random结果
     */
    public static String createRandomString(int length, boolean isNumber)
    {
        // 英数字组合
        int NUM_CHAR = 59;
        // 数字
        if (isNumber)
            NUM_CHAR = 10;

        String t = "";
        int j = 0;
        String[] v = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M",
                "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

        for (int i = 0; i < length; i++)
        {
            j = (int) (NUM_CHAR * Math.random());
            t = t + v[j];
        }

        return t;
    }

    /**
     * 检查文字列が<code>null</code。
     * 
     * @param str
     *            文字列
     * @return true或者false
     */
    public static boolean isEmpty(String str)
    {
        if (str == null || str.length() == 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
