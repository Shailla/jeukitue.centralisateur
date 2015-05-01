<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="addUser.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="addUser.titre" /></h1></center>
        <hr />
        
        <form:form modelAttribute="userDto" action="save_user.do">
            <table border="0">
                <tr>
	                <td><fmt:message key="global.nomDUtilisateur" /></td>
	                <td><form:input path="login" /></td>
	                <td><form:errors path="login" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.motDePasse" /></td>
                    <td><form:input path="password" /></td>
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

			<spring:hasBindErrors name="userDto">
				<ul>
					<c:forEach var="errMsgObj" items="${errors.globalErrors}">
						<li><spring:message code="${errMsgObj.code}" text="${errMsgObj.defaultMessage}" /></li>
					</c:forEach>
				</ul>
			</spring:hasBindErrors>

        </form:form>
    </body>
</html>