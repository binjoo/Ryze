<div class="col-md-2 sidebar">
    <#if sidebar_menu?exists>
    <#list sidebar_menu as menu>
        <ul class="nav nav-sidebar">
        <#if menu?exists>
        <#list menu as row>
            <li${(sidebar_active==row.key)?string(" class=\"active\"", "")}><a href="/admin${row.url}">${row.name}</a></li>
        </#list>
        </#if>
        </ul>
    </#list>
    </#if>
</div>