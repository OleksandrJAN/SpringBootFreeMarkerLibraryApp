<#import "/parts/common.ftl" as c>
<#import "/parts/alerts.ftl" as alert>
<#import "writerForms.ftl" as forms>

<#include "/parts/security.ftl">

<@c.page>

<#if isAdmin>
<form action="/writers/${writer.id}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <input type="hidden" name="_method" value="PUT" />

    <#if writerUpdated??>
        <@alert.success
            message = writerUpdated
        />
    </#if>

</#if>

<!--First Name-->
<@forms.inputForm
    inputLabel          = "Writer first name:"
    inputId             = "writerFirstNameInput"
    inputName           = "firstName"
    value               = ((writer.firstName)??)?then(writer.firstName, "")
    inputPlaceholder    = "First name"
    error               = (firstNameError??)?then(firstNameError, "")
    readonly            = (isAdmin)?then(false, true)
/>

<!--Last Name-->
<@forms.inputForm
    inputLabel          = "Writer last name:"
    inputId             = "writerLastNameInput"
    inputName           = "lastName"
    value               = ((writer.lastName)??)?then(writer.lastName, "")
    inputPlaceholder    = "Last name"
    error               = (lastNameError??)?then(lastNameError, "")
    readonly            = (isAdmin)?then(false, true)
/>


<#if isAdmin>
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
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