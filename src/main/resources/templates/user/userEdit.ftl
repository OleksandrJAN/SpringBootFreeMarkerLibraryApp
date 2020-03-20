<#import "/parts/common.ftl" as c>

<@c.page>

<!--part.updateUserRoleForm-->
<form action="/users/${user.id}" method="post">
    <!--User Name-->
    <div class="form-group row">
        <label class="col-md-3 col-form-label" for="userNameInput">User name:</label>
        <div class="col">
            <input readonly class="form-control" type="text" name="username"
                   value="${user.username}" id="userNameInput"/>
        </div>
    </div>

    <!--User Roles-->
    <div class="form-group row mx-auto">
        <div class="form-control ${(rolesError??)?string('is-invalid', '')}">
            <div class="btn-group" role="group">
                <#list roles as role>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" name="${role}" type="checkbox" id="${role}"
                                       ${user.roles?seq_contains(role)?string("checked", "")} >
                                <label class="form-check-label" for="${role}">${role}</label>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
        <#if rolesError??>
            <div class="invalid-feedback">
                ${rolesError}
            </div>
        </#if>
    </div>

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/users" role="button">Back</a>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</@c.page>