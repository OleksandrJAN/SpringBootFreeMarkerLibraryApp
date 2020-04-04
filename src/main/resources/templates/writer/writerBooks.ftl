<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>

<#include "/parts/security.ftl">

<@c.page>

<#list books as book>
    <#assign links = {
        "/books/" + book.id : book.bookName,
        "/books/" + book.id + "/reviews" : "Reviews"
    }>

    <@ui.card
        headerLinks = links
    >
        <p class="card-text">${book.annotation}</p>
    </@ui.card>

<#else>
    <div class="form-group row">
        <label class="col col-form-label">No books</label>
    </div>
</#list>

</@c.page>