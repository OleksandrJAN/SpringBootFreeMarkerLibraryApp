<#import "/parts/common.ftl" as c>
<#import "/parts/alerts.ftl" as alert>
<#import "logMacros.ftl" as l>

<@c.page>

<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <@alert.danger
        message = Session.SPRING_SECURITY_LAST_EXCEPTION.message
    />
</#if>

<#if registrationSuccessful??>
    <@alert.success
        message = "Registration successful"
    />
</#if>

<@l.login
    path = "/login"
    isRegisterForm = false
/>

</@c.page>