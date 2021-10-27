<%@ page import="com.spheos.portlet.admintools.constants.MvcCommandNames" %>
<%@ include file="/init.jsp" %>

<div class="container-fluid-1280">
	<h1><liferay-ui:message key="admintools.caption"/></h1>
	<portlet:actionURL name="<%=MvcCommandNames.REMOVE_PREVIOUS_DOCUMENT_VERSIONS %>" var="removePreviousVersionsURL">
		<portlet:param name="redirect" value="${currentURL}" />
	</portlet:actionURL>

	<aui:button-row>
		<aui:button onClick="${removePreviousVersionsURL.toString()}" value="Remove previous document versions" />
	</aui:button-row>
</div>
