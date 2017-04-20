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

