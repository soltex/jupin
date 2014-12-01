<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>


<div class="row">
	<div class="col-md-3">
		<div class="list-group">
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品类定义管理</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>商品属性定义管理</a>
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品牌信息管理</a>
			<a href="/pdm/view-colors" class="list-group-item " data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU颜色表</a>
			<a href="/pdm/view-sizetemplates" class="list-group-item active"><span class="glyphicon glyphicon-arrow-right"></span>SKU尺码模板管理</a>
		</div>
	</div>
	<div class="col-md-9">
		<h2>SKU尺码模板定义</h2>
		<a href="/pdm/add-sizetemplate" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog-lg">新建尺码模板</a>
		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>#ID</th>
					<th>颜色名称</th>
					<th>颜色RGB值</th>
					<th>颜色CSS代码</th>
					<th>显示排序</th>
					<th>##</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="color" items="${colors }">
				<tr>
					<td>${color.id }</td>
					<td>${color.colorName }</td>
					<td>${color.colorRGB }</td>
					<td>${color.colorCSS }</td>
					<td>${color.sort }</td>
					<td>
						<c:if test="${udcolor }">
							<a href="/pdm/update-color/${color.id }" data-toggle="modal" data-target="#modal-dialog">编辑</a> | 
							<a href="/pdm/delete-color-action/${color.id }" title="确认删除该项吗？" data-todo="ajaxTodo">删除</a>
						</c:if>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>


