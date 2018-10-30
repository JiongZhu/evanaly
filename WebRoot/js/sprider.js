/**
 * Created by wx6_2 on 2017/6/9.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    init()
    $('.loading').show()

    $(".event-list").mCustomScrollbar({
        horizontalScroll:true
    });

    $('#second-manage').collapse('hide')

    var flag = true
    $(document).on('click', '.left-menu-title', function () {
        var $nav = $('.left-menu');
        var $content = $('.left-menu-content');
        var $link = $('.left-menu > .nav > li > a')
        var $main = $('.content')

        if (flag) {
            $('#second-manage').collapse('hide')

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

    $("#search-startDate").val(new Date().Formatter('yyyy-MM-dd'))
    //开始时间
    $('#search-startDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true,
        startDate: new Date()
    }).on('changeDate', function (ev) {
        $('#search-endDatePicker').datetimepicker('setStartDate', ev.date);
    });

    //结束时间：
    $('#search-endDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true,
        startDate: new Date()
    }).on('changeDate', function (ev) {
        $('#search-startDatePicker').datetimepicker('setEndDate', ev.date)
    })

    $('.event-type > ul > li > i').click(function (e) {
        $(this).parent().toggleClass('selected').toggleClass('unselected')
    })
    $('.event-source > ul > li > i').click(function (e) {
        $(this).parent().toggleClass('selected').toggleClass('unselected')
    })


    //
    var pager = null
    var eventProp = {}
    var data = $.cookie("queryCondition")
    if (data != undefined) {
        $('.loading').show()

        data = $.parseJSON(data)

        pager = $('#cPager').pagination({
                coping: true,
                keepShowPN: true,
                homePage: '首页',
                endPage: '末页',
                isHide: true,
                current: 1,
                callback: function (api) {
                    $('.loading').show()

                    data = $.parseJSON($.cookie("queryCondition"))
                    data['page'] = api.getCurrent()
                    $.ajax({
                        type: 'POST',
                        data: JSON.stringify(data),
                        url: contextPath + '/sprider',
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            $('.loading').hide()

                            if (data['data'].length > 0) {
                                $('.empty-box').hide()
                                $('.event-box').show()
                                initTable(data['data'])
                            } else {
                                $('.empty-box').show()
                                $('.event-box').hide()
                            }

                            if (api.getCurrent() > data['count'])
                                api.setCurrent(data['count'])

                            api.setPageCount(data['count'])
                            api.init()
                        }
                    })
                }
            }, function (api) {
                data['page'] = api.getCurrent()
                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(data),
                    url: contextPath + '/sprider',
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data) {
                        $('.loading').hide()

                        if (data['data'].length > 0) {
                            $('.empty-box').hide()
                            $('.event-box').show()
                            $('.analyze-box').show()
                            initTable(data['data'])
                        } else {
                            $('.empty-box').show()
                            $('.event-box').hide()
                            $('.analyze-box').hide()
                        }
                        api.setPageCount(data['count'])
                        api.init()
                    }
                })
            }
        );

        $.ajax({
            type: 'POST',
            url: contextPath + '/analysis',
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                for (var key in data) {
                    initChart(key, data[key])
                }
            }
        })

        if (data['type'] != null) {
            if (data['type']['exhibition'] != null) {
                eventProp['1'] = data['type']['exhibition']
                $("#search-type").find("li[data-type='" + 1 + "']").addClass("selected").removeClass("unselected")
            } else {
                $("#search-type").find("li[data-type='" + 1 + "']").removeClass("selected").addClass("unselected")
            }
            if (data['type']['concert'] != null) {
                eventProp['2'] = data['type']['concert']
                $("#search-type").find("li[data-type='" + 2 + "']").addClass("selected").removeClass("unselected")
            } else {
                $("#search-type").find("li[data-type='" + 2 + "']").removeClass("selected").addClass("unselected")
            }
            if (data['type']['sport'] != null) {
                $("#search-type").find("li[data-type='" + 3 + "']").addClass("selected").removeClass("unselected")
                eventProp['3'] = data['type']['sport']
            } else {
                $("#search-type").find("li[data-type='" + 3 + "']").removeClass("selected").addClass("unselected")
            }
            if (data['type']['meeting'] != null) {
                $("#search-type").find("li[data-type='" + 4 + "']").addClass("selected").removeClass("unselected")
                eventProp['4'] = data['type']['meeting']
            } else {
                $("#search-type").find("li[data-type='" + 4 + "']").removeClass("selected").addClass("unselected")
            }
            if (data['type']['holiday'] != null) {
                eventProp['5'] = data['type']['holiday']
                $("#search-type").find("li[data-type='" + 5 + "']").addClass("selected").removeClass("unselected")
            } else {
                $("#search-type").find("li[data-type='" + 5 + "']").removeClass("selected").addClass("unselected")
            }
        }
        if (data['cityID'] != null) {
            $("#search-city").data("id", data['cityID'])
            $("#search-cityico").data("id", data['cityID'])
            $("#search-cityico").data("city", data['cityName'].split(','))
            $("#search-city").val(data['cityName'])
        }

        if (data['startDate'] != null)
            $("#search-startDate").val(new Date(data['startDate']).Formatter("yyyy-MM-dd"))
        if (data['endDate'] != null)
            $("#search-endDate").val(new Date(data['endDate']).Formatter("yyyy-MM-dd"))
        if (data['src'] != null) {
            $("#search-src").find("li").removeClass("selected").addClass("unselected")
            for (var i in data['src']) {
                $("#search-src").find("li[data-src='" + data['src'][i] + "']").addClass("selected").removeClass("unselected")
            }
        }
    } else {
        $('.loading').hide()

        $('.empty-box').hide()
        $('.event-box').hide()
        $('.analyze-box').hide()
    }

    $('#selectcity').citySelector({
        initCityURl: '/getAllCity',
        target: $('#search-city'),
        multiple: true,
        relatedTarget: $("#search-cityico")
    });

    //事件属性
    $('#event-prop').on('show.bs.modal', function (event) {
        var type = $(event.relatedTarget).data('type')
        $("#prop-submit").data("type", type)

        $("#prop-sponsorLevel").val(0)
        $("#prop-sponsorType").val(0)
        $("#prop-affectAge").val(0)
        $("#prop-fixedParticipation").val(0)
        $("#prop-affectCommunity").val(0)
        $("#prop-affectRange").val(0)
        $("#prop-heat").val(0)
        $("#prop-historicalLevel").val(0)
        $("#prop-frequency").val('')

        if (eventProp[type] != null) {
            if (eventProp[type].sponsorLevel != null)
                $("#prop-sponsorLevel").val(eventProp[type].sponsorLevel)
            if (eventProp[type].sponsorType != null)
                $("#prop-sponsorType").val(eventProp[type].sponsorType)
            if (eventProp[type].affectAge != null)
                $("#prop-affectAge").val(eventProp[type].affectAge)
            if (eventProp[type].fixedParticipation != null)
                $("#prop-fixedParticipation").val(eventProp[type].fixedParticipation)
            if (eventProp[type].affectCommunity != null)
                $("#prop-affectCommunity").val(eventProp[type].affectCommunity)
            if (eventProp[type].affectRange != null)
                $("#prop-affectRange").val(eventProp[type].affectRange)
            if (eventProp[type].heat != null)
                $("#prop-heat").val(eventProp[type].heat)
            if (eventProp[type].historicalLevel != null)
                $("#prop-historicalLevel").val(eventProp[type].historicalLevel)
            if (eventProp[type].frequency != null)
                $("#prop-frequency").val(eventProp[type].frequency)
        }
    })

    $("#prop-submit").on('click', function (ev) {
        var prop = {}

        var sponsorLevel = $("#prop-sponsorLevel").val()
        var sponsorType = $("#prop-sponsorType").val()
        var affectAge = $("#prop-affectAge").val()
        var fixedParticipation = $("#prop-fixedParticipation").val()
        var affectCommunity = $("#prop-affectCommunity").val()
        var affectRange = $("#prop-affectRange").val()
        var heat = $("#prop-heat").val()
        var historicalLevel = $("#prop-historicalLevel").val()
        var frequency = $("#prop-frequency").val()

        if (sponsorLevel != 0)
            prop['sponsorLevel'] = sponsorLevel
        else
            delete prop.sponsorLevel
        if (sponsorType != 0)
            prop['sponsorType'] = sponsorType
        else
            delete prop.sponsorType
        if (affectAge != 0)
            prop['affectAge'] = affectAge
        else
            delete prop.affectAge
        if (fixedParticipation != 0)
            prop['fixedParticipation'] = fixedParticipation
        else
            delete prop.fixedParticipation
        if (affectCommunity != 0)
            prop['affectCommunity'] = affectCommunity
        else
            delete prop.affectCommunity
        if (affectRange != 0)
            prop['affectRange'] = affectRange
        else
            delete prop.affectRange
        if (heat != 0)
            prop['heat'] = heat
        else
            delete prop.heat
        if (historicalLevel != 0)
            prop['historicalLevel'] = historicalLevel
        else
            delete prop.historicalLevel
        if (frequency != '')
            prop['frequency'] = frequency
        else
            delete prop.frequency

        prop['type'] = $(this).data('type')
        eventProp[$(this).data('type')] = prop
        $("#event-prop").modal('hide')
    })

    $("#search-btn").on('click', function (ev) {
        $('.loading').show()

        var data = {}
        var typeName = ['exhibition', 'concert', 'sport', 'meeting', 'holiday']

        var type = []
        $("#search-type").find(".selected").each(function () {
            return type.push($(this).data('type'))
        })
        var city = $("#search-city").data("id")
        var startDate = $("#search-startDate").val()
        var endDate = $("#search-endDate").val()
        var src = []
        $("#search-src").find(".selected").each(function () {
            return src.push($(this).data('src'))
        })

        if (type != null) {
            data['type'] = {}
            for (var index in type)
                data['type'][typeName[type[index] - 1]] = (eventProp[type[index]] || {type: type[index]})
        } else {
            delete data.type
        }
        if (city != null && city.length != 0) {
            data['cityName'] = $("#search-city").val()
            data['cityID'] = city
        } else {
            delete data.cityName
            delete data.cityID
        }
        if (startDate != '')
            data['startDate'] = new Date(startDate)
        else
            delete data.startDate
        if (endDate != '')
            data['endDate'] = new Date(endDate)
        else
            delete data.endDate
        if (src != null)
            data['src'] = src
        else
            delete data.src

        $.cookie("queryCondition", JSON.stringify(data));

        $.ajax({
            type: 'POST',
            url: contextPath + '/analysis',
            data: JSON.stringify(data),
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                for (var key in data) {
                    initChart(key, data[key])
                }
            }
        })

        if (pager == null) {
            pager = $('#cPager').pagination({
                    coping: true,
                    keepShowPN: true,
                    homePage: '首页',
                    endPage: '末页',
                    isHide: true,
                    current: 1,
                    callback: function (api) {
                        $('.loading').show()

                        data['page'] = api.getCurrent()
                        $.ajax({
                            type: 'POST',
                            data: JSON.stringify(data),
                            url: contextPath + '/sprider',
                            dataType: "json",
                            contentType: "application/json",
                            success: function (data) {
                                $('.loading').hide()

                                if (data['data'].length > 0) {
                                    $('.empty-box').hide()
                                    $('.event-box').show()
                                    initTable(data['data'])
                                } else {
                                    $('.empty-box').show()
                                    $('.event-box').hide()
                                }


                                if (api.getCurrent() > data['count'])
                                    api.setCurrent(data['count'])

                                api.setPageCount(data['count'])
                                api.init()
                            }
                        })
                    }
                }, function (api) {
                    data['page'] = 1
                    api.setCurrent(1)
                    $.ajax({
                        type: 'POST',
                        data: JSON.stringify(data),
                        url: contextPath + '/sprider',
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            $('.loading').hide()

                            if (data['data'].length > 0) {
                                $('.empty-box').hide()
                                $('.event-box').show()
                                $('.analyze-box').show()
                                initTable(data['data'])
                            } else {
                                $('.empty-box').show()
                                $('.event-box').hide()
                                $('.analyze-box').hide()
                            }

                            api.setPageCount(data['count'])
                            api.init()
                        }
                    })
                }
            );
        } else {
            data['page'] = 1
            pager.setCurrent(1)
            $.ajax({
                type: 'POST',
                data: JSON.stringify(data),
                url: contextPath + '/sprider',
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    $('.loading').hide()

                    if (data['data'].length > 0) {
                        $('.empty-box').hide()
                        $('.event-box').show()
                        $('.analyze-box').show()
                        initTable(data['data'])
                    } else {
                        $('.empty-box').show()
                        $('.event-box').hide()
                        $('.analyze-box').hide()
                    }
                    pager.setPageCount(data['count'])
                    pager.init()
                }
            })
        }
    })


    //显示模态框
    $('#event-detail').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        //主办方级别 1：国家政府 2：省政府 3：地方政府 4：国际性组织
        var sponsorLevel = ['未知', '国家政府', '省政府', '地方政府', '国际性组织']
        //主办方类型 1：国内民间协会 2：国内行业协会 3：国际民间协会 4：国际行业协会
        var sponsorType = ['未知', '国内民间协会', '国内行业协会', '国际民间协会', '国际行业协会']
        //影响年龄阶段 1：儿童 2：青年 3：成年 4：老年
        var affectAge = ['未知', '儿童', '青年', '成年', '老年']
        //是否有固定参与人群 1：是 2：否
        var fixedParticipation = ['未知', '是', '否']
        //影响社会群体 1：商务人群 2：社会大众
        var affectCommunity = ['未知', '商务人群', '社会大众']
        //影响范围 1：全国 2：全省 3：全市 4：全球 5：洲际
        var affectRange = ['未知', '全国', '全省', '全市', '全球', '洲际']
        //事件类型 1：展会 2：演唱会 3：体育赛事 4：政治会议 5：地方性节假日
        var type = ['未知', '展会', '演唱会', '体育赛事', '政治会议', '地方性节假日']
        //事件热度 1-3程度递增
        //历史悠久程度 1-3程度递增
        var degree = ['未知', '低', '中', '高']


        $("#event-city").text(button.data('city'));
        $("#event-name").text(button.data('name'));
        $("#event-startDate").text((new Date(button.data('startdate'))).Formatter('yyyy-MM-dd'));
        $("#event-endDate").text((new Date(button.data('enddate'))).Formatter('yyyy-MM-dd'));
        $("#event-sponsorLevel").text(sponsorLevel[button.data('sponsorlevel')]);
        $("#event-sponsorType").text(sponsorType[button.data('sponsortype')]);
        $("#event-affectAge").text(affectAge[button.data('affectage')]);
        $("#event-fixedParticipation").text(fixedParticipation[button.data('fixedparticipation')]);
        $("#event-affectCommunity").text(affectCommunity[button.data('affectcommunity')]);
        $("#event-affectRange").text(affectRange[button.data('affectrange')]);
        $("#event-type").text(type[button.data('type')]);
        $("#event-heat").text(degree[button.data('heat')]);
        $("#event-historicalLevel").text(degree[button.data('historicallevel')]);
        $("#event-frequency").text(button.data('frequency') + "次/年");
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
    //事件类型 1：展会 2：演唱会 3：体育赛事 4：政治会议 5：地方性节假日
    var type = ['无', '展会', '演唱会', '体育赛事', '政治会议']

    for (var i = 0; i < data.length; i++) {
        html += '<tr><td>' + (i + 1) + '</td>' +
            '<td>' + data[i]['name'] + '</td>' +
            '<td>' + data[i]['city']['name'] + '</td>' +
            '<td>' + new Date(data[i]['startDate']).Formatter('yyyy-MM-dd') + '</td>' +
            '<td>' + new Date(data[i]['endDate']).Formatter('yyyy-MM-dd') + '</td>' +
            '<td>' + type[data[i]['type']] + '</td><td>' +
            '<ul class="sprider-control clearfix">' +
            '<li><a href="javascript:void" data-toggle="modal" data-target="#event-detail"  data-id="' + data[i]['id'] + '" data-name="' + data[i]['name'] + '" data-sponsorLevel="' + data[i]['sponsorLevel'] + '" data-sponsorType="' + data[i]['sponsorType'] + '" data-affectAge="' + data[i]['affectAge'] + '" data-fixedParticipation="' + data[i]['fixedParticipation'] + '" data-affectCommunity="' + data[i]['affectCommunity'] + '" data-affectRange="' + data[i]['affectRange'] + '" data-type="' + data[i]['type'] + '" data-heat="' + data[i]['heat'] + '" data-frequency="' + data[i]['frequency'] + '" data-historicalLevel="' + data[i]['historicalLevel'] + '" data-startDate="' + data[i]['startDate'] + '" data-endDate="' + data[i]['endDate'] + '" data-src="' + data[i]['src'] + '" data-city="' + data[i]['city']['name'] + '"><i class="manage-ico ico-detail"></i><span>详情</span></a></li></ul></td></tr>'
    }
    $('#content').empty().html(html);
}

Date.prototype.Formatter = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

var type = {
    sponsorLevel: ['国家政府', '省政府', '地方政府', '国际性组织'],
    sponsorType: ['国内民间协会', '国内行业协会', '国际民间协会', '国际行业协会'],
    affectAge: ['儿童', '青年', '成年', '老年'],
    affectRange: ['全国', '全省', '全市'],
    type: ['展会', '演唱会', '体育赛事', '政治会议'],
    affectCommunity: ['商务人群', '社会大众'],
    heat: ['低', '中', '高'],
    src: ['易展网', '展易网', '大麦网', '京东演出票', '新华网', '新浪']
}

function initChart(key, value) {
    var chart = echarts.init(document.getElementById(key));
    var xAxis, data = []

    if (type[key] != null) {
        xAxis = type[key]
        data = new Array(xAxis.length)
        for (var i = 0; i < data.length; i++) {
            data[i] = 0;
        }
        for (var index in value) {
            data[value[index].type - 1] = value[index].count
        }
    } else {
        xAxis = value.map(function (item) {
            return item.type
        })

        data = value.map(function (item) {
            return item.count
        })
    }

    var option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        toolbox: {
            feature: {
                saveAsImage: {},
                magicType: {
                    type: ['line', 'bar']
                }
            },
            right: 40
        },
        grid: {
            containLabel: true,
        },
        xAxis: [
            {
                type: "category",
                data: xAxis
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '事件总数',
                type: 'line',
                data: data,
                itemStyle: {
                    normal: {
                        color: '#3398DB'
                    }
                }
            }
        ]
    };

    chart.setOption(option);

    $(window).resize(function(){
        chart.resize();
    });
}