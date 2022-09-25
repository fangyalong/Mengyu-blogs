/* 鼠标点击文字特效 */
var font = new Array( '★','❄','♧','♡','☭');
var color = new Array('#ff0000', '#eb4310', '#fbb417', '#ffff00', '#cdd541', '#99cc33', '#3f9337',
    '#dbae2a', '#24998d', '#1f9baa', '#0080ff', '#3366cc', '#003366', '#800080', '#a1488e',
    '#c71585', '#81cb61', '#d40cc0','#00FFFFFF','#FFC0CB','#fbb417' );
var f_idx = Math.floor(Math.random() * 20) % font.length;
jQuery(document).ready(function($) {
    $("html").click(function(e) {
        var $i = $("<span />").text(font[f_idx]);
        f_idx = Math.floor(Math.random() * 20) % font.length;
        c_idx = Math.floor(Math.random() * 40) % color.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 9999999999999999999999999,
            "top": y - 20,
            "left": x,
            "position": "absolute",
            "font-weight": "bold",
            "color": color[c_idx],
            "font-size": "2em"
    });
        $("html").append($i);
        $i.animate({
                "top": y - 180,
                "opacity": 0
            },
            1500,
            function() {
                $i.remove();
            });
    });
});

var percentScroll = 0;
$(document).bind('mousewheel DOMMouseScroll', function(event) { //on也可以 bind监听
    //获取滚动条的页面百分比
    window.onscroll = function() {
        //htmlHeight 是网页的总高度
        var htmlHeight = document.documentElement.scrollHeight;
        //clientHeight是网页在浏览器中的可视高度，
        var clientHeight = document.documentElement.clientHeight;
        //scrollTop是浏览器滚动条的top位置，
        var scrollTop = document.documentElement.scrollTop;
        //通过判断滚动条的top位置与可视网页之和与整个网页的高度来返回各项参数
        percentScroll = (scrollTop / (htmlHeight - clientHeight))*100; //该值为滚动条的页面百分比

        if(percentScroll<10){
            $("#backToTheTop").addClass("none")
            $("#backToTheTop1").addClass("none")

        }else{
            $("#backToTheTop").removeClass("none");
            $("#backToTheTop1").removeClass("none")

        }
        if(htmlHeight<2000){

            if(percentScroll>50){
                $("#gding").addClass("gding1");
            }else{
                $("#gding").removeClass("gding1");
            }

            if(percentScroll>50){
            $("#gding").removeClass("gding1");
            }

        } else{


            if(percentScroll>60){
                $("#gding").addClass("gding");
            }else{
                $("#gding").removeClass("gding");
            }

            if(percentScroll>60){
                $("#gding").removeClass("gding");
            }else {
            }
        }


        if (percentScroll<=100){
            $('#example4')
                .progress({
                    percent: percentScroll
                });
        }
    }
})


$("#navId").click(function (){
    $('.my-item').toggleClass('my-mobile-hide');
});





