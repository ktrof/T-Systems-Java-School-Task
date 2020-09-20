<%--
  Created by IntelliJ IDEA.
  User: Trofim Kremen
  Date: 19.09.2020
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="sbb train page">
    <meta name="author" content="Trofim Kremen">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <title>Hello, World!</title>
</head>
<body>
    <div class="container container-fluid">
        <h3>Train List</h3>
        <div class="list-group">
            <c:forEach var="train" items="trains">
        </div>
    </div>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:url value="/resources/js/jquery/jquery-3.5.1.min.js">"></script>
    <script src="<c:url value="/resources/js/popper/popper.min.js">"></script>
    <script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js">"></script>
</body>
</html>
