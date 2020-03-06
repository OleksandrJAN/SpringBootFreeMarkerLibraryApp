<#import "parts/common.ftl" as c>
<#import "parts/log.ftl" as l>

<@c.page>
<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <div class="alert alert-danger" role="alert" >
        ${Session.SPRING_SECURITY_LAST_EXCEPTION.message}
    </div>
</#if>

<#if registrationSuccessful??>
    <div class="alert alert-success" role="alert">
        Registration successful
    </div>
</#if>

<@l.login "/login" false />
</@c.page>