<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
	<title>mtsWechat</title>
	<!-- 页面禁止缓存 -->
	<meta http-equiv="pragma" content="no-cache">  
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="expires" content="0">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<link rel="stylesheet" href="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
	<script type="text/javascript">
	wx.config({
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '<%=request.getAttribute("appId")%>', // 必填，企业微信的cropID
	    timestamp: '<%=request.getAttribute("timestamp")%>', // 必填，生成签名的时间戳
	    nonceStr: '<%=request.getAttribute("noncestr")%>', // 必填，生成签名的随机串
	    signature: '<%=request.getAttribute("signature")%>', // 必填，签名，见附录1
	    jsApiList: [
	    	'hideOptionMenu'
	    	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
		var flg = <%=request.getAttribute("flg")%>;
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
	    if(flg) {
	    	wx.hideOptionMenu();
	    }
	});
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
	});
	</script>
	</head>
	<body>
        <div data-role="page">
          <div data-role="header">
                <h2>${message}</h2>
          </div>
          <div data-role="footer">
          </div>
        </div> 
</body>
</html>