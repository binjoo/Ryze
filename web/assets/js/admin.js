var pageObj = window.NameSpace || {};
pageObj.randomAESKey = function(){
	$("input[name=wechat_aeskey]").val(Utils.randomWord(false, 43));
}

pageObj.index = new function() {
	this.header_click = function(){
		$("ul.navbar-menu a").parent().removeClass();
		$(this).parent().addClass("active");
		$("div.sidebar ul").hide();
		$("#nav-menu-" + $(this).attr("name") + " li").removeClass();
		$("#nav-menu-" + $(this).attr("name") + " li:first").addClass("active");
		$("#nav-menu-" + $(this).attr("name")).show();
	},
	this.sidebar_click = function(){
		$("ul.nav-sidebar a").parent().removeClass();
		$(this).parent().addClass("active");
	}
};

$(document).ready(function() {
	//微信随机生成EncodingAESKey
	$("#randomAESKey").click(pageObj.randomAESKey);
	$("ul.navbar-menu a").click(pageObj.index.header_click);
	$("ul.nav-sidebar a").click(pageObj.index.sidebar_click);
	pageObj.index.sidebar_click;
});