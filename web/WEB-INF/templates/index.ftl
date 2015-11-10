<#include "/header.ftl">
<div class="index-forum-list">
    <div class="row">
        <div class="col-md-8">
        	<div class="main">
	        	<div class="topic-list-header">欢迎访问Yachal微社区</div>
	        	<div class="topic-list">
					<#if topics?exists>
					<#list topics as row>
					<div class="item">
						<div class="item-avatar pull-left">
							<a href="#"><img class="avatar avatar48" src="https://cdn.v2ex.co/avatar/1019/51fe/4093_large.png"/></a>
						</div>
						<div class="item-content pull-left">
							<a class="item-content-header" href="#">${row.title}</a>
							<p>
							<a class="btn btn-default btn-xs" href="#">${row.node_name}</a>
							&nbsp;•&nbsp;
							<a href="#">${row.nickname}</a>
							&nbsp;•&nbsp;
							<span>时间</span>
							&nbsp;•&nbsp;
							<span>最后回复来至 <a href="#">冰剑</a></span>
							</p>
						</div>
						<div class="item-count pull-right">
							<a href="#"><span class="badge">42</span></a>
						</div>
					</div>
					</#list>
					</#if>
				</div>
			</div>
		</div>
        <div class="col-md-4">
        	<div class="sidebar">
	        	哈哈
        	</div>
        </div>
	</div>
</div>
<#include "/footer.ftl">