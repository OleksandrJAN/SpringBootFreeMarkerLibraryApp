<#assign
    known = Session.SPRING_SECURITY_CONTEXT??
>

<#if known>
    <#assign
        currentUser = Session.SPRING_SECURITY_CONTEXT.authentication.principal
        isAdmin = currentUser.isAdmin()
    >
</#if>