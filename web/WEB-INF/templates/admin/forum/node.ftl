<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
	<#if nodes?exists>
	<#list nodes as node>
		<button type="button" class="btn btn-default btn-xs">${node.name}</button>
	</#list>
	</#if>
</div>
<#include "/admin/footer.ftl">