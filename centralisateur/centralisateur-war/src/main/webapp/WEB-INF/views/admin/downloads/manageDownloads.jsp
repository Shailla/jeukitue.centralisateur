<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="manageDownloads.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="manageDownloads.titre" /></h1></center>
        <hr />
        <table border="1">
            <tr>
                <th><fmt:message key="global.nom" /></th>
                <th><fmt:message key="global.categorie" /></th>
                <th><fmt:message key="global.compatibilite" /></th>
                <th><fmt:message key="global.version" /></th>
                <th><fmt:message key="global.taille" /></th>
            </tr>
            <c:forEach var="download" items="${downloads}">
                <tr>
                    <td>${download.nom}</td>
                    <td><fmt:message key="download.category.${download.categorie.name}" /></td>
                    <td>${download.compatibilite}</td>
                    <td>${download.version}</td>
                    <td>${download.taille}</td>
                    <td>
                        <button name="modify_download"><fmt:message key="global.modifier" /></button>
                    </td>
                    <td>
                        <a href="activate_download.do?downloadId=${download.id}"><fmt:message key="global.activer" /></a>
                    </td>
                    <td>
                        <a href="deactivate_download.do?downloadId=${download.id}"><fmt:message key="global.desactiver" /></a>
                    </td>
                    <td>
                        <a href="remove_download.do?downloadId=${download.id}"><fmt:message key="global.supprimer" /></a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        <br />
        <a href="add_download.do"><fmt:message key="global.ajouter" /></a>
        
    </body>
</html>