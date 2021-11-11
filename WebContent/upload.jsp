<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.Map"%>
<%@ page language="java" import="java.util.List"%>
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
    <link rel="stylesheet" href="css/style.css" />
	<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
	<script src="http://code.jquery.com/mobile/1.4.5/jquery.mobile-1.4.5.min.js"></script>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>  
    <script src="js/main.js"></script>
	<script type="text/javascript">
	wx.config({
	    beta: true,// 必须这么写，否则wx.invoke调用形式的jsapi会有问题
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
	    appId: '<%=session.getAttribute("appId")%>', // 必填，企业微信的cropID
	    timestamp: '<%=session.getAttribute("timestamp")%>', // 必填，生成签名的时间戳
	    nonceStr: '<%=session.getAttribute("noncestr")%>', // 必填，生成签名的随机串
	    signature: '<%=session.getAttribute("signature")%>', // 必填，签名，见附录1
	    jsApiList: [
	    	'hideOptionMenu','checkJsApi','chooseImage', 'uploadImage'
	    	] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});
	wx.ready(function(){
	    // config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
    	wx.hideOptionMenu();
    	wx.checkJsApi({
    	    jsApiList: ['chooseImage'], // 需要检测的JS接口列表，所有JS接口列表见附录2,
    	    success: function(res) {
    	        // 以键值对的形式返回，可用的api值true，不可用为false
    	        // 如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
    	    }
    	});
    	
	});
	wx.error(function(res){
	    // config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		alert(JSON.stringify(res));
	});


    var localIds = "";
    var imglocalIds=new Array(9);
    // 附件图片选择
	function wxChooseImage() {
        wx.chooseImage({  
            count: 9,  
            sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
            sourceType: ['album', 'camera'],      // 可以指定来源是相册还是相机，默认二者都有
            success: function (res) {
                // 返回选定照片的本地ID列表
                var reslocalIds = res.localIds;
                var number=reslocalIds.length + localIds.split(",").length - 1;
                if (number>9){
                	alert("选择附件超过9个！");
                	return;
                }
                if (reslocalIds.length>0){
	                while (reslocalIds.length>0)
	                {
	                	var reslocalId =reslocalIds.pop();
	                	if (localIds.match(reslocalId)){
	                		continue;
	                	}
	                	if (localIds==""){
	                		localIds=reslocalId;
	                	}else{
	                		localIds = localIds + "," +reslocalId
	                	}
	                }
	                // 附件图片添加
	                addImage();
                }
            },
            fail: function (res) {  
                alert("操作提示:"+JSON.stringify(res));  
            }
        });  
	}
    // 附件图片添加到画面显示
    function addImage() {
        // 图片本地ID
        imglocalIds = localIds.split(",");
        var row = Math.ceil((imglocalIds.length)/3);
        if (row<3) {
        	row++;
        }
        var i = 0;
        $('.ui-grid-b').each(function()
        {
            if (i<row) {
                 this.style.display='';
             } else {
                 this.style.display='none';
             }
            i++;
        });
        for (i=0;i<9;i++) {
        	var index=i+1;
            var id = "img-"+index;
            var delId = "img-del-"+index;
            $("#"+id).attr('src', '');
            $("#"+id).attr('alt', '');
            $("#"+id).removeAttr("onclick");
            $("#"+id).hide();
            $("#"+delId).attr('src', '');
            $("#"+delId).attr('alt', '');
            $("#"+delId).hide();
        }
        // img控件
        var index=0
        for(i=0;i<imglocalIds.length;i++){
            if (imglocalIds[i]!=""){
                index=i+1;
                var id = "img-"+index;
                var delId = "img-del-"+index;
                $("#"+id).attr('src', imglocalIds[i]);
                $("#"+id).attr('alt', imglocalIds[i]);
                
                $("#"+id).show();
                $("#"+delId).attr('src', 'img/del.png');
                $("#"+delId).attr('alt', 'img/del.png');
                document.getElementById(delId).style.display=""
            }
        }
        if (index<9) {
        	index++;
            var id = "img-"+index;
            $("#"+id).attr('src', 'img/add.png');
            $("#"+id).attr('alt', 'img/add.png');
            $("#"+id).attr('onclick', 'wxChooseImage();');
            $("#"+id).show();
        }
    }
    // 删除附件图片
    function delImage(obj) {
    	var delId=$(obj).attr("id");
        var id=delId.replace("del-","");
        if (localIds.split(",").length==1) {
        	localIds = "";
        } else if (localIds.indexOf($("#"+id).attr("alt") + ",")!=-1){
            localIds = localIds.replace($("#"+id).attr("alt") + ",", "");
        } else {
            localIds = localIds.replace("," + $("#"+id).attr("alt"), "");
        }
        // 图片刷新
        addImage();
    }
    // 上传的check
    function check() {
        if($("#content").val()=="" && localIds==""){
            alert("请填写工单内容或添加附件！");
        }else{
            syncUpload();
        }
    }
    // 附件图片上传
    var num=0;
    var serverIds = "";
    function syncUpload() {
        if (imglocalIds.length==0)
        {
            alert("请选择附件");
            return;
        }
        var imglocalId=imglocalIds.pop();
        wx.uploadImage({  
            localId: imglocalId,                // 需要上传的图片的本地ID，由chooseImage接口获得  
            isShowProgressTips: 1,              // 默认为1，显示进度提示  
            success: function (res) {
            	num++;
                // 返回图片的服务器端serverId 
                var serverId = res.serverId;
                if (serverIds=="") {
                    serverIds = serverId;
                } else {
                	serverIds = serverIds + "," + serverId
                }
                if (num==localIds.split(",").length && num==serverIds.split(",").length){
                    document.getElementById('serverIds').value=serverIds;
                    var form = document.getElementById('fileupload');
                    form.submit();
                } else {
                	syncUpload();
                }
            },fail: function (res) {  
                alert("上传提示:"+JSON.stringify(res));  
                return;
            }
        });
    }
    // 退出画面
    function logout(obj) {
        var form = document.forms[1];
        form.submit();
    }
    
    $(document).ready(function(){
        $("#content").after(
                "<div class='ui-grid-b'>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px;'>"
                + "<img id='img-1' name='fileField' src='img/add.png' alt='img/add.png' height='100' width='30%' onclick='wxChooseImage()'/>"
                + "<img id='img-del-1' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-2' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-2' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-3' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-3'  style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "</div>"
                + "<div class='ui-grid-b' style='display: none;'>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-4' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-4' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-5' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-5' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-6' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-6' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "</div>"
                + "<div class='ui-grid-b' style='display: none;'>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-7' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-7'style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-8' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-8' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "<div style='display: inline;position: relative;margin : 0px 0px 10px 10px'>"
                + "<img id='img-9' name='fileField' src='' alt='' height='100' width='30%' style='display:none;'/>"
                + "<img id='img-del-9' style='position: absolute;right: 0px;vertical-align: top;width: 20px;height: 20px;display: none;' onclick='delImage(this)'/>"
                + "</div>"
                + "</div>");
    });
	</script>
	</head>
    <body>
        <div data-role="page" id="pageupload">
          <div data-role="header"  >
            <h1>微信起工单</h1>
          </div>
          <div><span class="div-a" style="text-align:right;">&nbsp;</span><span class="div-b">
          <a href="workmenu.jsp" class="ui-table-columntoggle-btn ui-btn ui-btn-a ui-corner-all ui-shadow ui-mini" data-role="button" data-ajax="false">我的工单</a></span></div> 
          <div data-role="main" class="ui-content">
            <form id="fileupload" method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/UploadServlet" data-ajax="false">
                <input type="text" id="title" name="Title" maxlength="20"  placeholder="标题" data-clear-btn="true">
                <textarea id="content" name="Content" placeholder="内容...."></textarea>
<!--                 <input id="upload" type="button" class="btn" value="添加" onclick="wxChooseImage()"> -->
                <input id="button" type="button" class="btn" value="上传" onclick="check()">
                <input type="hidden" name="serverIds" id="serverIds"/>
            </form>
            <form action="<%=request.getContextPath()%>/LogoutServlet" method="post" data-ajax="false" onclick="WeixinJSBridge.call('closeWindow');">
                <input id="logout" type="submit" class="btn" value="退出">
            </form>
          </div>
          <div data-role="footer">
          </div>
        </div>
    </body>
</html>