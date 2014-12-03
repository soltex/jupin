<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/update-all-sizeinfos-action" method="post" commandName="sizeForm"  onsubmit="return save(this)">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">${sizeTemplate.templateName }</h4>
		<form:hidden path="sizeTemplateId" />
		</div>
		<div class="modal-body">
			<div class="panel panel-default">
				<div class="panel-body" id="checkbox_area_id">
					<label>
      					<form:checkbox path="waistlineable" /> 腰围
    				</label>
    				<label>
      					<form:checkbox path="weightable"  /> 体重
    				</label>
    				<label>
      					<form:checkbox path="hipable" /> 臀围
    				</label>
    				<label>
      					<form:checkbox path="chestable"  /> 胸围
    				</label>
    				<label>
      					<form:checkbox path="heightable"  /> 身高
    				</label>
    				<label>
      					<form:checkbox path="shoulderable" /> 肩宽
    				</label>
    				<input type="button" value="新建行" class="btn btn-default" onclick="newRow()"/>
				</div>
				<table class="table table-hover table-bordered" id="size_area_id">
					<thead>
						<tr>
							<th class="sizeName">尺码名称</th>
							<th class="waistlineable" <c:if test="${!sizeTemplate.waistlineable }">style='display:none'</c:if>>腰围</th>
							<th class="weightable" <c:if test="${!sizeTemplate.weightable }">style='display:none'</c:if>>体重</th>
							<th class="hipable" <c:if test="${!sizeTemplate.hipable }">style='display:none'</c:if>>臀围</th>
							<th class="chestable" <c:if test="${!sizeTemplate.chestable }">style='display:none'</c:if>>胸围</th>
							<th class="heightable" <c:if test="${!sizeTemplate.heightable }">style='display:none'</c:if>>身高</th>
							<th class="shoulderable" <c:if test="${!sizeTemplate.shoulderable }">style='display:none'</c:if>>肩宽</th>
							<th>##</th>
						</tr>
					</thead>
					<tbody id="sizeBody">
						<c:forEach var="size" items="${sizes }" varStatus="varStatus">
						<tr class="info">
							<td class="sizeNames"><form:hidden path="sizeIds[${varStatus.index }]"/><form:input cssClass="form-control required" path="sizeNames[${varStatus.index}]" /></td>
							<td class="waistlineable" <c:if test="${!sizeTemplate.waistlineable }">style='display:none'</c:if>>
								<form:input path="waistlineStarts[${varStatus.index}]" cssClass="form-control1 required" />~<form:input path="waistlineEnds[${varStatus.index}]" cssClass="form-control1 required" />
							</td>
							<td class="weightable" <c:if test="${!sizeTemplate.weightable }">style='display:none'</c:if>>
								<form:input cssClass="form-control1 required" path="weightStarts[${varStatus.index}]"/>~<form:input cssClass="form-control1 required" path="weightEnds[${varStatus.index}]" />
							</td>
							<td class="hipable" <c:if test="${!sizeTemplate.hipable }">style='display:none'</c:if>>
								<form:input cssClass="form-control1 required" path="hipStarts[${varStatus.index}]"/>~<form:input cssClass="form-control1 required" path="hipEnds[${varStatus.index}]" />
							</td>
							<td class="chestable" <c:if test="${!sizeTemplate.chestable }">style='display:none'</c:if>>
								<form:input cssClass="form-control1 required" path="chestStarts[${varStatus.index}]"/>~<form:input cssClass="form-control1 required" path="chestEnds[${varStatus.index}]"/>	
							</td>
							<td class="heightable" <c:if test="${!sizeTemplate.heightable }">style='display:none'</c:if>>
								<form:input cssClass="form-control1 required" path="heightStarts[${varStatus.index}]"/>~<form:input cssClass="form-control1 required" path="heightEnds[${varStatus.index}]"/>
							</td>
							<td class="shoulderable" <c:if test="${!sizeTemplate.shoulderable }">style='display:none'</c:if>>
								<form:input cssClass="form-control1 required" path="shoulderStarts[${varStatus.index}]"/>~<form:input cssClass="form-control1 required" path="shoulderEnds[${varStatus.index}]"/>
							</td>
							<td>##</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存尺码模板信息</button>
	</div>
</form:form>



<script type="text/javascript">

	$(document).ready(function() {
		
		
		$("input:checkbox","#checkbox_area_id").each(function() {
			var name = $(this).attr("name");
			if (this.checked == true) {
				$("."+name, "#size_area_id").show();
				$("input","td."+name).attr("disabled",false);
			}else{
				$("."+name, "#size_area_id").hide();
				$("input","td."+name).attr("disabled",true);
			}
		});		
		
		$("input:checkbox","#checkbox_area_id").click(function() {
			var name = $(this).attr("name");
			if (this.checked == true) {
				$("."+name).show();
				$("input","td."+name).attr("disabled",false);
			}else{
				$("."+name).hide();
				$("input","td."+name).attr("disabled",true);
			}
		});
	});
	
	var trTemplate = "<tr>" + 
					"<td class='sizeNames'><input type='hidden' name='sizeIds#index#' /><input type='text' class='form-control required' name='sizeNames#index#' /></td>" + 
					"<td class='waistlineable'><input type='text' class='form-control1 required' name='waistlineStarts#index#'/>~<input type='text' class='form-control1 required' name='waistlineEnds#index#'/></td>" + 
					"<td class='weightable'><input type='text' class='form-control1 required' name='weightStarts#index#'/>~<input type='text' class='form-control1 required' name='weightEnds#index#' /></td>" + 
					"<td class='hipable'><input type='text' class='form-control1 required' name='hipStarts#index#'/>~<input type='text' class='form-control1 required' name='hipEnds#index#' /></td>" + 
					"<td class='chestable'><input type='text' class='form-control1 required' name='chestStarts#index#'/>~<input type='text' class='form-control1 required' name='chestEnds#index#'/>	</td>" + 
					"<td class='heightable'><input type='text' class='form-control1 required' name='heightStarts#index#'/>~<input type='text' class='form-control1 required ' name='heightEnds#index#'/></td>" + 
					"<td class='shoulderable'><input type='text' class='form-control1 required' name='shoulderStarts#index#'/>~<input type='text' class='form-control1 required' name='shoulderEnds#index#'/></td>" + 
					"<td><button class='btn btn-default' onclick='delRow(this)'>删除</button></td>" + 
					"</tr>" ;
	var index =${fn:length(sizes)};
	
	function newRow() {
		var html = trTemplate.replace(new RegExp("#index#","gm"),"["+index+"]");
		$("#sizeBody").append(html);
		$("input:checkbox","#checkbox_area_id").each(function(i) {
			var name = $(this).attr("name");
			if (this.checked == true) {
				$("."+name).show();
				$("input","td."+name).attr("disabled",false);
			}else{
				$("."+name).hide();
				$("input","td."+name).attr("disabled",true);
			}
		});
		index++;
	}
	
	function delRow(obj) {
		$(obj).parent().parent().remove();
	}
	
	function save(form) {
		var $form = $(form);
		var isok = true;
		$(".required").each(function(i) {
			if (!$(this).attr('disabled')) {
				if ($(this).val() == '' || $(this).val() == null) {
					isok = false;
					return;
				}
			}
		});
		if (isok) {
			var action = $form.attr("action");
			$.post(action,$form.serialize(),function(data) {
				DWZ.ajaxDone(data);
			}, 'json');
		}else{
			alert('请填写全部选项');
		}
		return false;
	}
</script>