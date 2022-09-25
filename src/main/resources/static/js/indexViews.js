$(function (){
    RandomlyGetFamousQuotes();
})


$(document).on("click","#scroll_down_btn",function () {
    $(window).scrollTo(760,500);
})





function RandomlyGetFamousQuotes() {
    $.ajax({
        url:"https://v1.hitokoto.cn/",
        data:{
            Cati:"d",
            max_length:"20"
        },
        type:"GET",
        success:function (data){
            $("#p-title").html(data.hitokoto);
        }
    })}


function siteTime(){

    X = new Date("9/5/2022 12:00:00");
    Y = new Date();
    T = (Y.getTime() - X.getTime());
    M = 24 * 60 * 60 * 1000;
    a = T / M;
    A = Math.floor(a);
    b = (a - A) * 24;
    B = Math.floor(b);
    c = (b - B) * 60;
    C = Math.floor((b - B) * 60);
    D = Math.floor((c - C) * 60);

        currentTimeHtml = '本站已运行：' + A + '天'
        + B + '时' + C + '分' + D
        + '秒';

    document.getElementById("htmer_time").innerHTML = currentTimeHtml;
}


setInterval(siteTime, 1000);