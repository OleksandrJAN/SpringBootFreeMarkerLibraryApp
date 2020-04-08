<#import "/ui/alerts.ftl" as alert>
<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>


<#macro reviewPage
    review action
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
        <div class="col-md-2">
            <@ui.label
                text    = "Review assessment:"
                forId   = "selectedAssessment"
            />
        </div>
        <div class="col">
            <#if (review.assessment)??>
                <#assign
                    selectedItem = { "value" : review.assessment, "text" : review.assessment }
                    assessments = assessments?filter(assessment -> assessment != review.assessment)
                    disabled = false
                >
            <#else>
                <#assign
                    selectedItem = { "value" : "", "text" : "Choose review assessment" }
                    disabled = true
                >
            </#if>

            <#assign items = {}>
            <#list assessments as assessment>
                <#assign
                    items = items + {assessment : assessment}
                >
            </#list>

            <@ui.dropList
                mapCollection       = items
                selected            = selectedItem
                isSelectedDisabled  = disabled
                name                = "assessment"
                id                  = "selectedAssessment"
                error               = (assessmentError??)?then(assessmentError, "")
            />
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
                value           = ((review.text)??)?then(review.text, "")
                placeholder     = "Entry review text"
                error           = (textError??)?then(textError, "")
            />
        </div>
    </div>

    <!--Submit Button-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <#if putAction>
            <#assign backLink = action?keep_before_last("/") >
            <a class="btn btn-primary ml-auto" href="${backLink}" role="button">Back</a>
        </#if>
    </div>

</form>
</#macro>
