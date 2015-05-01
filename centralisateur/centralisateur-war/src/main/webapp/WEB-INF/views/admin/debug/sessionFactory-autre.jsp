<%@ page language="java" pageEncoding="ISO-8859-1"
	contentType="text/html;charset=ISO-8859-1"%>
<%@ include file="/WEB-INF/views/include.jsp"%>

<center>
    <h1>Session Factory - ${title}</h1>
</center>
<hr />
<br />

<table border="1" title="${title}">
    <!-- Affichage des titres des colonnes -->
	<tr>
		<c:forEach items="${items[0]}" var="var">
			<td><b>${var}</b></td>
		</c:forEach>
	</tr>

    <!-- Affichage des valeurs des colonnes -->
	<c:forEach items="${items}" var="var" begin="1">
		<tr>
			<c:forEach items="${var}" var="item">
				<td>${item}</td>
			</c:forEach>
		</tr>
	</c:forEach>
</table>