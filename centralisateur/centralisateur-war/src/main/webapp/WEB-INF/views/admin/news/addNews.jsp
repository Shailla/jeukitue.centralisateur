<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="addNews.titre" /></h1></center>
<hr />
<br />

<form:form modelAttribute="newsDto" action="save_news.do">
    <table border="0">
        <tr>
            <td><fmt:message key="global.texte" /></td>
            <td>
                <form:textarea path="text" rows="15" cols="50"></form:textarea>
                <form:errors path="text" cssClass="error" />
            </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <input type="submit" value="<fmt:message key='global.enregistrer' />" />
            </td>
        </tr>                
    </table>
    
</form:form>
