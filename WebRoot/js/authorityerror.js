/**
 * Created by wx6_2 on 2017/6/21.
 */
$(function () {
    init()

    var flag = true
    $(document).on('click', '.left-menu-title', function () {
        var $nav = $('.left-menu');
        var $content = $('.left-menu-content');
        var $link = $('.left-menu > .nav > li > a')
        var $main = $('.content')

        if (flag) {
            $nav.removeClass('col-md-2 col-xs-2').addClass('col-md-1 col-xs-1')
            $main.removeClass('col-md-10 col-xs-10').addClass('col-md-11 col-xs-11')
            $content.hide();
            $link.css('width', '30px');

            flag = false;
        } else{
            $nav.removeClass('col-md-1 col-xs-1').addClass('col-md-2 col-xs-2')
            $main.removeClass('col-md-11 col-xs-11').addClass('col-md-10 col-xs-10')
            $link.css('width', '156px');
            $content.show();

            flag =true;
        }
    });
})

function init() {
    var employee = $.cookie("employee")
    if(employee != undefined) {
        employee = $.parseJSON(employee)
        $('#logined').user(employee)
        if(employee.authority == 0)
            $("#manage-menu").show()

        $('#unlogin').addClass("hidden").removeClass("show");
        $('#logined').addClass("show").removeClass("hidden");
    } else {
        $('#logined').addClass("hidden").removeClass("show");
        $('#unlogin').addClass("show").removeClass("hidden");
    }
}