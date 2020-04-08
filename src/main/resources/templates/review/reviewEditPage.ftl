<#import "/parts/common.ftl" as c>
<#import "reviewForm.ftl" as reviewForm>

<#include "/parts/security.ftl">

<@c.page>

<!--Review Edit Page-->
<@reviewForm.reviewPage
    review              = review
    action              = reviewAction
    putAction           = true
/>

</@c.page>