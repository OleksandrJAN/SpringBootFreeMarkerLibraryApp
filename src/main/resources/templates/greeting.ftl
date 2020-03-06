<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>

<@c.page>
<h5>Hello, <#if currentUser??>${name}<#else>Guest</#if></h5>
</@c.page>