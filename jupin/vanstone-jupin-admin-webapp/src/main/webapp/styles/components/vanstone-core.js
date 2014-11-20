var VANSTONE = {
	statusCode: {ok:200, error:300, timeout:301},
	jsonEval:function (json) {
		try{
			return eval('(' + json + ')');
		} catch (e){
			return {};
		}
	},
	ajaxError:function (xhr, ajaxOptions, thrownError){
		alert("error")
	},
	ajaxDone:function (json){
		var businessState = json.businessState;
		var message = json.message;
		var redirectUrl = json.redirectUrl;
		if (message) {
			alert(message);
		}
		if (businessState && redirectUrl) {
			location.href=redirectUrl;
		}
	}
}

$(document).ready(function() {
	
	var $loading = $("#loading")
	$(document).ajaxStart(function(){
		
		$loading.width(coverWidth());
		$loading.height(coverHeight());
		$loading.show();
		$(".loadingImg", $loading).css("top",objCenterTop($(".loadingImg")) + "px");
		$(".loadingImg", $loading).css("left",objCenterLeft($(".loadingImg")) + "px");
		$(".loadingImg", $loading).show();
		
	}).ajaxStop(function(){
		$loading.hide();
	});
	
	initUI();
	
});

function coverWidth(){
	var docWidth = $(document).width();
	var windowWidth = $(window).width();
	return docWidth>=windowWidth ? docWidth:windowWidth;
}

function coverHeight(){
	var docHeight = $(document).height();
	var windowHeight = $(window).height();
	return docHeight>=windowHeight ? docHeight:windowHeight;
}

function objCenterTop(jobj){
	var windowHeight = $(window).height();
	//alert(windowHeight)
	var offsetY = $(window).scrollTop();
	//alert(offsetY);
	var loadingHeight = jobj.height();
	//alert(loadingHeight);
	var top = offsetY + (windowHeight/2 - loadingHeight/2);
	//alert(top);
	return top;
}

function objCenterLeft(jobj){
	var windowWidth = $(window).width();
	var offsetX = $(window).scrollLeft();
	var loadingWidth = jobj.width();
	var left = offsetX + (windowWidth - loadingWidth)/2;
	return left;
}

function initUI(_box){
	var $p = $(_box || document);
	$("form.required-validate", $p).each(function(){
		$(this).validation();
	});
	
//	$(".pagination a",$p).click(function() {
//		$("form.searchform").attr("action",$(this).attr("href"));
//		$("form.searchform").submit();
//		return false;
//	});
	
	$("a[target=ajax]", $p).click(function(event){
		var $this = $(this);
//		var rel = $this.attr("rel") || "container";
//		if (rel) $("#"+rel).loadUrl($this.attr("href"));
		$.ajax({
			type: 'GET',
			url:$this.attr("href"),
			dataType:"json",
			cache: false,
			success: VANSTONE.ajaxDone,
			error: VANSTONE.ajaxError
		});
		event.preventDefault();
	});
	
	//确认删除框
//	$("a.delete").click(function() {
//		$("a.href",$("#delete_dialog")).attr("href",this.href);
//		$("#delete_dialog").modal('show');
//		return false;
//	});
}

function validateCallback(form, callback) {
	var $form = $(form);
//	$form.validation();
//	if (!$form.valid()) {
//		return false;
//	}
	
	$.ajax({
		type: form.method || 'POST',
		url:$form.attr("action"),
		data:$form.serializeArray(),
		dataType:"json",
		cache: false,
		success: callback || VANSTONE.ajaxDone,
		error: VANSTONE.ajaxError
	});
	return false;
}
