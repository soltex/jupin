<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<%@ include file="/inc/var.jsp" %>

<form:form action="/pdm/update-enum-attribute-action" method="post" commandName="attributeForm"  cssClass="required-validate">

<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
		<h4 class="modal-title">修改文本品类属性</h4>
		</div>
		<div class="modal-body">
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
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">枚举属性值信息</h3>
				</div>
				<table class="table table-hover" >
					<thead>
						<tr>
							<th style="width: 40px">ID</th>
							<th>值信息</th>
							<th style="width: 70px">##</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="itemEntry" items="${attribute.values }">
						<tr>
							<td>${itemEntry.key }</td>
							<td>${itemEntry.value }</td>
							<td>
								<p>
								<a href="/pdm/delete-enumvalue-action/${itemEntry.key }" ajax-todo="ajaxTodo" title="确认删除该枚举值？">编辑</a> 
								<a href="/pdm/delete-enumvalue-action/${itemEntry.key }" ajax-todo="ajaxTodo" title="确认删除该枚举值？">删除</a> 
								</p>
								<p>
								<a href="/pdm/delete-enumvalue-action/${itemEntry.key }" ajax-todo="ajaxTodo" title="确认删除该枚举值？">上移</a> 
								<a href="/pdm/delete-enumvalue-action/${itemEntry.key }" ajax-todo="ajaxTodo" title="确认删除该枚举值？">下移</a> 
								</p>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
			</div>
	</div>
	<div class="modal-footer">
		<button type="submit" class="btn btn-default" >保存属性信息</button>
	</div>
	
</form:form>

