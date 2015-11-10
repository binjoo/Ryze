<#include "/header.ftl">
<div class="cleft">
    <h1>${message?if_exists}</h1>
	<#if data?exists>
	<#list data as row>
		<div>${row.id} | ${row.no}</div>
	</#list>
	</#if>
</div>
<div class="cright">
	user/index
</div>
<div class="clear"></div>
<#include "/footer.ftl">