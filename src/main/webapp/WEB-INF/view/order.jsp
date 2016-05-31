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
<h1>Order</h1>
<ul>
    <li>Order ID: <a href="<c:url value="/getOrder/?orderId=${order.getOrderId()}"/>">${order.getOrderId()}</a></li>
    <li>Store ID: <a href="<c:url value="/getStore/?storeId=${order.getStoreId()}"/>">${order.getStoreId()}</a></li>
    <li>Customer ID: <a href="<c:url value="/getCustomer/?customerId=${order.getCustomerId()}"/>">${order.getCustomerId()}</a></li>
    <li>Price: ${order.getPrice()}</li>
    <li>Create Date: ${order.getCreateDate()}</li>
    <li>Modifed Date: ${order.getLastModifiedDate()}</li>
</ul>
<br>
<h1>Lines:</h1>
<table border="1">
      <tr>
        <th>OrderLine ID</th>
        <th>Order ID</th>
        <th>MenuItem ID</th>
        <th>Quantity</th>
        <th>Price</th>
        <th>Create Date</th>
        <th>Last Modified Date</th>
      </tr>
    <c:forEach items="${orderLines}" var="line">
      <tr>
        <td>${line.getOrderLineId()}</td>
        <td>${line.getOrderId()}</td>
        <td>${line.getMenuItemId()}</td>
        <td>${line.getQuantity()}</td>
        <td>${line.getPrice()}</td>
        <td>${line.getCreateDate()}</td>
        <td>${line.getLastModifiedDate()}</td>
      </tr>
    </c:forEach>
</table>
</body>

</html>