<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Store</title>
</head>

<body>
<h1>Customer</h1>
<ul>
  <li>ID: ${customer.getCustomerId()}</li>
  <li>Name: ${customer.getName()}</li>
</ul>
<br>
<h1>Orders:</h1>
<table border="1">
      <tr>
        <th>Order ID</th>
        <th>Store ID</th>
        <th>Customer ID</th>
        <th>Price</th>
        <th>Create Date</th>
        <th>Last Modified Date</th>
      </tr>
    <c:forEach items="${orders}" var="order">
      <tr>
        <td><a href="<c:url value="/getOrder/?orderId=${order.getOrderId()}"/>">${order.getOrderId()}</a></td>
        <td>${order.getStoreId()}</td>
        <td><a href="<c:url value="/getCustomer/?customerId=${customer.getCustomerId()}"/>">${customer.getCustomerId()}</a></td>
        <td>${order.getPrice()}</td>
        <td>${order.getCreateDate()}</td>
        <td>${order.getLastModifiedDate()}</td>
      </tr>
    </c:forEach>
</table>
</body>

</html>