<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "/ui/alerts.ftl" as alert>

<@c.page>

<#if Session?? && Session.SPRING_SECURITY_LAST_EXCEPTION??>
    <@alert.danger
        message = Session.SPRING_SECURITY_LAST_EXCEPTION.message
    />
</#if>

<#if registrationSuccessful??>
    <@alert.success
        message = "Registration successful"
    />
</#if>


<form action="/login" method="post">
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
        inputId             = "userNameInput"
        inputName           = "password"
        inputType           = "password"
        inputPlaceholder    = "Password"
        inputError          = (passwordError??)?then(passwordError, "")
        labelText           = "Password:"
        labelMd             = "-md-2"
    />

    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Sign In</button>
        <a class="btn" role="button" href="/registration" >Sign Up</a>
    </div>

</form>

</@c.page>