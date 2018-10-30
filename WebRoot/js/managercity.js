/**
 * Created by wx6_2 on 2017/6/13.
 */
var contextPath = $('#ctxPath').text()
var pager ;
$(function () {
    $(window).on("load", function () {
        $(".scroll-content").mCustomScrollbar();
    });

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

    //初始化复选框
    $('#content').on('click', '.cCheckbox', function () {
        $(this).toggleClass('checked')
    })

    $('.cCheckbox:not(:first)').click(function () {
        $(this).toggleClass('checked')
    })

    $('.cCheckbox:first').click(function () {
        $(this).toggleClass('checked')
        if ($(this).hasClass('checked'))
            $('.cCheckbox:not(:first)').addClass('checked')
        else
            $('.cCheckbox:not(:first)').removeClass('checked')
    })

   pager = $('.cPager').pagination({
        pageCount:8,
        coping:true,
        keepShowPN: true,
        homePage:'首页',
        endPage:'末页',
        isHide: true,
        callback:function(api){
    	  var data = {};
          data['page'] = api.getCurrent()
          data['name'] =  api.getOption()['name'];
            $('.loading').show()
            searchAjax(data)
        },
        
    },function (api) {
    	  var data = {};
          data['page'] = api.getCurrent()
          data['name'] =  api.getOption()['name']
          searchAjax(data)
    });
    // 查询
    $('.search-btn').click(function () {
        $('.loading').show()

        var content = $('#search-content').val()
        data={
                page: 1,
                name: content
            }
        pager.setCurrent(1);
        pager.addOption('name',data['name']);
        searchAjax(data);
    })
    // 单个删除
     $('#content').on('click', '.city-del', function (e) {
        var ids = []
        var id = $(this).data('id')
        ids.push(id);
        deleteAjax(ids);
        
     })
    // 多删除
     $('#delete-all').click(function(){
    	 $('.cCheckbox:first').removeClass('checked')
         var ids = []
         $('.cCheckbox.checked').each(function () {
             ids.push($(this).data('id'))
         });
         $('.loading').show()
         deleteAjax(ids);
     })
     //显示模态框
    $('#city-addoredit').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget) // Button that triggered the modal
        if(button.data('id')==null){
        	$('#city-addoredit').find(".modal-title").html("添加城市");
        	$("#val_id").val('');
        	$("#val_name").val('');
	        $("#val_low").val('');
	        $("#val_mid").val('');
	        $("#val_high").val('');
        }else{
        	$('#city-addoredit').find(".modal-title").html("编辑城市");
	        $("#val_id").val(button.data('id'));
        	$("#val_name").val(button.data('name'));
	        $("#val_low").val(button.data('low'));
	        $("#val_mid").val(button.data('mid'));
	        $("#val_high").val(button.data('high'));

        }
    })
    
    //关闭后 清除
    $('#city-addoredit').on('hidden.bs.modal', function() {
    	$(this).find('#help-block').css('display', 'none');
    	$(this).find('.has-error').each(function(){
    		$(this).removeClass('has-error');
    	})
    	
    })
    //添加或修改
    $('#btn-addoredit').click(function(){
    	if(nameValidate()&&val_lowValidate()&&val_midValidate()&&val_highValidate()){
    		var modal = findParent(this,'modal');
    		var data = {
    			id:$('#form-addoredit #val_id').val(),
    			name:$('#form-addoredit #val_name').val(),
    			low:$('#form-addoredit #val_low').val(),
    			mid:$('#form-addoredit #val_mid').val(),
    			high:$('#form-addoredit #val_high').val()
    		};
    		if(data['id']==null||$.trim(data['id'])=='')
    			insertAjax(data);
    		else
    			updateAjax(data);
    		
    		modal.hide();
    	}
    	
    	
    	
    })
    
})
//修改ajax
function updateAjax(param){
	var data = {};
    data['page'] = pager.getCurrent()
    data['name'] = $('#search-content').val()
    
	 $.ajax({
         type: 'POST',
         data: param,
         url: contextPath + '/manage/updateCity',
         success: function (result) { 
		 	   if(result){
		 		   
		 		   searchAjax(data);
		 		   
		 	   }
    	  } 
    })
	
	
}
//添加 ajax
function insertAjax(param){
	var data = {};
    data['page'] = pager.getCurrent()
    data['name'] = $('#search-content').val()
    pager.addOption('name',data['name']);
	 $.ajax({
         type: 'POST',
         data: param,
         url: contextPath + '/manage/insertCity',
         success: function (result) { 
		 	   if(result){
		 		   
		 		   searchAjax(data);
		 		   
		 	   }
    	  } 
    })
}



// 删除ajax ids
function deleteAjax(ids){
	var data = {};
    data['page'] = pager.getCurrent()
    data['name'] = $('#search-content').val()
    pager.addOption('name',data['name']);
     if(ids.length != 0){
       $.ajax({
            type: 'POST',
            data: JSON.stringify(ids),
            url: contextPath + '/manage/deleteCity',
            dataType: "json",
            contentType: "application/json",
            success: function (result) { 
	    	   if(result){
	    		   
	    		   searchAjax(data);
	    		   
	    	   }
       	  } 
       })
    }
	
}




// 查询ajax
function searchAjax(data){
	$.ajax({
        type: 'POST',
        data: JSON.stringify(data),
        url: contextPath + '/manage/cityPagingQuery',
        dataType: "json",
        contentType: "application/json",
        success: function (data) {
            $('.loading').hide()


            initTable(data['data'])
            pager.setPageCount(data['count'])
            if (pager.getCurrent() > data['count'])
            	pager.setCurrent(data['count'])
            else
            	pager.setCurrent(pager.getCurrent())
            pager.init()
        }
    })
	
	
	
	
}


function initTable(data) {
    var html = ''
    for (var i = 0; i < data.length; i++) {
    	 html += '<tr><td><i class="cCheckbox" data-id="' + data[i]['id'] + '"></i></td><td>' + (i + 1) + '</td>' +
            '<td>' + data[i]['name'] + '</td>' +
            '<td>' + data[i]['low'] + '</td>' +
            '<td>' + data[i]['mid'] + '</td>' +
            '<td>' + data[i]['high'] + '</td><td>' +
            '<ul class="item-control clearfix">' +
            '<li ><a href="javascript:void" data-toggle="modal" data-target="#city-addoredit"  data-id="' + data[i]['id'] + '" data-name="' + data[i]['name'] + '" data-low="' + data[i]['low'] + '" data-mid="' + data[i]['mid'] + '" data-high="' + data[i]['high'] + '"><i class="manage-ico ico-detail"></i><span>编辑</span></a></li>'+
            '<li class="pull-right"><a  class="city-del" data-id="' + data[i]['id'] + '"><i class="manage-ico ico-del"></i><span>删除</span></a></li></ul></td></tr>'
    }
    $('#content').empty().html(html);
}
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
function nameValidate(){
	var obj =  $('#val_name');
		if ($.trim(obj.val()) == '') {
            $('#help-block').css('display', 'block').text('请输入事件名称!')
            obj.parent().addClass('has-error')
            return false;
        } else {
        	 obj.parent().removeClass('has-error')
             $('#help-block').css('display', 'none');
            return true;
        }
}

function val_lowValidate(){
	return numberValidate($("#val_low"));
}
function val_midValidate(){
	return numberValidate($("#val_mid"));
}
function val_highValidate(){
	return numberValidate($("#val_high"));
}

function numberValidate(obj){
	var numRegex =  /^\d+$/;
	var val = $.trim(obj.val());
	if ($.trim(obj.val()) == '') {
        $('#help-block').css('display', 'block').text('请输入值！')
        obj.parent().addClass('has-error')
        return false;
    }else if(!numRegex.test(val)){
    	 $('#help-block').css('display', 'block').text('值必须为整数！')
         obj.parent().addClass('has-error')
         return false;
    } else {
    	obj.parent().removeClass('has-error')
         $('#help-block').css('display', 'none');
        return true;
    }
}

function findParent(obj,className){
	if($(obj).hasClass(className))
		return obj
	return findParent($(obj).parent(), className);
}


