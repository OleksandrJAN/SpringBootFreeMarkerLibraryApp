<#import "parts/common.ftl" as c>

<@c.page>

<form action="/users" method="post">
    <div class="form-group row">
        <label class="col-sm-2 col-form-label"> User Name: </label>
        <div class="col-sm-6">
            <input readonly class="form-control" type="text" name="username" value="${user.username}" />
        </div>
    </div>

    <div class="btn-group" role="group">
        <#list roles as role>
        <div class="input-group mx-1">
            <div class="input-group-prepend">
                <div class="form-check form-check-inline">
                    <input class="form-check-input" name="${role}" type="checkbox" id="${role}"
                           ${user.roles?seq_contains(role)?string("checked", "")} >
                    <label class="form-check-label" for="${role}">${role}</label>
                </div>
            </div>
        </div>
        </#list>
    </div>

    <div class="row mt-2">
        <button class="btn btn-primary ml-3" type="submit">Save</button>
        <a class="btn btn-primary ml-3" href="/users">Back</a>
    </div>

    <input type="hidden" name="userId" value="${user.id}" />
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>

</@c.page>