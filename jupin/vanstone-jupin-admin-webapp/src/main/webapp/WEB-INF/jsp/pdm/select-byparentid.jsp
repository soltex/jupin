<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ include file="/inc/var.jsp" %>




<c:if test="${productCategoryDetail != null and !empty productCategoryDetail.childProductCategories }">
	<select id="level${no+1 }" size="10" class="form-control" name="level${no+1 }" no="${no+1 }">
		<c:forEach var="item" items="${productCategoryDetail.childProductCategories }">
		<option value="${item.id }" leafable="${item.leafable }">${item.categoryName }</option>
		</c:forEach>
	</select>
</c:if>
