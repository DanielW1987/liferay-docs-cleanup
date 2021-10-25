package com.spheos.service.admintools;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.spheos.service.admintools.api.DocumentsAndMediaService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

@Component(
	immediate = true
)
public class DocumentsAndMediaServiceImpl implements DocumentsAndMediaService {

	private static final Log log = LogFactoryUtil.getLog(DocumentsAndMediaServiceImpl.class);

	@Reference
	private GroupLocalService groupService;

	@Reference
	private CompanyLocalService companyLocalService;

	@Reference
	private DLAppService dlAppService;

	@Override
	public void removePreviousVersions() throws PortalException {
		for (Company company : companyLocalService.getCompanies()) {
			log.info(String.format("Processing company '%s' (%s)", company.getName(), company.getCompanyId()));
			List<Group> groups = groupService.getGroups(company.getCompanyId(), GroupConstants.ANY_PARENT_GROUP_ID, true);
			for (Group group : groups) {
				log.info(String.format("Processing group '%s' (%s)", group.getDescriptiveName(), group.getGroupId()));
				processFolder(group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
			}
		}
	}

	private void processFolder(long repositoryId, long folderId) throws PortalException {
		removeUnusedDocuments(repositoryId, folderId);
		List<Folder> folders = dlAppService.getFolders(repositoryId, folderId);
		for (Folder folder : folders) {
			log.info(String.format("Processing folder '%s'", folder.getName()));
			processFolder(repositoryId, folder.getFolderId());
		}
	}

	private void removeUnusedDocuments(long repositoryId, long folderId) throws PortalException {
		List<FileEntry> files = dlAppService.getFileEntries(repositoryId, folderId);
		for (FileEntry file : files) {
			log.info(String.format("Processing file '%s' (%s)", file.getTitle(), file.getVersion()));
			List<FileVersion> results = file.getFileVersions(-1);
			String latestVersion = file.getVersion();
			for (FileVersion fileVersion : results) {
				if (!fileVersion.getVersion().equals(latestVersion) && !fileVersion.isDraft()) {
					log.info(String.format("Deleting '%s (%s)'", file.getTitle(), fileVersion.getVersion()));
					dlAppService.deleteFileVersion(file.getFileEntryId(), fileVersion.getVersion());
				}
			}
		}
	}
}