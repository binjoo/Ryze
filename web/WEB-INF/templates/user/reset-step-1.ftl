<#include "/header.ftl">
<div class="content-user">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">找回密码</h3>
                </div>
                <div class="panel-body">
                    <form action="/user/reset" method="POST">
                        <input type="hidden" name="step" value="first" />
                        <div class="form-group">
                            <label for="form_email">邮箱：</label>
                            <input name="email" value="icesword28@qq.com" type="email" class="form-control" id="form_email" placeholder="yourname@domain.com" />
                        </div>
                        <div class="form-group">
                            <label for="form_captcha">验证码：</label>
                            <div class="input-group">
                                <span class="input-group-addon"><img class="captcha" src="/action/captcha" /></span>
                                <input name="captcha" type="text" class="form-control" placeholder="验证码">
                            </div>
                        </div>
                        <div class="form-group">
                            <button name="form-submit" data-loading-text="发送邮件中..." type="button" class="btn btn-primary">发送找回密码邮件</button>
                            <a href="/user/login" class="btn btn-link">返回登陆</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/assets/js/jquery.md5.js"></script>
<#include "/footer.ftl">