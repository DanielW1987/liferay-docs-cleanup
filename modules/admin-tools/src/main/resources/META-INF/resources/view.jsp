<%@ page import="com.spheos.portlet.admintools.constants.MvcCommandNames" %>
<%@ include file="/init.jsp" %>

<div class="container-fluid-1280">
	<p>
		<b><liferay-ui:message key="admintools.caption"/></b>
	</p>


	<portlet:actionURL name="<%=MvcCommandNames.DELETE_UNUSED_DOCUMENTS %>" var="deleteUnusedDocumentsURL">
		<portlet:param name="redirect" value="${currentURL}" />
	</portlet:actionURL>

	<aui:button-row>
		<aui:button onClick="${deleteUnusedDocumentsURL.toString()}" value="Delete unused documents" />
	</aui:button-row>
</div>
