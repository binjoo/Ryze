<#include "/header.ftl">
<div class="content-note">
    <div class="row">
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
    		<table class="table table-striped">
    		<#if parent_id?exists>
	    		<tr>
	    			<td colspan="2">
    					<a href="/note">
    						<span class="glyphicon glyphicon-folder-close"></span>
							返回上一层...
    					</a>
	    			</td>
	    		</tr>
	    	</#if>
    		<#if list?exists && (list?size>0)>
    		<#list list as row>
	    		<tr>
	    			<td>
	    				<#if row.type = 1>
	    					<a href="/note/folder/${row.id}">
	    						<span class="glyphicon glyphicon-folder-close"></span>
								${row.title}
	    					</a>
	    				<#elseif row.type = 0>
	    					<a href="/note/${row.id}">
	    						<span class="glyphicon glyphicon-file"></span>
								${row.title}
	    					</a>
	    				</#if>
	    			</td>
	    			<td style="width:120px;text-align:right">
	    				<#if row.type = 0>${friendly(row.created)}</#if>
	    			</td>
	    		</tr>
    		</#list>
    		<#else>
	    		<tr>
	    			<td colspan="2" class="tc">
			    		暂时还没有记事，点击<a href="/note/add<#if (parent_id)??>${"?folder_id=${parent_id}"}</#if>">这里</a>新增一条记事
	    			</td>
	    		</tr>
    		</#if>
			</table>
        </div>
        <div class="col-md-3">
        <#include "/note/menu.ftl">
        </div>
    </div>
</div>
<div class="clear"></div>
<#include "/footer.ftl">