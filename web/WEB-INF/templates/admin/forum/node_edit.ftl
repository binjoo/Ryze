<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
	<form action="/admin/user/manage" method="POST">
		<div class="panel panel-default">
			<div class="panel-heading">编辑</div>
			<div class="panel-body">
		        <div class="form-group">
		            <label for="site_title">节点名称</label>
		            <input type="text" class="form-control" name="name" value="${site_title?if_exists}" />
		        </div>
		        <div class="form-group">
		            <label for="site_description">节点别名</label>
		            <input type="text" class="form-control" name="no" value="${site_description?if_exists}" />
		        </div>
		        <div class="form-group">
		            <label for="site_url">节点描述</label>
		            <input type="text" class="form-control" name="description" value="${site_url?if_exists}" />
		        </div>
			</div>
		</div>
	</form>
</div>
<#include "/admin/footer.ftl">