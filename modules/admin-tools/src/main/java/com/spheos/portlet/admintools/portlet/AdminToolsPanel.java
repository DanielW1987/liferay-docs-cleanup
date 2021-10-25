package com.spheos.portlet.admintools.portlet;

import com.liferay.application.list.BasePanelApp;
import com.liferay.application.list.PanelApp;
import com.liferay.application.list.constants.PanelCategoryKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.spheos.portlet.admintools.constants.AdminToolsPortletKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

@Component(
        immediate = true,
        property = {
                "panel.app.order:Integer=0",
                "panel.category.key=" + PanelCategoryKeys.CONTROL_PANEL_CONFIGURATION
        },
        service = PanelApp.class)
public class AdminToolsPanel extends BasePanelApp {

    @Override
    public String getPortletId() {
        return AdminToolsPortletKeys.ADMINTOOLS;
    }

    @Override
    @Reference(target = "(javax.portlet.name=" + AdminToolsPortletKeys.ADMINTOOLS + ")", unbind = "-")
    public void setPortlet(Portlet portlet) {
        super.setPortlet(portlet);
    }
}
