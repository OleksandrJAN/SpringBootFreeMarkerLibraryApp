<#import "/parts/common.ftl" as c>
<#import "/parts/alerts.ftl" as alert>
<#import "logMacros.ftl" as l>

<@c.page>

<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <@alert.danger Session.SPRING_SECURITY_LAST_EXCEPTION.message />
</#if>

<#if registrationSuccessful??>
    <@alert.success "Registration successful" />
</#if>

<@l.login "/login" false />

</@c.page>