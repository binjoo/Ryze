<#include "/header.ftl">
<div class="content-note">
    <div class="row">
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
			<div class="panel panel-default">
				<div class="panel-body">
				    ${note.content?if_exists}
				</div>
			</div>
			<a class="btn btn-default" href="/note/edit/${note.id?if_exists}">编辑</a>
			<button type="button" class="btn btn-danger">删除</button>
        </div>
        <div class="col-md-3">
			<div id="admin-menu" class="menu-left tl">
			    <div class="panel panel-default">
			    	<div class="panel-heading">
				    	<h3 class="panel-title">记事信息</h3>
					</div>
			    	<div class="panel-body">
						<table class="table table-condensed">
							<tr>
								<td>记事状态</td>
								<td></td>
							</tr>
							<tr>
								<td>创建时间</td>
								<td></td>
							</tr>
							<tr>
								<td>最后修改</td>
								<td></td>
							</tr>
							<tr>
								<td>修改次数</td>
								<td>${note.modifyed?if_exists}</td>
							</tr>
							<tr>
								<td>标记语法</td>
								<td>${note.syntax?if_exists}</td>
							</tr>
							<tr>
								<td>文件长度</td>
								<td></td>
							</tr>
						</table>
			    	</div>
			    </div>
			</div>
        </div>
    </div>
</div>
<div class="clear"></div>
<#include "/footer.ftl">