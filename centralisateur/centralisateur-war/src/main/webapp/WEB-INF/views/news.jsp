<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="global.news" /></h1></center>
<hr />
<br />

<c:forEach var="newVar" items="${news}">
    <table>
        <tr>
            <th><fmt:formatDate value="${newVar.date}" /></th>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>${newVar.text}</td>
        </tr>
    </table>
    
    <sec:authorize access="hasRole('ROLE_ADMIN')">
	    <table>
	        <tr>
	            <td>TODO:modifier</td>
	            <td>TODO:supprimer</td>
	        </tr>
	    </table>        
	</sec:authorize>    
    <hr />
</c:forEach>
