package com.spheos.service.admintools.api;

import com.liferay.portal.kernel.exception.PortalException;

public interface DocumentsAndMediaService {

    void removePreviousVersions() throws PortalException;
}
