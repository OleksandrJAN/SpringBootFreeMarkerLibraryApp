<#import "/ui/ui.ftl" as ui>
<#import "/ui/hidden.ftl" as hidden>

<#macro bookAddForm
    action
    putAction=false selectedWriter=""
>
<form action="${action}" method="post" enctype="multipart/form-data">
    <@hidden.csrf />

    <#if putAction>
        <@hidden.method
            value = "PUT"
        />
    </#if>

    <!--Author drop list-->
    <div class="form-group row mx-auto">
        <div class="input-group">
            <select class="custom-select ${(selectedWriterError??)?string('is-invalid', '')}"
                    name="selectedWriter" id="selectedWriter">
                <#if selectedWriter?has_content>
                    <#assign writers = writers?filter(writer -> writer.id != selectedWriter.id)>
                    <option selected value="${selectedWriter.id}">${selectedWriter.toString()}</option>
                <#else>
                    <option selected disabled>Choose Author</option>
                </#if>
                <#list writers as writer>
                    <option value="${writer.id}">${writer.toString()}</option>
                </#list>
            </select>
            <div class="input-group-append">
                <a class="btn btn-primary" href="/writers/add" role="button">New</a>
            </div>
            <#if selectedWriterError??>
                <div class="invalid-feedback">
                    ${selectedWriterError}
                </div>
            </#if>
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
        <div class="col">
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
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="posterFileAddon">Poster</span>
                    </div>
                    <div class="custom-file">
                        <label class="custom-file-label" for="posterFile">Choose file</label>
                        <input class="custom-file-input" type="file" name="posterFile"
                               accept="image/*" id="posterFile" aria-describedby="posterFileAddon"
                        />
                    </div>
                </div>
                <#if posterFileError??>
                    <div class="invalid-feedback d-block">
                        ${posterFileError}
                    </div>
                </#if>
            </div>
        </div>

    </div>

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/books" role="button">Back</a>
    </div>

</form>

</#macro>