<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<#include "/parts/security.ftl">

<@c.page>

<div class="form-group row">
    <div class="col-md-1 col-form-label">
        <h5>Books</h5>
    </div>
    <#if isAdmin>
        <div class="col">
            <a class="btn btn-primary" href="/books/add">Add new book</a>
        </div>
    </#if>
</div>

<@ui.table
    headers = ["Name", "Author", "Genres"]
>

<#list books as book>
    <tr>
        <td><a href="/books/${book.id}">${book.bookName}</a></td>
        <td><a href="/writers/${book.writer.id}">${book.writer.toString()}</a></td>
        <td>
            <#list book.genres as genre>${genre}<#sep>, </#list>
        </td>
    </tr>
</#list>

</@ui.table>


</@c.page>