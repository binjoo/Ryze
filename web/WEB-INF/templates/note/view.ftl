<#include "/header.ftl">
<div class="content-note">
    <div class="row">
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
			<#if message??>
				<div class="alert alert-warning" role="alert">${message}</div>
			</#if>
			<#if note?exists>
				<div class="panel panel-default">
					<div class="panel-body">
					    ${note.content?if_exists}
					</div>
				</div>
				<a class="btn btn-default" href="/note/edit/${note.id?if_exists}">编辑</a>
				<button name="note_delete" type="button" class="btn btn-danger" onclick="if(confirm('确认彻底删除标题为 ${note.title?if_exists} 的文件？')) { location.href = '/note/delete/${note.id?if_exists}'; }">删除</button>
			</#if>
        </div>
        <div class="col-md-3">
			<div id="note_view_sidebar" class="tl">
			    <div class="panel panel-default">
			    	<div class="panel-body note_info">
						<#if note?exists>
							<table class="table table-condensed">
								<tr>
									<td style="width: 60px">记事状态</td>
									<td>${(note.status==0)?string("私密", "公开")}</td>
								</tr>
								<tr>
									<td>创建时间</td>
									<td>${friendly(note.created)}</td>
								</tr>
								<tr>
									<td>最后修改</td>
									<td>${friendly(note.updated)}</td>
								</tr>
								<tr>
									<td>修改次数</td>
									<td>${note.modifyed?if_exists}</td>
								</tr>
								<tr>
									<td>标记语法</td>
									<td>${(note.syntax==0)?string("Plain Text","Markdown")}</td>
								</tr>
								<tr>
									<td>内容大小</td>
									<td>${note.size?if_exists} 字节</td>
								</tr>
							</table>
						</#if>
			    	</div>
			    </div>
			</div>
        </div>
    </div>
</div>
<div class="clear"></div>
<#include "/footer.ftl">