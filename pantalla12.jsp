<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page errorPage = 'error.jsp' %>
<html>
<head>
	<%-- <title>pantalla21</title> --%>
	<link rel='stylesheet' type='text/css' href='iml.css'>
</head>
<body>
	<form method='GET' action='' accept-charset='utf-8'>
	<input type = "hidden" name = "p" value = "${param.p}">
	<input type = "hidden" name = "pdia" value = "${param.pdia}">
	<h1> Servicio de consulta de información sobre canales de TV </h1><br>
	<h2> Consulta 1: Fecha=${param.pdia}</h2>
	<h3> Seleccione un canal: </h3>
	<c:forEach var="canal" items="${canales}" varStatus="loop">
		<tr>
			<td><INPUT TYPE = "radio" name = "pcanal" value="${canal.nombre}">${loop.count}.- <b>Canal</b> = '${canal.nombre}' --- <b>Idioma</b> = '${canal.idioma}' --- <b>Grupo</b> = '${canal.grupo}'<br>
    </c:forEach>

	<button type="sumit" name = "pfase" value = "13">Enviar </button>
	<button type="sumit" name = "pfase" value = "01">Atrás </button>


</body>
</html>
