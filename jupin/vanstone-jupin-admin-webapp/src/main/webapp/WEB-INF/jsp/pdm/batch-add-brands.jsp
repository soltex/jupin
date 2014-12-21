<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/var.jsp" %>


<%@ include file="/inc/pdm-menu.jsp" %>
<div class="row">
	<div class="col-md-12">
		<h2>批量导入品牌信息</h2>
		<form:form action="/pdm/batch-add-brands-action" method="post" commandName="brandForm" cssClass="required-validate" enctype="multipart/form-data">
			<div class="form-group">
				<label for="batchImportFile">LOGO文件</label>
				<input type="file" name="batchImportFile" class="form-control input-sm" />
			</div>
			<button type="submit" class="btn btn-default">导入品牌信息</button>
			<a href="/pdm/search-brands" class="btn btn-default" data-history>返回</a>
			<a href="/styles/templates/import-brands-template.xls" class="btn btn-default" target="_blank">文件下载</a>
		</form:form>
	</div>
</div>


