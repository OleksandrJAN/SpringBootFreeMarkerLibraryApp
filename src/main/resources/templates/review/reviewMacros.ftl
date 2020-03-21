<#import "/parts/alerts.ftl" as alert>

<#macro reviewCards currUserId isAdmin>
<#list reviews as review>
<!--find a better solution-->
    <#assign
        currAssessment = review.assessment
        reviewAuthorId = review.author.id
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
                <#if reviewAuthorId == currUserId || isAdmin>
                    <div class="row mx-auto">
                        <a class="card-link" href="/users/${reviewAuthorId}/reviews/${review.id}">Edit</a>
                        <a class="card-link" href="/users/${reviewAuthorId}/reviews/${review.id}/delete">Delete</a>
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



<#macro reviewPage action buttonText>
<form action="${action}" method="post">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <#if reviewError??>
        <@alert.danger reviewError />
    </#if>

    <!--Review Assessment-->
    <div class="form-group row">
        <label class="col-md-2 col-form-label" for="selectedAssessment">Review assessment:</label>
        <div class="col">
            <select class="custom-select <#if assessmentError??>is-invalid</#if>"
                    name="assessment" id="selectedAssessment" >
                <#assign isSelectedAssessment = (review.assessment)??>
                <#if isSelectedAssessment>
                    <#assign selectedAssessment = review.assessment>
                    <#assign assessments = assessments?filter(assessment -> assessment != selectedAssessment)>
                    <option selected value="${selectedAssessment}">${selectedAssessment}</option>
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
        <label class="col-md-2 col-form-label" for="reviewTextArea">Review text:</label>
        <div class="col">
            <textarea class="form-control <#if textError??>is-invalid</#if>" name="text" rows="9"
                      id="reviewTextArea" placeholder="Entry review text"><#if review??>${review.text}</#if></textarea>
            <#if textError??>
                <div class="invalid-feedback">
                    ${textError}
                </div>
            </#if>
        </div>
    </div>

    <!--Submit Button-->
    <div class="form-group">
        <button class="btn btn-primary" type="submit">${buttonText}</button>
    </div>

</form>
</#macro>
