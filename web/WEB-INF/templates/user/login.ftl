<#include "/header.ftl">
<div class="content-user">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">登录</h3>
                </div>
                <div class="panel-body">
                    <form action="/user/login" method="POST">
                        <input type="hidden" name="callback" value="${callback?if_exists}"/>
                        <div class="form-group">
                            <label for="form_email">邮箱：</label>
                            <input name="email" value="icesword28@qq.com" type="email" class="form-control" id="form_email" placeholder="yourname@domain.com" />
                        </div>
                        <div class="form-group">
                            <label for="form_password">密码：</label>
                            <input name="password" value="123456" type="password" class="form-control" id="form_password" placeholder="********" />
                        </div>
                        <#if (siteLoginCaptcha?exists && siteLoginCaptcha = "1")>
                        <div class="form-group">
                            <label for="form_captcha">验证码：</label>
                            <div class="input-group">
                                <span class="input-group-addon"><img name="captcha_img" class="captcha" src="/action/captcha" /></span>
                                <input name="captcha" type="text" class="form-control" placeholder="验证码">
                            </div>
                        </div>
                        </#if>
                        <div class="form-group">
                            <button name="form-submit" data-loading-text="登录中..." type="button" class="btn btn-primary">登录</button>
                            <a class="btn btn-default" href="/user/register" role="button">没有帐号？立即注册</a>
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