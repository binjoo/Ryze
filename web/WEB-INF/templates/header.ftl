<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="轻击键盘，静候回音。" />
<meta name="keywords" content="冰剑,binjoo,wordpress,typecho,firefox,gtalk" />
<title>Ryze</title>
<link rel="stylesheet" type="text/css" href="/assets/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="/assets/css/common.css">
<link rel="stylesheet" type="text/css" href="/assets/css/style.css">
<script type="text/javascript" src="/assets/js/jquery.min.js"></script>
<script type="text/javascript" src="/assets/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/assets/js/base.js"></script>
</head>
<body>
<div id="wrap">
    <div id="header">
        <div class="header container">
            <div class="row">
                <div class="header-menu col-md-6 text-left">
                    <a href="/">首页</a>
                    <a href="/note">记事本</a>
                    <a href="/bookmark">书签</a>
                    <a href="/forum">论坛</a>
                </div>
                <div class="header-control col-md-6 text-right">
                <#if session.login?exists>
                	当前登录：
                	<div class="header-my-control">
                		<a class="my-control" href="/user/${session.login.id}">${session.login.nickname?if_exists} <span class="caret"></span></a>
                		<ul class="header-my-control-list" >
                			<li><a href="/user/message">我的消息</a></li>
                		</ul>
                	</div>
                    [
                    <a class="message" href="/user/message">消息<span class="badge">7</span></a>
                    |
                    <a href="/user/logout">退出</a>
                    ]
                <#else>
                	当前访客身份：游客
                    [
                    <a href="/user/login">登录</a>
                    |
                    <a href="/user/register">注册</a>
                    ]
                </#if>
                </div>
            </div>
        </div>
    </div>
    <div id="content">
        <div class="container">