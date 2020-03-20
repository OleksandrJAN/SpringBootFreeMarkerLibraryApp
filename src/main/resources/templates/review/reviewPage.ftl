<#import "/parts/common.ftl" as c>
<#import "reviewMacros.ftl" as r>

<#include "/parts/security.ftl">

<@c.page>

<!--Review Edit Page-->
<@r.reviewPage "/users/${currentUser.id}/reviews/${reviewId}" "Edit"/>


</@c.page>