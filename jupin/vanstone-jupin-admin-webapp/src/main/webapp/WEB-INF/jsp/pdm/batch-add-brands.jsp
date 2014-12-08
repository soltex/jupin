<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<div class="row">
	<div class="col-md-2">
		<div class="list-group" style="position: fixed;">
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品类定义管理</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>商品属性定义管理</a>
			<a href="/pdm/search-brands" class="list-group-item" data-history><span class="glyphicon glyphicon-arrow-right"></span>品牌信息管理</a>
			<a href="/pdm/view-colors" class="list-group-item active" data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU颜色表</a>
			<a href="/pdm/view-sizetemplates" class="list-group-item" data-ajax data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU尺码模板管理</a>
		</div>
	</div>
	<div class="col-md-10">
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


