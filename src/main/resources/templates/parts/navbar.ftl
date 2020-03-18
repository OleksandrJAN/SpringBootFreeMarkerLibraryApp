<#include "security.ftl">
<#import "log.ftl" as l>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Library</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <#if currentUser??>
                <li class="nav-item">
                    <a class="nav-link" href="/books">Books</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/writers">Writers</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/users/${currentUser.id}">Profile</a>
                </li>
                <#if isAdmin>
                    <li class="nav-item">
                        <a class="nav-link" href="/users">Users</a>
                    </li>
                </#if>
            </#if>
        </ul>

        <#if currentUser??>
            <div class="navbar-text mr-2">${name}</div>
            <a class="nav-link" href="/settings">Settings</a>
            <@l.logout/>
        </#if>

        <#if !currentUser?? && !springMacroRequestContext.requestUri?contains("/login") >
            <a class="btn btn-primary" href="/login">Log In</a>
        </#if>
    </div>
</nav>