<%@ page language='java' contentType='text/html;charset=utf-8'%>
<%@ taglib uri="http://java.sun.com/jstl/xml"  prefix="x" %>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<%@ page errorPage = 'error.jsp' %>
<html >
  <head>
    <title>XMSL</title>
  </head>

  <body>
    <c:import url="${param.fichero}"
              var="inputDoc" />

    <c:import url="/xmlToHtml.xsl"
              var="stylesheet" />

    <x:transform xml  = "${inputDoc}"
                 xslt = "${stylesheet}">
    </x:transform>
  </body>
</html>