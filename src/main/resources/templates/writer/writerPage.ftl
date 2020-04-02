<#import "/parts/common.ftl" as c>


<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "/ui/alerts.ftl" as alert>
<#import "writerForms.ftl" as forms>

<#include "/parts/security.ftl">


<@c.page>


<#if isAdmin>
<form action="/writers/${writer.id}" method="post">
    <@hidden.csrf />
    <@hidden.method value = "PUT" />

    <#if writerUpdated??>
        <@alert.success
            message = writerUpdated
        />
    </#if>

</#if>

<!--First Name-->
<@ui.labelInputRow
    inputId             = "writerFirstNameInput"
    inputName           = "firstName"
    inputValue          = ((writer.firstName)??)?then(writer.firstName, "")
    inputPlaceholder    = "First name"
    inputReadonly       = (isAdmin)?then(false, true)
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
    inputReadonly       = (isAdmin)?then(false, true)
    inputError          = (lastNameError??)?then(lastNameError, "")
    labelText           = "Writer last name:"
    labelMd             = "-md-2"
/>

<#if isAdmin>
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/writers" role="button">Back</a>
    </div>

</form>
</#if>

<h5>Books</h5>
<@forms.bookCards
    author      = writer
    books       = writer.books
    isAdmin     = isAdmin
/>

</@c.page>