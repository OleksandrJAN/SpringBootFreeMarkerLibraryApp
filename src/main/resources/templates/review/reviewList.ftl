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
            <@r.reviewPage "/books/${book.id}/reviews" "Add"/>
        </div>
    </div>
</#if>

<!--Reviews List-->
<@r.reviewCards currentUser.id isAdmin />

</@c.page>