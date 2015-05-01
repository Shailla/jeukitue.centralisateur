<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="global.news" /></h1></center>
<hr />
<br />

<c:forEach var="new" items="${news}">
    <table>
        <tr>
            <th><fmt:formatDate value="${new.date}" /></th>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td>${new.text}</td>
        </tr>
    </table>
    <c:if test="<%= (Boolean)session.getAttribute("isAdmin") %>">
	    <table>
	        <tr>
	            <td>TODO:modifier</td>
	            <td>TODO:supprimer</td>
	        </tr>
	    </table>        
    </c:if>
    <hr />
</c:forEach>
