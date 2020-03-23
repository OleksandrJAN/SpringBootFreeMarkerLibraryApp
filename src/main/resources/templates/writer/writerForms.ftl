<!--Input form-->
<#macro inputForm   inputLabel          inputId             inputName   value=""
                    inputPlaceholder="" readonly=false      error=""    type="text"
>
<div class="form-group row">
    <label for="${inputId}" class="col-md-2 col-form-label">${inputLabel}</label>
    <div class="col">
        <input class="form-control <#if error?has_content>is-invalid</#if>"
               value="${value}" type="${type}" name="${inputName}"
               placeholder="${inputPlaceholder}" id="${inputId}"
               <#if readonly>readonly</#if>
        />
        <#if error?has_content>
            <div class="invalid-feedback">
                ${error}
            </div>
        </#if>
    </div>
</div>
</#macro>


<!--Book Cards-->
<#macro bookCards author books isAdmin>

<#list books as book>

    <div class="form-group">
        <div class="card">

            <div class="card-header">
                <ul class="nav nav-pills card-header-pills">
                    <li class="nav-item">
                        <a class="nav-link" href="/books/${book.id}">${book.bookName}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/books/${book.id}/reviews">Reviews</a>
                    </li>
                </ul>
            </div>

            <div class="card-body">
                <p class="card-text">${book.annotation}</p>
                <#if isAdmin>
                    <div class="row">
                        <a class="card-link btn btn-link" href="/books/${book.id}"
                           role="button">Edit</a>
                        <form action="/books/${book.id}" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}" />
                            <input type="hidden" name="_method" value="DELETE" />
                            <button class="card-link btn btn-link" type="submit">Delete</button>
                        </form>
                    </div>
                </#if>
            </div>

        </div>
    </div>
<#else>
    <div class="form-group row">
        <label class="col col-form-label">No books</label>
    </div>
</#list>

</#macro>