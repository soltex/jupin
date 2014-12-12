<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/var.jsp" %>

<form:form action="/pdm/add-enum-value-action" method="post" commandName="attributeForm"  cssClass="required-validate">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">新增枚举属性值信息-【${attr4EnumValue.attr4Enum.attributeName }】</h4>
	</div>
	<div class="modal-body">
		<div class="form-group">
			<label for="attributeName">属性显示名称</label>
			<form:hidden path="id"/>
			<form:textarea path="objectText" cssClass="form-control" placeholder="枚举属性值" />
		</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存属性信息</button>
	</div>
</form:form>