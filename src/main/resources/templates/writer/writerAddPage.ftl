<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "/ui/alerts.ftl" as alert>

<@c.page>

<#if writerError??>
    <@alert.danger
        message = writerError
    />
</#if>

<form action="/writers" method="post">
    <@hidden.csrf />

    <!--First Name-->
    <@ui.labelInputRow
        inputId             = "writerFirstNameInput"
        inputName           = "firstName"
        inputValue          = ((writer.firstName)??)?then(writer.firstName, "")
        inputPlaceholder    = "First name"
        inputError          = (firstNameError??)?then(firstNameError, "")
        labelText           = "Writer first name:"
        labelMd             = "-md-2"
    />

    <!--Last Name-->
    <@ui.labelInputRow
        inputId             = "writerLastNameInput"
        inputName           = "lastName"
        inputValue          = ((writer.lastName)??)?then(writer.lastName, "")
        inputPlaceholder    = "Last name"
        inputError          = (lastNameError??)?then(lastNameError, "")
        labelText           = "Writer last name:"
        labelMd             = "-md-2"
    />

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/writers" role="button">Back</a>
    </div>

</form>

</@c.page>