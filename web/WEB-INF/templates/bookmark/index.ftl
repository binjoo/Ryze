<#include "/header.ftl">
<div class="content-bookmark">
    <div class="row">
        <div class="col-md-3">
        <#include "/bookmark/menu.ftl">
        </div>
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
    		<#if list?exists>
    		<#list list as row>
    			<div class="">
    				<div class="bookmark-title">
    					<a href="#">${row.title}</a>
    				</div>
    				<div class="bookmark-url">
    					${row.long_url}
    				</div>
    				<div class="bookmark-tags">
    					<#if row.tags?exists>
    					[
    					<#list row.tags as tag>
    					<a href="/bookmark/tag/${tag.name}">${tag.name}</a>
    					</#list>
    					]
    					</#if>
    				</div>
    			</div>
    		</#list>
    		</#if>
        </div>
    </div>
</div>
<div class="clear"></div>
<#include "/footer.ftl">