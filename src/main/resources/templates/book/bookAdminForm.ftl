<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>
<#import "/ui/alerts.ftl" as alert>

<#macro bookAdminForm
    book action
    putAction=false selectedWriter=""
>
<form action="${action}" method="post" enctype="multipart/form-data">
    <@hidden.csrf />

    <#if putAction>
        <@hidden.method
            value = "PUT"
        />
    </#if>

    <#if bookError??>
        <@alert.danger
            message = bookError
        />
    </#if>

    <!--Author drop list-->
    <div class="form-group row">
        <div class="col">
            <#if (book.writer)??>
                <#assign
                    selectedItem = { "value" : book.writer.id, "text" : book.writer.toString() }
                    writers = writers?filter(writer -> writer.id != book.writer.id)
                    disabled = false
                >
            <#else>
                <#assign
                    selectedItem = { "value" : "", "text" : "Choose Author" }
                    disabled = true
                >
            </#if>

            <#assign items = {}>
            <#list writers as writer>
                <#assign
                    items = items + {writer.id : writer.toString()}
                >
            </#list>

            <@ui.dropList
                mapCollection       = items
                selected            = selectedItem
                isSelectedDisabled  = disabled
                name                = "selectedWriter"
                id                  = "selectedWriter"
                error               = (selectedWriterError??)?then(selectedWriterError, "")
            />

        </div>
        <div class="col-md-1">
            <div class="row mx-auto">
                <a class="btn btn-primary ml-auto" role="button" href="/writers/add">New</a>
            </div>
        </div>
    </div>

    <!--Book Name-->
    <@ui.labelInputRow
        inputId             = "bookNameInput"
        inputName           = "bookName"
        inputValue          = ((book.bookName)??)?then(book.bookName, "")
        inputPlaceholder    = "Book name"
        inputError          = (bookNameError??)?then(bookNameError, "")
        labelText           = "Book name:"
        labelMd             = "-md-2"
    />


    <!--Annotation-->
    <div class="form-group">
        <@ui.label
            text    = "Book annotation:"
            forId   = "annotationTextArea"
        />
        <@ui.textArea
            id              = "annotationTextArea"
            name            = "annotation"
            rows            = 6
            value           = ((book.annotation)??)?then(book.annotation, "")
            placeholder     = "Book annotation"
            error           = (annotationError??)?then(annotationError, "")
        />
    </div>

    <!--Genres-->
    <div class="form-group row mx-auto">
        <@ui.checkboxRow
            collection = genres
            checkCollection = ((book.genres)??)?then(book.genres, [])
            error = (genresError??)?then(genresError, "")
        />
    </div>

    <div class="form-group row">
        <!--Publication date-->
        <div class="col-md-6">
            <@ui.labelInputRow
                inputId             = "publicationDateInput"
                inputName           = "publicationDate"
                inputValue          = ((book.publicationDate)??)?then((book.publicationDate)?string('yyyy-MM-dd'), "")
                inputType           = "date"
                inputError          = (publicationDateError??)?then(publicationDateError, "")
                labelText           = "Publication date:"
                labelMd             = "-md-4"
            />
        </div>

        <!--File Chooser-->
        <!--Bootstrap invalid feedback does not work-->
        <div class="col-md-6">
            <@ui.fileChooser
                name = "posterFile"
                id = "posterFile"
                prependId = "posterFileAddon"
                prependText = "Poster"
                error = (posterFileError??)?then(posterFileError, "")
            />
        </div>

    </div>

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <#assign backLink = ((book.id)??)?then("/books/${book.id}","/books")>
        <a class="btn btn-primary ml-auto" href="${backLink}" role="button">Back</a>
    </div>

</form>

</#macro>