/**
 * 
 */
package com.nrib.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.nrib.common.ConfUtil;
import com.nrib.common.Constant;
import com.nrib.wechat.Ticket;
import com.nrib.wechat.Token;
import com.nrib.wechat.UserDetail;
import com.nrib.wechat.UserTicket;

/**
 * @author gaoliming
 *
 *         2017/11/28
 */

@WebServlet("/CodeServlet")
public class CodeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7560190997463527072L;

	private static Logger logger = Logger.getLogger(CodeServlet.class);

	public static Token token = null;

    public static Ticket ticket = null;

    public static UserTicket userTicket = null;

    public static UserDetail userDetail = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();
		String contextPath = request.getContextPath();
        // 创建conf对象
        ConfUtil conf = ConfUtil.getInstance();
        try {
            String corpid = conf.getConfigvalueAndPar("CROPID");
            StringBuilder redirect_uri = new StringBuilder();
            redirect_uri.append(serverName);
            if (serverPort != 80) {
                redirect_uri.append(":").append(serverPort);
            }
            redirect_uri.append(contextPath);
            if ("/".equals(redirect_uri.substring(redirect_uri.length()))) {
                redirect_uri.append("LoginServlet");
            } else {
                redirect_uri.append("/").append("LoginServlet");
            }
            String agentid = conf.getConfigvalueAndPar("AGENTID");
            String state = null;
            String strcropCodeURL = getCodeURL(corpid, redirect_uri.toString(), Constant.SCOPE3, agentid, state,
                    Constant.GET_CROP_CODE_URL);
            logger.info(conf.getMsgvalueAndPar("I0022",
                    "CROPID = " + corpid
                    + " REDIRECT_URI = " + redirect_uri.toString()
                    + " SCOPE = " + Constant.SCOPE3
                    + " AGENTID = " + agentid
                    + " STATE = " + state));
            response.sendRedirect(strcropCodeURL);
        } catch (Exception e) {
            // 跳转到 哪一个页面
            request.setAttribute("message", "用户认证失败！");
            logger.error(conf.getMsgvalueAndPar("E0001", e.toString()));
        }
	}

    public static String getCodeURL(String corpid, String redirect_uri, String scope, String agentid,
            String state, String cropCodeURL) {
        String rtnURL = "";
        if (corpid != null) {
            rtnURL = cropCodeURL.replace("CORPID", corpid);
        }
        if (redirect_uri != null) {
            rtnURL = rtnURL.replace("REDIRECT_URI", redirect_uri);
        }
        if (scope != null) {
            rtnURL = rtnURL.replace("SCOPE", scope);
        }
        if (agentid != null) {
            rtnURL = rtnURL.replace("AGENTID", agentid);
        }
        if (state != null) {
            rtnURL = rtnURL.replace("STATE", state);
        }
        return rtnURL;
    }
}
