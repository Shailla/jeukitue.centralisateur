<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="supervision.title" /></h1></center>
<hr />
<br />

<table border="1">
    <table>
        <tr>
            <td>
               <form action="supervision.do" method="get">
                   <input type="submit" name="clear" value="<fmt:message key='global.vider' />" />
               </form>
            </td>
        </tr>
    </table>
    
    <table border="1">
		<c:forEach var="message" items="${messages}">
			<tr>
				<td><fmt:formatDate value="${message.date}" pattern="dd/MM/yyyy hh:mm:ss" /></td>
				<td>
				<c:choose>
				    <c:when test="${message.error}">
				        <td><font color="#FF0000">${message.message}</font></td>
                    </c:when>
                    <c:when test="${message.warning}">
                        <td><font color="#FFCC00">${message.message}</font></td>
                    </c:when>
                    <c:when test="${message.info}">
                        <td><font color="#000000">${message.message}</font></td>
                    </c:when>
				</c:choose>
              </td>
			</tr>
		</c:forEach>
    </table>
</table>
<hr />

