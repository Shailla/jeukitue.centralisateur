<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<center><h1><fmt:message key="global.tchat" /></h1></center>
<hr />
<br />

<div style='width:inherit;height:60%;overflow:auto'> 
	<table id='messages' border='0' align='left'>
		<c:forEach var="msg" items="${messages}">
		    <tr align='left'>
		      <th>${msg.date}</th>
		      <th>${msg.clientName}</th>
		      <td>${msg.message}</td>
		    </tr>
		</c:forEach>
	</table>
</div>

<form:form modelAttribute='message' id='envoyer' method='post'>
    <table id='messageInput' border='0'>
        <tr>
            <th>
	           <form:label for='message' path='message' ><fmt:message key="global.message" /></form:label>
	        </th>
	        <td>	        
                <form:textarea id='message' path='message' rows='3' cols='100' />
            </td>
        </tr>
        <tr>
            <td>
                <input id='envoyer' type='submit' value='<fmt:message key="global.envoyer" />' />
            </td>
        </tr>
    </table>
</form:form>

<script type="text/javascript">
	$(document).ready(function() {

		// Rafraichie toutes les 500 millisecondes
		setInterval(refresh, 500);
		
	    // Send a chat message to the server and receive the updated message list from the server
	    $("#envoyer").submit(function() {
	        var message = $("#message").val();
	        $("#message").val("");
	    	
	        sendMessageAndRefresh(message);

	        return false;               
	    });
	});

    function refresh() {
    	sendMessageAndRefresh('');
    }
	
	// Rafraichie la liste des messages de l'historique avec les données du serveur dans 'dto'
	function sendMessageAndRefresh(message) {
		$.getJSON("add_tchat_message.do", { messageContent: message }, function(dto) {		
			var msgs = dto.messages;
			
			// Update the message list 
			var htmlMessages = "<table id='messages' border='0' align='left'>";
			
			for(i in msgs) {
			    htmlMessages += "<tr>";
			    htmlMessages += "<th>" + msgs[i].date + "</th>";
			    htmlMessages += "<th>" + msgs[i].clientName + "</th>";
			    htmlMessages += "<td>" + msgs[i].message + "</td>";
			    htmlMessages += "</tr>";
			}
			
			htmlMessages += "</table>";
			
			$("#messages").html(htmlMessages);
		});
	}
</script>
