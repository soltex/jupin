<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form:form action="/pdm/update-color-action" method="post" commandName="colorForm" cssClass="required-validate">
	
<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">修改管理员信息</h4>
		</div>
		<div class="modal-body">
			<div class="form-group">
			 <label for="colorName">颜色名称</label>
			 <form:hidden path="id"/>
			<form:input path="colorName" cssClass="form-control" placeholder="颜色名称"  required="required" />
		</div>
		<div class="form-group">
			 <label for="colorRGB">颜色RGB值</label>
			<form:input path="colorRGB" cssClass="form-control" placeholder="颜色RGB值"  required="required" />
		</div>
		<div class="form-group">
			 <label for="colorCSS">颜色CSS</label>
			<form:input path="colorCSS" cssClass="form-control" placeholder="配置信息值"  required="required" />
		</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default">保存颜色信息</button>
	</div>
</form:form>