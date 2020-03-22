<#import "/parts/common.ftl" as c>
<#import "reviewMacros.ftl" as r>

<#include "/parts/security.ftl">

<@c.page>


<#if springMacroRequestContext.requestUri?contains("/books") >
    <#assign actionPath = "/books/${book.id}/reviews/${review.id}" >
<#else>
    <#assign actionPath = "/users/${review.author.id}/reviews/${review.id}" >
</#if>

<!--Review Edit Page-->
<@r.reviewPage
    action              = actionPath
    buttonText          = "Edit"
    reviewText          = displayedText
    reviewAssessment    = selectedAssessment
    putAction           = true
/>


</@c.page>