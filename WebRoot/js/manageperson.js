/**
 * Created by wx6_2 on 2017/6/10.
 */
var contextPath = $('#ctxPath').text()
var pager

$('#upload').fileinput({
    language: 'zh', //设置语言
    uploadUrl: '/upload',  //上传地址
    uploadAsync: true,
    dropZoneEnabled: false,
    allowedFileExtensions: ['xlsx', 'xls'],
    maxFileCount: 1,
})
//同步上传返回结果处理
$("#upload").on("fileuploaded", function (event, data) {
    $('#kv-success h5').append("共" + data.response.total + "条数据，成功添加" + data.response.success + "名员工！");
    $('#kv-success').fadeIn('slow');

    $.ajax({
        type: 'GET',
        data: {page: pager.getCurrent(), name: pager.getOption()["name"]},
        url: contextPath + '/manage/personPagingQuery',
        success: function (data) {
            var json = $.parseJSON(data);
            initTable(json['data'])

            pager.setPageCount(json['count'])
            pager.init();
        }
    })


});

$('#upload-download').on('hidden.bs.modal', function() {
    $('#kv-success h5').empty()
    $('#kv-success').fadeOut('slow');
    $('#upload-form')[0].reset()
})

$(function () {
    $('.loading').hide()

    $(".person-list").mCustomScrollbar({
        horizontalScroll:true
    });

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

    init()

    pager = $('#cPager').pagination({
            coping: true,
            keepShowPN: true,
            homePage: '首页',
            endPage: '末页',
            isHide: true,
            current: 1,
            name: "",
            callback: function (api) {
                $('.loading').show()

                $.ajax({
                    type: 'GET',
                    data: {"name": api.getOption()['name'], page: api.getCurrent()},
                    url: contextPath + '/manage/personPagingQuery',
                    success: function (data) {
                        $('.loading').hide()

                        var json = $.parseJSON(data);
                        initTable(json['data'])

                        if (api.getCurrent() > json['count'])
                            api.setCurrent(json['count'])

                        api.setPageCount(json['count'])
                        api.init()
                    }
                })
            }
        }, function (api) {
            $.ajax({
                type: 'GET',
                data: {"name": '', page: api.getCurrent()},
                url: contextPath + '/manage/personPagingQuery',
                success: function (data) {
                    $('.loading').hide()

                    var json = $.parseJSON(data);
                    initTable(json['data'])

                    api.setPageCount(json['count'])
                    api.init()
                }
            })
        }
    );

    $('#content').on('click', '.cCheckbox', function () {
        $(this).toggleClass('checked')
    })

    $('.cCheckbox:first').click(function () {
        $(this).toggleClass('checked')
        if ($(this).hasClass('checked'))
            $('.cCheckbox:not(:first)').addClass('checked')
        else
            $('.cCheckbox:not(:first)').removeClass('checked')
    })

    $('#delete-all').click(function () {
        $('.cCheckbox:first').removeClass('checked')
        var employees = []
        $('.cCheckbox.checked').each(function () {
            employees.push({"id": $(this).data('id')})
        });
        if (employees.length != 0)
            $.ajax({
                type: 'POST',
                data: JSON.stringify(employees),
                url: contextPath + '/manage/deleteEmployee',
                dataType: "json",
                contentType: "application/json",
                success: function (result) {
                    if (result) {
                        $.ajax({
                            type: 'GET',
                            data: {
                                page: pager.getCurrent(),
                                name: pager.getOption()['name']
                            },
                            url: contextPath + '/manage/personPagingQuery',
                            success: function (data) {
                                var json = $.parseJSON(data);
                                initTable(json['data'])
                                if (pager.getCurrent() > json['count'])
                                    pager.setCurrent(json['count'])

                                pager.setPageCount(json['count'])
                                pager.init()
                            }
                        })
                    }
                }
            })
    })


    $('#search-btn').click(function () {
        $('.loading').show()

        var content = $('#search-content').val()
        $.ajax({
            type: 'GET',
            data: {
                page: 1,
                name: content
            },
            url: content + '/manage/personPagingQuery',
            success: function (data) {
                $('.loading').hide()

                pager.addOption('name', content)
                var json = $.parseJSON(data);
                initTable(json['data'])

                pager.setPageCount(json['count'])
                pager.setCurrent(1)
                pager.init()
            }
        })
    })

    $('#person-edit').employee({isAdd: false, submitUrl: '/manage/updateEmployee', pager: pager})
    $('#person-add').employee({
        isAdd: true,
        url: '/manage/noValidate',
        submitUrl: '/manage/insertEmployee',
        pager: pager
    })

    //显示模态框
    $('#person-detail').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        var authority = ['管理员', '员工']

        $("#person-no").text(button.data('no'));
        $("#person-name").text(button.data('name'));
        $("#person-authority").text(authority[button.data('authority')]);
        $("#person-phone").text(button.data('phone'));
        $("#person-email").text(button.data('email'));
    })

    //显示模态框
    $('#person-edit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal

        $("#edit-id").val(button.data('id'));
        $("#edit-no").val(button.data('no'));
        $("#edit-name").val(button.data('name'));
        $("#edit-phone").val(button.data('phone'));
        $("#edit-email").val(button.data('email'));
        $("#edit-authority").val(button.data('authority'));
    })

    $('#content').on('click', '.person-del', function (e) {
        var employees = []
        var id = $(this).data('id')
        employees.push({"id": id})
        $.ajax({
            type: 'POST',
            data: JSON.stringify(employees),
            url: contextPath + '/manage/deleteEmployee',
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                if (result) {
                    $.ajax({
                        type: 'GET',
                        data: {
                            page: pager.getCurrent(),
                            name: pager.getOption()['name']
                        },
                        url: contextPath + '/manage/personPagingQuery',
                        success: function (data) {
                            var json = $.parseJSON(data);
                            initTable(json['data'])

                            if (pager.getCurrent() > json['count'])
                                pager.setCurrent(json['count'])

                            pager.setPageCount(json['count'])
                            pager.init();
                        }
                    })
                }
            }
        })
    })
})

function init() {
    $("#manage-menu").hide()
    var employee = $.cookie("employee")
    if (employee != undefined) {
        employee = $.parseJSON(employee)
        $('#logined').user(employee)
        if (employee.authority == 0)
            $("#manage-menu").show()

        $('#unlogin').addClass("hidden").removeClass("show");
        $('#logined').addClass("show").removeClass("hidden");
    } else {
        $('#logined').addClass("hidden").removeClass("show");
        $('#unlogin').addClass("show").removeClass("hidden");
    }
}

function initTable(data) {
    var html = ''
    var authority = ['管理员', '员工']

    for (var i = 0; i < data.length; i++) {
        html += '<tr><td><i class="cCheckbox" data-id="' + data[i]['id'] + '"></i></td><td>' + (i + 1) + '</td>' +
            '<td>' + data[i]['no'] + '</td>' +
            '<td>' + data[i]['name'] + '</td>' +
            '<td>' + authority[data[i]['authority']] + '</td>' +
            '<td>' + data[i]['phone'] + '</td>' +
            '<td>' + data[i]['email'] + '</td><td>' +
            '<ul class="item-control clearfix"><li>' +
            '<a data-toggle="modal" data-target="#person-edit" data-id="' + data[i]['id'] + '" data-no="' + data[i]['no'] + '" data-name="' + data[i]['name'] + '" data-authority="' + data[i]['authority'] + '" data-phone="' + data[i]['phone'] + '" data-email="' + data[i]['email'] + '"><i class="manage-ico ico-edit"></i><span>编辑</span></a></li>' +
            '<li><a data-toggle="modal" data-target="#person-detail" data-id="' + data[i]['id'] + '" data-no="' + data[i]['no'] + '" data-name="' + data[i]['name'] + '" data-authority="' + data[i]['authority'] + '" data-phone="' + data[i]['phone'] + '" data-email="' + data[i]['email'] + '"><i class="manage-ico ico-detail"></i><span>详情</span></a></li><li><a  class="person-del" data-id="' + data[i]['id'] + '"><i class="manage-ico ico-del"></i><span>删除</span></a></li></ul></td></tr>'
    }
    $('#content').empty().html(html);
}

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var Employee = function (element, options) {
        this.$element = $(element)
        this.$idInput = $(this.$element.find('input[name="id"]')[0])
        this.$noInput = $(this.$element.find('input[name="no"]')[0])
        this.$nameInput = $(this.$element.find('input[name="name"]')[0])
        this.$authorityInput = $(this.$element.find('select[name="authority"]')[0])
        this.$phoneInput = $(this.$element.find('input[name="phone"]')[0])
        this.$emailInput = $(this.$element.find('input[name="email"]')[0])
        this.$submitBtn = $(this.$element.find('button[name="submit"]')[0])
        this.$helpBlock = $(this.$element.find('.help-block')[0])
        this.options = options
        this.noValid = false
        this.phoneValid = false

        this.$noInput.blur($.proxy(this.noValidate, this))
        this.$phoneInput.blur($.proxy(this.phoneValidate, this))
        this.$nameInput.blur($.proxy(this.nameValidate, this))
        this.$authorityInput.change($.proxy(this.authorityValidate, this))
        this.$emailInput.blur($.proxy(this.emailValidate, this))
        this.$submitBtn.click($.proxy(this.submit, this))
    }

    Employee.DEFAULTS = {
        noReg: /^\d{11}$/,
        emailReg: /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/,
        phoneReg: /^(13\d|14\d|15\d|18\d)\d{8}$/,
    }

    Employee.prototype.noValidate = function (e) {
        var regex = this.options.noReg
        var value = this.$noInput.val()

        function success(data) {
            if (data == 'true') {
                this.$noInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                this.noValid = true
            }
            else {
                this.$helpBlock.css('display', 'block').text(data);
                this.$noInput.parent().removeClass('has-success').addClass('has-error')
            }
        }

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入工号!')
            this.$noInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                if (this.options.isAdd)
                    $.ajax({
                        type: 'GET',
                        url: contextPath + this.options.url,
                        data: {
                            no: value,
                        },
                        async: false,
                        success: $.proxy(success, this)
                    });
            } else {
                this.$noInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('工号格式错误!')
            }
        }
    }

    Employee.prototype.authorityValidate = function (e) {
        var value = this.$authorityInput.val()

        if (value == -1) {
            this.$helpBlock.css('display', 'block').text('请选择权限!')
            this.$authorityInput.parent().addClass('has-error').removeClass('has-success')
            return false
        } else {
            this.$authorityInput.parent().addClass('has-success').removeClass('has-error')
            this.$helpBlock.css('display', 'none');
            return true;
        }
    }

    Employee.prototype.phoneValidate = function (e) {
        var regex = this.options.phoneReg
        var value = this.$phoneInput.val().trim()

        function success(data) {
            if (data == 'true') {
                this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('手机号已存在!')
            } else {
                this.$phoneInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                this.phoneValid = true;
            }
        }

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入手机号!')
            this.$phoneInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                $.ajax({
                    type: 'GET',
                    url: contextPath + '/hasPhone',
                    data: {
                        phone: value,
                    },
                    async: false,
                    success: $.proxy(success, this)
                });
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
            if (data == 'true') {
                this.$helpBlock.css('display', 'none');
                this.$element.modal('hide')

                if (this.options.isAdd) {
                    this.$noInput.val('')
                    this.$nameInput.val('')
                    this.$authorityInput.val(-1)
                    this.$phoneInput.val('')
                    this.$emailInput.val('')

                    this.$noInput.parent().removeClass('has-success')
                    this.$nameInput.parent().removeClass('has-success')
                    this.$authorityInput.parent().removeClass('has-success')
                    this.$phoneInput.parent().removeClass('has-success')
                    this.$emailInput.parent().removeClass('has-success')
                }

                var pager = this.options.pager

                $.ajax({
                    type: 'GET',
                    data: {page: pager.getCurrent(), name: pager.getOption()["name"]},
                    url: contextPath + '/manage/personPagingQuery',
                    success: function (data) {
                        var json = $.parseJSON(data);
                        initTable(json['data'])

                        pager.setPageCount(json['count'])
                        pager.init();
                    }
                })

            }
            else {
                this.$helpBlock.css('display', 'block').text(data);
            }
        }

        if ((this.phoneValid || this.phoneValidate()) && this.nameValidate() && this.emailValidate() && (this.noValid || !this.options.isAdd || this.noValidate()) && this.authorityValidate()) {
            $.ajax({
                type: 'GET',
                url: contextPath + this.options.submitUrl,
                data: {
                    id: this.$idInput.val(),
                    no: this.$noInput.val(),
                    name: this.$nameInput.val(),
                    phone: this.$phoneInput.val(),
                    email: this.$emailInput.val(),
                    authority: this.$authorityInput.val()
                },
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