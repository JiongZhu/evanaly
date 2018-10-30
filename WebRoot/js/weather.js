/**
 * Created by wx6_2 on 2017/6/22.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    init()
    $('.loading').show()

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

    var cities;
    var weathers;
    var city;

    $.ajax({
        type: 'GET',
        async: false,
        url: contextPath + '/getAllCity',
        success: function (data) {
            cities = $.parseJSON(data)
        }
    })

    //获取日期
    var date = new Date();
    date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    $("#date").text(date);

    var location = $.cookie('location')
    if (location == undefined) {
        //获取地理位置
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(showPosition, showError);
        }
        else
            alert("当前浏览器不支持定位！")
    } else {
        var d = {
            "city.name": location,
            date: date
        };
        weathers = $.parseJSON(getWeather(d));

        location = $.parseJSON(location)
        $("#location-switcher").data("city", location.name)
        $("#location-switcher").data("id", location.id)
        $("#location").text(location.name)

        $('#selectcity').citySwitcher({
            target: $('#location'),
            cities: cities,
            relatedTarget: $("#location-switcher"),
        });

        if (weathers.length > 0)
            initWeather(weathers, city);
    }

    function showPosition(position) {
        $.ajax({
            url: 'http://api.map.baidu.com/geocoder/v2/?output=json&location=' + position.coords.latitude + ',' + position.coords.longitude + '&ak=WLrOM74FMI6kDPvfQFuHWvMvtsyT7t7M',
            type: 'GET',
            dataType: 'jsonp',
            async: false,
            success: function (data) {
                var d = {
                    "city.name": data.result.addressComponent.city.split("市")[0],
                    date: date
                };
                weathers = $.parseJSON(getWeather(d));

                city = data.result.addressComponent.city.split("市")[0];
                for (var item in cities) {
                    if (cities[item]['name'] == city) {
                        $("#location-switcher").data("city", city)
                        $("#location-switcher").data("id", cities[item]['id'])
                        break;
                    }
                }

                $('#selectcity').citySwitcher({
                    target: $('#location'),
                    cities: cities,
                    relatedTarget: $("#location-switcher"),
                });

                if (weathers.length > 0)
                    initWeather(weathers, city);
            }
        });
    }

    function showError(error) {
        alert(error.message)
        $("#location-switcher").data("city", '北京')
        $("#location-switcher").data("id", 1)
        $("#location").text('北京')
        $('#selectcity').citySwitcher({
            target: $('#location'),
            cities: cities,
            relatedTarget: $("#location-switcher"),
        });

        var d = {
            "city.name": '北京',
            date: date
        };
        weathers = $.parseJSON(getWeather(d));

        if (weathers.length > 0)
            initWeather(weathers, city);
    }

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

function getWeather(data) {
    var d;
    $.ajax({
        url: contextPath + "/weather/searchFiveDay",
        type: "GET",
        data: data,
        async: false,
        success: function (weathers) {
            $('.loading').hide()

            d = weathers;
        }
    });
    return d;
}

function initWeather(weathers, city) {
    $("#location").text(city);
    $("#temperature").text(weathers[0]['maxTem'] + "°/" + weathers[0]['minTem'] + "°");
    $("#des").text(weathers[0]['description']);
    $("#wind").text(weathers[0]['wind']);

    var lis = "";
    for (var i = 1; i < weathers.length; i++) {
        lis += "<li class='list-group-item'>" +
            "<div class='container-fluid'>" +
            "<div class='col-md-3 col-xs-3'><p>" + weathers[i]['date'] + "</p></div>" +
            "<div class='col-md-3 col-xs-3'><p>" + weathers[i]['description'] + "</p></div>" +
            "<div class='col-md-3 col-xs-3'><p>" + weathers[i]['wind'] + "</p></div>" +
            "<div class='col-md-3 col-xs-3'><p>" + weathers[i]['maxTem'] + "°/" + weathers[i]['minTem'] + "°" + "</p></div>" +
            "</div></li>"
    }

    $(".weather-list").empty().append($(lis));
}

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var CitySwitcher = function (element, options) {
        this.$element = $(element)
        this.$cityList = this.$element.find('.city-list > ul')
        this.$confirmBtn = this.$element.find("#city-confirm")
        this.$cityItem = null
        this.selectedCity = []
        this.selectedID = []
        this.options = options

        this.initCity()
        this.initSelectedBox()
        this.$confirmBtn.on('click', $.proxy(this.confirm, this))
    }

    CitySwitcher.DEFAULTS = {}

    CitySwitcher.prototype.initCity = function (e) {
        var sort = [['A', 'B', 'C', 'D'], ['E', 'F', 'G', 'H'], ['I', 'J', 'K', 'L'], ['M', 'N', 'O', 'P'], ['Q', 'R', 'S', 'T'], ['U', 'V', 'W'], ['X', 'Y', 'Z']]

        var cities = this.options.cities
        var mapped = {}

        for (var item in cities) {
            var c = pinyinUtil.getFirstLetter(cities[item]['name']).charAt(0)
            if (mapped[c] == null)
                mapped[c] = [];
            mapped[c].push(cities[item])
        }

        var index = 0
        for (var item in sort) {
            for (var char in sort[item]) {
                var html = ""
                for (var city in mapped[sort[item][char]])
                    html += "<li data-id='" + mapped[sort[item][char]][city]['id'] + "'>" + mapped[sort[item][char]][city]['name'] + "</li>"

                $(this.$cityList[index++]).empty().html(html)
            }
        }
        this.$cityItem = this.$cityList.find('li')
        this.$cityItem.on('click', $.proxy(this.select, this))

    }

    CitySwitcher.prototype.initSelectedBox = function (ev) {

        this.selectedCity.push(this.options.relatedTarget.data("city"))
        this.selectedID.push(this.options.relatedTarget.data("id"))
        var map = {
            '#firstpanel-link': ['A', 'B', 'C', 'D'],
            "#secondpanel-link": ['E', 'F', 'G', 'H'],
            "#thirdpanel-link": ['I', 'J', 'K', 'L'],
            "#fourthpanel-link": ['M', 'N', 'O', 'P'],
            "#fifthpanel-link": ['Q', 'R', 'S', 'T'],
            "#sixthpanel-link": ['U', 'V', 'W'],
            "#seventhpanel-link": ['X', 'Y', 'Z'],
        }
        var c = pinyinUtil.getFirstLetter(this.selectedCity[0]).charAt(0)
        this.$cityList.find("li[data-id='" + this.selectedID[0] + "']").addClass('selected')

        for (var id in map) {
            if (map[id].indexOf(c) != -1) {
                $(id).tab('show')
                break;
            }
        }
    }


    CitySwitcher.prototype.select = function (e) {

        this.$cityItem.removeClass('selected')
        $(e.target).addClass('selected')
        this.selectedCity.pop()
        this.selectedID.pop()
        this.selectedCity.push($(e.target).text())
        this.selectedID.push($(e.target).data('id'))

    }

    CitySwitcher.prototype.confirm = function (e) {
        this.options.target.text(this.selectedCity[0])
        this.options.relatedTarget.data("id", this.selectedID[0])
        this.options.relatedTarget.data("city", this.selectedCity[0])
        var date = new Date();
        date = date.getFullYear() + "-" + (date.getMonth() + 1) + "-" + date.getDate();
        var d = {
            "city.name": this.selectedCity[0],
            date: date
        };
        var weathers = $.parseJSON(getWeather(d));

        if (weathers.length > 0)
            initWeather(weathers, this.selectedCity[0]);

        var location = {city: this.selectedCity[0], id: this.selectedID[0]}
        $.cookie("location", JSON.stringify(location))

        this.$element.modal('hide')
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, CitySwitcher.DEFAULTS, typeof option == 'object' && option)
            var data = $this.data('rgb.citySelector')

            new CitySwitcher(this, options)
        })
    }

    var old = $.fn.citySwitcher

    $.fn.citySwitcher = Plugin
    $.fn.citySwitcher.Constructor = CitySwitcher

    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.citySwitcher.noConflict = function () {
        $.fn.citySwitcher = old
        return this
    }

}(jQuery);