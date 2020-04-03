<#import "/ui/alerts.ftl" as alert>
<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>



<#macro reviewCards currUserId isAdmin modificationActionPath>
<#list reviews as review>
<!--find a better solution-->
    <#assign
        currAssessment = review.assessment
    >
    <#if currAssessment == 'POSITIVE'>
        <#assign bg = 'bg-success'>
    <#elseif currAssessment == 'NEUTRAL'>
        <#assign bg = 'bg-light'>
    <#elseif currAssessment == 'NEGATIVE'>
        <#assign bg = 'bg-danger'>
    <#else>
        <#assign bg = 'bg-white'>
    </#if>

    <div class="form-group">

        <div class="card ${bg}">
            <div class="card-header">
                <ul class="nav nav-pills card-header-pills">
                    <li class="nav-item">
                        <a class="nav-link" href="/books/${review.book.id}">${review.book.bookName}</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/users/${review.author.id}">${review.author.username}</a>
                    </li>
                </ul>
            </div>
            <div class="card-body">
                <p class="card-text">${review.text}</p>
                <#if review.author.id == currUserId || isAdmin>
                    <div class="row">
                        <a class="card-link btn btn-link" href="${modificationActionPath}/${review.id}"
                           role="button">Edit</a>
                        <form action="${modificationActionPath}/${review.id}" method="post">
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
        <label class="col col-form-label">No reviews</label>
    </div>
</#list>
</#macro>



<#macro reviewPage
    action reviewText reviewAssessment
    putAction=false
>
<form action="${action}" method="post">
    <@hidden.csrf />

    <#if putAction>
        <@hidden.method
            value = "PUT"
        />
    </#if>

    <#if reviewError??>
        <@alert.danger
            message = reviewError
        />
    </#if>

    <!--Review Assessment-->
    <div class="form-group row">
        <label class="col-md-2 col-form-label" for="selectedAssessment">Review assessment:</label>
        <div class="col">
            <select class="custom-select <#if assessmentError??>is-invalid</#if>"
                    name="assessment" id="selectedAssessment" >
                <#if reviewAssessment?has_content>
                    <#assign assessments = assessments?filter(assessment -> assessment != reviewAssessment)>
                    <option selected value="${reviewAssessment}">${reviewAssessment}</option>
                <#else>
                    <option selected disabled>Choose review assessment</option>
                </#if>
                <#list assessments as assessment>
                    <option value="${assessment}">${assessment}</option>
                </#list>
            </select>
            <#if assessmentError??>
                <div class="invalid-feedback">
                    ${assessmentError}
                </div>
            </#if>
        </div>
    </div>

    <!--Review Text-->
    <div class="form-group row">
        <div class="col-md-2">
            <@ui.label
                text    = "Review text:"
                forId   = "reviewTextArea"
            />
        </div>
        <div class="col">
            <@ui.textArea
                id              = "reviewTextArea"
                name            = "text"
                rows            = 9
                value           = (reviewText?has_content)?then(reviewText, "")
                placeholder     = "Entry review text"
                error           = (textError??)?then(textError, "")
            />
        </div>
    </div>

    <!--Submit Button-->
    <div class="form-group">
        <button class="btn btn-primary" type="submit">Save</button>
    </div>

</form>
</#macro>
