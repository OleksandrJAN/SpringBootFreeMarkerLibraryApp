<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

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


<form action="/settings/password" method="post">
    <@hidden.csrf />
    <@hidden.method value = "PUT" />

    <!--current password confirmation-->
    <@ui.labelInputRow
        inputId             = "currentPasswordConfirmationInput"
        inputName           = "currentPasswordConfirmation"
        inputPlaceholder    = "Password"
        inputError          = (currentPasswordConfirmationError??)?then(currentPasswordConfirmationError, "")
        inputType           = "password"
        labelText           = "Current password:"
        labelMd             = "-md-2"
    />

    <!--new password-->
    <@ui.labelInputRow
        inputId             = "newPasswordInput"
        inputName           = "newPassword"
        inputPlaceholder    = "Password"
        inputError          = (newPasswordError??)?then(newPasswordError, "")
        inputType           = "password"
        labelText           = "New password:"
        labelMd             = "-md-2"
    />

    <!--new password confirmation-->
    <@ui.labelInputRow
        inputId             = "newPasswordConfirmationInput"
        inputName           = "newPasswordConfirmation"
        inputPlaceholder    = "Password"
        inputError          = (newPasswordConfirmationError??)?then(newPasswordConfirmationError, "")
        inputType           = "password"
        labelText           = "Retype new password:"
        labelMd             = "-md-2"
    />

    <button class="btn btn-primary" type="submit">Save</button>

</form>

</@c.page>