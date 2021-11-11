<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">  
<meta http-equiv="cache-control" content="no-cache">  
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
<title>mtsWechat</title> 
</head> 
<body> 
<div data-role="page" id="pageone">
  <div data-role="header">
    <h1>微信起工单</h1>
  </div>
  <div data-role="main">
	<table style="width:100%;">
	  <tr>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #e9e9e9;"><span>工单标题</span></td>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #ffffff;"><span><%=request.getAttribute("title")%></span></td>
	  </tr>
	  <tr>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #e9e9e9;"><span>原因</span></td>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #ffffff;"><span><%=request.getAttribute("content")%></span></td>
	  </tr>
	  <tr>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #e9e9e9;"><span>处理方法结果</span></td>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #ffffff;"><span><%=request.getAttribute("result1")%></span></td>
	  </tr>
	  <tr>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #e9e9e9;"><span>处理人</span></td>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #ffffff;"><span><%=request.getAttribute("user_name")%></span></td>
	  </tr>
	  <tr>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #e9e9e9;"><span>电话</span></td>
	    <td style="width:50%;font-weight: bold;padding: 10px;border: 1px solid #d6d6d6;background: #ffffff;"><span><%=request.getAttribute("tel")%></span></td>
	  </tr>
	</table>
  </div>
  <div data-role="main" class="ui-content">
     <input type="button" class="btn"  value="返回" onclick="javascript:history.go(-1);"/>
  </div>
</div> 
</body> 
</html> 