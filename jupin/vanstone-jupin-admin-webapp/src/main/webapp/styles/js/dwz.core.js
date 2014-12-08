/**
 * Created by huihuazhang on 14-10-10.
 */

(function($){

	window.DWZ = {
		regPlugins: [], // [function($parent){} ...]
		keyCode: {
			ENTER: 13, ESC: 27, END: 35, HOME: 36,
			SHIFT: 16, TAB: 9,
			LEFT: 37, RIGHT: 39, UP: 38, DOWN: 40,
			DELETE: 46, BACKSPACE:8
		},
		initUI: function(_box){
			var $p = $(_box || document);

			// 执行第三方jQuery插件【 第三方jQuery插件注册：DWZ.regPlugins.push(function($p){}); 】
			$.each(DWZ.regPlugins, function(index, fn){
				fn($p);
			});
		},
		eventType: {
			pageClear:'dwz.pageClear',	// 用于重新ajaxLoad，去除xheditor等需要特殊处理的资源
			ajaxDone:'dwz.ajaxDone', // ajax 表单提交完成触发的事件
			dialogAjaxSuccess:'dwz.dialogAjaxSuccess'
		},
		pageInfo: {pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"},
		statusCode: {ok:200, error:300, timeout:301, redirect: 302},
		keys: {statusCode:"statusCode", message:"message"},

		conf:{
			containerId: 'container',
			dialogId: 'modal-dialog',
			alertId:'dwz-alert-box',
			loginUrl:"", //session timeout
			messages:{ //alert message
				statusCode_503:'503 Service Temporarily Unavailable.',
				validateFormError:'There are {0} errors on the page, please correct them before continuing.',
				sessionTimout:'Session Timeout! Please re-sign in. Are you sure redirect to Login Page?',
				alertSelectMsg:'Please select an item.'
			},
			frag:{ // 页面碎片代码, 用法：$.extend(DWZ.conf.frag, {xxxFragId:''});
				alertError:'<div id="#alertId#" class="alert alert-danger alert-dismissible" role="alert">\
								<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>\
								<strong>Error: </strong> #message#\
							</div>',
				alertSuccess:'<div id="#alertId#" class="alert alert-success alert-dismissible" role="alert">\
								<button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>\
								#message#\
							</div>'
			},

			debug:false
		},

		alert:{
			_show: function(fragAlert, msg){
				var _html = fragAlert.replaceAll('#message#', msg).replaceAll('#alertId#', DWZ.conf.alertId);

				this.close();
				$('#'+DWZ.conf.containerId).prepend(_html);

				$('html,body').stop().animate({scrollTop:0}, 500, 'linear');
			},

			success: function(msg){
				this._show(DWZ.conf.frag.alertSuccess, msg);
			},
			error: function(msg){
				this._show(DWZ.conf.frag.alertError, msg);
			},
			close: function(){
				$('#'+DWZ.conf.alertId).remove();
			}

		},
		msg:function(key, args){
			var _format = function(str,args) {
				args = args || [];
				var result = str || "";
				for (var i = 0; i < args.length; i++){
					result = result.replace(new RegExp("\\{" + i + "\\}", "g"), args[i]);
				}
				return result;
			}
			return _format(this.conf.messages[key], args);
		},
		debug:function(msg){
			if (this.conf.debug) {
				if (typeof(console) != "undefined") console.log(msg);
				else alert(msg);
			}
		},
		loadLogin:function(msg){
			if (confirm(msg || DWZ.msg('sessionTimout'))) {
				window.location = DWZ.conf.loginUrl;
			}
		},

		jsonEval:function(data) {
			try{
				if ($.type(data) == 'string')
					return JSON.parse(data);
				else return data;
			} catch (e){
				return {};
			}
		},
		ajaxError:function(xhr, ajaxOptions, thrownError){

//			DWZ.alert.error("<div>Http status: " + xhr.status + " " + xhr.statusText + "</div>"
//				+ "<div>ajaxOptions: "+ajaxOptions + "</div>"
//				+ "<div>thrownError: "+thrownError + "</div>"
//				+ "<div>"+xhr.responseText+"</div>");
			alert("error");

		},
		ajaxDone:function(json, event){
			
			if(json[DWZ.keys.statusCode] == DWZ.statusCode.error) {
				//错误信息
				if(json[DWZ.keys.message]){ 
					error(json[DWZ.keys.message]);
				}
			} else if (json[DWZ.keys.statusCode] == DWZ.statusCode.timeout) {
				//超时信息
				if(json[DWZ.keys.message]) {
					info(json[DWZ.keys.message]);
				}
			} else if (json[DWZ.keys.statusCode] == DWZ.statusCode.redirect) {
				//转向重定向
				window.location.href=json.redirectUrl;
			} else if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok) {
				//成功信息
				if(json[DWZ.keys.message]) {
					info(json[DWZ.keys.message]);
				}
				
				//成功后执行dialog
				if (json.closeDialog) {
					$('#' + DWZ.conf.dialogId).modal('hide');
				}
				
				//转向loading信息
				if(json.forwardUrl) {
					if (json.dialog && !json.closeDialog) {
						$('#' + DWZ.conf.dialogId + ' .modal-content').loadUrl(json.forwardUrl);
					} else {
						$('#' + (json.rel || DWZ.conf.containerId)).loadUrl(json.forwardUrl);
					}
				}
				
				/*
				 * 注意不要重复绑定事件，否则可能会多次执行
				 * 触发dwz.submitDone事件，用法：
				 * $form.bind(DWZ.eventType.ajaxDone, function(event, json){
				 * 		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){ ... }
				 * });
				 */
				if (event) {
					$(event.target).trigger(DWZ.eventType.ajaxDone, [json]);
				}
				if (json.dialog) $(document).trigger(DWZ.eventType.dialogAjaxSuccess, [json]);
			}
			
		},
		ajaxTodo: function(url, event){
			$.ajax({
				type:'POST',
				url:url,
				dataType:"json",
				cache: false,
				success: function(json){
					DWZ.ajaxDone(json, event);
				},
				error: DWZ.ajaxError
			});
		},
		_init:function(){
			var _doc = $(document);
			if (!_doc.isBind(DWZ.eventType.pageClear)) {
				_doc.bind(DWZ.eventType.pageClear, function(event){
					var box = event.target;
					if ($.fn.xheditor) {
						$("textarea.editor", box).xheditor(false);
					}
				});
			}
		}
	};
	
	/**
	 * 扩展String方法
	 */
	$.extend(String.prototype, {
		isPositiveInteger:function(){
			return (new RegExp(/^[1-9]\d*$/).test(this));
		},
		isInteger:function(){
			return (new RegExp(/^\d+$/).test(this));
		},
		isNumber: function(value, element) {
			return (new RegExp(/^-?(?:\d+|\d{1,3}(?:,\d{3})+)(?:\.\d+)?$/).test(this));
		},
		trim:function(){
			return this.replace(/(^\s*)|(\s*$)|\r|\n/g, "");
		},
		startsWith:function (pattern){
			return this.indexOf(pattern) === 0;
		},
		endsWith:function(pattern) {
			var d = this.length - pattern.length;
			return d >= 0 && this.lastIndexOf(pattern) === d;
		},
		replaceSuffix:function(index){
			return this.replace(/\[[0-9]+\]/,'['+index+']').replace('#index#',index);
		},
		trans:function(){
			return this.replace(/&lt;/g, '<').replace(/&gt;/g,'>').replace(/&quot;/g, '"');
		},
		encodeTXT: function(){
			return (this).replaceAll('&', '&amp;').replaceAll("<","&lt;").replaceAll(">", "&gt;").replaceAll(" ", "&nbsp;");
		},
		replaceAll:function(os, ns){
			return this.replace(new RegExp(os,"gm"),ns);
		},
		replaceTm:function($data){
			if (!$data) return this;
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				return $data[$1.replace(/[{}]+/g, "")];
			});
		},
		replaceTmById:function(_box){
			var $parent = _box || $(document);
			return this.replace(RegExp("({[A-Za-z_]+[A-Za-z0-9_]*})","g"), function($1){
				var $input = $parent.find("#"+$1.replace(/[{}]+/g, ""));
				return $input.val() ? $input.val() : $1;
			});
		},
		isFinishedTm:function(){
			return !(new RegExp("{[A-Za-z_]+[A-Za-z0-9_]*}").test(this));
		},
		skipChar:function(ch) {
			if (!this || this.length===0) {return '';}
			if (this.charAt(0)===ch) {return this.substring(1).skipChar(ch);}
			return this;
		},
		isValidPwd:function() {
			return (new RegExp(/^([_]|[a-zA-Z0-9]){6,32}$/).test(this));
		},
		isValidMail:function(){
			return(new RegExp(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/).test(this.trim()));
		},
		isSpaces:function() {
			for(var i=0; i<this.length; i+=1) {
				var ch = this.charAt(i);
				if (ch!=' '&& ch!="\n" && ch!="\t" && ch!="\r") {return false;}
			}
			return true;
		},
		isPhone:function() {
			return (new RegExp(/(^([0-9]{3,4}[-])?\d{3,8}(-\d{1,6})?$)|(^\([0-9]{3,4}\)\d{3,8}(\(\d{1,6}\))?$)|(^\d{3,8}$)/).test(this));
		},
		isUrl:function(){
			return (new RegExp(/^[a-zA-z]+:\/\/([a-zA-Z0-9\-\.]+)([-\w .\/?%&=:]*)$/).test(this));
		},
		isExternalUrl:function(){
			return this.isUrl() && this.indexOf("://"+document.domain) == -1;
		}
	});

	$.fn.extend({
		/**
		 * @param {Object} op: {type:GET/POST, url:ajax请求地址, data:ajax请求参数列表, callback:回调函数 }
		 */
		ajaxUrl: function(op){
			var $this = $(this);

			$this.trigger(DWZ.eventType.pageClear);

			$.ajax({
				type: op.type || 'GET',
				url: op.url,
				data: op.data,
				cache: false,
				success: function(response){
					var json = DWZ.jsonEval(response);

					if (json[DWZ.keys.statusCode]==DWZ.statusCode.error){
						if (json[DWZ.keys.message]) DWZ.alert.error(json[DWZ.keys.message]);
					} else {
						$this.html(response).initUI();
						if ($.isFunction(op.callback)) op.callback(response);
					}

					if (json[DWZ.keys.statusCode]==DWZ.statusCode.timeout){

						DWZ.loadLogin(json[DWZ.keys.message]);
					}

				},
				error: DWZ.ajaxError,
				statusCode: {
					503: function(xhr, ajaxOptions, thrownError) {
						alert(DWZ.msg("statusCode_503") || thrownError);
					}
				}
			});
		},
		loadUrl: function(url,data,callback){
			$(this).ajaxUrl({url:url, data:data, callback:callback});
		},
		initUI: function(){
			return this.each(function(){
				DWZ.initUI(this);
			});
		},
		unitBox: function(){
			return $(this).parents(".dwz-unit-box:first");
		},
		isTag:function(tn) {
			if(!tn) return false;
			return $(this)[0].tagName.toLowerCase() == tn?true:false;
		},
		/**
		 * 判断当前元素是否已经绑定某个事件
		 * @param {Object} type
		 */
		isBind:function(type) {
			var _events = $(this).data("events");
			return _events && type && _events[type];
		}
	});

	// 初始化DWZ
	DWZ._init();

})(jQuery);