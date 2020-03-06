<#import "parts/common.ftl" as c>

<@c.page>
<div class="form-row">
    <div class="form-group col-md-6">
        <form class="form-inline" action="/messages" method="get" >
            <input class="form-control" type="text" name="filter" placeholder="Search by tag" value="${filter?ifExists}"/>
            <button class="btn btn-primary ml-2" type="submit">Search</button>
        </form>
    </div>
</div>

<a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
    Add new Message
</a>

<div class="collapse <#if message??>show</#if>" id="collapseExample">
    <div class="form-group mt-3">
        <form method="post">
            <div class="form-group">
                <input class="form-control ${(textError??)?string('is-invalid', '')}" type="text" name="text"
                       value="<#if message??>${message.text}</#if>" placeholder="Entry message" />
                <#if textError??>
                    <div class="invalid-feedback">
                        ${textError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input class="form-control ${(tagError??)?string('is-invalid', '')}" type="text" name="tag"
                       value="<#if message??>${message.tag}</#if>" placeholder="Tag" />
                <#if tagError??>
                <div class="invalid-feedback">
                    ${tagError}
                </div>
                </#if>
            </div>
            <div class="form-group">
                <button class="btn btn-primary" type="submit">Add</button>
            </div>
            <input type="hidden" name="_csrf" value="${_csrf.token}" />
        </form>
    </div>
</div>

<div class="card-columns">
    <#list messages as message>
    <div class="card my-3">
        <div class="m-2">
            <b>${message.id}</b>
            <span>${message.text}</span>
            <i>${message.tag}</i>
        </div>
        <div class="card-footer text-muted">
            ${message.authorName}
        </div>
    </div>
    <#else>
    <label class="col-sm-6 col-form-label mt-2">No messages</label>
    </#list>
</div>


</@c.page>