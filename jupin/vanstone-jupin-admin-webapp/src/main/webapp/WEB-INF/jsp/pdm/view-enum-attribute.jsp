<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/var.jsp" %>

<div class="row">
	<div class="col-md-2">
	
		<%@ include file="/inc/pdm-menu.jsp" %>
		
	</div>
	<div class="col-md-12">
		<h2>编辑枚举属性信息</h2>
		<form:form action="/pdm/update-enum-attribute-action" method="post" commandName="attributeForm"  cssClass="required-validate">
			<div class="form-group">
				<label for="attributeName">属性显示名称</label>
				<form:hidden path="id"/>
				<form:input path="attributeName" cssClass="form-control required" placeholder="属性显示名称"  />
			</div>
			<div class="form-group">
				 <label for="attributeDescription">属性描述信息</label>
				<form:textarea path="attributeDescription" cssClass="form-control" placeholder="属性描述信息" />
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="listshowable"/> 是否显示在列表页上
			    </label>
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="requiredable"/> 是否为必填项
			    </label>
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="multiselectable"/> 是否可多选
			    </label>
			</div>
			<div class="checkbox">
			    <label>
					<form:checkbox path="searchable"/> 是否作为搜索条件
			    </label>
			</div>
			<button type="submit" class="btn btn-default" >保存属性基本信息</button> 
			<a href="/pdm/search-attributes/${ATTRIBUTE_TYPE_ENUM.code }?pageNum=${attributeForm.pageNum}" data-history class="btn btn-default">返回</a>
		</form:form>
		<div class="panel panel-default">
			<div class="panel-heading">
					枚举属性值信息区域编辑
					<a href="/pdm/add-enum-value/${attributeForm.id }" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">添加属性值</a>
			</div>
			<table class="table table-hover" >
				<thead>
					<tr>
						<th style="width: 40px">ID</th>
						<th>值信息</th>
						<th style="width: 300px">##</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="itemEntry" items="${attribute.values }">
					<tr>
						<td>${itemEntry.key }</td>
						<td>${itemEntry.value }</td>
						<td>
							<p>
								<a href="/pdm/view-enum-value/${itemEntry.key }" data-toggle="modal" data-target="#modal-dialog">编辑</a> 
								<a href="/pdm/delete-enum-value-action/${itemEntry.key }" title="确认是否删除该项 ？"  data-todo="ajaxTodo">删除</a> 
								<a href="/pdm/top-enumvalue-action/${itemEntry.key }"   data-todo="ajaxTodo" title="确认置顶该枚举值？">置顶</a> 
								<a href="/pdm/up-enumvalue-action/${itemEntry.key }"   data-todo="ajaxTodo" title="确认上移该枚举值？">上移</a> 
								<a href="/pdm/down-enumvalue-action/${itemEntry.key }"   data-todo="ajaxTodo" title="确认下移该枚举值？">下移</a> 
								<a href="/pdm/bottom-enumvalue-action/${itemEntry.key }"   data-todo="ajaxTodo" title="确认置底该枚举值？">置底</a> 
							</p>
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>

