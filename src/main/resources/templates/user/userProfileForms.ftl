<!--User Name-->
<#macro usernameForm userName>
<div class="form-group row">
    <label class="col-md-2 col-form-label" for="userNameInput">User name:</label>
    <div class="col">
        <input readonly class="form-control" type="text" name="username"
               value="${userName}" id="userNameInput"
        />
    </div>
</div>
</#macro>


<!--User Roles-->
<#macro userRolesForm userId userRoles allRoles>
<form action="/users/${userId}/roles" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <div class="form-group row">
        <!--CheckBox Group-->
        <div class="col">
            <div class="form-control">
                <div class="btn-group" role="group">
                    <#list allRoles as role>
                        <div class="input-group">
                            <div class="input-group-prepend">
                                <div class="form-check form-check-inline">
                                    <input class="form-check-input" name="${role}" type="checkbox" id="${role}"
                                           ${userRoles?seq_contains(role)?string("checked", "")} >
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
</#macro>