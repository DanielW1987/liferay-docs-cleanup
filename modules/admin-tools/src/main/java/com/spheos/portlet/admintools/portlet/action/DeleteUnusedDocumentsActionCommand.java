package com.spheos.portlet.admintools.portlet.action;

import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.spheos.portlet.admintools.constants.AdminToolsPortletKeys;
import com.spheos.portlet.admintools.constants.MvcCommandNames;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import java.util.List;

@Component(
        immediate = true,
        property = {
                "javax.portlet.name=" + AdminToolsPortletKeys.PORTLET_NAME,
                "mvc.command.name=" + MvcCommandNames.DELETE_UNUSED_DOCUMENTS
        },
        service = MVCActionCommand.class
)
public class DeleteUnusedDocumentsActionCommand extends BaseMVCActionCommand {

    private static final Log log = LogFactoryUtil.getLog(DeleteUnusedDocumentsActionCommand.class);
    private static final long ROOT_FOLDER_ID = 0;

    @Reference
    private DLAppService documentService;

    @Override
    protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
        // ToDo: Repository Id ermitteln
        long repositoryId = 20142;
        try {
            processFolder(repositoryId, ROOT_FOLDER_ID);
        }
        catch (PortalException pe) {
            log.error("Error during removing previous versions of documents and media: " + pe.getMessage(), pe);
        }
    }

    private void processFolder(long repositoryId, long folderId) throws PortalException {
        removeUnusedDocuments(repositoryId, folderId);
        List<Folder> folders = documentService.getFolders(repositoryId, folderId);
        for (Folder folder : folders) {
            log.info(String.format("Processing folder '%s'", folder.getName()));
            processFolder(repositoryId, folder.getFolderId());
        }
    }

    private void removeUnusedDocuments(long repositoryId, long folderId) throws PortalException {
        List<FileEntry> files = documentService.getFileEntries(repositoryId, folderId);
        for (FileEntry file : files) {
            log.info(String.format("Processing file '%s' (%s)", file.getTitle(), file.getVersion()));
            List<FileVersion> results = file.getFileVersions(-1);
            String latestVersion = file.getVersion();
            for (FileVersion fileVersion : results) {
                if (!fileVersion.getVersion().equals(latestVersion) && !fileVersion.isDraft()) {
                    log.info(String.format("Deleting '%s (%s)'", file.getTitle(), fileVersion.getVersion()));
                    documentService.deleteFileVersion(file.getFileEntryId(), fileVersion.getVersion());
                }
            }
        }
    }
}
