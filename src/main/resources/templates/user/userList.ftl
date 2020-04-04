<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<@c.page>

<@ui.table
    headers = ["ID", "Name", "Roles"]
>

<#list users as user>
    <tr>
        <td>${user.id}</td>
        <td><a href="/users/${user.id}">${user.username}</a></td>
        <td>
            <#list user.roles as role>${role}<#sep>, </#list>
        </td>
    </tr>
</#list>

</@ui.table>


</@c.page>