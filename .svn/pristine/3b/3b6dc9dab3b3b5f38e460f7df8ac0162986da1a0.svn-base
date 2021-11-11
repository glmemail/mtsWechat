package com.nrib.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 共通处理类</br>
 *
 * @author yubx
 *
 */
public class NribCommon {

	/**
	 * 字符串判空处理</br>
	 *
	 * @param str
	 *            需要被处理的字符串
	 * @return 判断后的结果
	 */
	public static boolean isNullOrEmpty(String str) {
		if ("".equals(str) || null == str) {
			return true;
		}
		return false;
	}

	/**
	 * 根据画面传过来的key数组和value数组，将key和value分别转存储到LinkedHashMap中，追中设定到List中返回
	 *
	 * @param strKeys
	 *            画面传过来的额key数组
	 * @param strValues
	 *            画面传过来的额value数组
	 * @return
	 */
	public static List<LinkedHashMap<String, String>> setLinkedHashMapList(String[] strKeys, String[] strValues) {
		LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
		// key数组与value数组的长度一致，所以循环任一数据
		for (int i = 0; i < strKeys.length; i++) {
			linkedHashMap.put(strKeys[i].trim(), strValues[i].trim());
		}
		// 为了适应数据对象中属性的数据类型，将转存后的Map存放到List中
		List<LinkedHashMap<String, String>> resultMapList = new ArrayList<LinkedHashMap<String, String>>();
		resultMapList.add(linkedHashMap);

		// 返回值
		return resultMapList;
	}

	/**
	 * 转换字符串到16进制
	 *
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String res = null;
		MessageDigest md5 = MessageDigest.getInstance(NribNormalConstant.MD5);
		byte b[] = md5.digest(str.getBytes(NribNormalConstant.UTF8));
		res = toHexString(b);
		return res;
	}

	private static String toHexString(byte b[]) {
		StringBuffer buf = new StringBuffer();
		String str;
		for(int i = 0;i<b.length;i++) {
			str = Integer.toHexString(0xFF & b[i]);
			if(str.length() < 2) {
				str = "0" + str;
			}
			buf.append(str);
		}
		return buf.toString();
	}

	/**
	 * 取得堆栈信息字符串
	 * @param ex 异常类
	 * @return 堆栈信息字符串
	 */
	public static String getStackMsg(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		ex.printStackTrace(pw);

		StringBuffer errorSb = sw.getBuffer();

		return errorSb.toString();
	}
	
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
}
