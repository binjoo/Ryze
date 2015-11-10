<#macro box page size count url>
	<#assign pageCount = (count / size)?int>
	<#assign lr = 3>
	<#if (count % size > 0)>
    	<#assign pageCount = pageCount + 1>
	</#if>
    <#assign start = page - lr>
    <#if (page - lr < 1)>
    	<#assign start = 1>
    </#if>
    <#assign end = start + lr * 2>
    <#if (end > pageCount)>
    	<#assign end = pageCount>
    	<#assign start = end - lr * 2>
    	<#if (start < 1)>
    		<#assign start = 1>
    	</#if>
    </#if>
	<nav class="tc">
	  <ul class="pagination pagination-sm">
	    <li><a href="${url?replace("?", 1)}">&laquo;</a></li>
	    <#list start..end as i>
		    <li${(i == page)?string(" class=\"active\"", "")}><a href="${url?replace("?", i)}">${i}</a></li>
	    </#list>
	    <li><a href="${url?replace("?", pageCount)}">&raquo;</a></li>
	  </ul>
	</nav>
</#macro>
<#macro classic date>
<p>classic ${date} Julia Smith. All rights reserved.</p>
</#macro>