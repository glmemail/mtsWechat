<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- 页面禁止缓存 -->
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<link rel="stylesheet" href="css/style.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>mtsWechat</title> 
<script type="text/javascript">
function moveto(action) {
    location.href='<%=request.getContextPath()%>/WorkListServlet?action=' + action;
}
</script>
</head> 
<body> 
<div data-role="page" id="pageone">
  <div data-role="header">
    <h1>微信起工单</h1>
  </div>
  <div data-role="main" class="ui-content">
  <div align="center">
      <a onclick="moveto('0001')">未处理</a>
  </div>
  <br/>
  <div align="center">
      <a onclick="moveto('0002')">处理中</a>
  </div>
  <br/>
  <div align="center">
      <a onclick="moveto('0003')">已处理</a>
  </div>
  </div>
</div> 
</body> 
</html> 