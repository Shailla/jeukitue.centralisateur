<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="manageUsers.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="manageUsers.titre" /></h1></center>
        <hr />
        <table border="1">
            <tr>
                <th><fmt:message key="global.nomDUtilisateur" /></th>
                <th><fmt:message key="global.email" /></th>
                <th><fmt:message key="global.privileges" /></th>
            </tr>
            <c:forEach var="user" items="${users}">
                <tr>
                    <td>${user.login}</td>
                    <td>${user.email}</td>
                    <td>
                        <c:forEach var="role" items="${user.roles}" varStatus="status" ><c:if test="${status.index != 0}">, </c:if>${role}</c:forEach>
                    </td>
                    <td><a href="modify_user.do?user_id=${user.userId}"><fmt:message key="global.modifier" /></a></td>
                    <td><a href="remove_user.do?user_id=${user.userId}&user_login=${user.login}"><fmt:message key="global.supprimer" /></a></td>
                </tr>
            </c:forEach>
        </table>

        <br />
        <a href="add_user.do"><fmt:message key="global.ajouter" /></a>
        
    </body>
</html>