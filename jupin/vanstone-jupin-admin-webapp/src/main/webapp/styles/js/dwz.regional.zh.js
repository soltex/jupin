/**
 * @author ZhangHuihua@msn.com
 */
(function($){
	$.extend(DWZ.conf.messages, {
		statusCode_503:'服务器当前负载过大或者正在维护!',
		validateFormError:'提交数据不完整，{0}个字段有错误，请改正后再提交!',
		sessionTimout:'会话超时，请重新登录! 确定要跳转到登录页面吗？',
		alertSelectMsg:'请选择信息!'
	});

	if (DWZ.conf.datepicker) {
		$.extend(DWZ.conf.datepicker, {
			dayNames: ['日', '一', '二', '三', '四', '五', '六'],
			monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
		});
	}

	// dwz.validate.method.js 自定义验证方法，国际化
	if ($.validator) {
		$.extend($.validator.messages, {
			alphanumeric: "字母、数字、下划线",
			lettersonly: "必须是字母",
			phone: "数字、空格、括号"
		});
	}

})(jQuery);