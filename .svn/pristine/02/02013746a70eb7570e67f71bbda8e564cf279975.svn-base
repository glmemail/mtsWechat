<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.Map"%>
<%@ page language="java" import="java.util.List"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>mtsWechat</title> 
<!-- 页面禁止缓存 -->
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="css/pager.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<script src="js/pager.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	Page({
        num:<%=request.getAttribute("num")%>,                  //页码数
        startnum:<%=request.getAttribute("startnum")%>,             //指定页码
        elem:$('#page1'),       //指定的元素
        callback:function(n){   //回调函数
        	location.href='<%=request.getContextPath()%>/PageTurnServlet?action=<%=session.getAttribute("action")%>&startnum=' + n;
        }
    });
});
</script>
</head> 
<body> 
<div data-role="page" id="worklist">
  <div data-role="header"  >
    <h1>微信起工单</h1>
  </div>
  <div data-role="main" class="ui-content">
    <table data-role="table" data-mode="column" class="ui-responsive ui-shadow" id="myTable">
      <thead>
        <tr>
          <th>标题</th>
          <th>状态</th>
          <th>处理人</th>
        </tr>
      </thead>
      <tbody>
	    <c:forEach items="${worklist}" var="work" varStatus="vs">
	      <tr>
            <td style="width:50%;">
				<c:if test="${empty work.INSERT_NO}">
				    ${work.TITLE}
				</c:if>
				<c:if test="${not empty work.INSERT_NO}">
				    <a href="<%=request.getContextPath()%>/WorkDetailServlet?insertno=${work.INSERT_NO}">${work.TITLE}</a>
				</c:if>
            </td>
			<td style="width:20%;">${work.STATUS}</td>
			<td style="width:30%;">${work.USER_NAME}</td>
	       </tr>
	    </c:forEach>
      </tbody>
    </table>
  </div>
  <div style="width:100%;" class="ui-content"><ul class="pagination" id="page1"></ul></div>
</div>
</body>
</html>