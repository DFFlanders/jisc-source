<%@page import="java.util.*,
                java.text.*,
                blackboard.data.*,
                blackboard.persist.*,
                blackboard.db.*,
                blackboard.base.*,
                blackboard.platform.*,
                blackboard.platform.log.*,
                blackboard.platform.session.*,
                blackboard.platform.persistence.*,
                blackboard.platform.plugin.*,
                blackboard.platform.db.*" 
                
        errorPage="/error.jsp"
%>


<%@ taglib uri="/bbData" prefix="bbData"%>                
<%@ taglib uri="/bbUI" prefix="bbUI"%>

<bbData:context id="ctx">

<bbUI:docTemplate title="Content System Reports">
<bbUI:breadcrumbBar handle="admin_plugin_manage">
</bbUI:breadcrumbBar>

<bbUI:titleBar iconUrl="/images/ci/icons/tools_u.gif">Configure Content System Reports</bbUI:titleBar>
<font face='arial,helvetica' size='2'>There are no configuration options for this plugin.</font>

<div ALIGN="RIGHT"><bbUI:button name="ok" type="FORM_ACTION" alt="OK" action="LINK" targetUrl='/webapps/blackboard/admin/manage_plugins.jsp'/></div>

</bbUI:docTemplate>
</bbData:context>
