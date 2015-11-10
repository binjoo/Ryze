<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
    <form action="/admin/wechat/base" method="POST">
    <div class="panel panel-warning">
        <div class="panel-heading">关闭微信</div>
        <div class="panel-body">
            <div class="form-group">
                <label for="wechat_switch">关闭微信</label>
                <div class="checkbox">
                    <label class="radio-inline">
                        <input type="radio" name="wechat_switch" value="1" <#if wechat_switch??>${(wechat_switch=="1")?string("checked=\"checked\"","")}</#if> /> 是
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="wechat_switch" value="0" <#if wechat_switch??>${(wechat_switch=="0")?string("checked=\"checked\"","")}</#if> /> 否
                    </label>
                </div>
            </div>
        </div>
    </div>
    <div class="panel panel-default">
        <div class="panel-heading">接口配置</div>
        <div class="panel-body">
            <div class="form-group">
                <label for="wechat_url">URL</label>
				<p>email@example.com</p>
            </div>
            <div class="form-group">
                <label for="wechat_token">Token</label>
                <input type="text" class="form-control" name="wechat_token" placeholder="轻击键盘，静候回音。"value="${wechat_token?if_exists}" >
            </div>
            <div class="form-group">
                <label for="wechat_aeskey">EncodingAESKey</label>
				<div class="input-group">
                	<input type="text" class="form-control" name="wechat_aeskey" value="${wechat_aeskey?if_exists}" readonly/>
					<span class="input-group-btn">
						<button id="randomAESKey" class="btn btn-default" type="button">随机生成</button>
					</span>
				</div>
				<span class="help-block">消息加密密钥由43位字符组成，可随机修改，字符范围为A-Z，a-z，0-9。</span>
            </div>
            <div class="form-group">
                <label for="wechat_encoding">消息加解密方式</label>
                <div class="radio">
					<label>
						<input type="radio" name="wechat_encoding" value="1" <#if wechat_encoding??>${(wechat_encoding=="1")?string("checked=\"checked\"","")}</#if> /> 明文模式
						<span class="help-block">明文模式下，不使用消息体加解密功能，安全系数较低</span>
					</label>
				</div>
                <div class="radio">
					<label>
						<input type="radio" name="wechat_encoding" value="2" <#if wechat_encoding??>${(wechat_encoding=="2")?string("checked=\"checked\"","")}</#if> /> 兼容模式
						<span class="help-block">兼容模式下，明文、密文将共存，方便开发者调试和维护</span>
					</label>
				</div>
                <div class="radio">
					<label>
						<input type="radio" name="wechat_encoding" value="3" <#if wechat_encoding??>${(wechat_encoding=="3")?string("checked=\"checked\"","")}</#if> /> 安全模式（推荐）
						<span class="help-block">安全模式下，消息包为纯密文，需要开发者加密和解密，安全系数高</span>
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