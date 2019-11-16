<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% @page isErrorPage = "true"%>

<%
out.print("Error Message:");
out.print(exception.getMessage());
%>