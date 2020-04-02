<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<#include "/parts/security.ftl">

<@c.page>

<div class="form-group row">
    <div class="col-md-1 col-form-label">
        <h5>Writers</h5>
    </div>
    <#if isAdmin>
        <div class="col">
            <a class="btn btn-primary" href="/writers/add">Add new writer</a>
        </div>
    </#if>
</div>

<!--Форма поиска автора-->

<@ui.table
    headers = ["First name", "Last name"]
>

<#list writers as writer>
    <tr>
        <td><a href="/writers/${writer.id}">${writer.firstName}</a></td>
        <td><a href="/writers/${writer.id}">${writer.lastName}</a></td>
        <#if isAdmin>
            <td>
                <@hidden.deleteForm
                    action = "/writers/${writer.id}"
                />
            </td>
        </#if>
    </tr>
</#list>

</@ui.table>


</@c.page>