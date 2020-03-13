<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>

<div class="form-group row">
    <div class="col-sm-1 col-form-label">
        <h5>Books</h5>
    </div>
    <div class="col-sm-10">
        <#if isAdmin>
            <a class="btn btn-primary" href="/books/add">Add new book</a>
        </#if>
    </div>
</div>

<!--Форма поиска книги-->

<table class="table table-striped">
    <thead>
    <tr>
        <th scope="col">Name</th>
        <th scope="col">Author</th>
        <th scope="col">Genres</th>
    </tr>
    </thead>

    <tbody>
    <#list books as book>
    <tr>
        <td><a href="/books/${book.id}">${book.bookName}</a></td>
        <td>${book.writer.toString()}</td>
        <td>
            <#list book.genres as genre>${genre}<#sep>, </#list>
        </td>
    </tr>
    </#list>
    </tbody>
</table>

</@c.page>