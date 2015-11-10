<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
	<table class="table table-striped table-hover">
		<thead>
	        <tr>
		        <th class="w35 tc">#</th>
		        <th class="w100">节点</th>
		        <th>标题</th>
		        <th class="w60">回复</th>
		        <th class="w60">操作</th>
	        </tr>
     	</thead>
     	<tbody>
		<#if topics?exists>
		<#list topics as topic>
	        <tr>
		        <td></td>
		        <td>${topic.name}</td>
		        <td>${topic.title}</td>
		        <td>10</td>
		        <td><a href="#">编辑</></td>
	        </tr>
		</#list>
		</#if>
      </tbody>
	</table>
	<#import "/ftl/page.ftl" as p>
	<@p.box page=page size=size count=count url="/admin/user/manage/?"/>
</div>
<#include "/admin/footer.ftl">