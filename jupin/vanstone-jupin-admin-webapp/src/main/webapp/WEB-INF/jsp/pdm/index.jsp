<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/header.jsp"%>


<div class="row">
	<div class="col-md-3">
		<div class="list-group">
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品类定义管理</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>商品属性定义管理</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品牌信息管理</a>
			<a href="/pdm/view-colors" class="list-group-item" data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU颜色表</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>SKU尺码模板管理</a>
		</div>
	</div>
	<div class="col-md-9">
		
	</div>
</div>

<%@ include file="/inc/footer.jsp"%>