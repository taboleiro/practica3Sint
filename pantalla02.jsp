<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page errorPage = 'error.jsp' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn" %>
<html>
<head>
	<%-- <title>Hola mundo</title> --%>
	<link rel='stylesheet' type='text/css' href='iml.css'>
</head>
<body>
	<form method='GET' action='' accept-charset='utf-8'>
		<input type="hidden" name="p" value="${param.p}">
		<h1> Servicio de consulta de información sobre canales de TV </h1><br>
		Se han encontrado ${fn:length(warnings)} ficheros con warnings:<br>
		<lu>
        <c:forEach items="${warnings}" var="fichero" varStatus = "loop">
            <li>${fichero.key}</li>
            <lu>
                <c:forEach items="${fichero.value}" var="warning">
                    <li>${warning}</li>
                </c:forEach>
            </lu>
        </c:forEach>
        </lu>
		Se han encontrado ${fn:length(errores)} ficheros con errores:<br>
		<lu>
        <c:forEach items="${errores}" var="fichero" varStatus = "loop">
            <li>${fichero.key}</li>
            <lu>
                <c:forEach items="${fichero.value}" var="errores">
                    <li>${errores}</li>
                </c:forEach>
            </lu>
        </c:forEach>
        </lu>
		Se han encontrado ${fn:length(erroresFatales)} ficheros con errores fatales:<br>
		<lu>
        <c:forEach items="${erroresFatales}" var="fichero" varStatus = "loop">
            <li>${fichero.key}</li>
            <lu>
                <c:forEach items="${fichero.value}" var="erroresFatales">
                    <li>${erroresFatales}</li>
                </c:forEach>
            </lu>
        </c:forEach>
        </lu>
	</form>


</body>
</html>
