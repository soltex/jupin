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
		<h2>SKU颜色库定义</h2>
		<form:form action="/pdm/add-color-action" method="post" commandName="colorForm" cssClass="required-validate">
			<div class="form-group">
				 <label for="colorName">颜色名称</label>
				<form:input path="colorName" cssClass="form-control input-sm" placeholder="颜色名称"  required="required" />
			</div>
			<div class="form-group">
				 <label for="colorRGB">颜色RGB值</label>
				<form:input path="colorRGB" cssClass="form-control input-sm" placeholder="颜色RGB值"  required="required" />
			</div>
			<div class="form-group">
				 <label for="colorCSS">颜色CSS</label>
				<form:input path="colorCSS" cssClass="form-control input-sm" placeholder="配置信息值"  required="required" />
			</div>
			<button type="submit" class="btn btn-default">保存颜色信息</button>
		</form:form>
		
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


