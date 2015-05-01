<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1>Session Factory</h1></center>
<hr />
<br />

<c:choose>
    <c:when test="${activated}">
        <b><a href="/centralisateur-war/debug/sessionfactory/activate.do">Désactiver</a></b>
	</c:when>
	
	<c:otherwise>
	   <b><a href="/centralisateur-war/debug/sessionfactory/activate.do">Activer</a></b>
	</c:otherwise>
</c:choose>
<br />
<b><a href="/centralisateur-war/debug/sessionfactory/clear.do">Réinitialiser les statistiques</a></b>
<br />
<br />

<a href="/centralisateur-war/debug/sessionfactory/general.do">Statistiques générales</a>
<br />

<a href="/centralisateur-war/debug/sessionfactory/collections.do">Statistiques sur les collections</a>
<br />

<a href="/centralisateur-war/debug/sessionfactory/entities.do">Statistiques sur les entités</a>
<br />

<a href="/centralisateur-war/debug/sessionfactory/queries.do">Statistiques sur les requêtes</a>
<br />

<a href="/centralisateur-war/debug/sessionfactory/regions.do">Statistiques sur les régions</a>
