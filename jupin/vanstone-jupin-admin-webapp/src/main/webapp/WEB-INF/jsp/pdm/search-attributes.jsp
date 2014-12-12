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
		<h2>品类属性定义</h2>
		<a href="/pdm/add-attribute" class="btn btn-default" data-toggle="modal" data-target="#modal-dialog">新建品类属性信息</a>
		<a href="/pdm/search-attributes/${attributeType.code }" class="btn btn-default" data-history>刷新列表</a>
		<p></p>
		<ul class="nav nav-tabs" role="tablist">
			<li role="presentation" <c:if test="${attributeType eq ATTRIBUTE_TYPE_TEXT }">class='active'</c:if>><a href="/pdm/search-attributes/${ATTRIBUTE_TYPE_TEXT.code }" data-history>文本属性信息检索</a></li>
			<li role="presentation" <c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">class='active'</c:if>><a href="/pdm/search-attributes/${ATTRIBUTE_TYPE_ENUM.code }" data-history>枚举属性信息检索</a></li>
		</ul>
		<p></p>
		
		
		
		<form:form action="/pdm/search-attributes/${attributeType.code }" method="post" commandName="attributeForm"  onsubmit="return DWZ.dwzSearch(this, 'container')" id="pagerForm">
			<div class="form-group">
				<form:input path="key" cssClass="form-control input-sm" placeholder="属性关键字，属性名称以及属性描述信息"   />
				
				<input type="hidden" value="${pageInfo.pageNo }" name="pageNum">
				<input type="hidden" value="20" name="numPerPage">
				<input type="hidden" value="${param.orderField}" name="orderField">
				<input type="hidden" value="${param.orderDirection}" name="orderDirection">
				
			</div>
			<div class="select">
				<label>
					<form:select path="listshowable" cssClass="form-control input-sm">
						<form:option value="">--是否显示在列表页上--</form:option>
						<form:option value="true">是</form:option>
						<form:option value="false">否</form:option>
					</form:select>
    			</label>
    			<label>
    				<form:select path="requiredable" cssClass="form-control input-sm">
						<form:option value="">--是否为必填项--</form:option>
						<form:option value="true">是</form:option>
						<form:option value="false">否</form:option>
					</form:select>
    			</label>
    			
    			<c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">
    			<!-- 枚举值选项 -->
    			<label>
    				<form:select path="multiselectable" cssClass="form-control input-sm">
						<form:option value="">--是否可多选--</form:option>
						<form:option value="true">是</form:option>
						<form:option value="false">否</form:option>
					</form:select>
    			</label>
    			<label>
    				<form:select path="searchable" cssClass="form-control input-sm">
						<form:option value="">--是否作为搜索条件--</form:option>
						<form:option value="true">是</form:option>
						<form:option value="false">否</form:option>
					</form:select>
    			</label>
    			</c:if>
    			
			</div>
			<button type="submit" class="btn btn-default">检索</button>
						
		</form:form>
		<div id="search_brands_id">
			<table class="table table-hover" >
				<thead>
					<tr>
						<th style="width: 100px">属性类型</th>
						<th style="width:60px;">ID</th>
						<th style="width: 100px">属性名称</th>
						<th>属性描述</th>
						<th style="width:60px;">列表页</th>
						<th style="width:60px;">必填项</th>
						<c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">
						<th style="width:60px;">多选</th>
						<th style="width:60px;">搜索</th>
						</c:if>
						<th style="width: 120px;">##</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="item" items="${pageInfo.objects }">
					<tr class="">
						<td>${attributeType.desc }</td>
						<td>${item.id }</td>
						<td>${item.attributeName }</td>
						<td>${item.attributeDescription }</td>
						<td><c:choose><c:when test="${item.listshowable }">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${item.requiredable }">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
						
						<c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">
						<td><c:choose><c:when test="${item.multiselectable }">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${item.searchable }">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
						</c:if>
						<td>
							<c:if test="${attributeType eq ATTRIBUTE_TYPE_TEXT }">
							<a href="/pdm/view-text-attribute/${item.id }"  data-toggle="modal" data-target="#modal-dialog">编辑</a>
							</c:if>
							<c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">
							<a href="/pdm/view-enum-attribute/${item.id }?pageNum=${pageInfo.pageNo}" data-history>编辑</a>
							</c:if>
							<a href="/pdm/delete-attribute-action/${item.id }"  title="确认是否删除该项 ？"  data-todo="ajaxTodo">删除</a>
						</td>
					</tr>
					<c:if test="${attributeType eq ATTRIBUTE_TYPE_ENUM }">
					<tr>
						<td colspan="9">
							<c:forEach items="${item.values }" var="entryItem">
								<small>${entryItem.key }</small> - ${entryItem.value }
							</c:forEach>
						</td>
					</tr>
					</c:if>
					</c:forEach>
				</tbody>
			</table>
			
			<div rel="container" currentpage="${pageInfo.pageNo }" pagenumshown='<c:choose><c:when test="${pageInfo.pages >= 20}">20</c:when><c:otherwise>${pageInfo.pages}</c:otherwise></c:choose>' numperpage="${pageInfo.pageSize}" totalcount="${pageInfo.rows }" class="pagination"></div>
		</div>
		
	</div>
</div>