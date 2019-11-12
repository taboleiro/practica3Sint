<% @page isErrorPage = "true"%>

<%
out.print("Error Message:");
out.print(exception.getMessage());
%>