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
<h1>Welcome to our pizza stores!</h1>

<p>Please select a store</p>
<table border="1">
      <tr>
        <th>Store ID</th>
        <th>Name</th>
        <th>Create Date</th>
        <th>Last Modified Date</th>
      </tr>
    <c:forEach items="${stores}" var="store">
      <tr>
        <td><a href="<c:url value="/getStore/?storeId=${store.getStoreId()}"/>">${store.getStoreId()}</a></td>
        <td>${store.getName()}</td>
        <td>${store.getCreateDate()}</td>
        <td>${store.getLastModifiedDate()}</td>
      </tr>
    </c:forEach>
</table>

<br>

<p>Or a customer</p>
<table border="1">
      <tr>
        <th>Customer ID</th>
        <th>Name</th>
        <th>Create Date</th>
        <th>Last Modified Date</th>
      </tr>
    <c:forEach items="${customers}" var="customer">
      <tr>
        <td><a href="<c:url value="/getCustomer/?customerId=${customer.getCustomerId()}"/>">${customer.getCustomerId()}</a></td>
        <td>${customer.getName()}</td>
        <td>${customer.getCreateDate()}</td>
        <td>${customer.getLastModifiedDate()}</td>
      </tr>
    </c:forEach>
</table>
</body>

<spring:url value="/createCustomer" var="actionUrl" />

<form:form method="get"
                modelAttribute="customer" action="${actionUrl}">
    <div>
        <button type="submit">New Customer</button>
    </div>
</form:form>

</html>