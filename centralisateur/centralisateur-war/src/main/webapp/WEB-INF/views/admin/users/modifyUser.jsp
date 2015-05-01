<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="modifyUser.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="modifyUser.titre" /></h1></center>
        <hr />
        
        <form:form modelAttribute="userDto" action="save_modify_user.do">
            <form:hidden path="userId" />
            
            <table border="0">
                <tr>
                    <td><fmt:message key="global.nomDUtilisateur" /></td>
                    <td><form:input path="login" /></td>
                    <td><form:errors path="login" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.motDePasse" /></td>
                    <td><form:input path="password" value="xxxx" disabled="disabled" /></td>
                    <td><form:errors path="password" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.email" /></td>
                    <td><form:input path="email" /></td>
                    <td><form:errors path="email" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.privileges" /></td>
                    <td>
                        <form:select path="roles" multiple="multiple">
                            <form:option value="ROLE_ADMIN" />
                            <form:option value="ROLE_USER" />
                        </form:select>
                    </td>
                    <td><form:errors path="roles" cssClass="error" /></td>
                </tr>
                <tr>
                    <td>
                        <input type="submit" value="<fmt:message key='global.enregistrer' />" />
                    </td>
                </tr>                
            </table>
            
        </form:form>
                
    </body>
</html>