<#include "/header.ftl">
<div class="content-note">
    <div class="row">
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
			<form action="/note/folder/create" method="post" class="form-horizontal">
				<div class="form-group">
					<label for="note_title" class="col-sm-2 control-label">文件夹标题</label>
					<div class="col-sm-9">
						<input id="note_title" name="title" type="text" class="form-control" />
					</div>
				</div>
				<div class="form-group">
					<label for="note_content" class="col-sm-2 control-label">描述</label>
					<div class="col-sm-9">
						<textarea id="note_content" name="content" class="form-control" rows="6"></textarea>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-9">
						<button type="submit" class="btn btn-primary">创建文件夹</button>
					</div>
				</div>
			</form>
        </div>
        <div class="col-md-3">
        <#include "/note/menu.ftl">
        </div>
    </div>
</div>
<div class="clear"></div>
<#include "/footer.ftl">