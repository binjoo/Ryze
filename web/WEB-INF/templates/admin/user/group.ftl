<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
<div class="panel panel-default">
	<div class="panel-heading">管理组</div>
	<div class="panel-body">
	<table class="table table-striped table-hover">
		<thead>
	        <tr>
		        <th class="w35 tc">#</th>
		        <th class="w160">组名称</th>
		        <th class="w160">组类型</th>
		        <th></th>
	        </tr>
     	</thead>
     	<tbody>
		<#if groups?exists>
		<#list groups as group>
	        <#if !group.manage>
    			<#break>
	        </#if>
	        <tr>
		        <td></td>
		        <td>${group.name}</td>
		        <td>${group.manage?string("管理组", "用户组")}</td>
		        <td><a href="/admin/user/group/config/${group.id}">设置</a></td>
	        </tr>
		</#list>
		</#if>
   		</tbody>
	</table>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading">用户组</div>
	<div class="panel-body">
	<table class="table table-striped table-hover">
		<thead>
	        <tr>
		        <th class="w35 tc">#</th>
		        <th class="w160">组名称</th>
		        <th class="w160">组类型</th>
		        <th></th>
	        </tr>
     	</thead>
     	<tbody>
		<#if groups?exists>
		<#list groups as group>
	        <#if !group.manage>
	        <tr>
		        <td></td>
		        <td>${group.name}</td>
		        <td>${group.manage?string("管理组", "用户组")}</td>
		        <td><a href="/admin/user/group/config/${group.id}">设置</a></td>
	        </tr>
	        </#if>
		</#list>
		</#if>
   		</tbody>
	</table>
	</div>
</div>
</div>
<#include "/admin/footer.ftl">