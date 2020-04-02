<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<@c.page>

<form action="/registration" method="post">
    <@hidden.csrf />

    <!--User Name-->
    <@ui.labelInputRow
        inputId             = "userNameInput"
        inputName           = "username"
        inputValue          = ((user.username)??)?then(user.username, "")
        inputPlaceholder    = "User name"
        inputError          = (usernameError??)?then(usernameError, "")
        labelText           = "User name:"
        labelMd             = "-md-2"
    />

    <!--Password-->
    <@ui.labelInputRow
        inputId             = "passwordInput"
        inputName           = "password"
        inputType           = "password"
        inputPlaceholder    = "Password"
        inputError          = (passwordError??)?then(passwordError, "")
        labelText           = "Password:"
        labelMd             = "-md-2"
    />

    <!--Password Confirmation-->
    <@ui.labelInputRow
        inputId             = "passwordConfirmationInput"
        inputName           = "passwordConfirmation"
        inputType           = "password"
        inputPlaceholder    = "Retype password"
        inputError          = (passwordConfirmationError??)?then(passwordConfirmationError, "")
        labelText           = "Password:"
        labelMd             = "-md-2"
    />

    <div class="from-group row mx-auto">
        <button class="btn btn-primary" type="submit">Sign Up</button>
    </div>

</form>

</@c.page>