var contextPath = $('#ctxPath').text()

$(function () {
    init()

    $.get('js/cityData.json', function (cities) {
        $.ajax({
            type: 'GET',
            url: contextPath + '/getAllCity',
            success: function (result) {
                var map = {}
                result = $.parseJSON(result)
                result.forEach(function (item) {
                    if (cities[item['name']]) {
                        map[item['id']] = {
                            name: item['name'],
                            value: [cities[item['name']]['longitude'], cities[item['name']]['latitude']],
                            mid: item.mid,
                            high: item.high,
                            low: item.low
                        }
                    }
                    else
                        console.log(item['name'])
                })

                initChart(map)
            }
        })
    })
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

function initChart(map) {

    function weekDate() {
        var date = new Date();
        var week = []
        for (var i = 1; i <= 7; i++) {
            date.setDate(date.getDate() + 1)
            week.push(date.Formatter('yyyy-MM-dd'))
        }
        return week;
    }

    var week = weekDate()

    function convertMap() {
        var result = []
        for (var i in map) {
            result.push(map[i])
        }
        return result
    }

    var planePath = 'path://M1705.06,1318.313v-89.254l-319.9-221.799l0.073-208.063c0.521-84.662-26.629-121.796-63.961-121.491c-37.332-0.305-64.482,36.829-63.961,121.491l0.073,208.063l-319.9,221.799v89.254l330.343-157.288l12.238,241.308l-134.449,92.931l0.531,42.034l175.125-42.917l175.125,42.917l0.531-42.034l-134.449-92.931l12.238-241.308L1705.06,1318.313z';

    var color = ['#a6c84c', '#ffa022', '#ff6666'];
    var series = [];
    [['正常'], ['紧张'], ['饱和']].forEach(function (item, i) {
        series.push({
            name: item[0],
            type: 'lines',
            zlevel: 2,
            symbol: ['none', 'arrow'],
            symbolSize: 10,
            effect: {
                show: true,
                period: 6,
                trailLength: 0,
                symbol: planePath,
                symbolSize: 10
            },
            label: {
                normal: {
                    show: false,
                }
            },
            lineStyle: {
                normal: {
                    color: color[i],
                    width: 1,
                    opacity: 0.6,
                    curveness: 0.2
                }
            },
            tooltip: {
                formatter: function (param) {
                    return param.data.fromName + "->" + param.data.toName + ":" + param.data.total;
                }
            }
        });
    });

    series.push({
        type: 'effectScatter',
        coordinateSystem: 'geo',
        zlevel: 2,
        rippleEffect: {
            brushType: 'stroke'
        },
        label: {
            normal: {
                show: true,
                position: 'right',
                formatter: function (param) {
                    return param.data.name;
                }
            }
        },
        symbolSize: 3,
        itemStyle: {
            normal: {
                color: "#fff"
            }
        },
        tooltip: {
            formatter: function (param) {
                return param.data.name;
            }
        },
        data: convertMap()
    });

    var line;
    $.get('js/china.json', function (chinaJson) {
        echarts.registerMap('china', chinaJson);
        line = echarts.init(document.getElementById('line'));

        line.showLoading({
            text: '加载中',
            color: '#c23531',
            textColor: '#000',
            maskColor: 'rgba(255, 255, 255, 0.8)',
            zlevel: 0
        })

        var route = {};
        $.ajax({
            type: 'GET',
            url: contextPath + '/getAllRoute',
            async: false,
            success: function (result) {
                result = $.parseJSON(result)

                result.forEach(function (t) {
                    route[t.id] = {
                        low: t.low,
                        mid: t.mid,
                        high: t.high
                    }
                })
            }
        })

        var lineData = {}
        $.ajax({
            type: 'GET',
            url: contextPath + '/routewarn',
            async: false,
            success: function (result) {
                result = $.parseJSON(result)
                result.forEach(function (item) {
                    var date = (new Date(item['date'])).Formatter('yyyy-MM-dd')
                    if (!lineData[date])
                        lineData[date] = {
                            '正常': [],
                            '紧张': [],
                            '饱和': []
                        }

                    if (item.total <= route[item.route.id].low)
                        lineData[date]['正常'].push({
                            fromName: item.route.fromCity.name,
                            toName: item.route.toCity.name,
                            coords: [map[item.route.fromCity.id].value, map[item.route.toCity.id].value],
                            total: item.total
                        })
                    else if (item.total <= route[item.route.id].mid)
                        lineData[date]['紧张'].push({
                            fromName: item.route.fromCity.name,
                            toName: item.route.toCity.name,
                            coords: [map[item.route.fromCity.id].value, map[item.route.toCity.id].value],
                            total: item.total
                        })
                    else
                        lineData[date]['饱和'].push({
                            fromName: item.route.fromCity.name,
                            toName: item.route.toCity.name,
                            coords: [map[item.route.fromCity.id].value, map[item.route.toCity.id].value],
                            total: item.total
                        })
                })

                line.hideLoading()
            }
        })

        line.setOption({
            baseOption: {
                title: {
                    text: '航班预警图',
                    left: 'center',
                    top: '10%'
                },
                timeline: {
                    // y: 0,
                    axisType: 'category',
                    // realtime: false,
                    // loop: false,
                    autoPlay: true,
                    // currentIndex: 2,
                    playInterval: 3000,
                    // controlStyle: {
                    // position: 'left'
                    // },
                    data: week
                },
                backgroundColor: '#bbb',
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    top: 'middle',
                    right: '10%',
                    data: ['正常', '紧张', '饱和'],
                    textStyle: {
                        color: '#fff'
                    },
                },
                geo: {
                    map: 'china',
                    roam: true,
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            areaColor: '#323c48',
                            borderColor: '#404a59'
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    }
                },
                series: series
            },
            options: week.map(function (t) {
                return {series: [{data: lineData[t]['正常']}, {data: lineData[t]['紧张']}, {data: lineData[t]['饱和']}]}
            })
        });

        $(window).resize(function () {
            line.resize();
        });
    })

    var scatterData = {}
    $.ajax({
        type: 'GET',
        url: contextPath + '/citywarn',
        async: false,
        success: function (result) {
            result = $.parseJSON(result)
            result.forEach(function (item) {
                var date = (new Date(item['date'])).Formatter('yyyy-MM-dd')
                if (!scatterData[date])
                    scatterData[date] = {
                        '正常': [],
                        '紧张': [],
                        '饱和': []
                    }

                if (item.pnums <= map[item.city].low)
                    scatterData[date]['正常'].push({
                        name: map[item.city].name,
                        value: map[item.city].value.concat(item.pnums)
                    })
                else if (item.pnums <= map[item.city].mid)
                    scatterData[date]['紧张'].push({
                        name: map[item.city].name,
                        value: map[item.city].value.concat(item.pnums)
                    })
                else
                    scatterData[date]['饱和'].push({
                        name: map[item.city].name,
                        value: map[item.city].value.concat(item.pnums)
                    })
            })
        }
    })

    var weatherData = {}
    $.ajax({
        type: 'GET',
        url: contextPath + '/warnWeather',
        async: false,
        success: function (result) {
            result = $.parseJSON(result)
            result.forEach(function (item) {
                var date = (new Date(item['date'])).Formatter('yyyy-MM-dd')
                if (!weatherData[date])
                    weatherData[date] = []

                weatherData[date].push({
                    name: item.city.name,
                    value: map[item.city.id].value.concat(item.description).concat(item.wind)
                })
            })
        }
    })


    var scatter;
    $.get('js/china.json', function (chinaJson) {
        echarts.registerMap('china', chinaJson);
        scatter = echarts.init(document.getElementById('scatter'));
        scatter.setOption({
            baseOption: {
                title: {
                    text: '地区预警图',
                    left: 'center',
                    top: '10%'
                },
                timeline: {
                    // y: 0,
                    axisType: 'category',
                    // realtime: false,
                    // loop: false,
                    autoPlay: true,
                    // currentIndex: 2,
                    playInterval: 3000,
                    // controlStyle: {
                    // position: 'left'
                    // },
                    data: week
                },
                legend: {
                    orient: 'vertical',
                    top: 'middle',
                    right: '10%',
                    data: ['正常', '紧张', '饱和', '恶劣天气'],
                    textStyle: {
                        color: '#fff'
                    }
                },
                backgroundColor: '#bbb',
                tooltip: {},
                geo: {
                    map: 'china',
                    label: {
                        emphasis: {
                            show: false
                        }
                    },
                    roam: true,
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            return params.name + ' : ' + params.value[2];
                        }
                    },
                    itemStyle: {
                        normal: {
                            areaColor: '#323c48',
                            borderColor: '#111'
                        },
                        emphasis: {
                            areaColor: '#2a333d'
                        }
                    }
                },
                calculable: true,
                series: [{
                    name: '正常',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    symbolSize: 10,
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: function (param) {
                                return param.data.name;
                            }
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#a6c84c',
                        },
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }, {
                    name: '紧张',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    symbolSize: 20,
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: function (param) {
                                return param.data.name;
                            }
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ffa022'
                        },
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }, {
                    name: '饱和',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    symbolSize: 40,
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: function (param) {
                                return param.data.name;
                            }
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ff6666'
                        },
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }, {
                    name: '恶劣天气',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    symbolSize: 15,
                    label: {
                        normal: {
                            show: true,
                            position: 'right',
                            formatter: function (param) {
                                return param.data.name;
                            }
                        },
                        emphasis: {
                            show: false
                        }
                    },
                    tooltip: {
                        trigger: 'item',
                        formatter: function (params) {
                            var str = params.name + ' : '
                        if(params.value[2].indexOf('暴雨') >= 0 || params.value[2].indexOf('雷阵雨') >= 0)
                               str += params.value[2]
                        if(params.value[3].indexOf('6') >= 0 || params.value[3].indexOf('7') >= 0)
                               str += '\n风力：' + params.value[3]

                            return str;
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#f832ff'
                        },
                        emphasis: {
                            borderColor: '#fff',
                            borderWidth: 1
                        }
                    }
                }]
            },
            options: week.map(function (t) {
                return {series: [{data: scatterData[t]['正常']}, {data: scatterData[t]['紧张']}, {data: scatterData[t]['饱和']}, {data: weatherData[t]}]}
            })
        });
    })

    $(window).resize(function () {
        scatter.resize();
    });

    $('#carousel-example-generic').on('slid.bs.slide', function () {
        scatter.resize();
    })

}

Date.prototype.Formatter = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 // 月份
        "d+": this.getDate(),                    // 日
        "h+": this.getHours(),                   // 小时
        "m+": this.getMinutes(),                 // 分
        "s+": this.getSeconds(),                 // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()             // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};