
/**
 * Created by wx6_2 on 2017/6/13.
 */
$(function () {
    var contextPath = $('#ctxPath').text()
    init()

    $('.loading').show()
    $.ajax({
        type: 'GET',
        url: contextPath + '/weeklyAnalysis',
        success: function (data) {
            $('.loading').hide()
            data = $.parseJSON(data)
            for (var key in data) {
                initChart(key, data[key])
            }
        }
    })

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
        type: 'GET',
        url: contextPath + '/monthlyAnalysis',
        success: function (data) {
            data = $.parseJSON(data)
            for (var key in data) {
                initChart(key, data[key])
            }
        }
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

var type = {
    type: ['展会', '演唱会', '体育赛事', '政治会议'],
    monthType: ['展会', '演唱会', '体育赛事', '政治会议'],
}

function initChart(key, value) {
    var chart = echarts.init(document.getElementById(key));
    var xAxis, data = []
    var max = 0;

    if (type[key] != null) {
        xAxis = type[key]
        data = new Array(xAxis.length)
        for (var i = 0; i < data.length; i++) {
            data[i] = 0;
        }
        for (var index in value) {
            data[value[index].type - 1] = value[index].count
            if(max < value[index].count)
                max = value[index].count
        }
    } else {
        xAxis = value.map(function (item) {
            if (key == 'date' || key == 'monthDate')
                return new Date(item.date).Formatter('yyyy-MM-dd')
            else
                return item.type
        })

        data = value.map(function (item) {
            if(max < item.count)
                max = item.count
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

    $(window).resize(function(){
        chart.resize();
    });

    var seriesName = {city: '城市', monthCity: '城市', type: '事件类型', monthType: '事件类型'}
    if (key != 'date' && key != 'monthDate') {

        var pie = echarts.init(document.getElementById(key + "-pie"));
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
                    name: seriesName[key],
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

        var radar = echarts.init(document.getElementById(key + "-radar"));

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
                name: seriesName[key],
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
            pie.resize();
            radar.resize();
        });
    }
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