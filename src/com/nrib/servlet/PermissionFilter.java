package com.nrib.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.nrib.common.ConfUtil;

public class PermissionFilter implements Filter {

    private static Logger logger = Logger.getLogger(PermissionFilter.class);

    public void init(FilterConfig config) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        ConfUtil conf = ConfUtil.getInstance();
        // 首先将参数中的ServletRequest和ServletResponse强转为Http...
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hresp = (HttpServletResponse) response;
        // 获取请求中的Servletpath
        String servletpath = hreq.getServletPath();
        // 获取请求中的ContextPath
        String contextPath = hreq.getContextPath();
        // 获取session对象
        HttpSession session = hreq.getSession();
        // 获取session对象中flag的值，强转为String类型
        String login_flag = (String) session.getAttribute("login_flag");
        String url_flag = (String) session.getAttribute("url_flag");
        if ("/".equals(servletpath)) {
            session.removeAttribute("url_flag");
            session.setAttribute("url_flag", "url_success");
            if ("login_success".equals(login_flag)) {
                // login成功状态时，默认url的访问
                hresp.sendRedirect(contextPath + "/upload.jsp");
            } else {
                // login不成功状态时，跳转到用户认证的Code取得
                moveToURL(hreq, hresp, "/CodeServlet");
            }
        } else if ("/LoginServlet".equals(servletpath)) {
            if ("url_success".equals(url_flag)
                    && !"login_success".equals(login_flag)
                    && request.getParameter("code") != null) {
                // 有code时，跳转到用户认证的用户信息认证处理
                moveToURL(hreq, hresp, "/LoginServlet");
            } else {
                // 跳转到url错误
                hresp.sendRedirect(contextPath + "/error/404.jsp");
            }
        } else if ("/CodeServlet".equals(servletpath)) {
            if ("url_success".equals(url_flag)
                    && !"login_success".equals(login_flag)) {
                // 跳转到用户认证的Code取得
                moveToURL(hreq, hresp, "/CodeServlet");
            } else {
                // 跳转到url错误
                hresp.sendRedirect(contextPath + "/error/404.jsp");
            }
        } else {
            if ("login_success".equals(login_flag)) {
                // 继续原来操作
                chain.doFilter(request, response);
            } else {
                // 跳转到url错误
                hresp.sendRedirect(contextPath + "/error/405.jsp");
            }
        }
    }
    private void moveToURL(HttpServletRequest hreq, HttpServletResponse hresp, String url) throws ServletException, IOException {
        // 用户认证跳转
        RequestDispatcher rd = hreq.getRequestDispatcher(url);
        rd.forward(hreq, hresp);
    }

    @Override
    public void destroy() {
        
    }

}
