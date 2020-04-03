<#import "/parts/common.ftl" as c>
<#import "reviewMacros.ftl" as r>

<#include "/parts/security.ftl">

<@c.page>


<!--Review Edit Page-->
<@r.reviewPage
    action              = reviewAction
    reviewText          = review.text
    reviewAssessment    = review.assessment
    putAction           = true
/>


</@c.page>