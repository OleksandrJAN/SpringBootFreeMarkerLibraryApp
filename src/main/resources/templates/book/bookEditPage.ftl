<#import "/parts/common.ftl" as c>
<#import "/ui/ui.ftl" as ui>
<#import "bookAdminForm.ftl" as editForm>

<@c.page>

<@editForm.bookAdminForm
    book            = editedBook
    action          = "/books/${currentBook.id}"
    putAction       = true
/>

</@c.page>
