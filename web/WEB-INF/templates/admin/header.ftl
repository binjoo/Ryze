<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="description" content="轻击键盘，静候回音。" />
<meta name="keywords" content="冰剑,binjoo,wordpress,typecho,firefox,gtalk" />
<title>Yachal Framework</title>
<link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/assets/css/common.css">
<link rel="stylesheet" type="text/css" href="/assets/css/admin.css">
<script type="text/javascript" src="/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/js/base.js"></script>
<script type="text/javascript" src="/assets/js/admin.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="/admin">Control Panel</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <#if menu?exists>
                <#list menu as row>
                    <li${(menu_active==row.key)?string(" class=\"active\"", "")}><a href="/admin${row.url}">${row.name}</a></li>
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