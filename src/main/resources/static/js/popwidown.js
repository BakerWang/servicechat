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
$(document).ready(function(){
	 $("#startWebsocket").click(function(){ var websocket = null;
	    //var url = 'ws://'+window.location.host+'/web/count';
	    //判断当前浏览器是否支持WebSocket
	    if('WebSocket' in window){
	        websocket = new WebSocket('ws://localhost:8080/ws');
	    }
	    else{
	        alert('浏览器不支持 websocket')
	    }
	    //连接发生错误的回调方法
	    websocket.onerror = function(){
	    	setMessage("error");
	    };
	    //连接成功建立的回调方法
	    websocket.onopen = function(event){
	    	setMessage("open");
	    }
	    //接收到消息的回调方法
	    websocket.onmessage = function(event){
	    	setMessage(event.data);
	    }
	    //连接关闭的回调方法
	    websocket.onclose = function(){
	    	setMessage("close");
	    }
	    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
	    window.onbeforeunload = function(){
	        websocket.close();
	    }
	    //将消息显示在网页上
	    function setMessage(event){
	       // document.getElementById('message').innerHTML += innerHTML + '<br/>';
	        var ta = document.getElementById('message');
	        ta.value = ta.value + '\n' + event.data
	    }
	    //关闭连接
	    function closeWebSocket(){
	        websocket.close();
	    }
	    //发送消息
	    function send(){
	        var message = document.getElementById('responseText').value;
	        websocket.send(message);
	    }
	    });
	});


/*<textarea id="responseText" style="width: 500px; height: 300px;"></textarea>
<br>
<input type="text" name="message"  style="width: 300px" value="开始：">
<input type="button" value="发送消息" onclick="send(this.form.message.value)">
<input type="button" onclick="javascript:document.getElementById('responseText').value=''" value="清空聊天记录">
</form>*/


