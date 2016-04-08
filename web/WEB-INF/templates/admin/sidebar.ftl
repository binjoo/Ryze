<div class="col-md-2 sidebar">
	<#if admin_menu?exists>
	<#list admin_menu as nav>
		<#if nav??>
			<ul id="nav-menu-${nav.key}" class="nav nav-sidebar" ${(nav_index == 0)?string(" class=\"display:\"", "style=\"display: none\"")}>
				<#if nav.child?exists>
					<#list nav.child as row>
				        <li${(row_index == 0)?string(" class=\"active\"", "")}>
				        	<a href="/admin?action=${nav.action}&method=${row.action}" hidefocus="true" target="main">${row.name}</a>
				        </li>
					</#list>
				</#if>
			</ul>
		</#if>
	</#list>
	</#if>
</div>