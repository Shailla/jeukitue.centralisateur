<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="serviceChat.titre" /></h1></center>
<hr />
<br />

<table border="1">
    <tr>
        <th><fmt:message key="global.nom" /></th>
        <th><fmt:message key="global.adresseIp" /></th>
        <th><fmt:message key="global.port" /></th>
        <th><fmt:message key="global.ping" /> (<fmt:message key="global.milliseconds" />)</th>
    </tr>
    <c:forEach var="entry" items="${clients}">
        <tr>
            <td>${entry.key.clientName}</td>
            <td>${entry.key.hostAddress}</td>
            <td>${entry.key.port}</td>
            <td>${entry.value.lastPingValue}</td>
            <td>
               <form action="serviceChat.do" method="get">
                   <input type="hidden" name="clientName" value="${entry.key.clientName}" />
                   <input type="hidden" name="clientIp" value="${entry.key.hostAddress}" />
                   <input type="hidden" name="clientPort" value="${entry.key.port}" />
                   <input type="submit" name="ping" value="<fmt:message key='global.ping' />">
               </form>
            </td>
        </tr>
    </c:forEach>
</table>

    
<h2><fmt:message key="serviceChat.subtitle1" /></h2>
<hr />