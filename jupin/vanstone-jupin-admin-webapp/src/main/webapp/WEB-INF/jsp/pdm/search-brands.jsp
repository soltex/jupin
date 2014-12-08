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
			<a href="#" class="list-group-item"><span class="glyphicon glyphicon-arrow-right"></span>品牌信息管理</a>
			<a href="/pdm/view-colors" class="list-group-item " data-history><span class="glyphicon glyphicon-arrow-right"></span>SKU颜色表</a>
			<a href="/pdm/view-sizetemplates" class="list-group-item active"><span class="glyphicon glyphicon-arrow-right"></span>SKU尺码模板管理</a>
		</div>
	</div>
	<div class="col-md-10">
		<h2>品牌检索</h2>
		<a href="/pdm/add-brand" class="btn btn-default" data-history>新建品牌</a>
		<a href="/pdm/batch-add-brands" class="btn btn-default" data-history>批量导入品牌</a>
		<a href="/styles/templates/import-brands-template.xls" class="btn btn-default" target="_blank">文件下载</a>
		<p></p>
			<form:form action="/pdm/search-brands" method="post" commandName="brandForm"  onsubmit="return DWZ.dwzSearch(this, 'search_brands_id')" id="pagerForm">
				<div class="form-group">
					<form:input path="key" cssClass="form-control input-sm" placeholder="品牌关键字"   />
					
					<input type="hidden" value="${pageInfo.pageNo }" name="pageNum">
					<input type="hidden" value="20" name="numPerPage">
					<input type="hidden" value="${param.orderField}" name="orderField">
					<input type="hidden" value="${param.orderDirection}" name="orderDirection">
					
				</div>
				<button type="submit" class="btn btn-default">检索</button>
			</form:form>
			<div id="search_brands_id">
				<table class="table table-hover" >
					<thead>
						<tr>
							<th style="width:60px;">ID</th>
							<th>品牌信息</th>
							<th>英文名称</th>
							<th>首字母</th>
							<th>品牌拼音</th>
							<th>简介</th>
							<th>系统内置</th>
							<th>LOGO</th>
							<th>涉足品类数量</th>
							<th>商品数量</th>
							<th style="width: 100px;">##</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="brand" items="${pageInfo.objects }">
						<tr>
							<td>${brand.id }</td>
							<td>${brand.brandName }</td>
							<td>${brand.brandNameEN }</td>
							<td>${brand.brandNamefirstLetter }</td>
							<td>${brand.brandNamePinyin }</td>
							<td>${brand.content }</td>
							<td><c:choose> <c:when test="${brand.systemable }">是</c:when><c:otherwise>否</c:otherwise></c:choose></td>
							<td>LOGO</td>
							<td>${brand.productCategoryCount }</td>
							<td>${brand.productCount }</td>
							<td>
							<a href="/pdm/view-brand/${brand.id }" data-history>编辑</a>
							<a href="/pdm/delete-brand-action/${brand.id }"  title="确认是否删除该项 ？"  data-todo="ajaxTodo" class="">删除</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
				
				<div currentpage="${pageInfo.pageNo }" pagenumshown="10" numperpage="${pageInfo.pages }" totalcount="${pageInfo.rows }" class="pagination"></div>
			</div>
		
	</div>
</div>


	