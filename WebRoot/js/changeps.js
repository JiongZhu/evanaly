/**
 * Created by wx6_2 on 2017/6/14.
 */
var contextPath = $('#ctxPath').text()

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
    $("#manage-menu").hide()
    var employee = $.cookie("employee")
    if(employee != undefined) {
        employee = $.parseJSON(employee)
        $('#logined').user(employee)
        if(employee.authority == 0)
            $("#manage-menu").show()

        $("#employ-no").val(employee.no)
        $("#user-changeps").password({employee: employee})
        $('#unlogin').addClass("hidden").removeClass("show");
        $('#logined').addClass("show").removeClass("hidden");
    } else {
        $('#logined').addClass("hidden").removeClass("show");
        $('#unlogin').addClass("show").removeClass("hidden");
    }
}

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var Password = function (element, options) {
        this.$element = $(element)
        this.$psInput = this.$element.find("#employ-ps")
        this.$newpsInput = this.$element.find('#employ-newps')
        this.$confirmpsInput = this.$element.find('#employ-confirmps')
        this.$submitBtn = this.$element.find('#submit')
        this.$helpBlock = $(this.$element.find('.help-block')[0])
        this.psValid = false
        this.options = options

        this.$psInput.blur($.proxy(this.psValidate, this))
        this.$newpsInput.blur($.proxy(this.newpsValidate, this))
        this.$confirmpsInput.blur($.proxy(this.confirmpsValidate, this))
        this.$psInput.focus(this.tip)
        this.$newpsInput.focus(this.tip)
        this.$confirmpsInput.focus(this.tip)
        this.$submitBtn.click($.proxy(this.submit, this))
    }

    Password.DEFAULTS = {
        psReg: /^[\d\w]{6,12}$/
    }

    Password.prototype.psValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$psInput.val().trim()

        function success(data) {
            if(data == 'true') {
                this.$psInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                this.psValid = true
            } else {
                this.$psInput.parent().addClass('has-error').removeClass('has-success')
                this.$psInput.siblings('.help-block').css('display', 'block').text(data)
            }
        }

        if (value == '') {
            this.$psInput.siblings('.help-block').css('display', 'block').text('请输入当前密码!')
            this.$psInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                $.ajax({
                    type: 'GET',
                    url: contextPath + '/employee/validatePS',
                    data: {ps: value},
                    async: false,
                    success: $.proxy(success, this)
                })
            } else {
                this.$psInput.parent().addClass('has-error').removeClass('has-success')
                this.$psInput.siblings('.help-block').css('display', 'block').text('当前密码格式错误!')
            }
        }

        return false;
    }

    Password.prototype.tip = function (ev) {
        $(this).siblings('.help-block').css('display', 'block').text('密码格式为6-12字母和数字')
    }

    Password.prototype.newpsValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$newpsInput.val().trim()

        if (value == '') {
            this.$newpsInput.siblings('.help-block').css('display', 'block').text('请输入当前密码!')
            this.$newpsInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$newpsInput.parent().addClass('has-success').removeClass('has-error')
                this.$newpsInput.siblings('.help-block').css('display', 'none');
                return true;
            } else {
                this.$newpsInput.parent().addClass('has-error').removeClass('has-success')
                this.$newpsInput.siblings('.help-block').css('display', 'block').text('当前密码格式错误!')
            }
        }

        return false;
    }

    Password.prototype.confirmpsValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$confirmpsInput.val().trim()

        if (value == '') {
            this.$confirmpsInput.siblings('.help-block').css('display', 'block').text('请输入当前密码!')
            this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$confirmpsInput.parent().addClass('has-success').removeClass('has-error')
                this.$confirmpsInput.siblings('.help-block').css('display', 'none');

                if(this.$newpsInput.val().trim() != '' && this.$newpsInput.val().trim() != value) {
                    this.$confirmpsInput.siblings('.help-block').css('display', 'block').text('密码输入不一致!')
                    this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
                    return false
                }
                return true;
            } else {
                this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
                this.$confirmpsInput.siblings('.help-block').css('display', 'block').text('当前密码格式错误!')
            }
        }
        return false;
    }

    Password.prototype.submit = function (e) {
        if ((this.psValid || this.psValidate()) && this.newpsValidate() && this.confirmpsValidate()) {
            $.cookie("employee", null, {path: '/'})
            $("#changeps-form").submit()
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, Password.DEFAULTS, typeof option == 'object' && option)

            new Password($this, options);
        })
    }

    var old = $.fn.password

    $.fn.password = Plugin
    $.fn.password.Constructor = Password


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.password.noConflict = function () {
        $.fn.password = old
        return this
    }

}(jQuery);