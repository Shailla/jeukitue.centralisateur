<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="inscription.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="inscription.titre" /></h1></center>
        <hr />

        <form:form commandName="formulaire">
            <table border="0">
                <tr>
                    <spring:bind path="formulaire.login">
                        <td><fmt:message key="global.nomDUtilisateur" /></td>
                        <td><input type="text" name="${status.expression}" value="${status.value}" /></td>
                        <td>${status.errorMessage}</td>
                    </spring:bind>
                </tr>
                <tr>
                    <spring:bind path="formulaire.password">
                        <td><fmt:message key="global.motDePasse" /></td>
                        <td><input type="password" name="${status.expression}" value="${status.value}" /></td>
                        <td>${status.errorMessage}</td>
                    </spring:bind>
                </tr>
                <tr>
                    <spring:bind path="formulaire.email">
                        <td><fmt:message key="global.email" /></td>
                        <td><input type="text" name="${status.expression}" value="${status.value}" /></td>
                        <td>${status.errorMessage}</td>
                    </spring:bind>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="<fmt:message key='global.enregistrer' />" />
                    </td>
                </tr>                
            </table>

            <spring:hasBindErrors name="formulaire">
                <ul>
                    <c:forEach var="errMsgObj" items="${errors.globalErrors}">
                        <li><spring:message code="${errMsgObj.code}" text="${errMsgObj.defaultMessage}" /></li>
                    </c:forEach>
                </ul>
            </spring:hasBindErrors>

        </form:form>
    </body>
</html>