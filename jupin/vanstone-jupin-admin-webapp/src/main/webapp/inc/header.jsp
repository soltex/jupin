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

	<title>Jupin管理控制台</title>

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
	<script src="/styles/js/vanstone.messagebox.js"></script>
</head>



<body style="padding-top: 60px;">
	
	<nav class="navbar navbar-inverse  navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="/index_sub.jhtml" data-history>CentralServer控制台</a>
			</div>
			<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li><a href="/admin/weixin/view-weixininfos.jhtml" data-history>微信管理</a></li>
					<li><a href="/admin/configuration/view-configurations.jhtml" data-history>配置管理器</a></li>
					<li><a href="/admin/auth/view-admins.jhtml" data-history>账户管理</a></li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
				<li><a href="/logout.jhtml">退出</a></li>
			</ul>
			</div>
		</div>
	</nav>
	<div class="container-fluid" id="container">