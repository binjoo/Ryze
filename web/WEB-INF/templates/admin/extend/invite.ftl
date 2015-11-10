<#include "/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
	<ul class="nav nav-pills" role="tablist">
		<li role="presentation" class="active"><a href="#list" role="tab" data-toggle="tab">管理</a></li>
		<li role="presentation"><a href="#create" role="tab" data-toggle="tab">生成</a></li>
	</ul>
	<div class="tab-content pt5">
		<div role="tabpanel" class="tab-pane active" id="list">
		<div class="panel panel-default">
			<div class="panel-body">
			<form class="form-inline" action="/admin/user/manage" method="POST" role="form">
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
				<#if invites?exists>
				<#list invites as row>
			        <tr>
				        <td></td>
				        <td>${row.no}</td>
				        <td>${row.is_valid?string("有效", "无效")}</td>
				        <td><a href="#">编辑</></td>
			        </tr>
				</#list>
				</#if>
		      </tbody>
			</table>
			<#import "/ftl/page.ftl" as p>
			<@p.box page=page size=size count=count url="/admin/user/manage/?"/>
		</div>
		</div>
		<div role="tabpanel" class="tab-pane" id="create">
    		<form action="/admin/extend/invite" method="POST">
		    <div class="panel panel-default">
		        <div class="panel-body">
		            <div class="form-group">
		                <label for="site_title">数量</label>
		                <input type="text" class="form-control" id="num" name="site_title" value="1" />
		            </div>
		            <div class="form-group">
		                <label for="site_description">有效期（天）</label>
		                <input type="text" class="form-control" id="site_description" name="site_description" value="7" />
		            </div>
		            <div class="form-group">
		                <label for="site_email">奖励积分</label>
		                <input type="text" class="form-control" id="site_email" name="site_email" value="0" />
		            </div>
		        </div>
		    </div>
		    <div class="form-group">
		        <button type="submit" class="btn btn-primary">提交</button>
		    </div>
		   	</form>
		</div>
	</div>
</div>
<#include "/footer.ftl">