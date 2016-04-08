<#include "/admin/header.ftl">
<div class="panel panel-default">
	<div class="panel-body">
	<form class="form-inline" action="/admin?action=user&method=manage" method="POST" role="form">
		<div class="form-group">
			<div class="input-group">
	  			<span class="input-group-addon">昵称</span>
				<input type="text" class="form-control" name="nickname" />
		    </div>
		</div>
		<button type="submit" class="btn btn-default">
			<span class="glyphicon glyphicon-search"></span> 查询
		</button>
	</form>
	</div>
	<table class="table table-striped table-hover">
		<thead>
	        <tr>
		        <th class="w35 tc">#</th>
		        <th class="w160">昵称</th>
		        <th class="w60">积分</th>
		        <th></th>
	        </tr>
     	</thead>
     	<tbody>
		<#if users?exists>
		<#list users as user>
	        <tr>
		        <td></td>
		        <td>${user.nickname}</td>
		        <td>${user.credits}</td>
		        <td><a href="#">编辑</></td>
	        </tr>
		</#list>
		</#if>
      </tbody>
	</table>
	<#import "/ftl/page.ftl" as p>
	<@p.box page=page size=size count=count url="/admin?action=user&method=manage&page={?}"/>
</div>
<#include "/admin/footer.ftl">