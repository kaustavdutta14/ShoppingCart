<%@page import="java.util.List, com.amazon.domain.Item"%>
<html>
<head>
<title>Shopping cart</title>
</head>
<header><%@include file="header.jsp" %></header>
</head>
<body>
<body>
	<form name="Cart" action="/Shop/cs" method="post">

		<%
			String addToCartMessage=(String)request.getAttribute("addToCartMessage");
			if(addToCartMessage!=null){
				out.println(addToCartMessage);
			}
		%>

		<%
			List<Item> items = (List<Item>) session.getAttribute("items");
			if (items != null) {
		%>
		
	<table width="45%" border="1">
	<tr>
		<td>ID</td>
		<td>Item Name</td>
		<td>Price</td>
		<td>Quantity</td>
	</tr>
	
	<%
		for(Item item:items){
	%>
	
	<tr>
		<td><input type="checkbox" name="chkItem" value="<%=item.getId() %>"/>
		<td><%=item.getName() %></td>
		<td><%=item.getUnitPrice() %></td>
		<td><input type="text" name="qty<%=item.getId()%>"/> </td>
		
	</tr>
	
	<%
		}
	%>
	
	</table>
		<%
			}
		%>

		<input type="submit" name="action" value="Add to Cart"/>
		<input type="submit" name="action" value="Checkout"/>
		<input type="submit" name="action" value="Logout"/>
		<input type="submit" name="action" value="Help"/>
		<input type="hidden" name="page" value="cart" />

	</form>
</body>
<footer style="position:fixed:bottom:10%"><%@include file="footer.jsp" %></footer>
</html>