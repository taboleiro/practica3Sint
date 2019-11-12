<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page errorPage = 'error.jsp' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<%-- <title>Hola mundo</title> --%>
	<link rel='stylesheet' type='text/css' href='iml.css'>
</head>
<body>
	<form method='GET' action='' accept-charset='utf-8'>
	<h1> Servicio de consulta de información sobre canales de TV </h1><br>
	<h2> Consulta 1: Fecha = "${param.pdia}", canal = "${param.pcanal}" </h2><br>
	<h3> Seleccione una fecha: </h3><br>
	<c:forEach items="${peliculas}" var="pelicula">
		<tr>
			<td><INPUT TYPE = "radio" value=${pelicula.index}>${pelicula.index}.- <b>Título</b>: =  --- <b>Edad Mínima</b> =  --- <b>Hora</b> =  --- <b>Resumen</b> = <br>


	<button type="sumit" name = "pfase" value = "12">Enviar </button>
	<button type="sumit" name = "pfase" value = "01">Atrás </button>


</body>
</html>
