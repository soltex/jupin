<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/add-size-action" method="post" commandName="sizeForm"  onsubmit="return save(this)">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">增加尺寸-${sizeTemplate.templateName }</h4>
		<form:hidden path="sizeTemplateId" />
		</div>
		<div class="modal-body">
			<div class="panel panel-default">
				<div class="panel-body">
					<label onclick="return false">
      					<form:checkbox path="waistlineable" /> 腰围
    				</label>
    				<label onclick="return false">
      					<form:checkbox path="weightable"  /> 体重
    				</label>
    				<label onclick="return false">
      					<form:checkbox path="hipable" /> 臀围
    				</label>
    				<label onclick="return false">
      					<form:checkbox path="chestable"  /> 胸围
    				</label>
    				<label onclick="return false">
      					<form:checkbox path="heightable"  /> 身高
    				</label>
    				<label onclick="return false">
      					<form:checkbox path="shoulderable" /> 肩宽
    				</label>
				</div>
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th class="sizeName">尺码名称</th>
							<c:if test="${sizeForm.waistlineable }">
								<th class="waistlineable">腰围</th>
							</c:if>
							<c:if test="${sizeForm.weightable }">
								<th class="weightable">体重</th>
							</c:if>
							<c:if test="${sizeForm.hipable }">
								<th class="hipable">臀围</th>
							</c:if>
							<c:if test="${sizeForm.chestable }">
								<th class="chestable">胸围</th>
							</c:if>
							<c:if test="${sizeForm.heightable }">
								<th class="heightable">身高</th>
							</c:if>
							<c:if test="${sizeForm.shoulderable }">
								<th class="shoulderable">肩宽</th>
							</c:if>
						</tr>
					</thead>
					<tbody id="sizeBody">
						<tr>
							<td class="sizeNames"><input type="text" class="form-control required" name="sizeName" /></td>
							<c:if test="${sizeForm.waistlineable }">
							<td class="waistlineable">
								<input type="text" class="form-control1 required" name="waistlineStart"/>~<input type="text" class="form-control1 required" name="waistlineEnd"/>
							</td>
							</c:if>
							<c:if test="${sizeForm.weightable }">
							<td class="weightable">
								<input type="text" class="form-control1 required" name="weightStart"/>~<input type="text" class="form-control1 required" name="weightEnd" />
							</td>
							</c:if>
							<c:if test="${sizeForm.hipable }">
							<td class="hipable">
								<input type="text" class="form-control1 required" name="hipStart"/>~<input type="text" class="form-control1 required" name="hipEnd" />
							</td>
							</c:if>
							<c:if test="${sizeForm.chestable }">
							<td class="chestable">
								<input type="text" class="form-control1 required" name="chestStart"/>~<input type="text" class="form-control1 required" name="chestEnd"/>	
							</td>
							</c:if>
							<c:if test="${sizeForm.heightable }">
							<td class="heightable">
								<input type="text" class="form-control1 required" name="heightStart"/>~<input type="text" class="form-control1 required" name="heightEnd"/>
							</td>
							</c:if>
							<c:if test="${sizeForm.shoulderable }">
							<td class="shoulderable">
								<input type="text" class="form-control1 required" name="shoulderStart"/>~<input type="text" class="form-control1 required" name="shoulderEnd"/>
							</td>
							</c:if>
						</tr>
					</tbody>
				</table>
			</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存尺码模板信息</button>
	</div>
</form:form>



<script type="text/javascript">

	function save(form) {
		var $form = $(form);
		$(".required").each(function(i) {
			if (!$(this).attr('disabled')) {
				if ($(this).val() == '' || $(this).val() == null) {
					alert('请填写全部选项');
					return false;
				}
			}
		});
		var action = $form.attr("action");
		$.post(action,$form.serialize(),function(data) {
			DWZ.ajaxDone(data);
		}, 'json');
		return false;
	}
</script>