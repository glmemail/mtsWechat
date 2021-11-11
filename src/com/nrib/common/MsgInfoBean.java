package com.nrib.common;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhaoyubo
 *
 * 2017/07/31
 */
public class MsgInfoBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2539177867375047801L;

	// 系统ID
	String SYS_ID = "";

	// 服务器ID
	String SVR_ID = "";

	// 子系统ID
	String SUB_SYS_ID = "";

	// 企业ID
	String CMP_ID = "";

	// 时间戳
	String TIMESTAMP = "";

	// 信息
	List<Map<String, String>> MSG = null;

	public String getSYS_ID() {
		return SYS_ID;
	}

	public void setSYS_ID(String sYS_ID) {
		SYS_ID = sYS_ID;
	}

	public String getSVR_ID() {
		return SVR_ID;
	}

	public void setSVR_ID(String sVR_ID) {
		SVR_ID = sVR_ID;
	}

	public String getSUB_SYS_ID() {
		return SUB_SYS_ID;
	}

	public void setSUB_SYS_ID(String sUB_SYS_ID) {
		SUB_SYS_ID = sUB_SYS_ID;
	}

	public String getCMP_ID() {
		return CMP_ID;
	}

	public void setCMP_ID(String cMP_ID) {
		CMP_ID = cMP_ID;
	}

	public String getTIMESTAMP() {
		return TIMESTAMP;
	}

	public void setTIMESTAMP(String tIMESTAMP) {
		TIMESTAMP = tIMESTAMP;
	}

	public List<Map<String, String>> getMSG() {
		return MSG;
	}

	public void setMSG(List<Map<String, String>> mSG) {
		MSG = mSG;
	}

}
