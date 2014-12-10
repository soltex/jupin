<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.vanstone.com/imgserver/tag/1.0" prefix="imgserver" %>

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
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" ><a href="/pdm/view-base-brand/${brandForm.brandId }" data-history>编辑品牌【${brand.brandName }】基本信息</a></li>
			<li role="presentation" class="active"><a href="/pdm/view-brand-logo/${brandForm.brandId}" data-history>编辑品牌【${brand.brandName }】logo信息</a></li>
		</ul>
		
		<div class="row">
			<div class="col-md-4">
				<form:form action="/pdm/update-brand-logo-action" method="post" commandName="brandForm" cssClass="required-validate" enctype="multipart/form-data">
					<form:hidden path="brandId"/>
					<div class="form-group">
						<label for="logoMultipartFile">LOGO文件</label>
						<input type="file" name="logoMultipartFile" class="form-control input-sm" />
					</div>
					<button type="submit" class="btn btn-default">保存品牌信息</button>
					<a href="/pdm/search-brands" class="btn btn-default" data-history>返回</a>
				</form:form>
			</div>
			<div class="col-md-4">
				<c:if test="${brand.logoImage != null}">
					<p>原始图片</p>
					<p>
						<img src='<imgserver:url scaleSize="e300x300" fileId="${brand.logoImage.weedFile.fileid}" extName="${brand.logoImage.weedFile.extName }"></imgserver:url>' class="img-thumbnail" />	
					</p>
					<p>
						<a href="<imgserver:url scaleSize="orig" fileId="${brand.logoImage.weedFile.fileid}" extName="${brand.logoImage.weedFile.extName }"></imgserver:url>"  target="_blank">点击查看大图</a>
					</p>
				</c:if>
			</div>
		</div>
	</div>
</div>


