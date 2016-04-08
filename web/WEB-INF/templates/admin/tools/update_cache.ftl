<#include "/admin/header.ftl">
<form action="/admin?action=tools&method=update_cache" method="POST">
<div class="panel panel-default">
    <div class="panel-heading">更新缓存</div>
    <div class="panel-body">
		<div class="checkbox">
		  <label><input name="menu" type="checkbox" /> 后台菜单缓存</label>
		</div>
		<div class="checkbox">
		  <label><input name="cache" type="checkbox" /> 全站数据缓存</label>
		</div>
    </div>
</div>
<div class="form-group">
    <button type="submit" class="btn btn-primary">提交</button>
</div>
</form>
<#include "/admin/footer.ftl">