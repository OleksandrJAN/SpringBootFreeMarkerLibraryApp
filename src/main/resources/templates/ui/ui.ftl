<!--INPUT FORM-->
<#macro input
    inputId inputName
    type="text" value="" placeholder="" readonly=false error=""
>
<input class="form-control <#if error?has_content>is-invalid</#if>"
       value="${value}" type="${type}" name="${inputName}"
       placeholder="${placeholder}" id="${inputId}"
        <#if readonly>readonly</#if>
/>
<#if error?has_content>
    <div class="invalid-feedback">
        ${error}
    </div>
</#if>
</#macro>


<!--LABEL FOR INPUT FORM-->
<#macro label
    text forId
>
<label for="${forId}" class="col-form-label">${text}</label>
</#macro>


<!--TEXT AREA FORM-->
<#macro textArea
    id name rows
    value="" placeholder="" readonly=false error=""
>
<textarea class="form-control <#if error?has_content>is-invalid</#if>" name="${name}" rows="${rows}"
          id="${id}" placeholder="${placeholder}" <#if readonly>readonly</#if> ><#if value?has_content>${value}</#if></textarea>
<#if error?has_content>
    <div class="invalid-feedback">
        ${error}
    </div>
</#if>
</#macro>



<!--INPUT WITH LABEL ROW FORM-->
<#macro labelInputRow
    inputId inputName labelText
    inputType="text" inputValue="" inputPlaceholder="" inputReadonly=false inputError=""
    labelMd="" inputMd=""
>
<div class="form-group row">
    <div class="col${labelMd}">
        <@ui.label
            text        = labelText
            forId       = inputId
        />
    </div>

    <div class="col${inputMd}">
        <@ui.input
            inputId             = inputId
            inputName           = inputName
            type                = inputType
            value               = inputValue
            placeholder         = inputPlaceholder
            readonly            = inputReadonly
            error               = inputError
        />
    </div>
</div>
</#macro>


<!--CHECKBOXES ROW FORM-->
<#macro checkboxRow
    collection checkCollection error=""
>
<div class="form-control  <#if error?has_content>is-invalid</#if>">
    <div class="btn-group" role="group">
        <#list collection as item>
        <div class="input-group">
            <div class="input-group-prepend">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="${item}" type="checkbox" id="${item}"
                           ${checkCollection?seq_contains(item)?string("checked", "")} >
                    <label class="form-check-label" for="${item}">${item}</label>
                </div>
            </div>
        </div>
        </#list>
    </div>
</div>
<#if error?has_content>
    <div class="invalid-feedback">
        ${error}
    </div>
</#if>
</#macro>


<!--TABLE-->
<#macro table
    headers
>
<table class="table table-striped">
    <thead>
        <tr>
            <#list headers as header>
                <th scope="col">${header}</th>
            </#list>
        </tr>
    </thead>

    <tbody>
        <#nested>
    </tbody>
</table>

</#macro>


<!--CARDS-->
<#macro card
    headerLinks
    bgColor="bg-white"
>

<div class="form-group">
    <div class="card ${bgColor}">

        <div class="card-header">
            <ul class="nav nav-pills card-header-pills">
                <#list headerLinks as link, text>
                    <li class="nav-item">
                        <a class="nav-link" href="${link}">${text}</a>
                    </li>
                </#list>
            </ul>
        </div>

        <div class="card-body">
            <#nested>
        </div>

    </div>
</div>

</#macro>