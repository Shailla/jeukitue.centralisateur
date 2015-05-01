<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="addDownload.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="addDownload.titre" /></h1></center>
        <hr />

	<form:form modelAttribute="downloadFormulaire" action="save_download.do">
            <table border="0">
                <tr>
                    <td><fmt:message key="global.fichier" /></td>
                    <td><form:select path="fichier" items="${downloadFormulaire.deployableFiles}" /></td>
	                <td><form:errors path="fichier" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.nom" /></td>
                    <td><form:input path="nom" /></td>
                    <td><form:errors path="nom" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><fmt:message key="global.version" /></td>
                    <td><form:input path="version" /></td>
                    <td><form:errors path="version" cssClass="error" /></td>
                </tr>
                <tr>
	                <td><fmt:message key="global.categorie" /></td>
                    <td>
                        <form:select path="categorie">
                            <c:forEach var="var" items="${categories}">
                                <form:option value="${var.name}">
                                    <fmt:message key="download.category.${var.name}" />
                                </form:option>
                            </c:forEach>
                        </form:select>
                    </td>
                    <td><form:errors path="categorie" cssClass="error" /></td>
                </tr>
                <tr>
	                <td><fmt:message key="global.compatibilite" /></td>
                    <td><form:input path="compatibilite" /></td>
                    <td><form:errors path="compatibilite" cssClass="error" /></td>
                </tr>

                <tr>
                    <td><fmt:message key="global.description" /></td>
                    <td><form:input path="description" /></td>
                    <td><form:errors path="description" cssClass="error" /></td>
                </tr>
                <tr>
                    <td><input type="submit"  value="<fmt:message key='global.enregistrer' />" /></td>
                </tr>                
            </table>
        </form:form>
                
    </body>
</html>