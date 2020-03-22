<#import "/parts/common.ftl" as c>
<#import "userProfileForms.ftl" as profile>

<#include "/parts/security.ftl">

<@c.page>

    <!--User Name-->
    <@profile.usernameForm userProfile.username />

    <#if isAdmin>
        <!--User Roles-->
        <@profile.userRolesForm userProfile.id userProfile.roles roles />
    </#if>

    <!--Review Link-->
    <div class="form-group row mx-auto">
        <a href="${userProfile.id}/reviews">${userProfile.username} reviews</a>
    </div>

</@c.page>