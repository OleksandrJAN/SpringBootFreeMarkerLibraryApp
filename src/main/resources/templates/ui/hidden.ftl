<!--HIDDEN CSRF-->
<#macro csrf>
<input type="hidden" name="_csrf" value="${_csrf.token}" />
</#macro>

<!--HIDDEN METHOD-->
<#macro method value>
<input type="hidden" name="_method" value="${value}" />
</#macro>


<!--DELETE FORM-->
<#macro deleteForm action>
<form action="${action}" method="post">
    <@hidden.csrf />
    <@hidden.method value = "DELETE" />
    <button class="btn btn-danger" type="submit">Delete</button>
</form>
</#macro>


<!--LOGOUT FORM-->
<#macro logout>
<form action="/logout" method="post" >
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button class="btn btn-primary" type="submit">Log Out</button>
</form>
</#macro>
