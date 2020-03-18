<#import "parts/common.ftl" as c>

<@c.page>

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
                <td><a href="/users/${user.id}">${user.username}</a></td>
                <td>
                    <#list user.roles as role>${role}<#sep>, </#list>
                </td>
            </tr>
        </#list>
    </tbody>
</table>

</@c.page>