<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/WEB-INF/views/include.jsp" %>

<html>
    <head>
        <title>
            <tiles:getAsString name="title" />
        </title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/web/css/main.css"/>" />
        <script type="text/javascript" src="<c:url value="/scripts/jquery-1.4.2.min.js" /> "></script>
        <script type="text/javascript" src="<c:url value="/scripts/json2.js" /> "></script>
    </head>
    <body>
    	<div class="logout">
			<form id="logoutForm" action="<c:url value='/logout.do' />" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="submit" value="Quit in18n" />
			</form>
    	</div>
        <div class="menu">
            <tiles:insertAttribute name="menu" />
        </div>
        <div class="content">
            <tiles:insertAttribute name="content" />
        </div>
    </body>
</html>
