<#include "/header.ftl">
<div class="content-bookmark">
    <div class="row">
        <div class="col-md-3">
        <#include "/bookmark/menu.ftl">
        </div>
        <div class="col-md-9">
            <#include "/breadcrumb.ftl">
            <form action="/bookmark/add" method="POST">
                <div class="form-group">
                    <label for="form_url">网址：</label>
                    <input name="url" type="text" class="form-control" id="form_url" placeholder="http://" />
                </div>
                <div class="form-group">
                    <label for="form_title">标题：</label>
                    <input name="title" type="text" class="form-control" id="form_title" />
                </div>
                <div class="form-group">
                    <label for="form_title">标签：</label>
                    <input name="form_tags" type="text" class="form-control" id="form_tags" />
                </div>
                <div class="form-group">
                    <label for="form_title">描述：</label>
                    <textarea name="description" class="form-control" rows="3" id="form_description"></textarea>
                </div>
                <div class="form-group">
                    <button name="form-submit" data-loading-text="后台操作中..." type="button" class="btn btn-primary">添加书签</button>
                </div>
            </form>
        </div>
    </div>
</div>
<#include "/footer.ftl">