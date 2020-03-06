<#import "parts/common.ftl" as c>

<@c.page>


<form action="/books/add" method="post" >
    <!--Book Name-->
    <div class="form-group row">
        <label for="bookNameInput" class="col-md-2 col-form-label">Book name:</label>
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
        <textarea class="form-control ${(annotationError??)?string('is-invalid', '')}" name="annotation"
                  id="annotationTextArea" placeholder="Book annotation" rows="3" >
        <#if book??>
            ${book.annotation}
        </#if></textarea>

        <#if annotationError??>
            <div class="invalid-feedback">
                ${annotationError}
            </div>
        </#if>
    </div>

    <!--Genres-->
    <div class="form-group row mx-auto">
        <div class="btn-group ${(genresError??)?string('is-invalid', '')}" role="group">
            <#list genres as genre>
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="form-check form-check-inline">
                            <label class="form-check-label" for="${genre}">${genre}</label>
                            <input class="form-check-input "
                                   name="${genre}" type="checkbox" id="${genre}"
                                   <#if book??>
                                   ${book.genres?seq_contains(genre)?string("checked", "")}
                                    </#if>
                            />
                        </div>
                    </div>
                </div>
            </#list>
        <#if genresError??>
            <div class="invalid-feedback">
                ${genresError}
            </div>
        </#if>
        </div>
    </div>

    <div class="form-group row">
        <!--Publication date-->
        <label for="publicationDateInput" class="col-form-label col-md-2">Publication date:</label>
        <div class="form-group col-md-3" >
            <input class="form-control ${(publicationDateError??)?string('is-invalid', '')}" type="date"
                   name="publicationDate" id="publicationDateInput"
            />
            <#if publicationDateError??>
                <div class="invalid-feedback">
                    ${publicationDateError}
                </div>
            </#if>
        </div>

        <!--File Chooser-->
        <div class="form-group col-md-7">
            <div class="input-group">
                <div class="input-group-prepend">
                    <span class="input-group-text" id="posterFileAddon">Poster</span>
                </div>
                <div class="custom-file">
                    <label class="custom-file-label" for="posterFile">Choose file</label>
                    <input class="custom-file-input ${(posterFileError??)?string('is-invalid', '')}"
                           type="file" name="posterFile"  id="posterFile" aria-describedby="posterFileAddon"
                    />
                    <#if posterFileError??>
                        <div class="invalid-feedback">
                            ${posterFileError}
                        </div>
                    </#if>
                </div>
            </div>
        </div>

    </div>

    <!--Author drop list-->
    <div class="input-group mb-3">
        <select class="custom-select" name="selectedWriter" id="selectedWriter" required>
            <option selected disabled>Choose Author</option>
            <!--Проход по авторам!-->
            <#list writers as writer>
                <option name="${writer.id}">${writer.toString()}</option>
            </#list>
        </select>
        <div class="invalid-feedback">
            Please, select an author or create a new one
        </div>
        <div class="input-group-append">
            <a class="btn btn-primary" href="/writers/add" role="button" >New</a>
        </div>
    </div>


    <!--Buttons-->
    <div class="form-group row mx-auto">
        <button class="btn btn-primary" type="submit">Save</button>
        <a class="btn btn-primary align-self-end ml-auto" href="/books" role="button">Back</a>
    </div>


    <input type="hidden" name="_csrf" value="${_csrf.token}" />

</form>

</@c.page>
