/**
 * Created by wx6_2 on 2017/6/10.
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

    initPanel()
    $('#to-update').click(function (e) {
        panelToggle();
        e.preventDefault();
    })

    $("#info-cancel").click(function (e) {
        panelToggle();
        e.preventDefault();
    })
    $("#info-confirm").click(function (e) {
        panelToggle();
        e.preventDefault();
    })


});

function initPanel() {
    $('#user-update').addClass('hidden')
    $('#user-info').addClass('show');
}

function panelToggle() {
    $('#user-info').toggleClass('hidden').toggleClass('show')
    $('#user-update').toggleClass('hidden').toggleClass('show')
}

function init() {
    $("#manage-menu").hide()
    var emp = $.cookie("employee")
    if(emp != undefined) {
        emp = $.parseJSON(emp)
        $('#logined').user(emp)
        if(emp.authority == 0)
            $("#manage-menu").show()

        initInfo(emp)
        $("#user-update").employee({emp: emp})

        $('#unlogin').addClass("hidden").removeClass("show");
        $('#logined').addClass("show").removeClass("hidden");
    } else {
        $('#logined').addClass("hidden").removeClass("show");
        $('#unlogin').addClass("show").removeClass("hidden");
    }
}

function initInfo(employee) {
    $("#info-no").text(employee.no)
    $("#info-name").text(employee.name)
    $("#info-email").text(employee.email)
    $("#info-tel").text(employee.phone)

    $("#employ-no").val(employee.no)
    $("#employ-name").val(employee.name)
    $("#employ-email").val(employee.email)
    $("#employ-phone").val(employee.phone)
}

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var Employee = function (element, options) {
        this.$element = $(element)
        this.$noInput = this.$element.find("#employ-no")
        this.$nameInput = this.$element.find('#employ-name')
        this.$phoneInput = this.$element.find('#employ-phone')
        this.$emailInput = this.$element.find('#employ-email')
        this.$submitBtn = this.$element.find('#info-confirm')
        this.$helpBlock = $(this.$element.find('.help-block')[0])
        this.options = options

        this.$phoneInput.blur($.proxy(this.phoneValidate, this))
        this.$nameInput.blur($.proxy(this.nameValidate, this))
        this.$emailInput.blur($.proxy(this.emailValidate, this))
        this.$submitBtn.click($.proxy(this.submit, this))
    }

    Employee.DEFAULTS = {
        emailReg: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
        phoneReg: /^(13\d|14\d|15\d|18\d)\d{8}$/,
    }

    Employee.prototype.phoneValidate = function (e) {
        var regex = this.options.phoneReg
        var value = this.$phoneInput.val().trim()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入手机号!')
            this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$phoneInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('手机号格式错误!')
            }
        }

        return false;
    }

    Employee.prototype.nameValidate = function (e) {
        var value = this.$nameInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入姓名!')
            this.$nameInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (value.length <= 6) {
                this.$nameInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$nameInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('请输入正确的姓名!')
            }
        }

        return false;
    }

    Employee.prototype.emailValidate = function (e) {
        var regex = this.options.emailReg
        var value = this.$emailInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入邮箱!')
            this.$emailInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$emailInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$emailInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('邮箱格式错误!')
            }
        }

        return false;
    }

    Employee.prototype.submit = function (e) {
        function success(data) {
            if(data == 'true'){
                this.options.emp.name = employee.name
                this.options.emp.phone = employee.phone
                this.options.emp.email = employee.email

                initInfo(this.options.emp)

                $.cookie('employee', JSON.stringify(this.options.emp), {path: '/'})
            }
        }
        if (this.phoneValidate() && this.nameValidate() && this.emailValidate()) {
            var employee = {
                no: this.$noInput.val(),
                name: this.$nameInput.val(),
                phone: this.$phoneInput.val(),
                email: this.$emailInput.val()
            }
            $.ajax({
                type: 'GET',
                url: contextPath + '/employee/update',
                data: employee,
                success: $.proxy(success, this)
            });
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, Employee.DEFAULTS, typeof option == 'object' && option)

            new Employee($this, options);
        })
    }

    var old = $.fn.employee

    $.fn.employee = Plugin
    $.fn.employee.Constructor = Employee


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.employee.noConflict = function () {
        $.fn.employee = old
        return this
    }

}(jQuery);