<#import "parts/common.ftl" as c>

<@c.page>

<#if writerError??>
<div class="alert alert-danger" role="alert">
    ${writerError}
</div>
</#if>

<form action="/writers/add" method="post" >
    <!--First Name-->
    <div class="form-group row">
        <label for="writerFirstNameInput" class="col-md-2 col-form-label">Writer first name:</label>
        <div class="col-md-10">
            <input class="form-control ${(firstNameError??)?string('is-invalid', '')}" value="<#if writer??>${writer.firstName}</#if>"
                   type="text" name="firstName" placeholder="First name" id="writerFirstNameInput"
            />
            <#if firstNameError??>
                <div class="invalid-feedback">
                    ${firstNameError}
                </div>
            </#if>
        </div>
    </div>

    <!--Last Name-->
    <div class="form-group row">
        <label for="writerLastNameInput" class="col-md-2 col-form-label">Writer last name:</label>
        <div class="col-md-10">
            <input class="form-control ${(lastNameError??)?string('is-invalid', '')}" value="<#if writer??>${writer.lastName}</#if>"
                   type="text" name="lastName" placeholder="Last name" id="writerLastNameInput"
            />
            <#if lastNameError??>
                <div class="invalid-feedback">
                    ${lastNameError}
                </div>
            </#if>
        </div>
    </div>

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/writers" role="button">Back</a>
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}" />

</form>

</@c.page>