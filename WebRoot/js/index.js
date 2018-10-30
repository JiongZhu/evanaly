/**
 * Created by wx6_2 on 2017/6/14.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    init()
})

function init() {
    $("#manage-menu").hide()
    var employee = $.cookie("employee");
    if (employee == undefined) {
        $.ajax({
            type: 'GET',
            url: contextPath + '/isLogin',
            success: function (employee) {
                if (employee != "") {
                    $('#unlogin').addClass("hidden").removeClass("show");
                    $('#logined').addClass("show").removeClass("hidden");

                    $.cookie("employee", employee, {path: '/'});
                    employee = $.parseJSON(employee);
                    $('#logined').user(employee)

                    if (employee.authority == 0) {
                        $("#manage-menu").show()
                    }
                } else {
                    $('#logined').addClass("hidden").removeClass("show");
                    $('#unlogin').addClass("show").removeClass("hidden");
                }
            }
        });
    } else {
        $('#unlogin').addClass("hidden").removeClass("show");
        $('#logined').addClass("show").removeClass("hidden");

        employee = $.parseJSON(employee);
        $('#logined').user(employee)

        if (employee.authority == 0) {
            $("#manage-menu").show()
        }
    }
}