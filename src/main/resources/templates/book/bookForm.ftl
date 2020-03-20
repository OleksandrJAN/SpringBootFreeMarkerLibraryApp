<!--update request-->
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
                <div class="col">
                    <textarea readonly class="form-control" name="annotation" rows="6" id="annotationTextArea">${book.annotation}</textarea>
                </div>
            </div>

            <!--Author-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="selectedWriter">Writer:</label>
                <div class="col">
                    <input readonly class="form-control" type="text" name="selectedWriter"
                           value="${book.writer.toString()}" id="selectedWriter"
                    />
                </div>
            </div>

            <!--Genres-->
            <div class="form-group row">
                <label class="col-md-3 col-form-label" for="bookGenres">Genres:</label>
                <div class="col">
                    <div class="form-control" id="bookGenres">
                        <#list book.genres as genre>${genre}<#sep>, </#list>
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

            <!--Reviews-->
            <div class="form-group">
                <a class="btn btn-primary" href="${book.id}/reviews" role="button">Reviews</a>
            </div>

        </div>

        <!--Poster File-->
        <div class="col-md-3">
            <img src="/img/${book.filename}">
        </div>

    </div>

</form>