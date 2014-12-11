<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/var.jsp" %>

<form:form action="/pdm/add-attribute-action" method="post" commandName="attributeForm"  cssClass="required-validate">


<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">新建品类属性</h4>
		</div>
		<div class="modal-body">
			<div class="radio">
				<c:forEach var="item" items="${attributeTypes }">
				<label class="checkbox-inline"><form:radiobutton path="attributeTypeCode" value="${item.code }" required="required"/>${item.desc }</label> 
				</c:forEach>
			</div>
			<div class="form-group">
				<label for="attributeName">属性显示名称</label>
				<form:input path="attributeName" cssClass="form-control required" placeholder="属性显示名称"  />
			</div>
			<div class="form-group">
				 <label for="attributeDescription">属性描述信息</label>
				<form:textarea path="attributeDescription" cssClass="form-control" placeholder="属性描述信息" />
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="listshowable"/> 是否显示在列表页上
			    </label>
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="requiredable"/> 是否为必填项
			    </label>
			</div>
			
			
			<div class="enumtype">
				<div class="checkbox">
				    <label>
						<form:checkbox path="multiselectable"/> 是否可多选
				    </label>
				</div>
				<div class="checkbox">
				    <label>
						<form:checkbox path="searchable"/> 是否作为搜索条件
				    </label>
				</div>
				<div class="form-group">
					 <label for="enumValues">枚举值（可使用逗号，回车进行分割，填写多个）</label>
					<form:textarea path="enumValues" cssClass="form-control" placeholder="枚举类型属性的值,可使用逗号，回车进行分割，填写多个"  rows="5"/>
				</div>
			</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存属性信息</button>
	</div>
</form:form>


<script type="text/javascript">

	function validateAttributeForm() {
		var $attributeTypeCode = $("input:radio:checked","#attributeForm");
		if ($attributeTypeCode.val() == '0') {
			//文本属性
			$('.enumtype').hide();
			$("#enumValues").removeAttr('required');
		}else{
			$('.enumtype').show();
			$("#enumValues").attr('required','required');
		}
	}
	
	$(document).ready(function() {
		validateAttributeForm();
		
		$("input:radio","#attributeForm").click(function() {
			validateAttributeForm();
		});
	});	
	
</script>
