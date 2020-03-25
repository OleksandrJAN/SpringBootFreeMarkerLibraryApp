<#import "/parts/common.ftl" as c>
<#import "bookForms.ftl" as bf>

<@c.page>

<@bf.bookAddForm
    action          = "/books/${currentBookId}"
    putAction       = true
    selectedWriter  = book.writer
/>

</@c.page>
