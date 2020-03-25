<#import "/parts/common.ftl" as c>
<#import "bookForms.ftl" as bf>

<#include "/parts/security.ftl">

<@c.page>


<@bf.bookForm
    admin = isAdmin
/>


</@c.page>