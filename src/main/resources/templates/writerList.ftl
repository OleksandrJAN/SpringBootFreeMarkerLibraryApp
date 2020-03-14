<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">

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
            <th scope="col">Books</th>
        </tr>
    </thead>

    <tbody>
    <#list writers as writer>
        <tr>
            <td><a href="/writers/${writer.id}">${writer.firstName}</a></td>
            <td>${writer.lastName}</td>
            <td>
                <#list writer.books as book>${book.bookName}<#sep>, </#list>
            </td>
        </tr>
    </#list>
    </tbody>
</table>

</@c.page>