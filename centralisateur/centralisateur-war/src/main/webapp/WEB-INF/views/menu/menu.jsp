<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<h2><fmt:message key="global.actions" /></h2>
<table border="0">
    <tr><td>
        <a href="/centralisateur-war/secure/welcome.do"><fmt:message key="global.accueil" /></a>
    </td></tr>
    <tr><td>
        <a href="/centralisateur-war/secure/downloads.do"><fmt:message key="global.telechargements" /></a>
    </td></tr>
    <tr><td>
        <a href="/centralisateur-war/secure/tchat/list.do"><fmt:message key="lien.tchatter" /></a>
    </td></tr>
    <tr><td>
        <a href="/centralisateur-war/secure/news.do"><fmt:message key="lien.voirLesNews" /></a>
    </td></tr>
    <tr><td>
        <a href="/centralisateur-war/secure/testArc.do"><fmt:message key="lien.testArc" /></a>
    </td></tr>
 </table>

<%-- Show the administration menu only if the current user has got admin privilige --%>
<c:if test="<%= (Boolean)session.getAttribute("isAdmin") %>">
	<br />
	<h2><fmt:message key="global.administration" /></h2>
	<table border="0">
	    <tr><td>
		    <a href="/centralisateur-war/secure/admin/manage_users.do"><fmt:message key="lien.gestionUtilisateur" /></a>
	    </td></tr>
	    <tr><td>
		    <a href="/centralisateur-war/secure/admin/add_news.do"><fmt:message key="lien.ajoutNews" /></a>
	    </td></tr>
	    <tr><td>
	        <a href="/centralisateur-war/secure/admin/manage_downloads.do"><fmt:message key="lien.gestionTelechargements" /></a>
	    </td></tr>
	    <tr><td>
		    <a href="/centralisateur-war/secure/admin/servicesScoreboard.do"><fmt:message key="lien.servicesScoreboard" /></a>
	    </td></tr>
	    <tr><td>
		    <a href="/centralisateur-war/secure/admin/supervision.do"><fmt:message key="lien.supervision" /></a>
	    </td></tr>
	    <tr><td>
	        <a href="/centralisateur-war/secure/admin/performanceMonitor.do"><fmt:message key="lien.performanceMonitor" /></a>
	    </td></tr>
    </table>
</c:if>
