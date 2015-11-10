<#if breadcrumb?exists>
<ol class="breadcrumb">
	<li><a href="/"><span class="glyphicon glyphicon-home"></span> 首页</a></li>
	<#list breadcrumb as row>
		<#if row_has_next>
			<li><a href="${row.url}">${row.name}</a></li>
		<#else>
			<li class="active">${row.name}</li>
		</#if>
	</#list>
</ol>
<#else>
<ol class="breadcrumb">
    <li><a href="/">首页</a></li>
</ol>
</#if>