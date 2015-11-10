<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
    <form action="/admin/global/base" method="POST">
    <div class="panel panel-default">
        <div class="panel-heading">站点信息</div>
        <div class="panel-body">
            <div class="form-group">
                <label for="site_title">站点名称</label>
                <input type="text" class="form-control" name="site_title" placeholder="Yachal Framework" value="${site_title?if_exists}" />
            </div>
            <div class="form-group">
                <label for="site_description">站点描述</label>
                <input type="text" class="form-control" name="site_description" placeholder="轻击键盘，静候回音。"value="${site_description?if_exists}" >
            </div>
            <div class="form-group">
                <label for="site_url">站点地址</label>
                <input type="text" class="form-control" name="site_url" placeholder="http://www.domain.com"value="${site_url?if_exists}" >
            </div>
            <div class="form-group">
                <label for="site_email">管理员邮箱</label>
                <input type="text" class="form-control" name="site_email" placeholder="yourname@domain.com"value="${site_email?if_exists}" >
            </div>
        </div>
    </div>
    <div class="panel panel-warning">
        <div class="panel-heading">关闭站点</div>
        <div class="panel-body">
            <div class="form-group">
                <label for="site_title">关闭站点</label>
                <div class="checkbox">
                    <label class="radio-inline">
                        <input type="radio" name="site_switch" value="1" <#if site_switch??>${(site_switch=="1")?string("checked=\"checked\"","")}</#if> /> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="site_switch" value="0" <#if site_switch??>${(site_switch=="0")?string("checked=\"checked\"","")}</#if> /> 否
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="form-group">
        <button type="submit" class="btn btn-primary">提交</button>
    </div>
    </form>
</div>
<#include "/admin/footer.ftl">