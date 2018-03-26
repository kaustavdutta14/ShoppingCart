<%@page import="java.util.List, com.amazon.domain.SummaryOfAllItems,com.amazon.domain.Summary"%>
<html>
<head>
<title>Summary of Purchase</title>
</head>
<header><%@include file="header.jsp" %></header>
<body>
	<form name="loginForm" action="/Shop/cs" method="post">
	<table width="59%" border="1">
	<tr>
		<td>Item Name</td>
		<td>Quantity</td>
	</tr>
	
	<%
		SummaryOfAllItems summaryOfAllItems=(SummaryOfAllItems)session.getAttribute("summaryOfAllItems");
		List<Summary> lstSummary=summaryOfAllItems.getSummary();
	    
		for(Summary item:lstSummary){
	%>
	
	<tr>
		<td><%=item.getName() %></td>
		<td><%=item.getQuantity() %></td>		
	</tr>
	
	<%
		}
	%>
	
	<input type="hidden" name="page" value="summary"/>
	</table>
	<%
	Float grandTotal=summaryOfAllItems.getGrandTotal();
	out.println("GRAND TOTAL: "+grandTotal);
	%>
	<br></br>
	<input type="hidden" name="page" value="Summary"/>
	
	<input type="submit" name="action" value="Back"/>
	<input type="submit" name="action" value="Payment"/>
	<input type="submit" name="action" value="Logout"/>
	<input type= "submit" name="action" value="Help"/>

	</form>

</body>
<footer style="position:fixed:bottom:10%"><%@include file="footer.jsp" %></footer>
</html>