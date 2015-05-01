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
        <div id="menu">
            <tiles:insertAttribute name="menu" />
        </div>
        <div id="content">
            <tiles:insertAttribute name="content" />
        </div>
    </body>
</html>
