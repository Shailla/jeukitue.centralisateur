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
        <table border="">
            <tr>
                <th><fmt:message key="global.nomDUtilisateur" /></th>
                <td>${login}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.motDePasse" /></th>
                <td>xxxx</td>
            </tr>
            <tr>
                <th><fmt:message key="global.email" /></th>
                <td>${email}</td>
            </tr>
            <tr>
                <th><fmt:message key="global.privileges" /></th>
                <td>
                    <c:forEach var="role" items="${roles}" varStatus="status" ><c:if test="${status.index != 0}">, </c:if>${role}</c:forEach>
                </td>
            </tr>        
        </table>
        
        <br />
        <a href="/centralisateur-war/secure/admin/manage_users.do"><fmt:message key="lien.retourGestionUtilisateur" /></a>        
        
    </body>
</html>