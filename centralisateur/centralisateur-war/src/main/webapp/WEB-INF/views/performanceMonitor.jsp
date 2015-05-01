<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="global.performanceMonitor" /></h1></center>
<hr />
<br />

<c:forEach var="perf" items="${data}">
    <table border="1">
        <tr>
            <th><center>Classe</center></th>
            <th><center>Méthode</center></th>
            <th><center>Nombre d'exécutions</center></th>
            <th><center>Temps moyen (min / max / first)</center></th>
            <th><center>Nbr de collections (min / max)</center></th>
            <th><center>Nbr d'entités (min / max)</center></th>
        </tr>
        <tr>
            <td><center>${perf.key.classe}</center></td>
            <td><center>${perf.key.methode}</center></td>
            <td><center>${perf.value.numberOfExecutions}</center></td>
            <td><center>${perf.value.executionTimeAverage} (${perf.value.minExecutionTime} / ${perf.value.maxExecutionTime} / ${perf.value.firstExecutionTime})</center></td>
            <td><center>${perf.value.collectionCountAverage} (${perf.value.minCollectionCount} / ${perf.value.maxCollectionCount})</center></td>
            <td><center>${perf.value.entityCountAverage} (${perf.value.minEntityCount} / ${perf.value.maxEntityCount})</center></td>
        </tr>
    </table>
</c:forEach>
