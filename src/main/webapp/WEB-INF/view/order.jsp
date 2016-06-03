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

<spring:url value="/saveOrder" var="saveURL" />
<spring:url value="/" var="homeURL" />
<spring:url value="/deleteOrder/?orderId=${order.getOrderId()}" var="deleteURL" />
<spring:url value="/createOrderLine/?orderId=${order.getOrderId()}" var="addLineURL" />

<h1>Order</h1>
<form:form method="post" modelAttribute="order" action="${saveURL}">

    <span>
        <button type="submit">Save</button>
        <a href="${homeURL}">Home</a>
        <a href="${deleteURL}">Delete</a>
        <a href="${addLineURL}">Add Line</a>
    </span>

    <form:hidden path="orderId"/>
    <form:hidden path="createDate"/>
    <form:hidden path="lastModifiedDate"/>

    <div>
        <label>ID</label>: ${order.getOrderId()}
    </div>
    <spring:bind path="storeId">
        <div>
            <span>
                <label>Store: </label>
                <c:choose>
                    <c:when test="${order.getStoreId() >= 0}">
                        <form:hidden path="storeId"/>
                        <c:out value="${store.getName()}"/>
                    </c:when>
                    <c:otherwise>
                        <form:select path="storeId">
                            <form:option value="-1" label="--- Select ---" />
                            <form:options items="${stores}" />
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
    </spring:bind>
    <spring:bind path="customerId">
        <div>
            <span>
                <label>Customer: </label>
                <c:choose>
                    <c:when test="${order.getCustomerId() >= 0}">
                        <form:hidden path="customerId"/>
                        <c:out value="${customer.getName()}"/>
                    </c:when>
                    <c:otherwise>
                        <form:select path="customerId">
                            <form:option value="-1" label="--- Select ---" />
                            <form:options items="${customers}" />
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
    </spring:bind>
    <spring:bind path="price">
        <div>
            <span>
                <label>Price: </label>
                <form:input path="price" type="text" id="price" placeholder="Price" />
            </span>
        </div>
    </spring:bind>

</form:form>

<br>

<c:if test="${orderLines.size() > 0}">
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
            <td><a href="<c:url value="/getOrderLine/?orderLineId=${line.getOrderLineId()}"/>">${line.getOrderLineId()}</a></td>
            <td>${line.getOrderId()}</td>
            <td>${line.getMenuItemId()}</td>
            <td>${line.getQuantity()}</td>
            <td>${line.getPrice()}</td>
            <td>${line.getCreateDate()}</td>
            <td>${line.getLastModifiedDate()}</td>
          </tr>
        </c:forEach>
    </table>
</c:if>
</body>

</html>