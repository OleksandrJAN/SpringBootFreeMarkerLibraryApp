<#import "parts/common.ftl" as c>

<@c.page>

<h5>List of users</h5>

<table class="table table-striped">
    <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Roles</th>
        </tr>
    </thead>

    <tbody>
    <#list users as user>
    <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>
            <#list user.roles as role>${role}<#sep>, </#list>
        </td>
        <td>
            <button class="btn btn-primary" type="button" onclick="window.location.href='/users/${user.id}'">Edit</button>
        </td>
    </tr>
    </#list>
    </tbody>
</table>

</@c.page>