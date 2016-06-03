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

<spring:url value="/saveOrderLineDetail" var="saveURL" />
<spring:url value="/" var="homeURL" />
<spring:url value="/deleteOrderLineDetail/?orderLineDetailId=${orderLineDetail.getOrderLineDetailId()}" var="deleteURL" />
<spring:url value="/getOrderLine/?orderLineId=${orderLineDetail.getOrderLineId()}" var="orderLineURL" />

<h1>Order Line Detail</h1>
<form:form method="post" modelAttribute="orderLineDetail" action="${saveURL}">

    <span>
        <button type="submit">Save</button>
        <a href="${deleteURL}">Delete</a>
        <a href="${homeURL}">Home</a>
        <a href="${orderLineURL}">Return to Order Line</a>
    </span>

    <form:hidden path="orderLineDetailId"/>
    <form:hidden path="orderLineId"/>
    <form:hidden path="createDate"/>
    <form:hidden path="lastModifiedDate"/>

    <div>
        <label>ID</label>: ${orderLineDetail.getOrderLineDetailId()}
    </div>
    <div>
        <label>Line ID</label>: ${orderLineDetail.getOrderLineId()}
    </div>
    <spring:bind path="menuItemDetailId">
        <div>
            <span>
                <label>Menu Item Detail: </label>
                <c:choose>
                    <c:when test="${orderLineDetail.getMenuItemDetailId() >= 0}">
                        <form:hidden path="menuItemDetailId"/>
                        <c:out value="${menuItemDetail.getName()}"/>
                    </c:when>
                    <c:otherwise>
                        <form:select path="menuItemDetailId">
                            <form:option value="-1" label="--- Select ---" />
                            <form:options items="${menuItemDetails}" />
                        </form:select>
                    </c:otherwise>
                </c:choose>
            </span>
        </div>
    </spring:bind>
    <spring:bind path="placement">
        <div>
            <span>
                <label>Placement: </label>
                <form:select path="placement">
                    <form:option value="NONE" label="--- Select ---" />
                    <form:options items="${placementOptions}" />
                </form:select>
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

</body>

</html>