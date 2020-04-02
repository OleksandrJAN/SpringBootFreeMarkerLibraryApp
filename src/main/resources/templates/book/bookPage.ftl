<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<#include "/parts/security.ftl">


<@c.page>

<div class="form-group row">
    <div class="col">
        <!--Book Name-->
        <@ui.labelInputRow
            inputId             = "bookNameInput"
            inputName           = "bookName"
            inputValue          = book.bookName
            inputPlaceholder    = "First name"
            inputReadonly       = true
            labelText           = "Book name:"
            labelMd             = "-md-3"
        />

        <!--Annotation-->
        <div class="form-group row">
            <div class="col-md-3">
                <@ui.label
                    text    = "Book annotation:"
                    forId   = "annotationTextArea"
                />
            </div>
            <div class="col">
                <@ui.textArea
                    id          = "annotationTextArea"
                    name        = "annotation"
                    rows        = 6
                    value       = book.annotation
                    readonly    =true
                />
            </div>
        </div>

        <!--Author-->
        <@ui.labelInputRow
            inputId             = "selectedWriter"
            inputName           = "selectedWriter"
            inputValue          = book.writer.toString()
            inputReadonly       = true
            labelText           = "Writer:"
            labelMd             = "-md-3"
        />

        <!--Genres-->
        <@ui.labelInputRow
            inputId             = "bookGenres"
            inputName           = "bookGenres"
            inputValue          = book.genres?join(", ")
            inputReadonly       = true
            labelText           = "Genres:"
            labelMd             = "-md-3"
        />

        <!--Publication date-->
        <@ui.labelInputRow
            inputId             = "publicationDateInput"
            inputName           = "publicationDate"
            inputType           = "date"
            inputValue          = (book.publicationDate)?string('yyyy-MM-dd')
            inputReadonly       = true
            labelText           = "Publication date:"
            labelMd             = "-md-3"
        />

        <!--Reviews-->
        <div class="form-group row mx-auto">
            <a class="btn btn-primary" href="${book.id}/reviews" role="button">Reviews</a>
            <#if isAdmin>
                <a class="btn btn-warning mx-1" href="${book.id}/edit" role="button">Edit</a>
                <@hidden.deleteForm
                    action = "/books/${book.id}"
                />
            </#if>
        </div>
    </div>

    <!--Poster File-->
    <div class="col-md-3">
        <img src="/img/${book.filename}">
    </div>

</div>

</@c.page>