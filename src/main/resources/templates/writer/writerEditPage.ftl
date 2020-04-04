<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "writerAdminForm.ftl" as editForm>

<@c.page>

<@editForm.writerAdminForm
    writer      = editedWriter
    action      = "/writers/" + currentWriter.id
    putAction   = true
/>

</@c.page>