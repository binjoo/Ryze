<div class="col-md-2 sidebar">
    <ul class="nav nav-sidebar">
    <#if sidebar_menu?exists>
    <#list sidebar_menu as row>
    	<#if row??>
	        <li${(sidebar_active==row.key)?string(" class=\"active\"", "")}><a href="/admin${row.url}">${row.name}</a></li>
    	<#else>
    		</ul>
    		<ul class="nav nav-sidebar">
    	</#if>
    </#list>
    </#if>
    </ul>
</div>