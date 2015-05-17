<div class="starter-template">
    <h3>${article.getTitle()}</h3>
    <p>Viewed: ${article.getViews()} times</p>
    <h4>${article.getAuthor()} - ${article.getCreatedAt()}</h4>
    
    <#if isAllowed??>
    	<h4>${article.getEditLink()} | ${article.getDeleteLink()}</h4>
    </#if>
    
    <h5>${article.getContent()}</h5>
    
    <#if hasNoComments??>
    	<hr> 
    	<p>${hasNoComments}</p>
	<#else>    
        <#list comments as comment>
        	<hr> 
            <h5>${comment.getCreatedAt()}</h5>
            <h5>${comment.getContent()}</h5>
			       
		</#list>
	</#if>
    
    <hr>
    
    <form class="form-horizontal" role="form" id='comment-create-form' method='POST' action="/comment/create/">
    	 <input type='hidden' name='article-id' value="${article.getId()}" />
    	<label for="content">Add new comment</label>
    	<textarea class="form-control" name='comment-content' id="content" rows='4' cols='50' 
    		form='comment-create-form' placeholder="Enter comment content">
    	</textarea class="form-control">
    </form>
    
    <input type='submit' class="btn btn-primary" form='comment-create-form' />
    
       
</div class="starter-template">
