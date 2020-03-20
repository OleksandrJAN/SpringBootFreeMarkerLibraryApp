<#import "/parts/common.ftl" as c>

<@c.page>

<form action="/books" method="post" enctype="multipart/form-data">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />

    <!--Author drop list-->
    <div class="input-group mb-3">
        <select class="custom-select ${(selectedWriterError??)?string('is-invalid', '')}"
                name="selectedWriter" id="selectedWriter">
            <#if selectedWriter??>
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

    <!--Book Name-->
    <div class="form-group row">
        <label class="col-md-2 col-form-label" for="bookNameInput">Book name:</label>
        <div class="col-md-10">
            <input class="form-control ${(bookNameError??)?string('is-invalid', '')}" value="<#if book??>${book.bookName}</#if>"
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
                  id="annotationTextArea" placeholder="Book annotation"><#if book??>${book.annotation}</#if></textarea>
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
        <label for="publicationDateInput" class="col-form-label col-md-2">Publication date:</label>
        <div class="form-group col-md-3">
            <input class="form-control ${(publicationDateError??)?string('is-invalid', '')}" type="date"
                   name="publicationDate" id="publicationDateInput"
                   value="<#if book?? && book.publicationDate??>${(book.publicationDate)?string('yyyy-MM-dd')}</#if>"
            />
            <#if publicationDateError??>
                <div class="invalid-feedback">
                    ${publicationDateError}
                </div>
            </#if>
        </div>

        <!--File Chooser-->
        <!--Bootstrap invalid feedback does not work-->
        <div class="form-group col-md-7">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="posterFileAddon">Poster</span>
                </div>
                <div class="custom-file">
                    <label class="custom-file-label" for="posterFile">Choose file</label>
                    <input class="custom-file-input" type="file" name="posterFile"
                           id="posterFile" aria-describedby="posterFileAddon"
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

    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/books" role="button">Back</a>
    </div>

</form>

</@c.page>
