<%@page import="com.vanstone.jupin.ecs.product.define.attribute.AttributeType"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>





<p></p>
<ul class="nav nav-tabs nav-justified" role="tablist">
	<li role="presentation"><a href="/pdm/add-productcategory"  data-history><span class="glyphicon glyphicon-music"></span> 品类定义管理</a></li>
	<li role="presentation"><a href="/pdm/search-attributes/${ATTRIBUTE_TYPE_TEXT.code }"  data-history><span class="glyphicon glyphicon-music"></span> 商品属性定义管理</a></li>
	<li role="presentation"><a href="/pdm/search-brands"  data-history><span class="glyphicon glyphicon-music"></span> 品牌信息管理</a></li>
	<li role="presentation"><a href="/pdm/view-colors"  data-history><span class="glyphicon glyphicon-music"></span> SKU颜色表</a></li>
	<li role="presentation"><a href="/pdm/view-sizetemplates"  data-ajax data-history><span class="glyphicon glyphicon-music"></span> SKU尺码模板管理</a></li>
</ul>