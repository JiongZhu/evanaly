/**
 * Created by wx6_2 on 2017/6/10.
 */
var contextPath = $('#ctxPath').text()

$(function () {
    init()

    $('.loading').hide()

    $(".event-list").mCustomScrollbar({
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

    // 初始化日历控件
    //开始时间
    $('#edit-startDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $('#edit-endDatePicker').datetimepicker('setStartDate', ev.date);
    });

    //结束时间：
    $('#edit-endDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $('#edit-startDatePicker').datetimepicker('setEndDate', ev.date)
    })

    //开始时间
    $('#search-startDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $('#search-endDatePicker').datetimepicker('setStartDate', ev.date);
    });

    //结束时间：
    $('#search-endDatePicker').datetimepicker({
        language: 'zh-CN',
        minView: 4,
        todayHighlight: true,
        autoclose: true,
        todayBtn: true
    }).on('changeDate', function (ev) {
        $('#search-startDatePicker').datetimepicker('setEndDate', ev.date)
    })

    //初始化复选框
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

    var pager = $('#cPager').pagination({
            coping: true,
            keepShowPN: true,
            homePage: '首页',
            endPage: '末页',
            isHide: true,
            current: 1,
            name: '',
            sponsorLevel: null,
            sponsorType: null,
            affectAge: null,
            fixedParticipation: null,
            affectCommunity: null,
            affectRange: null,
            type: null,
            heat: null,
            frequency: null,
            historicalLevel: null,
            startDate: null,
            endDate: null,
            cityID: null,
            callback: function (api) {
                $('.loading').show()

                var data = {};
                data['page'] = api.getCurrent()
                data['name'] = api.getOption()['name']
                if (api.getOption()['sponsorLevel'] != null)
                    data['sponsorLevel'] = api.getOption()['sponsorLevel']
                if (api.getOption()['sponsorType'] != null)
                    data['sponsorType'] = api.getOption()['sponsorType']
                if (api.getOption()['affectAge'] != null)
                    data['affectAge'] = api.getOption()['affectAge']
                if (api.getOption()['fixedParticipation'] != null)
                    data['fixedParticipation'] = api.getOption()['fixedParticipation']
                if (api.getOption()['affectCommunity'] != null)
                    data['affectCommunity'] = api.getOption()['affectCommunity']
                if (api.getOption()['affectRange'] != null)
                    data['affectRange'] = api.getOption()['affectRange']
                if (api.getOption()['type'] != null)
                    data['type'] = api.getOption()['type']
                if (api.getOption()['heat'] != null)
                    data['heat'] = api.getOption()['heat']
                if (api.getOption()['frequency'] != null)
                    data['frequency'] = api.getOption()['frequency']
                if (api.getOption()['historicalLevel'] != null)
                    data['historicalLevel'] = api.getOption()['historicalLevel']
                if (api.getOption()['startDate'] != null)
                    data['startDate'] = api.getOption()['startDate']
                if (api.getOption()['endDate'] != null)
                    data['endDate'] = api.getOption()['endDate']
                if (api.getOption()["cityID"] != null)
                    data['cityID'] = api.getOption()["cityID"]

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(data),
                    url: contextPath + '/manage/eventPagingQuery',
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
            data['name'] = ''
            if (api.getOption()['sponsorLevel'] != null)
                data['sponsorLevel'] = api.getOption()['sponsorLevel']
            if (api.getOption()['sponsorType'] != null)
                data['sponsorType'] = api.getOption()['sponsorType']
            if (api.getOption()['affectAge'] != null)
                data['affectAge'] = api.getOption()['affectAge']
            if (api.getOption()['fixedParticipation'] != null)
                data['fixedParticipation'] = api.getOption()['fixedParticipation']
            if (api.getOption()['affectCommunity'] != null)
                data['affectCommunity'] = api.getOption()['affectCommunity']
            if (api.getOption()['affectRange'] != null)
                data['affectRange'] = api.getOption()['affectRange']
            if (api.getOption()['type'] != null)
                data['type'] = api.getOption()['type']
            if (api.getOption()['heat'] != null)
                data['heat'] = api.getOption()['heat']
            if (api.getOption()['frequency'] != null)
                data['frequency'] = api.getOption()['frequency']
            if (api.getOption()['historicalLevel'] != null)
                data['historicalLevel'] = api.getOption()['historicalLevel']
            if (api.getOption()['startDate'] != null)
                data['startDate'] = api.getOption()['startDate']
            if (api.getOption()['endDate'] != null)
                data['endDate'] = api.getOption()['endDate']
            if (api.getOption()["cityID"] != null)
                data['cityID'] = api.getOption()["cityID"]

            $.ajax({
                type: 'POST',
                data: JSON.stringify(data),
                url: contextPath + '/manage/eventPagingQuery',
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
        $("#event-pnums").text(button.data('pnums'));
    })

    //显示模态框
    $('#event-edit').on('shown.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        if(button.data('id')==null||button.data('id')==''){
        	$('#event-edit').find(".modal-title").html("添加事件");
        	$(button).data('city','');
        }
        else
        	$('#event-edit').find(".modal-title").html("编辑事件");
        $("#edit-id").val(button.data('id'));
        $("#edit-city").val(button.data('city'));
        $("#edit-city").data('id', button.data('cityid'));
        $("#edit-cityico").data('city', button.data('city'));
        $("#edit-cityico").data('id', button.data('cityid'));
        $("#edit-startDatePicker").data('date', new Date(button.data('startdate')).Formatter('yyyy-MM-dd'));
        $("#edit-endDatePicker").data('date', new Date(button.data('enddate')).Formatter('yyyy-MM-dd'));
        $("#edit-name").val(button.data('name'));
        $("#edit-startDate").val(new Date(button.data('startdate')).Formatter('yyyy-MM-dd'));
        $("#edit-endDate").val(new Date(button.data('enddate')).Formatter('yyyy-MM-dd'));
        $("#edit-sponsorLevel").val(button.data('sponsorlevel'));
        $("#edit-sponsorType").val(button.data('sponsortype'));
        $("#edit-affectAge").val(button.data('affectage'));
        $("#edit-fixedParticipation").val(button.data('fixedparticipation'));
        $("#edit-affectCommunity").val(button.data('affectcommunity'));
        $("#edit-affectRange").val(button.data('affectrange'));
        $("#edit-type").val(button.data('type'));
        $("#edit-heat").val(button.data('heat'));
        $("#edit-historicalLevel").val(button.data('historicallevel'));
        $("#edit-frequency").val(button.data('frequency'));
        $("#event-pnums").text(button.data('pnums'));
        
    })
    //关闭后 清除
    $('#event-edit').on('hidden.bs.modal', function() {
    	$(this).find('.help-block').css('display', 'none');
    	$(this).find('.has-error').each(function(){
    		$(this).removeClass('has-error');
    	})
    	
    })
    $('#selectcity').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        if (button.data("multiple"))
            $('#selectcity').citySelector({initCityURl: '/getAllCity', target: $('#search-city'), multiple: true, relatedTarget: button});
        else
            $('#selectcity').citySelector({initCityURl: '/getAllCity', target: $('#edit-city'), relatedTarget: button});

    })

    $('#event-edit').eventValidate({submitUrl: '/manage/updateEvent', pager: pager})

    $('#delete-all').click(function () {
        $('.cCheckbox:first').removeClass('checked')
        var ids = []
        $('.cCheckbox.checked').each(function () {
            ids.push($(this).data('id'))
        });
        $('.loading').show()


        if(ids.length != 0)
        $.ajax({
            type: 'POST',
            data: JSON.stringify(ids),
            url: contextPath + '/manage/deleteEvent',
            dataType: "json",
            contentType: "application/json",
            success: function (result) {

                var data = {};
                data['page'] = pager.getCurrent()
                data['name'] = pager.getOption()['name']
                if (pager.getOption()['sponsorLevel'] != null)
                    data['sponsorLevel'] = pager.getOption()['sponsorLevel']
                if (pager.getOption()['sponsorType'] != null)
                    data['sponsorType'] = pager.getOption()['sponsorType']
                if (pager.getOption()['affectAge'] != null)
                    data['affectAge'] = pager.getOption()['affectAge']
                if (pager.getOption()['fixedParticipation'] != null)
                    data['fixedParticipation'] = pager.getOption()['fixedParticipation']
                if (pager.getOption()['affectCommunity'] != null)
                    data['affectCommunity'] = pager.getOption()['affectCommunity']
                if (pager.getOption()['affectRange'] != null)
                    data['affectRange'] = pager.getOption()['affectRange']
                if (pager.getOption()['type'] != null)
                    data['type'] = pager.getOption()['type']
                if (pager.getOption()['heat'] != null)
                    data['heat'] = pager.getOption()['heat']
                if (pager.getOption()['frequency'] != null)
                    data['frequency'] = pager.getOption()['frequency']
                if (pager.getOption()['historicalLevel'] != null)
                    data['historicalLevel'] = pager.getOption()['historicalLevel']
                if (pager.getOption()['startDate'] != null)
                    data['startDate'] = pager.getOption()['startDate']
                if (pager.getOption()['endDate'] != null)
                    data['endDate'] = pager.getOption()['endDate']
                if (pager.getOption()["cityID"] != null)
                    data['cityID'] = pager.getOption()["cityID"]

                if (result) {
                    $.ajax({
                        type: 'POST',
                        data: JSON.stringify(data),
                        url: contextPath + '/manage/eventPagingQuery',
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            $('.loading').hide()

                            initTable(data['data'])
                            if (pager.getCurrent() > data['count'])
                                pager.setCurrent(data['count'])

                            pager.setPageCount(data['count'])
                            pager.init()
                        }
                    })
                }
            }
        })
    })

    $('#content').on('click', '.event-del', function (e) {
        var ids = []
        var id = $(this).data('id')
        ids.push(id)
        $.ajax({
            type: 'POST',
            data: JSON.stringify(ids),
            url: contextPath + '/manage/deleteEvent',
            dataType: "json",
            contentType: "application/json",
            success: function (result) {
                var data = {};
                data['page'] = pager.getCurrent()
                data['name'] = pager.getOption()['name']
                if (pager.getOption()['sponsorLevel'] != null)
                    data['sponsorLevel'] = pager.getOption()['sponsorLevel']
                if (pager.getOption()['sponsorType'] != null)
                    data['sponsorType'] = pager.getOption()['sponsorType']
                if (pager.getOption()['affectAge'] != null)
                    data['affectAge'] = pager.getOption()['affectAge']
                if (pager.getOption()['fixedParticipation'] != null)
                    data['fixedParticipation'] = pager.getOption()['fixedParticipation']
                if (pager.getOption()['affectCommunity'] != null)
                    data['affectCommunity'] = pager.getOption()['affectCommunity']
                if (pager.getOption()['affectRange'] != null)
                    data['affectRange'] = pager.getOption()['affectRange']
                if (pager.getOption()['type'] != null)
                    data['type'] = pager.getOption()['type']
                if (pager.getOption()['heat'] != null)
                    data['heat'] = pager.getOption()['heat']
                if (pager.getOption()['frequency'] != null)
                    data['frequency'] = pager.getOption()['frequency']
                if (pager.getOption()['historicalLevel'] != null)
                    data['historicalLevel'] = pager.getOption()['historicalLevel']
                if (pager.getOption()['startDate'] != null)
                    data['startDate'] = pager.getOption()['startDate']
                if (pager.getOption()['endDate'] != null)
                    data['endDate'] = pager.getOption()['endDate']
                if (pager.getOption()["cityID"] != null)
                    data['cityID'] = pager.getOption()["cityID"]

                if (result) {
                    $.ajax({
                        type: 'POST',
                        data: JSON.stringify(data),
                        url: contextPath + '/manage/eventPagingQuery',
                        dataType: "json",
                        contentType: "application/json",
                        success: function (data) {
                            initTable(data['data'])

                            if (pager.getCurrent() > data['count'])
                                pager.setCurrent(data['count'])

                            pager.setPageCount(data['count'])
                            pager.init();
                        }
                    })
                }
            }
        })
    })

    //查询
    $('#search-btn').click(function () {
        $('.loading').show()

        pager.setCurrent(1)
        var data = {page: pager.getCurrent()}

        var name = $("#search-name").val()
        var cityID = $("#search-city").data("id")
        var startDate = $("#search-startDate").val()
        var endDate = $("#search-endDate").val()
        var frequency = $("#search-frequency").val()
        var historicalLevel = $("#search-historicalLevel").val()
        var heat = $("#search-heat").val()
        var type = $("#search-type").val()
        var sponsorLevel = $("#search-sponsorLevel").val()
        var sponsorType = $("#search-sponsorType").val()
        var affectCommunity = $("#search-affectCommunity").val()
        var affectAge = $("#search-affectAge").val()
        var fixedParticipation = $("#search-fixedParticipation").val()
        var affectRange = $("#search-affectRange").val()

        pager.addOption("name", name)
        data["name"] = name

        if (cityID == undefined || cityID.length == 0) {
            pager.addOption("cityID", null)
        } else {
            pager.addOption("cityID", cityID)
            data["cityID"] = cityID
        }

        if (startDate != "") {
            pager.addOption("startDate", startDate)
            data["startDate"] = new Date(startDate)
        } else {
            pager.addOption("startDate", null)
        }
        if (endDate != "") {
            pager.addOption("endDate", endDate)
            data["endDate"] = new Date(endDate)
        } else {
            pager.addOption("endDate", null)
        }
        if (frequency != "") {
            pager.addOption("frequency", frequency)
            data["frequency"] = frequency
        } else {
            pager.addOption("frequency", null)
        }
        if (historicalLevel != 0) {
            pager.addOption("historicalLevel", historicalLevel)
            data["historicalLevel"] = historicalLevel
        } else {
            pager.addOption("historicalLevel", null)
        }
        if (heat != 0) {
            pager.addOption("heat", heat)
            data["heat"] = heat
        } else {
            pager.addOption("heat", null)
        }
        if (type != 0) {
            pager.addOption("type", type)
            data["type"] = type
        } else {
            pager.addOption("type", null)
        }
        if (sponsorLevel != 0) {
            pager.addOption("sponsorLevel", sponsorLevel)
            data["sponsorLevel"] = sponsorLevel
        } else {
            pager.addOption("sponsorLevel", null)
        }
        if (sponsorType != 0) {
            pager.addOption("sponsorType", sponsorType)
            data["sponsorType"] = sponsorType
        } else {
            pager.addOption("sponsorType", null)
        }
        if (affectCommunity != 0) {
            pager.addOption("affectCommunity", affectCommunity)
            data["affectCommunity"] = affectCommunity
        } else {
            pager.addOption("affectCommunity", null)
        }
        if (affectAge != 0) {
            pager.addOption("affectAge", affectAge)
            data["affectAge"] = affectAge
        } else {
            pager.addOption("affectAge", null)
        }
        if (fixedParticipation != 0) {
            pager.addOption("fixedParticipation", fixedParticipation)
            data["fixedParticipation"] = fixedParticipation
        } else {
            pager.addOption("fixedParticipation", null)
        }
        if (affectRange != 0) {
            pager.addOption("affectRange", affectRange)
            data["affectRange"] = affectRange
        } else {
            pager.addOption("affectRange", null)
        }

        $.ajax({
            type: 'POST',
            data: JSON.stringify(data),
            url: contextPath + '/manage/eventPagingQuery',
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
    if(employee != undefined) {
        employee = $.parseJSON(employee)
        $('#logined').user(employee)
        if(employee.authority == 0)
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
    var type = ['无', '展会', '演唱会', '体育赛事', '政治会议', '地方性节假日']

    for (var i = 0; i < data.length; i++) {
        html += '<tr><td><i class="cCheckbox" data-id="' + data[i]['id'] + '"></i></td><td>' + (i + 1) + '</td>' +
            '<td>' + data[i]['name'] + '</td>' +
            '<td>' + data[i]['city']['name'] + '</td>' +
            '<td>' + new Date(data[i]['startDate']).Formatter('yyyy-MM-dd') + '</td>' +
            '<td>' + new Date(data[i]['endDate']).Formatter('yyyy-MM-dd') + '</td>' +
            '<td>' + type[data[i]['type']] + '</td><td>' +
            '<ul class="item-control clearfix"><li>' +
            '<a data-toggle="modal" data-target="#event-edit" data-id="' + data[i]['id'] +'"data-pnums="'+data[i]['pnums']+ '" data-name="' + data[i]['name'] + '" data-sponsorLevel="' + data[i]['sponsorLevel'] + '" data-sponsorType="' + data[i]['sponsorType'] + '" data-affectAge="' + data[i]['affectAge'] + '" data-fixedParticipation="' + data[i]['fixedParticipation'] + '" data-affectCommunity="' + data[i]['affectCommunity'] + '" data-affectRange="' + data[i]['affectRange'] + '" data-type="' + data[i]['type'] + '" data-heat="' + data[i]['heat'] + '" data-frequency="' + data[i]['frequency'] + '" data-historicalLevel="' + data[i]['historicalLevel'] + '" data-startDate="' + data[i]['startDate'] + '" data-endDate="' + data[i]['endDate'] + '" data-src="' + data[i]['src'] + '" data-city="' + data[i]['city']['name'] + '" data-cityid="' + data[i]['city']['id'] + '"><i class="manage-ico ico-edit"></i><span>编辑</span></a></li>' +
            '<li><a data-toggle="modal" data-target="#event-detail"  data-id="' + data[i]['id']+'"data-pnums="'+data[i]['pnums'] + '" data-name="' + data[i]['name'] + '" data-sponsorLevel="' + data[i]['sponsorLevel'] + '" data-sponsorType="' + data[i]['sponsorType'] + '" data-affectAge="' + data[i]['affectAge'] + '" data-fixedParticipation="' + data[i]['fixedParticipation'] + '" data-affectCommunity="' + data[i]['affectCommunity'] + '" data-affectRange="' + data[i]['affectRange'] + '" data-type="' + data[i]['type'] + '" data-heat="' + data[i]['heat'] + '" data-frequency="' + data[i]['frequency'] + '" data-historicalLevel="' + data[i]['historicalLevel'] + '" data-startDate="' + data[i]['startDate'] + '" data-endDate="' + data[i]['endDate'] + '" data-src="' + data[i]['src'] + '" data-city="' + data[i]['city']['name'] + '"><i class="manage-ico ico-detail"></i><span>详情</span></a></li><li><a  class="event-del" data-id="' + data[i]['id'] + '"><i class="manage-ico ico-del"></i><span>删除</span></a></li></ul></td></tr>'
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

+function ($) {
    'use strict';

    // USER CLASS DEFINITION
    // =========================

    var EventValidate = function (element, options) {
    	this.$element = $(element)
    	this.$idInput = this.$element.find('#edit-id')
    	this.$nameInput = this.$element.find('#edit-name')
    	this.$cityInput = this.$element.find('#edit-city')
    	this.$startDateInput = this.$element.find('#edit-startDate')
    	this.$endDateInput = this.$element.find('#edit-endDate')
    	this.$sponsorLevelInput = this.$element.find('#edit-sponsorLevel')
    	this.$sponsorTypeInput = this.$element.find('#edit-sponsorType')
    	this.$affectAgeInput = this.$element.find('#edit-affectAge')
    	this.$fixedParticipationInput = this.$element.find('#edit-fixedParticipation')
    	this.$affectCommunityInput = this.$element.find('#edit-affectCommunity')
    	this.$affectRangeInput = this.$element.find('#edit-affectRange')
    	this.$typeInput = this.$element.find('#edit-type')
    	this.$heatInput = this.$element.find('#edit-heat')
    	this.$historicalLevelInput = this.$element.find('#edit-historicalLevel')
    	this.$frequencyInput = this.$element.find('#edit-frequency')
    	this.$helpBlock = $(this.$element.find('.help-block')[0])
        this.$submitBtn = $(this.$element.find('button[name="submit"]')[0])
        this.options = options

        this.$nameInput.blur($.proxy(this.nameValidate, this))
        this.$submitBtn.click($.proxy(this.submit, this))
    }

    EventValidate.DEFAULTS = {}


    EventValidate.prototype.nameValidate = function (e) {
        var value = this.$nameInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请输入事件名称!')
            this.$nameInput.parent().addClass('has-error')
        } else {
            this.$nameInput.parent().removeClass('has-error')
            this.$helpBlock.css('display', 'none');
            return true;
        }

        return false;
    }

    EventValidate.prototype.cityValidate = function (e) {
        var value = this.$cityInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请选择事件发生地')
            this.$cityInput.parent().addClass('has-error')
        } else {
            this.$cityInput.parent().removeClass('has-error')
            this.$helpBlock.css('display', 'none');
            return true;
        }

        return false;
    }

    EventValidate.prototype.startDateValidate = function (e) {
        var value = this.$startDateInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请选择事件开始时间')
            this.$startDateInput.parent().addClass('has-error')
        } else {
            this.$startDateInput.parent().removeClass('has-error')
            this.$helpBlock.css('display', 'none');
            return true;
        }

        return false;
    }

    EventValidate.prototype.endDateValidate = function (e) {
        var value = this.$endDateInput.val()

        if (value == '') {
            this.$helpBlock.css('display', 'block').text('请选择事件开始时间')
            this.$endDateInput.parent().addClass('has-error')
        } else {
            this.$endDateInput.parent().removeClass('has-error')
            this.$helpBlock.css('display', 'none');
            return true;
        }

        return false;
    }

    EventValidate.prototype.submit = function (e) {
        function success(data) {
            if (data == 'true') {
                this.$helpBlock.css('display', 'none');
                this.$element.modal('hide')

                var pager = this.options.pager

                var data = {};
                data['page'] = pager.getCurrent()
                data['name'] = pager.getOption()['name']
                if (pager.getOption()['sponsorLevel'] != null)
                    data['sponsorLevel'] = pager.getOption()['sponsorLevel']
                if (pager.getOption()['sponsorType'] != null)
                    data['sponsorType'] = pager.getOption()['sponsorType']
                if (pager.getOption()['affectAge'] != null)
                    data['affectAge'] = pager.getOption()['affectAge']
                if (pager.getOption()['fixedParticipation'] != null)
                    data['fixedParticipation'] = pager.getOption()['fixedParticipation']
                if (pager.getOption()['affectCommunity'] != null)
                    data['affectCommunity'] = pager.getOption()['affectCommunity']
                if (pager.getOption()['affectRange'] != null)
                    data['affectRange'] = pager.getOption()['affectRange']
                if (pager.getOption()['type'] != null)
                    data['type'] = pager.getOption()['type']
                if (pager.getOption()['heat'] != null)
                    data['heat'] = pager.getOption()['heat']
                if (pager.getOption()['frequency'] != null)
                    data['frequency'] = pager.getOption()['frequency']
                if (pager.getOption()['historicalLevel'] != null)
                    data['historicalLevel'] = pager.getOption()['historicalLevel']
                if (pager.getOption()['startDate'] != null)
                    data['startDate'] = pager.getOption()['startDate']
                if (pager.getOption()['endDate'] != null)
                    data['endDate'] = pager.getOption()['endDate']
                if (pager.getOption()["cityID"] != null)
                    data['cityID'] = pager.getOption()["cityID"]

                $.ajax({
                    type: 'POST',
                    data: JSON.stringify(data),
                    url: contextPath + '/manage/eventPagingQuery',
                    dataType: "json",
                    contentType: "application/json",
                    success: function (data) {
                        initTable(data['data'])

                        pager.setPageCount(data['count'])
                        pager.init();
                    }
                })
            }
            else {
                this.$helpBlock.css('display', 'block').text(data);
            }
        }

        if (this.nameValidate() && this.startDateValidate() && this.endDateValidate() && this.cityValidate()) {
            var startDate = new Date(this.$startDateInput.val())
            var endDate = new Date(this.$endDateInput.val())
            var cityID = this.$cityInput.data("id")
            if (!this.options.multiple)
                cityID = cityID[0]

            var data = {
                id: this.$idInput.val(),
                name: this.$nameInput.val(),
                sponsorLevel: this.$sponsorLevelInput.val(),
                sponsorType: this.$sponsorTypeInput.val(),
                affectAge: this.$affectAgeInput.val(),
                fixedParticipation: this.$fixedParticipationInput.val(),
                affectCommunity: this.$affectCommunityInput.val(),
                affectRange: this.$affectRangeInput.val(),
                type: this.$typeInput.val(),
                heat: this.$heatInput.val(),
                frequency: this.$frequencyInput.val(),
                historicalLevel: this.$historicalLevelInput.val(),
                startDate: startDate,
                endDate: endDate,
                "city.id": cityID
            }
            var subUrl =  (data.id!=null&&data.id!="")?"/manage/updateEvent":"/manage/insertEvent"; 
            $.ajax({
                type: 'GET',
                url: contextPath + subUrl,
                data: data,
                success: $.proxy(success, this)
            });
        }
    }

    // CAROUSEL PLUGIN DEFINITION
    // ==========================
    function Plugin(option) {
        return this.each(function () {
            var $this = $(this)
            var options = $.extend({}, EventValidate.DEFAULTS, typeof option == 'object' && option)

            new EventValidate($this, options);
        })
    }

    var old = $.fn.eventValidate

    $.fn.eventValidate = Plugin
    $.fn.eventValidate.Constructor = EventValidate


    // CAROUSEL NO CONFLICT
    // ====================

    $.fn.eventValidate.noConflict = function () {
        $.fn.eventValidate = old
        return this
    }

}(jQuery);