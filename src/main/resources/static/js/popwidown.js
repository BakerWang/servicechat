/* var ws = $.websocket("ws://localhost:8080/ws", {
			//发送消息
			         
		        open: function() {
		        	
		        },
		        close: function() { 
		        	
		        },
		        events: {
		        	message: function(e) {
		                	$('#content').append(e.data + '<br>')     
		                }
		        }
		});*/
function openwin() {
	window.open ("page.html", "newwindow", "height=100, width=400, toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no")
};
var lanren = {
        navFast:function(){
        	var scr_height = window.screen.availHeight;
        	var float_right= 300;
        	var o = document.getElementById("float_right");
            var defaultTop = (scr_height - float_right)/2 - 100; //默认顶部保持上下居中，再往上去100像素
                $(window).scroll(function(){
                        var offsetTop = defaultTop + $(window).scrollTop()+'px';
                        $('#float_right').animate({top:offsetTop},
                        {duration: 600,  //滑动速度
                        queue: false    //此动画将不进入动画队列
                });                                               
                });
        }
};
/*	 $.ajax({
cache: true,
type: "POST",
url:ajaxCallUrl,
data:$('#yourformid').serialize(),// 你的formid
async: false,
error: function(request) {
    alert("Connection error");
},
success: function(data) {
    $("#commonLayout_appcreshi").parent().html(data);
}
}); */
$(function(){
	var websocket = null;
	//开启websocket
    if('WebSocket' in window){
  	  websocket = new WebSocket('wss://localhost:8443/ws');
    }
    else{
      confirm('浏览器不支持 websocket')
    }
	 $("#startWebsocket").click(function(){
		 var ta = document.getElementById('message');
		 ta.value='';
		 //startWebsocket();
		 
		 $("#stratChat").show();
	 });
	 //关闭聊天
	 $("#closeChat").click(function(){
		 closeWebSocket();
		 setTimeout(function () { 
			 $("#stratChat").hide();
		    }, 10);
	 });
	 //发送聊天消息
	 $('#sendMsg').click(function(){
		 send();
	 });
//entery 触发发送事件
	 document.onkeydown = function(e){
		 var ev = document.all ? window.event : e;
		 if(ev.keyCode==13) {
			 send();//处理发送事件
		 }
	 }
//以下微websocket处理步骤
	 var startWebsocket=function(){
	    var url = 'wss://'+window.location.host+'/ws';
	    console.log(url);
	    //判断当前浏览器是否支持WebSocket
	    if(websocket == null|| websocket == undefined){
	    	  websocket = new WebSocket(url);
	    }
	    
	 };
	    //连接发生错误的回调方法
	    websocket.onerror = function(){
	    	setMessage("error");
	    };
	    //连接成功建立的回调方法
	    websocket.onopen = function(event){
	    	setMessage("会话开始！");
	    }
	    //接收到消息的回调方法
	    websocket.onmessage = function(event){
	    	console.log("客户端接受的数据-onmessage:"+event.data)
	    	setMessage(event.data);
	    }
	    //连接关闭的回调方法
	    websocket.onclose = function(){
	    	setMessage("退出");
	    }
	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function(){
	        websocket.close();
	    }
	    //将消息显示在网页上
	    function setMessage(data){
	       // document.getElementById('message').innerHTML += innerHTML + '<br/>';
	        var ta = document.getElementById('message');
	        ta.value = ta.value + '\n' + data
	    }
	    //关闭连接
	    function closeWebSocket(){
	    		 //websocket.close();
	    		 //
	    }
	    //发送消息
	    function send(){
	        var message = document.getElementById('responseText');
	        var data = message.value;
	        websocket.send(data);
	        message.value = '';
	        var ta = document.getElementById('message');
	        ta.value = ta.value + '\n'+'我：'+data
	    }
	});


