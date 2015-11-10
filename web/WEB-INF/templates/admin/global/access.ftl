<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
    <form action="/admin/global/access" method="POST">
    <div class="panel panel-default">
        <div class="panel-heading">注册控制</div>
        <div class="panel-body">
            <div class="form-group">
                <label for="site_register">是否开启注册</label>
                <div class="checkbox">
                    <label class="radio-inline">
                        <input type="radio" name="site_register" value="1" <#if site_register??>${(site_register=="1")?string("checked=\"checked\"","")}</#if> /> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="site_register" value="0" <#if site_register??>${(site_register=="0")?string("checked=\"checked\"","")}</#if> /> 否
                    </label>
                </div>
            </div>
            <div class="form-group">
                <label for="site_invite">是否需要邀请码</label>
                <div class="checkbox">
                    <label class="radio-inline">
                        <input type="radio" name="site_invite" value="1" <#if site_invite??>${(site_invite=="1")?string("checked=\"checked\"","")}</#if> /> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="site_invite" value="0" <#if site_invite??>${(site_invite=="0")?string("checked=\"checked\"","")}</#if> /> 否
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