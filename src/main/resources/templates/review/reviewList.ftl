<#import "/parts/common.ftl" as c>
<#import "reviewMacros.ftl" as r>

<#include "/parts/security.ftl">

<@c.page>

<#if book??>
    <div class="form-group row">
        <div class="col">
            <button class="btn btn-primary" data-toggle="collapse" type="button" data-target="#collapseReview">
                Write a review
            </button>
        </div>
    </div>

    <div class="collapse <#if review??>show</#if>" id="collapseReview">
        <div class="form-group">
            <!--Review Add Page-->
            <#if review??>
                <@r.reviewPage
                    action              = "/books/${book.id}/reviews"
                    buttonText          = "Add"
                    reviewText          = review.text
                    reviewAssessment    = review.assessment
                />
            <#else>
                <@r.reviewPage
                    action              = "/books/${book.id}/reviews"
                    buttonText          = "Add"
                />
            </#if>

        </div>
    </div>
</#if>

<!--Reviews List-->
<#if book??>
    <#assign modificationPath = "/books/${book.id}/reviews">
<#else>
    <#assign modificationPath = "/users/${userProfile.id}/reviews">
</#if>

<@r.reviewCards
    currUserId  = currentUser.id
    isAdmin     = isAdmin
    modificationActionPath = modificationPath
/>

</@c.page>