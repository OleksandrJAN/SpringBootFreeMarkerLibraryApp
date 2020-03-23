<#import "/parts/common.ftl" as c>
<#import "/parts/alerts.ftl" as alert>
<#import "writerForms.ftl" as forms>

<@c.page>

<#if writerError??>
    <@alert.danger
        message = writerError
    />
</#if>

<form action="/writers" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <!--First Name-->
    <@forms.inputForm
        inputLabel          = "Writer first name:"
        inputId             = "writerFirstNameInput"
        inputName           = "firstName"
        value               = ((writer.firstName)??)?then(writer.firstName, "")
        inputPlaceholder    = "First name"
        error               = (firstNameError??)?then(firstNameError, "")
    />

    <!--Last Name-->
    <@forms.inputForm
        inputLabel          = "Writer last name:"
        inputId             = "writerLastNameInput"
        inputName           = "lastName"
        value               = ((writer.lastName)??)?then(writer.lastName, "")
        inputPlaceholder    = "Last name"
        error               = (lastNameError??)?then(lastNameError, "")
    />

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/writers" role="button">Back</a>
    </div>

</form>

</@c.page>