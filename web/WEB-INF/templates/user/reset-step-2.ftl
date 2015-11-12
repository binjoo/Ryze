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
                        <input type="hidden" name="step" value="second" />
                        <input type="hidden" name="email" value="${email?if_exists}" />
                        <input type="hidden" name="token" value="${token?if_exists}" />
                        <div class="form-group">
                            <label for="form_password">新的登录密码：</label>
                            <input name="password" type="text" class="form-control" id="form_password" placeholder="********" />
                        </div>
                        <div class="form-group">
                            <label for="form_confirm_password">再次输入密码：</label>
                            <input name="confirm_password" type="text" class="form-control" id="form_confirm_password" placeholder="********" />
                        </div>
                        <div class="form-group">
                            <label for="form_captcha">验证码：</label>
                            <div class="input-group">
                                <span class="input-group-addon"><img class="captcha" src="/action/captcha" /></span>
                                <input name="captcha" type="text" class="form-control" placeholder="验证码">
                            </div>
                        </div>
                        <div class="form-group">
                            <button name="form-submit" data-loading-text="确认中..." type="button" class="btn btn-primary">确认重置密码</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/assets/js/jquery.md5.js"></script>
<#include "/footer.ftl">