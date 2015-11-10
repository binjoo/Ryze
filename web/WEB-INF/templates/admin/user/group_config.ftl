<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
<form role="form">
<div class="panel panel-default">
	<div class="panel-heading fwb">基础设置</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="name">组名称</label>
			<input type="text" class="form-control" name="name" value="${group.name}"/>
		</div>
		<div class="form-group">
			<label for="name">组类型</label>
			<select class="form-control">
				<option value="1"${group.manage?string(" selected", "")}>管理组</option>
				<option value="0"${group.manage?string("", " selected")}>用户组</option>
			</select>
		</div>
		<div class="form-group">
			<label for="name">访问级别</label>
			<div class="input-group">
				<div class="radio">
					<label>
						<input type="radio" name="allow_visit" value="1"${(group.allow_visit==1)?string(" checked", "")}/> 禁止访问
						<small>（禁止用户浏览本站任何页面）</small>
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="allow_visit" value="2"${(group.allow_visit==2)?string(" checked", "")}/> 正常访问
						<small>（当站点开放的时候，可以正常浏览页面）</small>
					</label>
				</div>
				<div class="radio">
					<label>
						<input type="radio" name="allow_visit" value="3"${(group.allow_visit==3)?string(" checked", "")}/> 超级访问
						<small>（即便站点关闭也可以访问，一般用于管理员或站内测试组）</small>
					</label>
				</div>
			</div>
		</div>
		<div class="form-group">
			<label for="name">允许发送短消息</label>
			<div class="input-group">
				<label class="radio-inline">
					<input type="radio" name="allow_message" value="1"${group.allow_message?string(" checked", "")}/> 是
				</label>
				<label class="radio-inline">
					<input type="radio" name="allow_message" value="0"${group.allow_message?string("", " checked")}/> 否
				</label>
    		</div>
		</div>
	</div>
</div>
<div class="panel panel-default">
	<div class="panel-heading fwb">用户权限</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Email address</label>
			<input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email" />
		</div>
	</div>
</div>
<#if group.manage>
<div class="panel panel-default">
	<div class="panel-heading fwb">管理权限</div>
	<div class="panel-body">
		<div class="form-group">
			<label for="exampleInputEmail1">Email address</label>
			<input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
		</div>
	</div>
</div>
</#if>
<button type="submit" class="btn btn-default">Confirm identity</button>
</form>
</div>
<#include "/admin/footer.ftl">