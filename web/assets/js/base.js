//  
//                                  _oo8oo_
//                                 o8888888o
//                                 88" . "88
//                                 (| -_- |)
//                                 0\  =  /0
//                               ___/'==='\___
//                             .' \\|     |// '.
//                            / \\|||  :  |||// \
//                           / _||||| -:- |||||_ \
//                          |   | \\\  -  /// |   |
//                          | \_|  ''\---/''  |_/ |
//                          \  .-\__  '-'  __/-.  /
//                        ___'. .'  /--.--\  '. .'___
//                     ."" '<  '.___\_<|>_/___.'  >' "".
//                    | | :  `- \`.:`\ _ /`:.`/ -`  : | |
//                    \  \ `-.   \_ __\ /__ _/   .-` /  /
//                =====`-.____`.___ \_____/ ___.`____.-`=====
//                                  `=---=`
//  
//  
//               ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// 
//                          佛祖保佑         永无bug
//    
var Utils = {};
var common = {};
/*
 ** randomWord 产生任意长度随机字母数字组合
 ** randomFlag-是否任意长度 min-任意长度最小位[固定位数] max-任意长度最大位
 */
Utils.randomWord = function(randomFlag, min, max){
	var str = "",
		range = min,
		arr = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'];

	// 随机产生
	if(randomFlag){
		range = Math.round(Math.random() * (max-min)) + min;
	}
	for(var i=0; i<range; i++){
		pos = Math.round(Math.random() * (arr.length-1));
		str += arr[pos];
	}
	return str;
}
Utils.goUrl = function(url) {
	location.href = Utils.isNull(url) ? "/" : url;
}
Utils.isNull = function(obj) {
	if (!obj || obj == null) {
		return true;
	} else {
		return false;
	}
}
Utils.formAlert = function(form, msg, type){
	if(form && msg){
		$(form).before('<div class="alert alert-' + (type ? type : 'success') + '" role="alert">' + msg + '</div>');
	}
}
Utils.showPromptAjax = function(msg){
	var focus = true;
	for ( var i = 0; i < msg.length; i++) {
		var tmp = msg[i].split("#");
		if(tmp.length >= 2){
			html = '<span class="form-message control-label">' + tmp[1] + "</span>";
			if(focus){
				$("[name=" + tmp[0] + "]:first").focus();focus = false;
			}
			if(tmp[0] == "captcha"){
				$("[name=" + tmp[0] + "]:first").parent().prev("[for=form_" + tmp[0] + "]").append(html).parent().addClass("has-error");
			}else{
				$("[name=" + tmp[0] + "]:first").prev("[for=form_" + tmp[0] + "]").append(html).parent().addClass("has-error");
			}
		}
	}
}
common.submit = function(){
	var $btn = $(this).button('loading'), form = $(this).parents("form");
	$(".form-message").remove();
	$("div.alert[class^=alert]").remove();
	$(".form-group").removeClass("has-error");

	$.post(form.attr("action"), form.serialize(), function(data) {
		if(data.status == 1){
			if(data.callback){
				Utils.goUrl(data.callback);
			}else{
				Utils.formAlert(form, data.message);
			}
		}else if(data.status == 0){
			Utils.formAlert(form, data.message, "danger");
			$btn.button('reset');
		}else if (data.message) {
			Utils.showPromptAjax(data.message);
			$btn.button('reset');
		}
		$("img[name=captcha_img]").attr("src", "/action/captcha?" + Math.random());
	})
}
common.captcha = function(){
	$(this).attr("src", "/action/captcha?" + Math.random());
}
common.controlIn = function(){
	$(".header-my-control-list").show();
}
common.controlOut = function(){
	$(".header-my-control-list").hide();
}


$(document).ready(function() {
	$("button[name=form-submit]").click(common.submit);
	$("img[name=captcha_img]").click(common.captcha);
	
	$(".header-my-control").mouseover(common.controlIn);
	$(".header-my-control").mouseout(common.controlOut);
});