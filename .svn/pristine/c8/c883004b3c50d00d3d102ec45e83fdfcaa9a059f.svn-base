package com.nrib.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nrib.common.ConfUtil;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(LogoutServlet.class);
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 创建conf对象
        ConfUtil conf = ConfUtil.getInstance();
        HttpSession session = request.getSession();
        String sid = session.getId();
        String userId = (String) session.getAttribute("UserId");
        session.removeAttribute("UserId");
        session.removeAttribute("UserName");
        session.removeAttribute("login_flag");
        session.removeAttribute("url_flag");
        session.removeAttribute("worklistall");
        session.removeAttribute("message");
        session.removeAttribute(sid);

        session.removeAttribute("noncestr");
        session.removeAttribute("timestamp");
        session.removeAttribute("signature");
        session.removeAttribute("appId");
        session.removeAttribute("token");
        session.removeAttribute("flg");
        logger.info(conf.getMsgvalueAndPar("I0003", "UserId:" + userId));
    }
}
