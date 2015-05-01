<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="servicesScoreboard.titre" /></h1></center>
<hr />
<br />

<table>
    <tr>
        <td>
			<form action="servicesScoreboard.do" method="get">
			    <input type="submit" name="startAllServices" value="<fmt:message key='servicesScoreboard.startAllServices' />" />
			</form>
		</td>
		<td>	
			<form action="servicesScoreboard.do" method="get">
			    <input type="submit" name="stopAllServices" value="<fmt:message key='servicesScoreboard.stopAllServices' />" />
			</form>
		</td>
	</tr>
</table>

<br />

<!-- Etats des services et actions sur les services -->
<table border="1">
    <tr>
        <th><fmt:message key="global.nomDuService" /></th>
        <th><fmt:message key="global.state" /></th>
    </tr>
    <c:forEach var="entry" items="${servicesStates}">
        <tr>
            <td>${entry.key}</td>
            <c:choose>
                <c:when test="${entry.value.starting}">
                    <td><font color="#0000FF"><fmt:message key="global.starting" /></font></td>
                    <td>
	                    <form action="servicesScoreboard.do" method="get">
	                        <input type="hidden" name="serviceName" value="${entry.key}" />
	                        <input type="submit" name="stopOneService" value="<fmt:message key='global.stop' />" />
	                    </form>
                    </td>
                </c:when>
                <c:when test="${entry.value.stopping}">
                    <td><font color="#FFFF00"><fmt:message key="global.stopping" /></font></td>
                    <td>
                        <form action="servicesScoreboard.do" method="get">
                            <input type="hidden" name="serviceName" value="${entry.key}" />
                            <input type="submit" name="stopOneService" value="<fmt:message key='global.stop' />" />
                        </form>
                    </td>
                </c:when>
                <c:when test="${entry.value.started}">
                    <td><font color="#00FF00"><fmt:message key="global.started" /></font></td>
                    <td>
                        <form action="servicesScoreboard.do" method="get">
                            <input type="hidden" name="serviceName" value="${entry.key}" />
                            <input type="submit" name="stopOneService" value="<fmt:message key='global.stop' />" />
                        </form>
                    </td>
                </c:when>
                <c:otherwise>
                    <td><font color="#FF0000"><fmt:message key="global.stopped" /></font></td>
                    <td>
                        <form action="servicesScoreboard.do" method="get">
                            <input type="hidden" name="serviceName" value="${entry.key}" />
                            <input type="submit" name="startOneService" value="<fmt:message key='global.start' />" />
                        </form>
                    </td>
                </c:otherwise>
            </c:choose>
        </tr>
    </c:forEach>        
</table>
