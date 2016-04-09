<div class="modal fade" id="nodeEdit" tabindex="-1" role="dialog" aria-labelledby="nodeEditLabel">
	<form name="node_edit" action="/admin?action=forum&method=node_edit" method="POST">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="nodeEditLabel">节点信息</h4>
			</div>
			<div class="modal-body">
		        <div class="form-group">
		            <label for="site_title">上级节点</label>
					<select class="form-control" name="parent_id">
						<option value="">无</option>
						<#if parent?exists>
						<#list parent as row>
							<option value="${row.id}">${row.name}(${row.no})</option>
						</#list>
						</#if>
					</select>
		        </div>
		        <div class="form-group">
		            <label for="site_description">节点别名</label>
		            <input type="text" class="form-control" name="no" value="${site_description?if_exists}" />
		        </div>
		        <div class="form-group">
		            <label for="site_title">节点名称</label>
		            <input type="text" class="form-control" name="name" value="${site_title?if_exists}" />
		        </div>
		        <div class="form-group">
		            <label for="site_url">节点描述</label>
		            <input type="text" class="form-control" name="description" value="${site_url?if_exists}" />
		        </div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
				<button type="button" class="btn btn-primary" id="node_save">保存</button>
			</div>
		</div>
	</div>
	</form>
</div>