package com.spheos.portlet.admintools.portlet;

import com.spheos.portlet.admintools.constants.AdminToolsPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=category.hidden",
				"com.liferay.portlet.scopeable=true",
				"javax.portlet.display-name=AdminTools",
				"javax.portlet.expiration-cache=0",
				"javax.portlet.init-param.portlet-title-based-navigation=true",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/view.jsp",
				"javax.portlet.name=" + AdminToolsPortletKeys.ADMINTOOLS,
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=administrator",
				"javax.portlet.supports.mime-type=text/html",
				"com.liferay.portlet.add-default-resource=true",
		},
		service = Portlet.class
)
public class AdminToolsPortlet extends MVCPortlet {
}