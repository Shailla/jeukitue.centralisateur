<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="welcomeAdmin.titre" /></h1></center>
<hr />
<br />

<h2><fmt:message key="global.actions" /></h2>
<br />
<a href="../secure/downloads.do"><fmt:message key="global.telechargements" /></a>
<br />
<a href="../secure/news.do"><fmt:message key="lien.voirLesNews" /></a>
<hr />
<h2><fmt:message key="global.administration" /></h2>
<br />
<a href="../secure/admin/manage_users.do"><fmt:message key="lien.gestionUtilisateur" /></a>
<br />
<a href="../secure/admin/add_news.do"><fmt:message key="lien.ajoutNews" /></a>
<br />
<a href="../secure/admin/manage_downloads.do"><fmt:message key="lien.gestionTelechargements" /></a>
<br />
<a href="../secure/admin/servicesScoreboard.do"><fmt:message key="lien.scoreboard" /></a>
<br />
<a href="../secure/admin/supervision.do"><fmt:message key="lien.supervision" /></a>


