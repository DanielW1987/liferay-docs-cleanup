package com.spheos.service.admintools.api;

import com.liferay.portal.kernel.exception.PortalException;

public interface DocumentsAndMediaService {

    void removePreviousVersions(Long repositoryId) throws PortalException;
}
