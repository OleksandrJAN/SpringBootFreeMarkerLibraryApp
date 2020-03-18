<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

<@c.page>

    <!--User Name-->
    <div class="form-group row">
        <label class="col-md-2 col-form-label" for="userNameInput">User name:</label>
        <div class="col">
            <input readonly class="form-control" type="text" name="username"
                   value="${userProfile.username}" id="userNameInput"/>
        </div>
    </div>

    <#if isAdmin>
        <!--User Roles-->
        <form action="/users/${userProfile.id}" method="post">
            <input type="hidden" name="_csrf" value="${_csrf.token}" />

            <div class="form-group row">
                <!--CheckBox Group-->
                <div class="col">
                    <div class="form-control">
                        <div class="btn-group" role="group">
                            <#list roles as role>
                                <div class="input-group">
                                    <div class="input-group-prepend">
                                        <div class="form-check form-check-inline">
                                            <input class="form-check-input" name="${role}" type="checkbox" id="${role}"
                                                   ${userProfile.roles?seq_contains(role)?string("checked", "")} >
                                            <label class="form-check-label" for="${role}">${role}</label>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
                <!--Save Button-->
                <div class="col-md-1">
                    <button class="btn btn-primary float-right" type="submit">Save</button>
                </div>
            </div>
        </form>
    </#if>

    <!--Review Link-->
    <div class="form-group row mx-auto">
        <a href="${userProfile.id}/reviews">${userProfile.username} reviews</a>
    </div>

</@c.page>