<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
<form class="form-inline mb20" action="/admin/user/manage" method="POST" role="form">
	<div class="form-group">
		<div class="input-group">
  			<span class="input-group-addon">节点名称</span>
			<input type="text" class="form-control" name="nickname" />
	    </div>
	</div>
	<button type="submit" class="btn btn-default">
		<span class="glyphicon glyphicon-search"></span> 查询
	</button>
	<a href="/admin?action=forum&method=node" class="btn btn-primary">
		<span class="glyphicon glyphicon-pencil"></span> 新增
	</a>
</form>
<div class="panel panel-default">
	<div class="panel-heading">城市</div>
	<div class="panel-body">
		<#if nodes?exists>
		<#list nodes as node>
			<a href="/admin?action=forum&method=editNode" class="btn btn-default btn-xs">${node.name}</a>
		</#list>
		</#if>
	</div>
</div>
</div>
<#include "/admin/footer.ftl">