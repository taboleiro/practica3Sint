<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>ç
<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ page errorPage = 'error.jsp' %>
<html>
<head>
	<%-- <title>Hola mundo</title> --%>
	<link rel='stylesheet' type='text/css' href='iml.css'>
</head>
<body>
	<form method='GET' action='' accept-charset='utf-8'>
	<h1> Servicio de consulta de información sobre canales de TV </h1><br>
	<h2> Consulta 1: "${param.pdia}" </h2><br>
	<h3> Seleccione un canal: </h3><br>
	<% ArrayList<String> fechas = (ArrayList<String>request.getAttribute("fechas");%>
	<c:forEach items="" var="canal" items="${canales}">
		<tr>
			<td><INPUT TYPE = "radio" value=${canal.index}>${canal.index}.-.- <b>Canal</b>: = --- b>Idioma</b> = --- <b>Grupo</b> = <br>
    </c:forEach>

	<button type="sumit" name = "pfase" value = "13">Enviar </button>
	<button type="sumit" name = "pfase" value = "01">Atrás </button>


</body>
</html>
