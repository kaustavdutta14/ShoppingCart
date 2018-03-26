<html>
<head>
<title>Please login</title>
</head>
<header><%@include file="header.jsp" %></header>
<script type="text/javascript">
function reset(){
	document.getElementById("reset").innerHTML="";
}
</script>
<body>
	<form name="loginForm" action="/Shop/cs" method="post">
	
	<%
	String invalidUserMessage=(String)request.getAttribute("invalidUser");
	if(invalidUserMessage!=null){
	%>
	
	<span id="reset" style="color: red"><%=invalidUserMessage %></span>
	
	
	<%
	}
	%>
	
	<p>Username: <input type="text" name="username" required/></p>
	<p>Password: <input type="password" name="password" required/></p>
	
	<input type="submit" name="action" value="Login"/>
	<input type="reset" value="Reset" onclick="reset()"/>
	<input type="hidden" name="page" value="login"/>
	
	</form>

</body>
<footer style="position:fixed:bottom:10%"><%@include file="footer.jsp" %></footer>
</html>