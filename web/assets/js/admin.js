var pageObj = window.NameSpace || {};
/**
 * 后台首页
 */
pageObj.index = new function() {
	this.init = function(){
		$("ul.navbar-menu a").click(this.header_click);
		$("ul.nav-sidebar a").click(this.sidebar_click);
	},
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
	};
};

/**
 * 论坛-节点管理
 */
pageObj.node = new function(){
	this.init = function(){
		$("#node_save").click(this.btn_save);
		$("#node_edit").click(this.btn_edit);
	},
	this.btn_search = function(){
		
	},
	this.btn_edit = function(){
		$('#nodeEditWindow').modal({backdrop: 'static'});
	},
	this.btn_save = function(){
		var con = false;
		$(".modal-body input").parent().removeClass("has-error");
		if($("input[name=name]").val() == ""){
			con = true;
			$("input[name=name]").parent().addClass("has-error");
		}
		if($("input[name=no]").val() == ""){
			con = true;
			$("input[name=no]").parent().addClass("has-error");
		}
		if(con) return;
		$("form[name=node_edit]").submit();
	},
	this.a_node_edit = function(id){
		var param = {
			action: 'forum',
			method: 'nodeEdit',
			id: id
		}
		$.ajax({url: "/admin", data: param, success: function(data){
			if(data.status == 0){
				$("#nodeEditWindow input[name=id]").val(data.node.id);
				$("#nodeEditWindow select[name=parent_id]").val(data.node.parent_id);
				$("#nodeEditWindow input[name=no]").val(data.node.no);
				$("#nodeEditWindow input[name=name]").val(data.node.name);
				$("#nodeEditWindow input[name=description]").val(data.node.description);
				$('#nodeEditWindow').modal({backdrop: 'static'});
			}
		}});
	}
}

$(document).ready(function() {
	for (obj in pageObj) {
    	pageObj[obj].init();
	}
});