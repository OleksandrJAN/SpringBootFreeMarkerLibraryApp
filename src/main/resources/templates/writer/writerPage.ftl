<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "/ui/alerts.ftl" as alert>

<#include "/parts/security.ftl">


<@c.page>

<!--First Name-->
<@ui.labelInputRow
    inputId             = "writerFirstNameInput"
    inputName           = "firstName"
    inputValue          = ((writer.firstName)??)?then(writer.firstName, "")
    inputPlaceholder    = "First name"
    inputReadonly       = true
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
    inputReadonly       = true
    inputError          = (lastNameError??)?then(lastNameError, "")
    labelText           = "Writer last name:"
    labelMd             = "-md-2"
/>

<!--Actions-->
<div class="form-group row mx-auto">
    <a class="btn btn-primary" href="${writer.id}/books" role="button">Books</a>
    <#if isAdmin>
        <a class="btn btn-warning ml-1" href="${writer.id}/edit" role="button">Edit</a>
        <div class="ml-auto">
            <@hidden.deleteForm
                action = "/writers/${writer.id}"
            />
        </div>
    </#if>
</div>


</@c.page>