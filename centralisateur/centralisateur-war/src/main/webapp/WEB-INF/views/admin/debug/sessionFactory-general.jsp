<%@ page language="java" pageEncoding="ISO-8859-1"
	contentType="text/html;charset=ISO-8859-1"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<center>
    <h1>Session Factory - général</h1>
</center>
<hr />
<br />

<table border="1">
	<tr>
		<td><b>Nom</b></td>
		<td><b>Valeur</b></td>
	</tr>

	<c:forEach items="${general}" var="var">
		<tr>
			<td>${var[0]}</td>
			<td>${var[1]}</td>
		</tr>
	</c:forEach>
</table>