<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>

<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="icon" href="/favicon.ico">

	<title>CentralServer控制台</title>

	<!-- Bootstrap core CSS -->
	<link href="/styles/components/bootstrap-3.2.0-dist/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="/styles/css/style.css" rel="stylesheet">
	<link href="/styles/components/pnotify/pnotify.core.css" rel="stylesheet" type="text/css" />
	<link href="/styles/components/pnotify/pnotify.buttons.css" rel="stylesheet" type="text/css" />
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="/styles/components/bootstrap-3.2.0-dist/js/ie10-viewport-bug-workaround.js"></script>

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<!--
	https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js
	https://oss.maxcdn.com/respond/1.4.2/respond.min.js
	-->
	<script src="/styles/components/bootstrap-3.2.0-dist/js/html5shiv.min.js"></script>
	<script src="/styles/components/bootstrap-3.2.0-dist/js/respond.min.js"></script>
	<![endif]-->
	
	
	<!-- Bootstrap core JavaScript ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="/styles/components/bootstrap-3.2.0-dist/js/jquery-1.11.1.min.js"></script>
	<script src="/styles/components/bootstrap-3.2.0-dist/js/bootstrap.min.js"></script>
	
	<script src="/styles/components/jquery-validation-1.13.0-dist/jquery.validate.min.js"></script>
	<script src="/styles/components/jquery-validation-1.13.0-dist/additional-methods.min.js"></script>
	<script src="/styles/components/jquery-validation-1.13.0-dist/localization/messages_zh.min.js"></script>
	
	<!-- PNotify -->
	<script type="text/javascript" src="/styles/components/pnotify/pnotify.core.js"></script>
	<script type="text/javascript" src="/styles/components/pnotify/pnotify.buttons.js"></script>
	
		
	<script src="/styles/js/dwz.core.js"></script>
	<script src="/styles/js/dwz.history.js"></script>
	<script src="/styles/js/dwz.ui.js"></script>
	<script src="/styles/js/dwz.regional.zh.js"></script>
	<script src="/styles/js/pdp.common.js"></script>
	<script src="/styles/js/dwz.pagination.js"></script>
	
	<script src="/styles/js/vanstone.pnotify.js"></script>

</head>



<body style="padding-top: 60px;">
	
<div class="container-fluid">
	<div class="row">
		<div class="col-md-4">></div>
		<div class="col-md-4">
			<form:form role="form" action="/login-action.jhtml" cssClass="form-signin required-validate" method="post"  commandName="passportForm" >
				<div class="form-group">
					<label for="adminName">账户名</label> 
					<form:input cssClass="form-control"  path="adminName"  required="required" />
				</div>
				<div class="form-group">
					<label for="adminPwd">密码</label> 
					<form:password cssClass="form-control" path="adminPwd"  required="required" />
				</div>
				<div class="checkbox">
					<label> <input type="checkbox" name="rememerPassword"> 记住密码 </label>
				</div>
				<button type="submit" class="btn btn-info">登 录</button>
			</form:form>
		</div>
		<div class="col-md-4"></div>
	</div>
</div>
	
<!-- dwz components -->
<div id="background" style="display: none"></div>
<div id="progressBar" style="display: none">数据加载中，请稍等.</div>

<div style="display: none;" id="data_loading" class=""><div class="inner">处理中...</div></div>

<!-- Modal -->
<div class="modal fade" id="modal-dialog" tabindex="-1" role="dialog" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			
		</div>
	</div>
</div>


<script type="text/javascript">
	$('#passportForm').bind(DWZ.eventType.ajaxDone, function(event, json){
		if (json[DWZ.keys.statusCode] == DWZ.statusCode.ok){
			window.location.href=json.params.redirectUrl;
		}
	});
</script>

</body>
</html>



