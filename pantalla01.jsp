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
		<input type="hidden" name="p" value="${param.p}">
		<h1> Servicio de consulta de información sobre canales de TV </h1><br>
		<h1> Bienvenido a este servicio </h1><br>
		<h2> Seleccione una consulta </h2><br>
		<a href="/p3/Sint25P3?p=taboleiro8&pfase=02">Pulsa aquí para ver los ficheros erróneos</a><br><br>
		<input type = "radio" name = "pfase" value = "11" checked = "true">Consulta 1: películas de un día en un canal<br>
		<input type = "radio" name = "pfase" value = "30" checked = "true">consulta 2: convertir TVML a tabla HTML<br>
        <button type="sumit">Enviar </button>
	</form>

		
</body>
</html>
