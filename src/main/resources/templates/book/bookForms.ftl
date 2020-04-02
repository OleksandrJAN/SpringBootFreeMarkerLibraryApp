<#macro bookAddForm action putAction=false selectedWriter="">
<form action="${action}" method="post" enctype="multipart/form-data">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <#if putAction>
        <input type="hidden" name="_method" value="PUT"/>
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
    <div class="form-group row">
        <label class="col-md-2 col-form-label" for="bookNameInput">Book name:</label>
        <div class="col">
            <input class="form-control ${(bookNameError??)?string('is-invalid', '')}"
                   value="<#if (book.bookName)??>${book.bookName}</#if>"
                   type="text" name="bookName" placeholder="Book name" id="bookNameInput"
            />
            <#if bookNameError??>
                <div class="invalid-feedback">
                    ${bookNameError}
                </div>
            </#if>
        </div>
    </div>

    <!--Annotation-->
    <div class="form-group">
        <label for="annotationTextArea">Book annotation:</label>
        <textarea class="form-control ${(annotationError??)?string('is-invalid', '')}" name="annotation" rows="6"
                  id="annotationTextArea" placeholder="Book annotation"><#if (book.annotation)??>${book.annotation}</#if></textarea>
        <#if annotationError??>
            <div class="invalid-feedback">
                ${annotationError}
            </div>
        </#if>
    </div>

    <!--Genres-->
    <div class="form-group row mx-auto">
        <div class="form-control ${(genresError??)?string('is-invalid', '')}">
            <div class="btn-group" role="group">
                <#list genres as genre>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="form-check form-check-inline">
                            <input class="form-check-input" name="${genre}" type="checkbox" id="${genre}"
                            <#if book??>${book.genres?seq_contains(genre)?string("checked", "")}</#if>
                            />
                            <label class="form-check-label" for="${genre}">${genre}</label>
                        </div>
                    </div>
                </div>
                </#list>
            </div>
        </div>
        <#if genresError??>
            <div class="invalid-feedback">
                ${genresError}
            </div>
        </#if>
    </div>

    <div class="form-group row">
        <!--Publication date-->
        <div class="col">
            <div class="form-group row">
                <label class="col-md-4 col-form-label" for="publicationDateInput" >Publication date:</label>
                <div class="col">
                    <input class="form-control ${(publicationDateError??)?string('is-invalid', '')}" type="date"
                           name="publicationDate" id="publicationDateInput"
                           value="<#if (book.publicationDate)??>${(book.publicationDate)?string('yyyy-MM-dd')}</#if>"
                    />
                    <#if publicationDateError??>
                        <div class="invalid-feedback">
                            ${publicationDateError}
                        </div>
                    </#if>
                </div>
            </div>
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