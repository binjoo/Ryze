<#include "/header.ftl">
<div class="content-note">
    <div class="row">
        <div class="col-md-9">
    		<#include "/breadcrumb.ftl">
			<form action="/note/edit" method="post" class="form-horizontal">
				<#if message??>
					<div class="alert alert-warning" role="alert">${message}</div>
				</#if>
				<input name="id" type="hidden" value="${(note.id)?if_exists}" />
				<div class="form-group">
					<label for="note_title" class="col-sm-2 control-label">记事标题</label>
					<div class="col-sm-9">
						<input id="note_title" name="title" type="text" value="${(note.title)?if_exists}" class="form-control" placeholder="不填写则默认为新建时间" />
					</div>
				</div>
				<div class="form-group">
					<label for="note_content" class="col-sm-2 control-label">记事内容</label>
					<div class="col-sm-9">
						<textarea id="note_content" name="content" class="form-control" rows="6">${(note.content)?if_exists}</textarea>
					</div>
				</div>
				<div class="form-group">
					<label for="note_parent_id" class="col-sm-2 control-label">所在文件夹</label>
					<div class="col-sm-9">
						<select id="note_parent_id" name="parent_id" class="form-control">
							<option value="0">/</option>
							<#if folder?exists>
							<#list folder as row>
						        <option value="${row.id}" <#if (folder_id)??>${(folder_id==row.id)?string("selected=\"selected\"","")}</#if>>${row.title}</option>
							</#list>
							</#if>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="note_syntax" class="col-sm-2 control-label">标记语法</label>
					<div class="col-sm-9">
						<select id="note_syntax" name="syntax" class="form-control">
							<option value="0" <#if (note.syntax)??>${(note.syntax==0)?string("selected=\"selected\"","")}</#if>>Plain Text</option>
							<option value="1" <#if (note.syntax)??>${(note.syntax==1)?string("selected=\"selected\"","")}</#if>>Markdown</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-9">
						<label class="radio-inline">
							<input type="radio" id="note_status1" name="status" value="1" <#if (note.status)??>${(note.status==1)?string("checked","")}</#if>/> 公开
						</label>
						<label class="radio-inline">
							<input type="radio" id="note_status0" name="status" value="0" <#if (note.status)??>${(note.status==0)?string("checked","")}</#if>/> 私密
						</label>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-offset-2 col-sm-9">
						<button type="submit" class="btn btn-primary">保存笔记</button>
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