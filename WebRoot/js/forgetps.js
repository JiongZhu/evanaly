/**
 * Created by wx6_2 on 2017/6/22.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    $('#forgetps').forgetPS();
});

+function ($) {
    'use strict';
    // USER CLASS DEFINITION
    // =========================

    var ForgetPS = function (element, options) {
        this.$element = $(element)
        this.$phoneInput = $(this.$element.find('input[name="phone"]')[0])
        this.$codeInput = this.$element.find('#code')
        this.$confirmpsInput = this.$element.find('#confirmps')
        this.$psInput = $(this.$element.find('input[name="password"]')[0])
        this.$sendCodeBtn = this.$element.find('#sendCode')
        this.$nextBtn = this.$element.find('#next')
        this.$confirmBtn = this.$element.find('#confirm')
        this.$firstHelpBlock = this.$element.find('#first-helpblock')
        this.$secondHelpBlock = this.$element.find('#second-helpblock')
        this.options = options

        this.$phoneInput.blur($.proxy(this.phoneValidate, this))
        this.$codeInput.blur($.proxy(this.codeValidate, this))
        this.$psInput.blur($.proxy(this.psValidate, this))
        this.$psInput.focus($.proxy(this.tip, this))
        this.$confirmpsInput.blur($.proxy(this.confirmpsValidate, this))
        this.$sendCodeBtn.click($.proxy(this.sendCode, this))
        this.$nextBtn.click($.proxy(this.next, this))
        this.$confirmBtn.click($.proxy(this.submit, this))
    }

    ForgetPS.DEFAULTS = {
        phoneReg: /^(13\d|14\d|15\d|18\d)\d{8}$/,
        codeReg: /^[\d\w]{4}$/,
        psReg: /^[\d\w]{6,12}$/
    }

    ForgetPS.prototype.tip = function (ev) {
        this.$secondHelpBlock.css('display', 'block').text('密码格式为6-12字母和数字')
    }

    ForgetPS.prototype.phoneValidate = function (e) {
        var regex = this.options.phoneReg
        var value = this.$phoneInput.val().trim()

        if (value == '') {
            this.$firstHelpBlock.css('display', 'block').text('请输入手机号!')
            this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$phoneInput.parent().addClass('has-success').removeClass('has-error')
                this.$firstHelpBlock.css('display', 'none');
                return true;
            } else {
                this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
                this.$firstHelpBlock.css('display', 'block').text('手机号格式错误!')
            }
        }

        return false;
    }

    ForgetPS.prototype.sendCode = function (e) {
        var time = 60;
        var instance;

        function interval() {
            this.$sendCodeBtn.text(time-- + "秒后重新发送");
            if (time == -1) {
                this.$sendCodeBtn.removeAttr("disabled");
                this.$sendCodeBtn.text("发送验证码");
                clearInterval(instance);
            }
        }

        function success(data) {
            if (data == '') {
                this.$sendCodeBtn.attr("disabled", "true");
                instance = setInterval($.proxy(interval, this), 1000);
            }
            else
                this.$firstHelpBlock.css('display', 'block').text(data);
        }

        if (this.phoneValidate()) {
            $.ajax({
                type: 'GET',
                url: contextPath + '/sendCode',
                data: {
                    phone: this.$phoneInput.val().trim()
                },
                success: $.proxy(success, this)
            })
        }
    }

    ForgetPS.prototype.codeValidate = function (e) {
        var regex = this.options.codeReg
        var value = this.$codeInput.val()

        if (value == '') {
            this.$firstHelpBlock.css('display', 'block').text('请输入验证码!')
            this.$codeInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$codeInput.parent().addClass('has-success').removeClass('has-error')
                this.$firstHelpBlock.css('display', 'none');
                return true;
            } else {
                this.$codeInput.parent().addClass('has-error').removeClass('has-success')
                this.$firstHelpBlock.css('display', 'block').text('验证码格式错误!')
            }
        }

        return false;
    }

    ForgetPS.prototype.next = function (e) {
        function success(data) {
            if(data == 'true') {
                $('#firstStep').toggleClass('hidden').toggleClass('show');
                $('#secondStep').toggleClass('hidden').toggleClass('show');
            } else {
                this.$firstHelpBlock.css('display', 'block').text(data)
                this.$codeInput.parent().addClass('has-error').removeClass('has-success')
                this.$firstHelpBlock.css('display', 'block').text('验证码错误!')
            }
        }

        if (this.phoneValidate() && this.codeValidate()) {
            $.ajax({
                type: 'GET',
                url: contextPath + '/validateCode',
                data: {
                    code: this.$codeInput.val().trim()
                },
                success: $.proxy(success, this)
            })
        }
    }

    ForgetPS.prototype.psValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$psInput.val().trim()

        if (value == '') {
            this.$secondHelpBlock.css('display', 'block').text('请输入新密码!')
            this.$psInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$psInput.parent().addClass('has-success').removeClass('has-error')
                this.$secondHelpBlock.css('display', 'none');
                return true;
            } else {
                this.$psInput.parent().addClass('has-error').removeClass('has-success')
                this.$secondHelpBlock.css('display', 'block').text('密码格式错误!')
            }
        }

        return false;
    }

    ForgetPS.prototype.confirmpsValidate = function (e) {
        var regex = this.options.psReg
        var value = this.$confirmpsInput.val().trim()

        if (value == '') {
            this.$secondHelpBlock.css('display', 'block').text('请输入确认密码!')
            this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$confirmpsInput.parent().addClass('has-success').removeClass('has-error')
                this.$secondHelpBlock.css('display', 'none');

                if (this.$psInput.val().trim() != '' && this.$psInput.val().trim() != value) {
                    this.$secondHelpBlock.css('display', 'block').text('密码输入不一致!')
                    this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
                    return false
                }
                return true;
            } else {
                this.$confirmpsInput.parent().addClass('has-error').removeClass('has-success')
                this.$secondHelpBlock.css('display', 'block').text('当前密码格式错误!')
            }
        }
        return false;
    }

    ForgetPS.prototype.submit = function (e) {
        if (this.newpsValidate() && this.confirmpsValidate()) {
            $("#forgetps").submit()
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, ForgetPS.DEFAULTS, typeof option == 'object' && option)

            new ForgetPS($this, options);
        })
    }

    var old = $.fn.forgetPS

    $.fn.forgetPS = Plugin
    $.fn.forgetPS.Constructor = ForgetPS


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.forgetPS.noConflict = function () {
        $.fn.forgetPS = old
        return this
    }

}(jQuery);