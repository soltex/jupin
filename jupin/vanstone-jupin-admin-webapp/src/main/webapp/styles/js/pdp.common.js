function selectProvince(obj, replaceHtmlId) {
	var $obj = $(obj);
	var $html = $("#" + replaceHtmlId);
	$html.load("/common/view-cities-select.jhtml?provinceId=" + $obj.val());
}

function deleteRegion(obj) {
	var $obj = $(obj);
	$obj.parent().parent().remove();
	if (!$("input[name*='fid']") || $("input[name*='fid']").size() <=0) {
		$("#images_region").html('');
	}
}