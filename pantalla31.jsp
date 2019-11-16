<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml"  prefix="x" %>
<%@ page language='java' contentType='text/html;charset=utf-8'%>

<%@ page errorPage = 'error.jsp' %>
<html >
  <head>
    <title>XMSL</title>
  </head>

  <body>
    <c:import url="${param.ficheroXML}"
              var="inputDoc" />

    <c:import url="/xmlToHtml.xsl"
              var="stylesheet" />

    <x:transform xml  = "${inputDoc}"
                 xslt = "${stylesheet}">
    </x:transform>
  </body>
</html>