<%@page import="com.vanstone.jupin.ecs.product.define.attribute.AttributeType"%>
<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<!-- Set Attribute Type Value -->
<c:set var="ATTRIBUTE_TYPE_TEXT" value="<%=AttributeType.Text%>"></c:set>
<c:set var="ATTRIBUTE_TYPE_ENUM" value="<%=AttributeType.Enum%>"></c:set>
