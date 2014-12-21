<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ include file="/inc/var.jsp" %>



<%@ include file="/inc/pdm-menu.jsp" %>
<div class="row">
	
	
	<div class="col-md-10">
		<h2>增加品牌信息</h2>
		<form:form action="/pdm/add-brand-action" method="post" commandName="brandForm" cssClass="required-validate" enctype="multipart/form-data">
			<div class="form-group">
				 <label for="brandName">品牌名称</label>
				<form:input path="brandName" cssClass="form-control input-sm" placeholder="品牌名称"  required="required" />
			</div>
			<div class="form-group">
				 <label for="brandNameEN">品牌英文名称</label>
				<form:input path="brandNameEN" cssClass="form-control input-sm" placeholder="品牌英文名称" />
			</div>
			<div class="form-group">
				<label for="logoMultipartFile">LOGO文件</label>
				<input type="file" name="logoMultipartFile" class="form-control input-sm" />
			</div>
			<div class="form-group">
				<label for="content">品牌介绍</label>
				<form:textarea path="content" cssClass="form-control input-sm" rows="5" />
			</div>
			<button type="submit" class="btn btn-default">保存品牌信息</button>
			<a href="/pdm/search-brands" class="btn btn-default" data-history>返回</a>
		</form:form>
	</div>
</div>


