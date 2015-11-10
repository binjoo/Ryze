<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>新增书签</title>
<link rel="stylesheet" type="text/css" href="/theme/default/css/style.css">
<style type="text/css">
.form {
    margin:0;
    padding:0;
}
fieldset{
    border:1px dashed #CCC;
    padding:10px;
    margin:10px;
}
legend {
	text-align: left;
    color:#fff;
    background: #666;
    border: 1px solid #333;
    padding: 2px 6px;
}
label {
	text-align: right;
    width:64px;
    height:16px;
    margin:3px 2px;
    padding:5px 2px;
    color:#666;
    background: #ccc;
}
.row{
	text-align: left;
	clear: both;	
}
.div_texbox {
    width:347px;
    left: 64px;
    background-color:#E6E6E6;
    height:32px;
    margin-top:3px;
    padding-top:5px;
    padding-bottom:3px;
    padding-left:5px;
}
.textbox {
    width:285px;
    color: #999999;
    padding:5px;
}
input:focus, input:hover {
    background-color:#F0FFE6;
}
.button_div {
    width:287px;
    float:right;
    text-align:right;
    height:35px;
    margin-top:3px;
    padding:5px 32px 3px;
}
.buttons {
    padding:6px 14px;
    border:2px solid;
    border-color: #fff #d8d8d0 #d8d8d0 #fff;
    background: #e3e3db;
    color: #989070;
    font-weight:bold;
}
</style>
<script type="text/javascript" src="theme/default/js/jquery.min.js"></script>
<script>
$(document).ready(function(){
	$("input[name=submit]").click(function(){
		alert("xxx");
		$(".form").submit();
	})
})
</script>
</head>
<body>
<fieldset>
<legend>易用性表单设计</legend>
<form action="/bookmark/insert" method="post" class="form">
	<input type="hidden" name="md5" value="${bookmarkMD5}"/>
	<div class="row">
	    <label for="name">名称：</label>
	    <div class="div_texbox">
	        <input name="name" type="text" class="textbox" id="name" value="${title}" />
	    </div>
    </div>
	<div class="row">
	    <label for="address">网址：</label>
	    <div class="div_texbox">
	        <input name="address" type="text" class="textbox" id="address" value="${url}" />
	    </div>
    </div>
	<div class="row">
	    <label for="city">标签：</label>
	    <div class="div_texbox">
	        <input name="city" type="text" class="textbox" id="city" value="" />
	    </div>
    </div>
	<div class="row">
	    <div class="button_div">
	        <input name="submit" type="submit" value="Submit" class="buttons" />
	    </div>
    </div>
</form>
</fieldset>
</body>
</html>