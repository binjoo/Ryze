<#include "/header.ftl">
<div class="cleft">
	<#if bookmarks??>
	<#list bookmarks as row>
		<div>${row.title}</div>
	</#list>
	</#if>
</div>
<div class="cright">
	2
</div>
<div class="clear"></div>
<#include "/footer.ftl">