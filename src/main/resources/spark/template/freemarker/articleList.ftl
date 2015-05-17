<#if hasNoArticles??>
    <div class="starter-template">
        <h1>${hasNoArticles}</h1>
    </div class="starter-template">
<#else>
    <div class="starter-template">
        <p>Number of articles: ${articleCount}</p>
    
        <#list articles as article>
            <h3>${article.getTitle()}</h3>
            <h4>${article.getAuthor()} - ${article.getCreatedAt()}</h4>
            <h4>${article.getSummaryLink()}</h4>
        </#list>
    </div class="starter-template">
</#if>