<#include "/header.ftl">
<div class="content-user">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">注册</h3>
                </div>
                <div class="panel-body">
                    <form action="/user/register" method="POST">
                        <div class="form-group">
                            <label for="form_email">邮箱：</label>
                            <input name="email" value="${email?if_exists}" type="email" class="form-control" id="form_email" placeholder="yourname@domain.com" />
                        </div>
                        <div class="form-group">
                            <label for="form_nickname">昵称：</label>
                            <input name="nickname" type="text" class="form-control" id="form_nickname" placeholder="请填写2-16位的中文、字母、数字或者下划线作为昵称..." />
                        </div>
                        <div class="form-group">
                            <label for="form_password">输入密码：</label>
                            <input name="password" type="password" class="form-control" id="form_password" placeholder="********" />
                        </div>
                        <div class="form-group">
                            <label for="form_confirm_password">确认密码：</label>
                            <input name="confirm_password" type="password" class="form-control" id="form_confirm_password" placeholder="********" />
                        </div>
                        <#if (siteInvite?exists && siteInvite = "1")>
                        <div class="form-group">
                            <label for="form_invite">激活码：</label>
                            <input name="invite" type="text" class="form-control" id="form_invite" placeholder="如果没有激活码，请向管理员索取。" />
                        </div>
                        </#if>
                        <#if (siteRegCaptcha?exists && siteRegCaptcha = "1")>
                        <div class="form-group">
                            <label for="form_captcha">验证码：</label>
                            <div class="input-group">
                                <span class="input-group-addon"><img name="captcha_img" class="captcha" src="/action/captcha" /></span>
                                <input name="captcha" type="text" class="form-control" placeholder="验证码">
                            </div>
                        </div>
                        </#if>
                        <div class="form-group">
                            <button name="form-submit" data-loading-text="注册中..." type="button" class="btn btn-primary">注册</button>
                            <a class="btn btn-default" href="/user/login" role="button">已有账号，立即登录</a>
                            <a href="/user/reset" class="btn btn-link">忘记密码？</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/assets/js/jquery.md5.js"></script>
<#include "/footer.ftl">