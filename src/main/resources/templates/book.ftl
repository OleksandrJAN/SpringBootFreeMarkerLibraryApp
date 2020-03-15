<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">


<@c.page>

<form>
    <div class="form-group row">

        <div class="col">
            <!--Book Name-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="bookNameInput">Book name:</label>
                <div class="col">
                    <input readonly class="form-control" type="text" name="bookName"
                           value="${book.bookName}" id="bookNameInput"
                    />
                </div>
            </div>

            <!--Annotation-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="annotationTextArea">Book annotation:</label>
                <!--if you move it to multiple lines, you will see extra spaces and line breaks at the start of the annotation-->
                <div class="col">
                    <textarea readonly class="form-control" name="annotation" rows="6" id="annotationTextArea">${book.annotation}</textarea>
                </div>
            </div>

            <!--Author drop list-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="selectedWriter">Writer:</label>
                <div class="col">
                    <select class="custom-select" name="selectedWriter" id="selectedWriter">
                        <option value="${book.writer.id}" selected disabled>
                            ${book.writer.toString()}
                        </option>
                    </select>
                </div>
            </div>

            <!--Genres-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label">Genres:</label>
                <div class="col">
                    <div class="form-control">
                        <div class="btn-group" role="group">
                            <#list book.genres as genre>
                                <div class="form-check form-check-inline">
                                    <input checked disabled class="form-check-input" type="checkbox"
                                           name="${genre}" id="${genre}"
                                    />
                                    <label class="form-check-label" for="${genre}">${genre}</label>
                                </div>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>

            <!--Publication date-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="publicationDateInput" >Publication date:</label>
                <div class="col">
                    <input readonly class="form-control" type="date" name="publicationDate" id="publicationDateInput"
                           value="${(book.publicationDate)?string('yyyy-MM-dd')}"
                    />
                </div>
            </div>

        </div>

        <!--Poster File-->
        <div class="col-md-3">
            <img src="/img/${book.filename}">
        </div>

    </div>
</form>

<!--Reviews-->


</@c.page>