<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<#include "/parts/security.ftl">


<@c.page>

<!--User Name-->
<@ui.labelInputRow
    inputId             = "userNameInput"
    inputName           = "username"
    inputValue          = userProfile.username
    inputPlaceholder    = "Username"
    inputReadonly       = true
    labelText           = "User name:"
    labelMd             = "-md-2"
/>

<!--User Roles-->
<#if isAdmin && userProfile != currentUser>
    <hr/>
    <form action="/users/${userProfile.id}/roles" method="post">
        <@hidden.csrf />

        <div class="form-group row">
            <div class="col">
                <@ui.checkboxRow
                    collection      = roles
                    checkCollection = userProfile.roles
                />
            </div>
            <!--Save Button-->
            <div class="col-md-1">
                <button class="btn btn-primary float-right" type="submit">Save</button>
            </div>
        </div>
    </form>
    <hr/>
</#if>

<!--Review Link-->
<div class="form-group row mx-auto">
    <a class="btn btn-primary" role="button" href="${userProfile.id}/reviews">Reviews</a>
    <#if isAdmin && userProfile != currentUser>
        <div class="ml-auto">
            <@hidden.deleteForm
                action = "/users/${userProfile.id}"
            />
        </div>
    </#if>
</div>

</@c.page>