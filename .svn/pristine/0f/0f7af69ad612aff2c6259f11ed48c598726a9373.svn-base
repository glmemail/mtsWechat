package com.nrib.servlet;

import java.io.IOException;

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
import com.nrib.wechat.WorkDetail;

/**
 * Servlet implementation class WorkDetailServlet
 */
@WebServlet("/WorkDetailServlet")
public class WorkDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(WorkDetailServlet.class);
       
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
        url.append(Constant.WORK_DETAIL_URL);
        String wechatId = (String) session.getAttribute("userId");
        String insertNo = (String) request.getParameter("insertno");
        if (StringUtil.isEmpty(wechatId) || StringUtil.isEmpty(insertNo)) {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "取得失败！");
            moveToURL(hreq, hresp, "/message.jsp");
            return;
        }
        WorkDetail workDetail = getWorkDetail(url.toString(), insertNo, wechatId);
        if (workDetail != null) {
            // 标题
            request.setAttribute("title", workDetail.getTITLE());
            // 内容
            request.setAttribute("content", workDetail.getCONTENT());
            // 处理方法和结果
            request.setAttribute("result1", workDetail.getRESULT());
            // 用户名
            request.setAttribute("user_name", workDetail.getUSER_NAME());
            // 电话
            request.setAttribute("tel", workDetail.getTEL());
            // 显示工单详细画面
            moveToURL(hreq, hresp, "/workdetail.jsp");
        } else {
            logger.info(conf.getMsgvalueAndPar("I0018"));
            request.setAttribute("message", "取得失败！");
            moveToURL(hreq, hresp, "/message.jsp");
        }
    }
    
    private WorkDetail getWorkDetail(String url, String insertNo, String wechatId) {
        WorkDetail t = new WorkDetail();
        StringBuilder pam = new StringBuilder();
        pam.append("{");
        pam.append("\"").append("ESBREQ").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("DATA").append("\"").append(":");
        pam.append("{");
        pam.append("\"").append("xInsertNo").append("\"").append(":");
        pam.append("\"").append(insertNo).append("\"").append(",");
        pam.append("\"").append("xWeChatId").append("\"").append(":");
        pam.append("\"").append(wechatId).append("\"");
        pam.append("}");
        pam.append("}");
        pam.append("}");
        t = CommonUtil.getWorkDetail(url, pam.toString());
        return t;
    }

    private void moveToURL(HttpServletRequest hreq, HttpServletResponse hresp, String url) throws ServletException, IOException {
        // 用户认证跳转
        RequestDispatcher rd = hreq.getRequestDispatcher(url);
        rd.forward(hreq, hresp);
    }
    

}
