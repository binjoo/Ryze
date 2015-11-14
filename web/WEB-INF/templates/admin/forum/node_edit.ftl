<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
<div class="panel panel-default">
	<div class="panel-body">
	<form class="form-inline" action="/admin/user/manage" method="POST" role="form">
		<div class="form-group">
			<div class="input-group">
	  			<span class="input-group-addon">节点名称</span>
				<input type="text" class="form-control" name="nickname" />
		    </div>
		</div>
		<button type="submit" class="btn btn-default">
			<span class="glyphicon glyphicon-search"></span> 查询
		</button>
		<a href="/admin?action=forum&method=editNode" class="btn btn-primary">
			<span class="glyphicon glyphicon-pencil"></span> 新增
		</a>
	</form>
	</div>
	<ul class="list-group">
		<li class="list-group-item">
			<#if nodes?exists>
			<#list nodes as node>
				<button type="button" class="btn btn-default btn-xs">${node.name}</button>
			</#list>
			</#if>
		</li>
	</ul>
</div>
</div>
<#include "/admin/footer.ftl">