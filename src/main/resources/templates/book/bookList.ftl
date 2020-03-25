<#import "/parts/common.ftl" as c>

<#include "/parts/security.ftl">

<@c.page>

<div class="form-group row">
    <div class="col-md-1 col-form-label">
        <h5>Books</h5>
    </div>
    <div class="col">
        <#if isAdmin>
            <a class="btn btn-primary" href="/books/add">Add new book</a>
        </#if>
    </div>
</div>


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
            <td><a href="/writers/${book.writer.id}">${book.writer.toString()}</a></td>
            <td>
                <#list book.genres as genre>${genre}<#sep>, </#list>
            </td>
            <#if isAdmin>
                <td>
                    <form action="/books/${book.id}" method="post">
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