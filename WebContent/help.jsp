<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<header><%@include file="header.jsp" %></header>
<body>
<form name="Help" action="/Shop/cs" method ="post" >
<h1 style="text-align:center"><strong>This when you should go to Google</strong></h1>
<h6 style="text-align:center"><input type="submit" name="action" value="Logout"/><input type= "submit" name="action" value="Back"/></h6>
<input type="hidden" name="page" value="help"/>
</form>
</body>
<footer style="position:fixed:bottom:10%"><%@include file="footer.jsp" %></footer>
</html>