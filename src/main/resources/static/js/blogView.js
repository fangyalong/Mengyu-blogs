$("#payButton").popup({
    popup: $('.payQR.popup'),
    on: 'click',
    position: 'bottom center'
});




tocbot.init({
    // Where to render the table of contents.
    tocSelector: '.js-toc',
    // Where to grab the headings to build the table of contents.
    contentSelector: '.js-toc-content',
    // Which headings to grab inside of the contentSelector element.
    headingSelector: 'h1, h2, h3',
    // For headings inside relative or absolute positioned containers within content.
    hasInnerContainers: true,
    headingsOffset: 100,
    scrollSmoothOffset: -80
});


$(document).on("blur","#qq",function () {
    let qq=$(this).val();
    if(qq!=""){
        $.ajax({
            url:"https://api.lixingyong.com/api/qq?id="+qq,
            type:"GET",
            success:function (data) {
                if (data.code == 500) {
                    console.log(data)
                    $("#qq_avatar").attr("src", "/images/commentAvatar.jpg");
                    $("[name='email']").val("");
                    $("[name='nickname']").val("");
                } else {
                    var obj = JSON.parse(data);
                    $("#qq_avatar").attr("src", obj.avatar);
                    $("[name='email']").val(obj.email);
                    $("[name='nickname']").val(obj.nickname);
                }
            }
        })
    }

});





