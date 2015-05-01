<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="addUserConfirmation.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="addUserConfirmation.titre" /></h1></center>
        <hr />

        <fmt:message key="addUserConfirmation.message" />
        <br />
        <table border="1">
            <tr>
                <th><fmt:message key="global.nom" /></th>
                <td>${formulaire.nom}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.version" /></th>
                <td>${formulaire.version}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.categorie" /></th>
                <td>${formulaire.categorie}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.compatibilite" /></th>
                <td>${formulaire.compatibilite}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.taille" /></th>
                <td>${formulaire.taille} <fmt:message key="global.octets" /></td>
            </tr>
            <tr>
                <th><fmt:message key="global.typeMime" /></th>
                <td>${formulaire.typeMime}</td>
            </tr>

        </table>
        
        <br />
        <a href="/centralisateur-war/secure/admin/manage_downloads.do"><fmt:message key="lien.gestionTelechargements" /></a>        
        
    </body>
</html>