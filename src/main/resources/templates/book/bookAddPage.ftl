<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "bookAdminForm.ftl" as addForm>

<@c.page>

<@addForm.bookAdminForm
    book    = (book??)?then(book, {})
    action  = "/books"
/>

</@c.page>

