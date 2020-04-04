<#import "/parts/common.ftl" as c>

<#import "/ui/ui.ftl" as ui>
<#import "writerAdminForm.ftl" as addForm>

<@c.page>

<@addForm.writerAdminForm
    writer  = (writer??)?then(writer, {})
    action  = "/writers"
/>

</@c.page>