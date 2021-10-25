package com.spheos.portlet.admintools.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.spheos.portlet.admintools.constants.AdminToolsPortletKeys;
import com.spheos.portlet.admintools.constants.MvcCommandNames;
import com.spheos.service.admintools.api.DocumentsAndMediaService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + AdminToolsPortletKeys.PORTLET_NAME,
                "mvc.command.name=" + MvcCommandNames.REMOVE_PREVIOUS_DOCUMENT_VERSIONS
        },
        service = MVCActionCommand.class
)
public class RemovePreviousDocumentVersionsActionCommand extends BaseMVCActionCommand {

    private static final Log log = LogFactoryUtil.getLog(RemovePreviousDocumentVersionsActionCommand.class);

    @Reference
    private DocumentsAndMediaService documentsAndMediaService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) {
        try {
            documentsAndMediaService.removePreviousVersions();
        }
        catch (PortalException pe) {
            log.error("Error during removing previous versions of documents and media: " + pe.getMessage(), pe);
        }
    }
}
