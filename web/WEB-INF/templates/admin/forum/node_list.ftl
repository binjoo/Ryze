<#include "/admin/header.ftl">
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
	<button id="node_edit" type="button" class="btn btn-primary">
  		<span class="glyphicon glyphicon-pencil"></span> 新增
	</button>
</form>
<#if parents?exists>
<#list parents as parent>
	<div class="panel panel-default">
		<div class="panel-heading">${parent.name}</div>
		<div class="panel-body">
			<#if nodes?exists>
			<#list nodes as node>
				<#if node.parent_id == parent.id>
					<a href="javascript:pageObj.node.a_node_edit('${node.id}')" class="btn btn-default btn-xs">${node.name}</a>
				</#if>
			</#list>
			</#if>
		</div>
	</div>
</#list>
</#if>
<!-- 编辑窗口 -->
<#include "/admin/forum/node_edit.ftl">
<#include "/admin/footer.ftl">