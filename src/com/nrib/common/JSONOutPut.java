/**
 * 
 */
package com.nrib.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * @author zhaoyubo
 *
 * 2017/08/01
 */
public class JSONOutPut {
	private static Logger logger = Logger.getLogger(JSONOutPut.class);
	public static boolean createJSONFile(MsgInfoBean info) throws IOException {
		// 读取属性文件
    	ConfUtil cfUtl = ConfUtil.getInstance();
		boolean result = false;
		BufferedWriter jsonOut = null;
		String time = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		String path = Constant.JSON_PATH + File.separator;
		File filePath = new File(path);
        if (!filePath.exists())
        {
            filePath.mkdirs();
        }
        
        String name = path + "/" + time + ".json";
        File file = new File(name);

        if (file.exists())
        {
            String str4 = NribCommon.createRandomString(4, false);
            name = path + "/" + time + str4 + ".json";
        }
        String msgInfo = JSONUtil.toJson(info);
        try {
        	jsonOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(name), true)));
            jsonOut.write(msgInfo);
            jsonOut.newLine();
            jsonOut.flush();
            result = true;
            logger.info(cfUtl.getMsgvalueAndPar("I0008",name));
            logger.info(cfUtl.getMsgvalueAndPar("I0009",msgInfo));
        } catch(Exception e) {
        	logger.info(cfUtl.getMsgvalueAndPar("E0002"));
        	return result;
        }
        finally{
        	if (jsonOut != null) {
        		jsonOut.close();
        	}
        }
		return result;
		
	}
}
