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

<spring:url value="/saveOrderLine" var="saveURL" />
<spring:url value="/" var="homeURL" />
<spring:url value="/deleteOrderLine/?orderLineId=${orderLine.getOrderLineId()}" var="deleteURL" />
<spring:url value="/getOrder/?orderId=${orderLine.getOrderId()}" var="orderURL" />

<h1>Order Line</h1>
<form:form method="post" modelAttribute="orderLine" action="${saveURL}">

    <span>
        <button type="submit">Save</button>
        <a href="${deleteURL}">Delete</a>
        <a href="${homeURL}">Home</a>
        <a href="${orderURL}">Return to Order</a>
    </span>

    <form:hidden path="orderLineId"/>
    <form:hidden path="orderId"/>
    <form:hidden path="createDate"/>
    <form:hidden path="lastModifiedDate"/>

    <div>
        <label>ID</label>: ${orderLine.getOrderLineId()}
    </div>
    <spring:bind path="menuItemId">
        <div>
            <span>
                <label>Menu Item: </label>
                <c:choose>
                    <c:when test="${orderLine.getMenuItemId() >= 0}">
                        <form:hidden path="menuItemId"/>
                        <c:out value="${menuItem.getName()}"/>
                    </c:when>
                    <c:otherwise>
                        <form:select path="menuItemId">
                            <form:option value="-1" label="--- Select ---" />
                            <form:options items="${menuItems}" />
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
    </spring:bind>
    <spring:bind path="quantity">
        <div>
            <span>
                <label>Quantity: </label>
                <form:input path="quantity" type="text" id="quantity" placeholder="Quantity" />
            </span>
        </div>
    </spring:bind>
    <spring:bind path="price">
        <div>
            <span>
                <label>Price (each): </label>
                <form:input path="price" type="text" id="price" placeholder="Price" />
            </span>
        </div>
    </spring:bind>

</form:form>

<br>

<c:if test="${orderLineDetails.size() > 0}">
    <h1>Lines:</h1>
    <table border="1">
          <tr>
            <th>OrderLineDetail ID</th>
            <th>OrderLine ID</th>
            <th>MenuItemDetail ID</th>
            <th>Placement</th>
            <th>Price</th>
            <th>Create Date</th>
            <th>Last Modified Date</th>
          </tr>
        <c:forEach items="${orderLineDetails}" var="detail">
          <tr>
            <td>${detail.getOrderLineDetailId()}</td>
            <td>${detail.getOrderLineId()}</td>
            <td>${detail.getMenuItemDetailId()}</td>
            <td>${detail.getPlacement()}</td>
            <td>${detail.getPrice()}</td>
            <td>${detail.getCreateDate()}</td>
            <td>${detail.getLastModifiedDate()}</td>
          </tr>
        </c:forEach>
    </table>
</c:if>
</body>

</html>