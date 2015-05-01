<%@ page language="java" pageEncoding="ISO-8859-1" contentType="text/html;charset=ISO-8859-1" %>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title><fmt:message key="login.titre" /></title>
    </head>
    <body>
        <center><h1><fmt:message key="login.titre" /></h1></center>
        <hr />
        <center>
	        <form method="post" action="j_acegi_security_check">        
	            <table border="0">
		            <tr>
	                    <td><fmt:message key="global.nom" /></td>
	                    <td>
	                        <input type="text" name="j_username" />
	                    </td>                
	                </tr>
	                <tr>
	                    <td><fmt:message key="global.motDePasse" /></td>
	                    <td>
	                        <input type="password" name="j_password" />
	                    </td>
	                </tr>
	                <tr>
	                    <td>
	                        <input type="submit" value="<fmt:message key='global.soumettre' />" />
	                    </td>
	                    <td>
	                       <a href="inscription.do"><fmt:message key="lien.inscription" /></a>
	                    </td>
	                </tr>
	            </table>
	        </form>
        </center>
    </body>
</html>