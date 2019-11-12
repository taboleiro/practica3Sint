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
	<input type = "hidden" name = "p" value = "${param.p}">
	<input type = "hidden" name = "pdia" value = "${param.pdia}">
	<input type = "hidden" name = "pcanal" value = "${param.pcanal}">
	<h1> Servicio de consulta de información sobre canales de TV </h1>
	<h2> Consulta 1: Fecha=${param.pdia},Canal=${param.pcanal}</h2>
	<h3> Éste es el resultado: </h3>
	<ul>
	<c:forEach items="${peliculas}" var="pelicula" varStatus = "loop">
		<li>${loop.count}.- <b>Título</b> = '${pelicula.titulo}'  --- <b>Edad Mínima</b> = '${pelicula.edad}' --- <b>Hora</b> = '${pelicula.hora}' --- <b>Resumen</b> = '${pelicula.resumen}'</li>
    </c:forEach>
    </ul>
	<button type="sumit" name = "pfase" value = "12">Atrás </button>
	<button type="sumit" name = "pfase" value = "01">Inicio </button>


</body>
</html>
