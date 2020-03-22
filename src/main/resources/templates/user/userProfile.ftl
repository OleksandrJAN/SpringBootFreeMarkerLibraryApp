<#import "/parts/common.ftl" as c>
<#import "userProfileForms.ftl" as profile>

<#include "/parts/security.ftl">

<@c.page>

    <!--User Name-->
    <@profile.usernameForm
        userName = userProfile.username
    />

    <#if isAdmin>
        <!--User Roles-->
        <@profile.userRolesForm
            userId      = userProfile.id
            userRoles   = userProfile.roles
            allRoles    = roles
        />
    </#if>

    <!--Review Link-->
    <div class="form-group row mx-auto">
        <a href="${userProfile.id}/reviews">${userProfile.username} reviews</a>
    </div>

</@c.page>