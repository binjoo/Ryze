var pageObj = {};
pageObj.randomAESKey = function(){
	$("input[name=wechat_aeskey]").val(Utils.randomWord(false, 43));
}
$(document).ready(function() {
	//微信随机生成EncodingAESKey
	$("#randomAESKey").click(pageObj.randomAESKey);
});