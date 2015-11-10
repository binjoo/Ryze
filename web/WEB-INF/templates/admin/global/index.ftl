<#include "/admin/header.ftl">
<div class="col-md-10 col-md-offset-2 main">
	<div class="jumbotron">
		<div class="container">
		  <h1>Hello, ${session.login.nickname?if_exists}!</h1>
		  <p>...</p>
		  <p><a class="btn btn-primary btn-lg" href="#" role="button">Learn more</a></p>
		</div>
	</div>
    <div class="panel panel-default">
        <div class="panel-heading">系统信息</div>
        <div class="panel-body">
            <table class="table mb0">
                <tr>
                  <td class="w160 tr">服务器名称及版本</td>
                  <td>${os_info?if_exists}</td>
                </tr>
                <tr>
                  <td class="w160 tr">JVM使用内存</td>
                  <td>
		            <div class="progress" style="margin-bottom: 0px">
					  <div class="progress-bar" role="progressbar" aria-valuenow="${jvm_info_free?if_exists}" aria-valuemin="0" aria-valuemax="${jvm_info_total?if_exists}" style="width: ${jvm_info_free / jvm_info_total * 100}%;">
					    ${jvm_info?if_exists}
					  </div>
					</div>
                  </td>
                </tr>
                <tr>
                  <td class="w160 tr">JAVA运行环境版本</td>
                  <td>${java_version?if_exists}</td>
                </tr>
                <tr>
                  <td class="w160 tr">JAVA安装路径</td>
                  <td>${java_home?if_exists}</td>
                </tr>
                <tr>
                  <td class="w160 tr">程序工作路径</td>
                  <td>${user_dir?if_exists}</td>
                </tr>
                <tr>
                  <td class="w160 tr">默认临时路径</td>
                  <td>${java_tmpdir?if_exists}</td>
                </tr>
            </table>
        </div>
    </div>
</div>
<#include "/admin/footer.ftl">