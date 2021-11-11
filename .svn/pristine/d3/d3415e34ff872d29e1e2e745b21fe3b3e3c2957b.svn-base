package com.nrib.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ConfUtil {

	// 初期化本类的对象
	private static ConfUtil conf;

	// 初期化属性文件对象
	private static Properties prop = null;

	// 初期化属性文件路径
	private static String path = null;
	
	// 初期化属性文件对象
	private static Properties propMsg = null;

	// 初期化属性文件路径
	private static String pathMsg = null;
	
	public static Logger logger = Logger.getLogger(ConfUtil.class);
	
	private static String MTSC_HOME = null;
	
	/**
     * 取得MTSHOME
     * 
     * @return
     */
    public static String getMTSC_HOME() {
    	return MTSC_HOME;
    }

	/**
	 * 私有构造器
	 */
	private ConfUtil() {
		
		MTSC_HOME = System.getenv("MTSC_HOME");
		
		// 取得属性文件（全路径）
		if(MTSC_HOME == null || "".equals(MTSC_HOME)) {
			logger.error("[E0000] [系统未取到MTSC_HOME的值，请检查系统设置]");
		} else {
			path = MTSC_HOME + "/WechatAccesser/conf/config.properties";
		}
		// 创建属性文件对象
		prop = new Properties();
		
		// 创建文件对象
		File cfileInfo = new File(path);
		
		// 创建输入流
		InputStreamReader creader;
		try {
			creader = new InputStreamReader(new FileInputStream(cfileInfo), "utf-8");
			// 根据输入流加载属性文件
			prop.load(creader);
			// 关闭输入流
			creader.close();
		} catch (Exception e) {
			logger.error("[E0000] [config.properties 不存在或加载错误]");
		}
		
		try {
		// 取得属性文件（全路径）
		pathMsg = this.getClass().getClassLoader().getResource("messageResource_zh_cn.properties").getPath();

		// 创建属性文件对象
		propMsg = new Properties();
		
		// 创建文件对象
		File fileInfo = new File(pathMsg);
		// 创建输入流
		InputStreamReader reader = new InputStreamReader(new FileInputStream(fileInfo), "utf-8");
		// 根据输入流加载属性文件
		propMsg.load(reader);
		// 关闭输入流
		reader.close();
		} catch (Exception e) {
			logger.error("[E0000] [message.properties 不存在或加载错误]");
		}
	}

	/**
	 * Object对象向文字列变更。
	 * 
	 * @param obj
	 *            Object对象
	 * @return 向文字
	 */
	public static String objToStr(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return String.valueOf(obj);
		}
	}

	/**
	 * 根据key值，从属性文件中取得对应的value值</br>
	 * 
	 * @param key
	 *            键值
	 * @return 属性文件中键值对应的value值
	 */
	public String getConfigvalueAndPar(String key) {
			// 根据key值取得对应的value值
			String value = objToStr(prop.get(key));
			return value;
	}
	
	/**
	 * 根据key值，从属性文件中取得对应的value值</br>
	 * 
	 * @param key
	 *            键值
	 * @return 属性文件中键值对应的value值
	 */
	public String getMsgvalueAndPar(String key) {
		// 根据key值取得对应的value值
		String msg = objToStr(propMsg.get(key));
        msg = "[" + key + "]" + "[" + msg + "]";
        return msg;
	}
	
	public String getMsgvalueAndPar(String key,String val) {
		// 根据key值取得对应的value值
		String msg = objToStr(propMsg.get(key));
        msg = "[" + key + "]" + "[" + msg + "]" + "[" + val + "]";
        return msg;
	}

	/**
	 * 单例模式下，对外公开的借口，用来提供本类的唯一对象</br>
	 * 
	 * @return 本类的唯一对象
	 */
	public static synchronized ConfUtil getInstance() {
		if (conf == null) {
			conf = new ConfUtil();
		}
		return conf;
	}

}
