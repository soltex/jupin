<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/add-sizetemplate-action" method="post" commandName="sizeForm"  onsubmit="return save(this)">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">新建尺码模板</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label for="templateName">模板名称</label>
				<form:input path="templateName" cssClass="form-control required" placeholder="模板名称"  />
			</div>
			<div class="form-group">
				 <label for="templateContent">模板描述信息</label>
				<form:textarea path="templateContent" cssClass="form-control" placeholder="模板描述信息" />
			</div>
			<div class="panel panel-default">
				<div class="panel-body">
					<label>
      					<input type="checkbox"  checked="checked" name="waistlineable" /> 腰围
    				</label>
    				<label>
      					<input type="checkbox"  checked="checked" name="weightable" /> 体重
    				</label>
    				<label>
      					<input type="checkbox"  checked="checked" name="hipable" /> 臀围
    				</label>
    				<label>
      					<input type="checkbox"  checked="checked" name="chestable" /> 胸围
    				</label>
    				<label>
      					<input type="checkbox"  checked="checked" name="heightable" /> 身高
    				</label>
    				<label>
      					<input type="checkbox"  checked="checked" name="shoulderable" /> 肩宽
    				</label>
    				<input type="button" value="新建行" class="btn btn-default" onclick="newRow()"/>
				</div>
				<table class="table table-hover table-bordered">
					<thead>
						<tr>
							<th class="sizeName">尺码名称</th>
							<th class="waistlineable">腰围</th>
							<th class="weightable">体重</th>
							<th class="hipable">臀围</th>
							<th class="chestable">胸围</th>
							<th class="heightable">身高</th>
							<th class="shoulderable">肩宽</th>
							<th>##</th>
						</tr>
					</thead>
					<tbody id="sizeBody">
						<tr>
							<td class="sizeNames"><input type="text" class="form-control required" name="sizeNames[0]" /></td>
							<td class="waistlineable">
								<input type="text" class="form-control1 required" name="waistlineStarts[0]"/>~<input type="text" class="form-control1 required" name="waistlineEnds[0]"/>
							</td>
							<td class="weightable">
								<input type="text" class="form-control1 required" name="weightStarts[0]"/>~<input type="text" class="form-control1 required" name="weightEnds[0]" />
							</td>
							<td class="hipable">
								<input type="text" class="form-control1 required" name="hipStarts[0]"/>~<input type="text" class="form-control1 required" name="hipEnds[0]" />
							</td>
							<td class="chestable">
								<input type="text" class="form-control1 required" name="chestStarts[0]"/>~<input type="text" class="form-control1 required" name="chestEnds[0]"/>	
							</td>
							<td class="heightable">
								<input type="text" class="form-control1 required" name="heightStarts[0]"/>~<input type="text" class="form-control1 required" name="heightEnds[0]"/>
							</td>
							<td class="shoulderable">
								<input type="text" class="form-control1 required" name="shoulderStarts[0]"/>~<input type="text" class="form-control1 required" name="shoulderEnds[0]"/>
							</td>
							<td><button class='btn btn-default' onclick='delRow(this)'>删除</button></td>
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

	$(document).ready(function() {
		$("input:checkbox",".panel-body").click(function() {
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
					"<td class='sizeNames'><input type='text' class='form-control required' name='sizeNames#index#' /></td>" + 
					"<td class='waistlineable'><input type='text' class='form-control1 required' name='waistlineStarts#index#'/>~<input type='text' class='form-control1 required' name='waistlineEnds#index#'/></td>" + 
					"<td class='weightable'><input type='text' class='form-control1 required' name='weightStarts#index#'/>~<input type='text' class='form-control1 required' name='weightEnds#index#' /></td>" + 
					"<td class='hipable'><input type='text' class='form-control1 required' name='hipStarts#index#'/>~<input type='text' class='form-control1 required' name='hipEnds#index#' /></td>" + 
					"<td class='chestable'><input type='text' class='form-control1 required' name='chestStarts#index#'/>~<input type='text' class='form-control1 required' name='chestEnds#index#'/>	</td>" + 
					"<td class='heightable'><input type='text' class='form-control1 required' name='heightStarts#index#'/>~<input type='text' class='form-control1 required ' name='heightEnds#index#'/></td>" + 
					"<td class='shoulderable'><input type='text' class='form-control1 required' name='shoulderStarts#index#'/>~<input type='text' class='form-control1 required' name='shoulderEnds#index#'/></td>" + 
					"<td><button class='btn btn-default' onclick='delRow(this)'>删除</button></td>" + 
					"</tr>" ;
	var index =1;
	
	function newRow() {
		var html = trTemplate.replace(new RegExp("#index#","gm"),"["+index+"]");
		$("#sizeBody").append(html);
		$("input:checkbox",".panel-body").each(function(i) {
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