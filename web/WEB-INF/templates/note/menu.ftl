<div class="tl">
    <div class="panel" style="background-color: transparent;">
	  	<a class="btn btn-default btn-block" href="/note/add<#if (parent_id)??>${"?folder_id=${parent_id}"}</#if>" role="button">新建记事</a>
		<#if !parent_id?exists>
	  		<a class="btn btn-default btn-block" href="/note/add?type=folder" role="button">创建文件夹</a>
    	</#if>
    </div>
    <div class="panel panel-info">
      <div class="panel-body">
        <p>...</p>
      </div>
    </div>
</div>