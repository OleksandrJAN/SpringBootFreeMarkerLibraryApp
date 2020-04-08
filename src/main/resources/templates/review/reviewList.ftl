<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "reviewForm.ftl" as reviewForm>

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
            <@reviewForm.reviewPage
                review              = (review??)?then(review, {})
                action              = "/books/" + book.id + "/reviews"
            />
        </div>
    </div>
</#if>


<!-- MAP [colors] WITH {ReviewAssessment : ReviewColor} -->
<#include "reviewColors.ftl">

<!--Reviews List-->
<#list reviews as review>
    <@ui.card
        headerLinks = {
            "/books/" + review.book.id      :  review.book.bookName,
            "/users/" + review.author.id    :  review.author.username
        }
        bgColor     = colors[review.assessment]
    >

    <p class="card-text">${review.text}</p>
    <#if review.author.id == currentUser.id || isAdmin>
        <div class="row">
            <a class="card-link btn btn-link" href="${reviewCardAction}/${review.id}" role="button">Edit</a>
            <form action="${reviewCardAction}/${review.id}" method="post">
                <@hidden.csrf />
                <@hidden.method
                    value = "DELETE"
                />
                <button class="card-link btn btn-link" type="submit">Delete</button>
            </form>
        </div>
    </#if>

    </@ui.card>
<#else>
    <div class="form-group row">
        <label class="col col-form-label">No reviews</label>
    </div>
</#list>


</@c.page>