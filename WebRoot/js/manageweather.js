/**
 * Created by wx6_2 on 2017/6/12.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    $('.loading').show()
    init()

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

    var pager = $('#cPager').pagination({
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
                    url: contextPath + '/manage/weatherURLPagingQuery',
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
                url: contextPath + '/manage/weatherURLPagingQuery',
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
        var ids = []
        $('.cCheckbox.checked').each(function () {
            ids.push($(this).data('id'))
        });
        if (ids.length != 0)
            $.ajax({
                type: 'POST',
                data: JSON.stringify(ids),
                url: contextPath + '/manage/deleteWeatherURL',
                dataType: "json",
                contentType: "application/json",
                success: function (result) {
                    if (result) {
                        $.ajax({
                            type: 'GET',
                            data: {
                                page: pager.getCurrent(),
                                name: pager.getOption()["name"]
                            },
                            url: contextPath + '/manage/weatherURLPagingQuery',
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

    $('#weather-edit').weatherURL({isAdd: false, submitUrl: '/manage/updateWeatherURL', pager: pager})
    $('#weather-add').weatherURL({isAdd: true, submitUrl: '/manage/insertWeatherURL', pager: pager})

    $('#search-btn').click(function () {
        $('.loading').show()

        var content = $('#search-content').val()
        $.ajax({
            type: 'GET',
            data: {
                page: 1,
                "name": content
            },
            url: contextPath + '/manage/weatherURLPagingQuery',
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

    //显示模态框
    $('#weather-detail').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal

        $("#weather-name").text(button.data('name') + "天气数据源");
        $("#weather-urlA").text(button.data('urla'));
        $("#weather-urlB").text(button.data('urlb'));
    })

    //显示模态框
    $('#weather-edit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal

        $("#edit-name").val(button.data('name'));
        $("#edit-urlA").val(button.data('urla'));
        $("#edit-urlB").val(button.data('urlb'));
        $("#edit-id").val(button.data('id'));
    })

    $('#content').on('click', '.weather-del', function (e) {
        var id = $(this).data('id')
        $.ajax({
            type: 'POST',
            data: JSON.stringify([id]),
            dataType: "json",
            contentType: "application/json",
            url: contextPath + '/manage/deleteWeatherURL',
            success: function (result) {
                if (result) {
                    $.ajax({
                        type: 'GET',
                        data: {
                            page: pager.getCurrent(),
                            name: pager.getOption()["name"]
                        },
                        url: contextPath + '/manage/weatherURLPagingQuery',
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

    for (var i = 0; i < data.length; i++) {
        html += '<tr><td><i class="cCheckbox" data-id="' + data[i]['id'] + '"></i></td><td>' + (i + 1) + '</td>' +
            '<td>' + data[i]['city']['name'] + '</td>' +
            '<td>' + data[i]['urlA'] + '</td>' +
            '<td>' + data[i]['urlB'] + '</td><td>' +
            '<ul class="item-control clearfix"><li>' +
            '<a data-toggle="modal" data-target="#weather-edit" data-id="' + data[i]['id'] + '" data-name="' + data[i]['city']['name'] + '" data-urlA="' + data[i]['urlA'] + '" data-urlB="' + data[i]['urlB'] + '"><i class="manage-ico ico-edit"></i><span>编辑</span></a></li>' +
            '<li><a data-toggle="modal" data-target="#weather-detail" data-id="' + data[i]['id'] + '" data-name="' + data[i]['city']['name'] + '" data-urlA="' + data[i]['urlA'] + '" data-urlB="' + data[i]['urlB'] + '"><i class="manage-ico ico-detail"></i><span>详情</span></a></li><li><a  class="weather-del" data-id="' + data[i]['id'] + '"><i class="manage-ico ico-del"></i><span>删除</span></a></li></ul></td></tr>'
    }
    $('#content').empty().html(html);
}

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var WeatherURL = function (element, options) {
        this.$element = $(element)
        this.$idInput = $(this.$element.find('input[name="id"]')[0])
        this.$nameInput = $(this.$element.find('input[name="name"]')[0])
        this.$urlAInput = $(this.$element.find('input[name="urlA"]')[0])
        this.$urlBInput = $(this.$element.find('input[name="urlB"]')[0])
        this.$helpBlock = $(this.$element.find('.help-block')[0])
        this.$submitBtn = $(this.$element.find('button[name="submit"]')[0])
        this.options = options
        this.nameValide = false

        this.$nameInput.blur($.proxy(this.nameValidate, this))
        this.$urlAInput.blur($.proxy(this.urlAValidate, this))
        this.$urlBInput.blur($.proxy(this.urlBValidate, this))
        this.$submitBtn.click($.proxy(this.submit, this))
    }

    WeatherURL.DEFAULTS = {
        urlReg: "^((https|http|ftp|rtsp|mms)?://)"
        + "?(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?" //ftp的user@
        + "(([0-9]{1,3}\.){3}[0-9]{1,3}" // IP形式的URL- 199.194.52.184
        + "|" // 允许IP和DOMAIN（域名）
        + "([0-9a-z_!~*'()-]+\.)*" // 域名- www.
        + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\." // 二级域名
        + "[a-z]{2,6})" // first level domain- .com or .museum
        + "(:[0-9]{1,4})?" // 端口- :80
        + "((/?)|" // a slash isn't required if there is no file name
        + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$"
    }

    WeatherURL.prototype.nameValidate = function (e) {
        var value = this.$nameInput.val()

        function success(data) {
            if (data == 'true') {
                this.$helpBlock.css('display', 'block').text('该城市已存在!')
                this.$nameInput.parent().addClass('has-error').removeClass('has-success')
            } else {
                this.$nameInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                this.nameValide = true
            }
        }

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入城市名!')
            this.$nameInput.parent().addClass('has-error').removeClass('has-success')
            return false;
        } else {
            $.ajax({
                url: contextPath + '/manage/hasCity',
                method: 'GET',
                data: {city: value},
                success: $.proxy(success, this)
            })

        }
    }

    WeatherURL.prototype.urlAValidate = function (e) {
        var regex = new RegExp(this.options.urlReg)
        var value = this.$urlAInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入中国天气网地址!')
            this.$urlAInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$urlAInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$urlAInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('中国天气网地址格式错误!')
            }
        }

        return false;
    }

    WeatherURL.prototype.urlBValidate = function (e) {
        var regex = new RegExp(this.options.urlReg)
        var value = this.$urlBInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入天气网地址!')
            this.$urlBInput.parent().addClass('has-error').removeClass('has-success')
        } else {
            if (regex.test(value)) {
                this.$urlBInput.parent().addClass('has-success').removeClass('has-error')
                this.$helpBlock.css('display', 'none');
                return true;
            } else {
                this.$urlBInput.parent().addClass('has-error').removeClass('has-success')
                this.$helpBlock.css('display', 'block').text('天气网地址格式错误!')
            }
        }

        return false;
    }

    WeatherURL.prototype.submit = function (e) {
        function success(data) {
            if (data == 'true') {
                this.$helpBlock.css('display', 'none');
                this.$element.modal('hide')

                if (this.options.isAdd) {
                    this.$nameInput.val('')
                    this.$urlAInput.val('')
                    this.$urlBInput.val('')

                    this.$nameInput.parent().removeClass('has-success')
                    this.$urlAInput.parent().removeClass('has-success')
                    this.$urlBInput.parent().removeClass('has-success')
                }

                var pager = this.options.pager

                $.ajax({
                    type: 'GET',
                    data: {page: pager.getCurrent(), name: pager.getOption()["name"]},
                    url: contextPath + '/manage/weatherURLPagingQuery',
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

        if ((!this.options.isAdd || (this.nameValidate() || this.nameValide)) && this.urlAValidate() && this.urlBValidate()) {
            if (!this.options.isAdd)
                $.ajax({
                    type: 'GET',
                    url: contextPath + this.options.submitUrl,
                    data: {
                        id: this.$idInput.val(),
                        "city.name": this.$nameInput.val(),
                        urlA: this.$urlAInput.val(),
                        urlB: this.$urlBInput.val(),
                    },
                    success: $.proxy(success, this)
                });
            else {
                $.ajax({
                    type: 'GET',
                    url: contextPath + this.options.submitUrl,
                    data: {
                        cityName: this.$nameInput.val(),
                        urlA: this.$urlAInput.val(),
                        urlB: this.$urlBInput.val(),
                    },
                    success: $.proxy(success, this)
                });
            }
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, WeatherURL.DEFAULTS, typeof option == 'object' && option)

            new WeatherURL($this, options);
        })
    }

    var old = $.fn.weatherURL

    $.fn.weatherURL = Plugin
    $.fn.weatherURL.Constructor = WeatherURL


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.weatherURL.noConflict = function () {
        $.fn.weatherURL = old
        return this
    }

}(jQuery);