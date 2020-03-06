<#import "parts/common.ftl" as c>

<@c.page>

<form method="post">
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label"> User Name: </label>
        <div class="col-sm-6">
            <input readonly class="form-control" type="text" name="username" value="${username}" />
        </div>
    </div>
    <div class="form-group row">
        <label  class="col-sm-2 col-form-label"> Password: </label>
        <div class="col-sm-6">
            <input class="form-control" type="password" name="password" placeholder="Password"/>
        </div>
    </div>
    <button class="btn btn-primary" type="submit">Save</button>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
</form>


</@c.page>