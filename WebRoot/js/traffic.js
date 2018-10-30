/**
 * Created by wx6_2 on 2017/6/23.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    init()
    $('.loading').show()

    $(".traffic-list").mCustomScrollbar({
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

    $.ajax({
        type: 'POST',
        url: contextPath + '/initPFChart',
        dataType: "json",
        data: JSON.stringify({}),
        contentType: "application/json",
        success: function (data) {
            initChart(data)
        }
    })

    $("#search-date").val(new Date().Formatter('yyyy-MM-dd'))

    $('#search-datePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true,
        startDate: new Date()
    })

    $('#selectcity').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        if (button.data("from"))
            $('#selectcity').citySelector({
                initCityURl: '/getAllCity',
                target: $('#from-city'),
                multiple: true,
                relatedTarget: button
            });
        else
            $('#selectcity').citySelector({
                initCityURl: '/getAllCity',
                target: $('#to-city'),
                multiple: true,
                relatedTarget: button
            });

    })

    $('#flight-list').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        var from = button.data('from')
        var to = button.data('to')

        $.ajax({
            url: contextPath + '/getFlights',
            data: {routeID: button.data('route')},
            type: 'GET',
            success: function (data) {
                data = $.parseJSON(data);
                var html = ''

                for(var item in data) {
                    html += '<tr>' +
                        '<td>' + from + '</td>' +
                        '<td>' + to + '</td>' +
                        '<td>' + data[item]['flightNo'] + '</td></tr>'
                }

                $("#flight-content").html(html)
            }
        })

    })

    var pager = $('#cPager').pagination({
            coping: true,
            keepShowPN: true,
            homePage: '首页',
            endPage: '末页',
            isHide: true,
            from: null,
            to: null,
            date: new Date().Formatter("yyyy-MM-dd"),
            current: 1,
            callback: function (api) {
                $('.loading').show()

                var data = {};
                data['page'] = api.getCurrent()
                if (api.getOption()['from'] != null)
                    data['from'] = api.getOption()['from']
                if (api.getOption()['to'] != null)
                    data['to'] = api.getOption()['to']
                if (api.getOption()['date'] != null)
                    data['date'] = api.getOption()['date']

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(data),
                    url: contextPath + '/searchPF',
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data) {
                        $('.loading').hide()

                        initTable(data['data'])

                        if (api.getCurrent() > data['count'])
                            api.setCurrent(data['count'])

                        api.setPageCount(data['count'])
                        api.init()
                    }
                })
            }
        }, function (api) {
            var data = {};
            data['page'] = api.getCurrent()
            if (api.getOption()['form'] != null)
                data['form'] = api.getOption()['form']
            if (api.getOption()['to'] != null)
                data['to'] = api.getOption()['to']
            if (api.getOption()['date'] != null)
                data['date'] = api.getOption()['date']

            $.ajax({
                type: 'POST',
                data: JSON.stringify(data),
                url: contextPath + '/searchPF',
                dataType: "json",
                contentType: "application/json",
                success: function (data) {
                    $('.loading').hide()

                    initTable(data['data'])

                    api.setPageCount(data['count'])
                    api.init()
                }
            })
        }
    );

    //查询
    $('#search-btn').click(function () {
        $('.loading').show()

        pager.setCurrent(1)
        var data = {page: pager.getCurrent()}

        var from = $("#from-city").data("id")
        var to = $("#to-city").data("id")
        var date = $("#search-date").val()

        if (from == undefined || from.length == 0) {
            pager.addOption("from", null)
        } else {
            pager.addOption("from", from)
            data["from"] = from
        }

        if (to == undefined || to.length == 0) {
            pager.addOption("to", null)
        } else {
            pager.addOption("to", to)
            data["to"] = to
        }

        if (date == undefined) {
            pager.addOption("date", new Date().Formatter("yyyy-MM-dd"))
        } else {
            pager.addOption("date", date)
            data["date"] = date
        }

        $.ajax({
            type: 'POST',
            data: JSON.stringify(data),
            url: contextPath + '/initPFChart',
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                initChart(data)
            }
        })

        $.ajax({
            type: 'POST',
            data: JSON.stringify(data),
            url: contextPath + '/searchPF',
            dataType: "json",
            contentType: "application/json",
            success: function (data) {
                $('.loading').hide()

                initTable(data['data'])

                if (pager.getCurrent() > data['count'])
                    pager.setCurrent(data['count'])

                pager.setPageCount(data['count'])
                pager.init();
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
        html += '<tr>' +
            '<td>' + data[i]['route']['fromCity']['name'] + '</td>' +
            '<td>' + data[i]['route']['toCity']['name'] + '</td>' +
            '<td>' + new Date(data[i]['date']).Formatter('yyyy-MM-dd') + '</td>' +
            '<td>' + data[i]['showPnums'] + '</td>' +
            '<td>' + data[i]['concertPnums'] + '</td>' +
            '<td>' + data[i]['sportPnums'] + '</td>' +
            '<td>' + data[i]['polityPnums'] + '</td>' +
            '<td>' + data[i]['total'] + '</td>' +
            '<td><a href="javascript:void" data-toggle="modal" data-target="#flight-list" data-from="' + data[i]['route']['fromCity']['name'] + '" data-to="' + data[i]['route']['toCity']['name'] + '" data-route="' + data[i]['route']['id'] + '">查看航班列表</a></td>';
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

function initChart(value) {
    var chart = echarts.init(document.getElementById('type'));
    var xAxis = ['展会影响人数', '演唱会影响人数', '体育赛事影响人数', '政治会议影响人数']
    var max = 0;
    var data = [value.showPnums, value.concertPnums, value.sportPnums, value.polityPnums]

    for (var item in data) {
        if (data[item] > max)
            max = data[item]
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
                name: '数量',
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

    var pie = echarts.init(document.getElementById("type-pie"));
    var option = {
        tooltip: {
            trigger: 'item',
            formatter: "{b} : {c} ({d}%)"
        },
        legend: {
            left: 'top',
            data: xAxis
        },
        series: [
            {
                name: '事件类型',
                type: 'pie',
                radius: '55%',
                center: ['40%', '60%'],
                data: xAxis.map(function (item, index) {
                    return {name: item, value: data[index]}
                }),
                itemStyle: {
                    emphasis: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    pie.setOption(option)

    var radar = echarts.init(document.getElementById("type-radar"));

    var option = {
        radar: {
            indicator: xAxis.map(function (item) {
                return {name: item, max: max}
            }),
            center: ['50%', '50%'],
            radius: 90,
            startAngle: 90,
            splitNumber: 4,
            shape: 'circle',
            name: {
                formatter: '{value}',
                textStyle: {
                    color: '#72ACD1'
                }
            },
            splitArea: {
                areaStyle: {
                    color: ['rgba(114, 172, 209, 0.2)',
                        'rgba(114, 172, 209, 0.4)', 'rgba(114, 172, 209, 0.6)',
                        'rgba(114, 172, 209, 0.8)', 'rgba(114, 172, 209, 1)'],
                    shadowColor: 'rgba(0, 0, 0, 0.3)',
                    shadowBlur: 10
                }
            },
            axisLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 0.5)'
                }
            },
            splitLine: {
                lineStyle: {
                    color: 'rgba(255, 255, 255, 0.5)'
                }
            }
        },
        tooltip: {},
        series: {
            name: '事件类型',
            type: 'radar',
            itemStyle: {
                emphasis: {
                    lineStyle: {
                        width: 4
                    }
                }
            },
            data: [
                {
                    value: data,
                    symbol: 'rect',
                    symbolSize: 5,
                    lineStyle: {
                        normal: {
                            type: 'dashed'
                        }
                    },
                    tooltip: {}
                }
            ]
        }
    };

    radar.setOption(option);

    $(window).resize(function(){
        chart.resize();
        pie.resize();
        radar.resize();
    });

}