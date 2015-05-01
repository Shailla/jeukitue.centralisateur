<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="addNewsConfirmation.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="addNewsConfirmation.titre" /></h1></center>
        <hr />

        <fmt:message key="addNewsConfirmation.message" />
        <br />
        <table border>
            <tr>
                <th><fmt:message key="global.date" /></th>
                <td><fmt:formatDate value="${new.date}" />
            </tr>
            <tr>
                <th><fmt:message key="global.texte" /></th>
                <td>${formulaire.text}</td>
            </tr>
        </table>
        
        <br />
        <a href="/centralisateur-war/secure/news.do"><fmt:message key="lien.voirLesNews" /></a>
        <br />
        <a href="/centralisateur-war/secure/welcome.do"><fmt:message key="lien.retourWelcomeAdmin" /></a>        
        
    </body>
</html>