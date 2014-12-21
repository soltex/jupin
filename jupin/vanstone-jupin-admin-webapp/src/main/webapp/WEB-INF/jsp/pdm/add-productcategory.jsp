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
		<h2>商品品类管理<small>添加商品品类</small></h2>
		<form:form action="/pdm/add-productcategory-action" method="post" commandName="productCategoryForm" cssClass="required-validate" enctype="multipart/form-data">
			<div class="form-group">
				 <label for="categoryName">父品类</label>
				<jsp:include page="select-parent-productcategory.jsp"></jsp:include>
			</div>
			<div class="form-group">
				 <label for="categoryName">品类名称</label>
				<form:input path="categoryName" cssClass="form-control input-sm" placeholder="品类名称"  required="required" />
			</div>
			<div class="form-group">
				 <label for="description">品类描述信息</label>
				<form:textarea path="description" cssClass="form-control" placeholder="品类描述信息" />
			</div>
			<div class="form-group">
				<label for="logoMultipartFile">品类封面图片</label>
				<input type="file" name="coverImageFile" class="form-control input-sm" />
			</div>
			<div class="form-group">
				<label for="content">排序</label>
				<form:input path="sort" cssClass="form-control input-sm" placeholder="排序"  />
			</div>
			<div class="checkbox">
				<label>
					<form:checkbox path="skuColor" /> 是否存在SKU颜色属性
				</label>
			</div>
			<div class="checkbox">
				<label class="checkbox-inline">
					<form:checkbox path="existSizeTemplate" /> 是否存在尺码属性
				</label>
				<form:select path="sizeTemplateId">
					<c:if test="${!empty sizeTemplates }">
					<c:forEach var="item" items="${sizeTemplates }">
					<form:option value="${item.id }">${item.templateName }</form:option>
					</c:forEach>
					</c:if>
				</form:select>
			</div>
			<div class="form-group">
				<div class="panel panel-default">
					<div class="panel-heading">请选择品类属性&nbsp;&nbsp;<a href="/pdm/add-attribute-dialog" class="btn btn-default">新建属性</a> <a href="/pdm/select-attributes" class="btn btn-default">选择属性</a></div>
					<table class="table table-hover">
						<thead>
							<tr>
								<th>属性类型</th>
								<th>属性名称</th>
								<th>属性值</th>
								<th style='width:50px'>##</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>&nbsp;</td>
								<td></td>
								<td></td>
								<td><a href="" class="">删除</a><input type="hidden" name="attributeIds" /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<button type="submit" class="btn btn-default">保存品类信息</button>
			<a href="/pdm/search-productcategories" class="btn btn-default" data-history>返回</a>
		</form:form>
	</div>
</div>


<script type="text/javascript">
	$(document).ready(function() {
		
		//绑定尺码模板点击事件
		$("#existSizeTemplate").click(function() {
			if ($(this).attr("checked") == true) {
				//$("#sizeTemplateId").
			}
		});
		
		
	});
</script>