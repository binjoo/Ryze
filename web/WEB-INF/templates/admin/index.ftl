<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="description" content="轻击键盘，静候回音。" />
<meta name="keywords" content="冰剑,binjoo,wordpress,typecho,firefox,gtalk" />
<title>Ryze Control Panel</title>
<link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/assets/css/common.css">
<link rel="stylesheet" type="text/css" href="/assets/css/admin.css">
<script type="text/javascript" src="/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/js/base.js"></script>
<script type="text/javascript" src="/assets/js/admin.js"></script>
</head>
<body scroll="no">
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/admin">Ryze Control Panel</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-menu">
                <#if admin_menu?exists>
                <#list admin_menu as row>
                    <li${(row_index == 0)?string(" class=\"active\"", "")}>
                    	<a href="/admin?action=${row.action}" name="${row.action}" hidefocus="true" target="main">${row.name}</a>
                    </li>
                </#list>
                </#if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
            	<p class="navbar-text">你好，${session.login.nickname?if_exists}</p>
            	<li><a href="/">站点</a></li>
            	<li><a href="#">退出</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container-fluid">
	<div class="row">
	<#include "/admin/sidebar.ftl">
	<div class="col-md-10 col-md-offset-2 main">
		<iframe src="/admin?action=global&method=index" id="main" name="main" width="100%" height="100%" frameborder="0" scrolling="yes">
	</iframe>
	</div>
    </div>
</div>
</body>
</html>