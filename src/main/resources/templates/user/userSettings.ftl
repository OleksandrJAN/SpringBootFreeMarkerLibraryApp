<#import "/parts/common.ftl" as c>
<#import "userProfileForms.ftl" as profile>

<@c.page>

<!--User Name-->
<@profile.usernameForm
    userName = userProfile.username
/>


<form action="/settings/password" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="hidden" name="_method" value="PUT" />

    <!--current password confirmation-->
    <@profile.passwordForm
        passwordLabel       =   "Current password:"
        passwordInputId     =   "currentPasswordConfirmationInput"
        passwordInputName   =   "currentPasswordConfirmation"
        passwordError       =   (currentPasswordConfirmationError??)?then(currentPasswordConfirmationError, "")
    />

    <!--new password-->
    <@profile.passwordForm
        passwordLabel       =   "New password:"
        passwordInputId     =   "newPasswordInput"
        passwordInputName   =   "newPassword"
        passwordError       =   (newPasswordError??)?then(newPasswordError, "")
    />

    <!--new password confirmation-->
    <@profile.passwordForm
        passwordLabel       =   "Retype new password:"
        passwordInputId     =   "newPasswordConfirmationInput"
        passwordInputName   =   "newPasswordConfirmation"
        passwordError       =   (newPasswordConfirmationError??)?then(newPasswordConfirmationError, "")
    />

    <button class="btn btn-primary" type="submit">Save</button>

</form>

</@c.page>