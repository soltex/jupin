<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<form action="/add-example-action" method="post">
	<input type="text" name="strings[0]"/> <br />
	<input type="text" name="strings[1]"/> <br />
	<input type="text" name="strings[2]"/> <br />
	<input type="text" name="strings[3]"/> <br />
	<input type="text" name="strings[4]"/> <br />
	<button type="submit">保存</button>
</form>