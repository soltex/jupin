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
			<a href="/pdm/search-brands" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品牌信息管理</a>
			<a href="/pdm/view-colors" class="list-group-item " data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU颜色表</a>
			<a href="/pdm/view-sizetemplates" class="list-group-item active"><span class="glyphicon glyphicon-arrow-right"></span>SKU尺码模板管理</a>
		</div>
	</div>
	<div class="col-md-10">
		<h2>SKU尺码模板定义</h2>
		<a href="/pdm/add-sizetemplate" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">新建尺码模板</a>
		<p></p>
		
		
		
		<c:forEach var="bean" items="${wrapBeans }">
			<div class="panel panel-default">
				<div class="panel-heading">
					<span class="badge">${bean.sizeTemplate.id}</span>
					<span class="badge">${bean.sizeTemplate.templateName }</span>
					<a href="/pdm/delete-sizetemplate-action/${bean.sizeTemplate.id }" class="btn btn-default" data-todo="ajaxTodo" title="确认删除当前项？">删除</a>
					<a href="/pdm/update-base-sizetemplate/${bean.sizeTemplate.id }" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">编辑基本信息</a>
					<a href="/pdm/add-size/${bean.sizeTemplate.id }" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">新建尺码</a>
				</div>
				<div class="panel-body">${bean.sizeTemplate.templateContent }</div>
				<table class="table table-hover">
					<thead>
						<tr>
							<th class="sizeid" style="width:100px;">尺码ID</th>
							<th class="sizeName" style="width:10%">尺码名称</th>
							<th class="waistlineable" style="width:10%">腰围</th>
							<th class="weightable" style="width:10%">体重</th>
							<th class="hipable" style="width:10%">臀围</th>
							<th class="chestable" style="width:10%">胸围</th>
							<th class="heightable" style="width:10%">身高</th>
							<th class="shoulderable" style="width:10%">肩宽</th>
							<th style="width:200px;">##</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="size" items="${bean.sizes}">
						<tr>
							<td>${size.id }</td>
							<td>${size.sizeName }</td>
							<td>${size.waistlineStart }~${size.waistlineEnd }</td>
							<td>${size.weightStart }~${size.weightEnd }</td>
							<td>${size.hipStart }~${size.hipEnd }</td>
							<td>${size.chestStart }~${size.chestEnd }</td>
							<td>${size.heightStart }~${size.heightEnd }</td>
							<td>${size.shoulderStart }~${size.shoulderEnd }</td>
							<td>
								<c:if test="${bean.udable }">
									<a href="/pdm/update-size/${size.id }" data-toggle="modal" data-target="#modal-dialog" class="btn btn-default" >编辑</a> 
									<a href="/pdm/delete-size-action/${size.id }" title="确认删除该项吗？" class="btn btn-default" data-todo="ajaxTodo">删除</a>
								</c:if>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				<div class="panel-footer"><a href="/pdm/update-all-sizeinfos/${bean.sizeTemplate.id }" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">批量修改尺码信息</a></div>
			</div>
		</c:forEach>
	</div>
</div>


