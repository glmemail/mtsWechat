package com.nrib.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nrib.common.ConfUtil;

/**
 * Servlet implementation class PageTurnServlet
 */
@WebServlet("/PageTurnServlet")
public class PageTurnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(PageTurnServlet.class);
       
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
        request.setCharacterEncoding("UTF-8");
        // ServletRequest和ServletResponse强转为Http...
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;
        HttpSession session = request.getSession();
        // 创建conf对象
        ConfUtil conf = ConfUtil.getInstance();
        List worklistall= (List) session.getAttribute("worklistall");
        int startnum = Integer.valueOf(request.getParameter("startnum"));
        int pagenum = 0;
        if (conf.getConfigvalueAndPar("NUM") == null || "".equals(conf.getConfigvalueAndPar("NUM"))) {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "每页件数不能为空！");
            moveToURL(hreq, hresp, "/message.jsp");
            return;
        } else {
            pagenum = Integer.valueOf(conf.getConfigvalueAndPar("NUM"));
            if (pagenum == 0) {
                logger.info(conf.getMsgvalueAndPar("I0018"));
                request.setAttribute("message", "每页件数不能为0！");
                moveToURL(hreq, hresp, "/message.jsp");
                return;
            }
        }
        if (worklistall != null) {
            List worklistnew = new ArrayList();
            // 翻页list计算
            int pageTurnnum = worklistall.size()- (startnum-1) * pagenum;
            if (pageTurnnum >= 0) {
                if (pageTurnnum > pagenum) {
                    for (int i =(startnum-1) * pagenum;i < (startnum) * pagenum;i++) {
                        worklistnew.add(worklistall.get(i));
                    }
                } else {
                    for (int i =(startnum-1) * pagenum;i < worklistall.size();i++) {
                        worklistnew.add(worklistall.get(i));
                    }
                }
            }
            request.setAttribute("worklist", worklistnew);
            request.setAttribute("num", (int) Math.ceil((double) worklistall.size()/pagenum));
            request.setAttribute("startnum", startnum);
            moveToURL(hreq, hresp, "/worklist.jsp");
        } else {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "工单取得失败！");
            moveToURL(hreq, hresp, "/message.jsp");
        }
    }
    
    private void moveToURL(HttpServletRequest hreq, HttpServletResponse hresp, String url) throws ServletException, IOException {
        // 用户认证跳转
        RequestDispatcher rd = hreq.getRequestDispatcher(url);
        rd.forward(hreq, hresp);
    }
    

}
