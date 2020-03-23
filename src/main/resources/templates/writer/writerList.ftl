<#import "/parts/common.ftl" as c>

<#include "/parts/security.ftl">

<@c.page>

<div class="form-group row">
    <div class="col-sm-1 col-form-label">
        <h5>Writers</h5>
    </div>
    <div class="col-sm-10">
        <#if isAdmin>
            <a class="btn btn-primary" href="/writers/add">Add new writer</a>
        </#if>
    </div>
</div>

<!--Форма поиска автора-->

<table class="table table-striped">
    <thead>
        <tr>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
        </tr>
    </thead>

    <tbody>
    <#list writers as writer>
        <tr>
            <td><a href="/writers/${writer.id}">${writer.firstName}</a></td>
            <td><a href="/writers/${writer.id}">${writer.lastName}</a></td>
            <#if isAdmin>
                <td>
                    <form action="/writers/${writer.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}" />
                        <input type="hidden" name="_method" value="DELETE" />
                        <button class="btn btn-danger" type="submit">Delete</button>
                    </form>
                </td>
            </#if>
        </tr>
    </#list>
    </tbody>
</table>

</@c.page>