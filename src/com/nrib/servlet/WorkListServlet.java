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

import com.nrib.common.CommonUtil;
import com.nrib.common.ConfUtil;
import com.nrib.common.Constant;
import com.nrib.common.StringUtil;
import com.nrib.wechat.WorkList;

/**
 * Servlet implementation class WorkListServlet
 */
@WebServlet("/WorkListServlet")
public class WorkListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(WorkListServlet.class);
       
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
        StringBuilder url = new StringBuilder();
        url.append(conf.getConfigvalueAndPar("SSMIP"));
        url.append(Constant.WORK_LIST_URL);
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
        String wechatId = (String) session.getAttribute("UserId");
        String action = request.getParameter("action");
        if (StringUtil.isEmpty(wechatId) || StringUtil.isEmpty(action)) {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "取得失败！");
            moveToURL(hreq, hresp, "/message.jsp");
            return;
        }
        WorkList workList= getWorkList(url.toString(), wechatId, action);
        if (workList != null) {
            List worklist = workList.getWorkList();
            List worklistnew = new ArrayList();
            if (worklist.size() > pagenum) {
                for (int i =0;i < pagenum;i++) {
                    worklistnew.add(worklist.get(i));
                }
            } else {
                for (int i =0;i < worklist.size();i++) {
                    worklistnew.add(worklist.get(i));
                }
            }
            session.removeAttribute("worklistall");
            session.setAttribute("worklistall", worklist);
            request.setAttribute("worklist", worklistnew);
            request.setAttribute("num", (int) Math.ceil((double) worklist.size()/pagenum));
            request.setAttribute("startnum", 1);
            moveToURL(hreq, hresp, "/worklist.jsp");
        } else {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "取得失败！");
            
            moveToURL(hreq, hresp, "/message.jsp");
        }
    }
    
    private WorkList getWorkList(String url, String wechatId, String action) {
        WorkList t = new WorkList();
        StringBuilder pam = new StringBuilder();
        pam.append("{");
        pam.append("\"").append("ESBREQ").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("DATA").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("xWechatId").append("\"").append(":");
        pam.append("\"").append(wechatId).append("\"").append(",");
        pam.append("\"").append("xStatus").append("\"").append(":");
        pam.append("\"").append(action).append("\"");
        pam.append("}");
        pam.append("}");
        pam.append("}");
        t = CommonUtil.getWorkList(url, pam.toString());
        return t;
    }

    private void moveToURL(HttpServletRequest hreq, HttpServletResponse hresp, String url) throws ServletException, IOException {
        // 用户认证跳转
        RequestDispatcher rd = hreq.getRequestDispatcher(url);
        rd.forward(hreq, hresp);
    }
    

}
