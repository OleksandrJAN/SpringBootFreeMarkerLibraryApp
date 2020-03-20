<#import "/parts/common.ftl" as c>

<@c.page>

<form method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <!--username-->
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label">User name:</label>
        <div class="col-sm-6">
            <input readonly class="form-control" type="text" name="username" value="${username}" />
        </div>
    </div>

    <!--current password confirmation-->
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label">Current password:</label>
        <div class="col-sm-6">
            <input class="form-control <#if currentPasswordConfirmError??>is-invalid</#if>" type="password"
                   name="currentPasswordConfirmation" placeholder="Password"
            />
            <#if currentPasswordConfirmError??>
                <div class="invalid-feedback">
                    ${currentPasswordConfirmError}
                </div>
            </#if>
        </div>
    </div>

    <!--new password-->
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label">New password:</label>
        <div class="col-sm-6">
            <input class="form-control <#if newPasswordError??>is-invalid</#if>" type="password"
                   name="newPassword" placeholder="Password"
            />
            <#if newPasswordError??>
                <div class="invalid-feedback">
                    ${newPasswordError}
                </div>
            </#if>
        </div>
    </div>

    <!--new password confirmation-->
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label">Retype new password:</label>
        <div class="col-sm-6">
            <input class="form-control <#if newPasswordConfirmError??>is-invalid</#if>" type="password"
                   name="newPasswordConfirmation" placeholder="Password"/>
            <#if newPasswordConfirmError??>
                <div class="invalid-feedback">
                    ${newPasswordConfirmError}
                </div>
            </#if>
        </div>
    </div>

    <button class="btn btn-primary" type="submit">Save</button>

</form>

</@c.page>