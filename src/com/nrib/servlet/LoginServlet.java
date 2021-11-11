/**
 * 
 */
package com.nrib.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.nrib.common.CommonUtil;
import com.nrib.common.ConfUtil;
import com.nrib.common.Constant;
import com.nrib.common.NribCommon;
import com.nrib.wechat.Ticket;
import com.nrib.wechat.Token;
import com.nrib.wechat.UserDetail;
import com.nrib.wechat.UserTicket;

/**
 * @author zhaoyubo
 *
 *         2017/07/28
 */

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7560190997463527072L;

	private static Logger logger = Logger.getLogger(LoginServlet.class);

    public Ticket ticket = null;

    private UserTicket userTicket = null;

    private UserDetail userDetail = null;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        doPost(request, response);
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String sid = session.getId();
        // 创建conf对象
        ConfUtil conf = ConfUtil.getInstance();
        // 跳转页面
        String redirect = request.getContextPath() + "/upload.jsp";
        try {
            String code = request.getParameter("code");
            String corpid = conf.getConfigvalueAndPar("CROPID");
            logger.info(conf.getMsgvalueAndPar("I0027", "CODE = " + code));
            if (code == null) {
                redirect = request.getContextPath() + "/login.jsp";
                // 跳转到页面
                request.setAttribute("message", "登录处理  取得的认证CODE失败！");
                response.sendRedirect(redirect);
                return;
            }
            session.removeAttribute("login_flag");
            session.removeAttribute("UserId");
            session.removeAttribute("UserName");
            // 获取access_token
            String secret = conf.getConfigvalueAndPar("APPSECRET");
            String tokenUrl = Constant.GET_TOKEN_URL;
            logger.info(conf.getMsgvalueAndPar("I0023",
                    "CROPID = " + corpid
                    + " SECRET = " + secret));
            Token token = (Token) session.getAttribute("token");
            if (token != null) {
                // 对比Token的过期时间和当前时间，如果当前时间大于等于过期时间，则token过期，重新获取Token
                Date date = new Date();
                Long currentTime = date.getTime();
                Long expirationTime = token.getExpiration();
                if (expirationTime < currentTime) {
                    // 如果token过期，则重新获取token
                    token = headtoken(corpid, secret, tokenUrl);
                    session.setAttribute("token", token);
                }
            } else {
                // 如果token为空（第一次发送消息），则获取token
                token = headtoken(corpid, secret, tokenUrl);
                session.setAttribute("token", token);
            }

            if (token == null) {
                redirect = request.getContextPath() + "/login.jsp";
                // 跳转到页面
                request.setAttribute("message", "token为空！");
                logger.error(conf.getMsgvalueAndPar("E0032", "token为空"));
                response.sendRedirect(redirect);
                return;
            } else {
                if (ticket == null) {
                    // 初次获取ticket
                    ticket = getTicket(Constant.GET_JSAPI_TICKET_URL, token.getAccessToken());
                } else {
                    // 已有ticket
                    Date date = new Date();
                    Long currentTime = date.getTime();
                    Long expirationTime = ticket.getExpiration();
                    if (expirationTime < currentTime) {
                        // ticket过期，重新获取
                        ticket = getTicket(Constant.GET_JSAPI_TICKET_URL, token.getAccessToken());
                    }
                }
            }

            // 准备生成签名
            Map<String, Object> maps = new HashMap<String, Object>();
            String noncestr = NribCommon.createRandomString(16, false);
            String jsapi_ticket = ticket.getTicket();
            long timestamp = System.currentTimeMillis();
            StringBuilder url =new StringBuilder();
            url.append(request.getScheme());
            url.append("://");
            url.append(request.getServerName());
            url.append(request.getContextPath());
            url.append("/upload.jsp");
            StringBuilder str = new StringBuilder();
            str.append("jsapi_ticket=");
            str.append(jsapi_ticket);
            str.append("&noncestr=");
            str.append(noncestr);
            str.append("&timestamp=");
            str.append(timestamp);
            str.append("&url=");
            str.append(url.toString());
            String signature = "";
            signature= DigestUtils.sha1Hex(str.toString());
            session.setAttribute("noncestr", noncestr);
            session.setAttribute("timestamp", timestamp);
            session.setAttribute("signature", signature);
            session.setAttribute("appId", corpid);
            session.setAttribute("flg", true);

            logger.info(conf.getMsgvalueAndPar("I0024"));
            // 获取userticket
            String userTicketurl = Constant.GET_USERTICKET_URL;
            userTicket = getUserTicket(userTicketurl, token.getAccessToken(), code);
            if (userTicket == null) {
                redirect = request.getContextPath() + "/login.jsp";
                // 跳转页面
                request.setAttribute("message", "用户认证失败！");
                logger.error(conf.getMsgvalueAndPar("E0001", "UserTicket = null"));
                response.sendRedirect(redirect);
                return;
            }
            if (userTicket.getOpenId() != null || userTicket.getUserTicket() == null
                    || userTicket.getErrcode() != 0) {
                redirect = request.getContextPath() + "/login.jsp";
                // 跳转页面
                request.setAttribute("message", "用户认证失败！");
                logger.error(conf.getMsgvalueAndPar("E0001", "UserTicket:" + userTicket.toString()));
                response.sendRedirect(redirect);
                return;
            } else {
                logger.info(conf.getMsgvalueAndPar("I0025", userTicket.toString()));
            }
            session.setAttribute("UserId", userTicket.getUserId());
            session.setAttribute(sid, userTicket.getUserId());
            String userDetailURL = Constant.GET_USERDETAIL_URL;
            String user_ticket = userTicket.getUserTicket();
            // 获取userDetail
            userDetail = getUserDetail(userDetailURL, token.getAccessToken(), user_ticket);
            if (userDetail == null) {
                redirect = request.getContextPath() + "/login.jsp";
                // 跳转到页面
                request.setAttribute("message", "用户认证失败！");
                logger.error(conf.getMsgvalueAndPar("E0001", "UserDetail = null"));
                response.sendRedirect(redirect);
                return;
            }
            logger.info(conf.getMsgvalueAndPar("I0026", userDetail.toString()));
            logger.info(conf.getMsgvalueAndPar("I0001", "UserId = " + userTicket.getUserId()));
            session.setAttribute("UserName", userDetail.getName());
            session.setAttribute("login_flag", "login_success");
        } catch (Exception e) {
            redirect = request.getContextPath() + "/login.jsp";
            // 跳转到页面
            request.setAttribute("message", "用户认证失败！");
            logger.error(conf.getMsgvalueAndPar("E0001", e.toString()));
        }
        // 跳转到页面
        response.sendRedirect(redirect);
        
	}

	public static Token headtoken(String id, String secret, String tokenUrl) {
		Token token = new Token();
		token = CommonUtil.getToken(id, secret, tokenUrl);
		return token;
	}

	public static Ticket getTicket(String ticketurl, String token) {
		Ticket t = new Ticket();
		t = CommonUtil.getTicket(ticketurl, token);
		return t;
	}

    /**
     * 获取成员信息
     * @param userTicketurl
     * @param access_token
     * @param code
     * @return
     */
    public static UserTicket getUserTicket(String userTicketurl, String access_token, String code) {
        UserTicket t = new UserTicket();
        t = CommonUtil.getUserTicket(userTicketurl, access_token, code);
        return t;
    }

    public static UserDetail getUserDetail(String userDetailturl, String access_token, String user_ticket) {
        UserDetail t = null;
        StringBuilder pam = new StringBuilder();
        pam.append("{");
        pam.append("\"");
        pam.append("user_ticket");
        pam.append("\"");
        pam.append(": ");
        pam.append("\"");
        pam.append(user_ticket);
        pam.append("\"");
        pam.append("}");
        t = CommonUtil.getUserDetail(userDetailturl, access_token, pam.toString());
        return t;
    }

}
