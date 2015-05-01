<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<title><fmt:message key="downloads.titre" /></title>
<center><h1><fmt:message key="downloads.titre" /></h1></center>
<hr />
<br />

<c:forEach var="download" items="${downloads}">
    <table border="1">
        <tr>
            <th><fmt:message key="global.nom" /></th>
            <th><fmt:message key="global.categorie" /></th>
            <th><fmt:message key="global.version" /></th>
            <th><fmt:message key="global.taille" /></th>
        </tr>
        <tr>
            <td>${download.nom}</td>
            <td><fmt:message key="download.category.${download.categorie.name}" /></td>
            <td>${download.version}</td>
            <td>${download.version}</td>
            <td>${download.taille} <fmt:message key="global.octets" /></td>
            <td>
                <a href="/centralisateur-war/secure/download.do?fichier=${download.nomFichier}"><fmt:message key="global.telecharger" /></a>
            </td>
        </tr>
    </table>
</c:forEach>