<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/update-base-sizetemplate-action" method="post" commandName="sizeForm"  cssClass="required-validate">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">编辑尺码模板基本信息</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
				<label for="templateName">模板名称</label>
				<form:hidden path="sizeTemplateId"  />
				<form:input path="templateName" cssClass="form-control required" placeholder="模板名称"  />
			</div>
			<div class="form-group">
				 <label for="templateContent">模板描述信息</label>
				<form:textarea path="templateContent" cssClass="form-control" placeholder="模板描述信息" />
			</div>
		</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存尺码模板基本信息</button>
	</div>
</form:form>



