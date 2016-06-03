<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pizza Store</title>
</head>

<body>
<h1>Store</h1>

<spring:url value="/saveStore" var="saveURL" />
<spring:url value="/" var="homeURL" />
<spring:url value="/deleteStore/?storeId=${store.getStoreId()}" var="deleteURL" />

<form:form method="post" modelAttribute="store" action="${saveURL}">

    <form:hidden path="storeId"/>
    <form:hidden path="createDate"/>
    <form:hidden path="lastModifiedDate"/>

    <div>
        <label>ID</label>: ${store.getStoreId()}
    </div>
    <spring:bind path="name">
        <div>
            <label>Name</label><form:input path="name" type="text" id="name" placeholder="Name" />
        </div>
    </spring:bind>

    <button type="submit">Save</button> <a href="${homeURL}">Home</a> <a href="${deleteURL}">Delete</a>
</form:form>

<br>

<c:if test="${orders.size() > 0}">
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
            <td><a href="<c:url value="/getCustomer/?customerId=${order.getCustomerId()}"/>">${order.getCustomerId()}</a></td>
            <td>${order.getPrice()}</td>
            <td>${order.getCreateDate()}</td>
            <td>${order.getLastModifiedDate()}</td>
          </tr>
        </c:forEach>
    </table>
</c:if>

</body>

</html>