$(document).on("click","#backToTheTop",function () {
    $(window).scrollTo(0,500);
})

var waypoint = new Waypoint({
    element: document.getElementById('Indexbody1'),
    handler: function(data) {
        if (data=='down'){
            $("#backToTheTop").removeClass("animate__animated animate__bounceOutRight");
            $("#backToTheTop1").removeClass("animate__animated animate__bounceOutRight");

            $("#backToTheTop").addClass("animate__animated animate__bounceInRight");
            $("#backToTheTop1").addClass("animate__animated animate__bounceInRight");

            $("#backToTheTop").show();

            $("#backToTheTop1").show();

        }else {
            $("#backToTheTop").hide();
            $("#backToTheTop1").hide();

            $("#backToTheTop").removeClass("animate__animated animate__bounceInRight");
            $("#backToTheTop1").removeClass("animate__animated animate__bounceInRight ");

            $("#backToTheTop").addClass("animate__animated animate__bounceOutRight");
            $("#backToTheTop1").addClass("animate__animated animate__bounceOutRight");

            $("#backToTheTop").show();

            $("#backToTheTop1").show();
        }

    }
})

