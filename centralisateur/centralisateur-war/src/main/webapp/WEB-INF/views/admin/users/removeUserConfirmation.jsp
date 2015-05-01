<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="removeUserConfirmation.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="removeUserConfirmation.titre" /></h1></center>
        <hr />

        <fmt:message key="removeUserConfirmation.message">
            <fmt:param value="${username}"></fmt:param>
        </fmt:message>
        
        <br />
        <a href="/centralisateur-war/secure/admin/manage_users.do"><fmt:message key="lien.retourGestionUtilisateur" /></a>        
        
    </body>
</html>