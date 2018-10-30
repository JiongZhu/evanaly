/**
 * Created by wx6_2 on 2017/6/9.
 */
var contextPath = $('#ctxPath').text()
var qrcode;
$(function () {
    $('#login-no').loginValidate({url : '/login'})
    $('#login-phone').phoneValidate()

    var no = $.cookie('no')
    if(no != undefined) {
        $("#employee-no").val(no)
    }

    var $validateImg = $("#validate-img");

    getQRCode();

    //更换验证码
    $validateImg.click(function () {
        $(this).attr('src', contextPath + '/validateImage?' + new Date());
    });

    setInterval(checkQRCode, 6000);

    //刷新二维码
    $(".qrcode").click(function(){
        getQRCode();
    })

});

function getQRCode(){
    $.ajax({
        url:'generateQRCode',
        type:'get',
        cache:false,
        success:function(d){
            $('.qrcode').html("");
            qrcode = d;
            $('.qrcode').qrcode({
                width: 144,
                height: 144,
                text: d
            });
        }
    })

}

function checkQRCode() {
		
        var data = {'qrCode': qrcode};
        $.ajax({
            url: 'checkQRCode',
            type: 'post',
            data: data,
            success: function (m) {
                if (m == "Y") {
                    window.location = contextPath + "/index.jsp";
                }
            }

        })
}
