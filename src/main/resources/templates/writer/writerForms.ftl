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