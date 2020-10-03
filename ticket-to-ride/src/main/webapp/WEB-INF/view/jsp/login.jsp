<%--
  Created by IntelliJ IDEA.
  User: trofim
  Date: 02.10.2020
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html>
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="sbb train page">
    <meta name="author" content="Trofim Kremen">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/css/bootstrap.min.css"/>">
    <title>Login</title>
</head>
<body>
<header>
    <nav class="navbar navbar-light bg-light">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <img src="<c:url value="/resources/images/SBB-logo.png"/>" width="40" height="40" class="d-inline-block align-top" alt="">
            SBB App
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="<c:url value="/"/>">Home</a>
                <a class="nav-item nav-link" href="<c:url value="/trains"/>">Trains</a>
                <a class="nav-item nav-link active" href="<c:url value="/stations"/>">Stations<span class="sr-only">(current)</span></a>
            </div>
        </div>
    </nav>
</header>
<main>
    <sec:authorize access="isAuthenticated()">
        <% response.sendRedirect("/"); %>
    </sec:authorize>
    <div class="container container-fluid">
        <div class="row">
            <div class="col-md-6 col-md-offset-3">
                <c:if test="${param.error != null}">
                    <div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>Failed to log in.</strong>
                        <br>
                        Please make sure that you have entered your <strong>login</strong> and
                        <strong>password </strong>
                        correctly.
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <c:if test="${param.logout != null}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <p>You have logged out!</p>
                        <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </c:if>
                <h1>Log In</h1>
                <form:form action="/login" method="post">
                    <div class="form-group">
                        <label for="login" class="control-label">Login
                            <input id="login" type="text" placeholder="Enter login">
                        </label>
                    </div>
                    <div class="form-group">
                        <label for="password" class="control-label">Password
                            <input id="password" type="password" placeholder="Enter password">
                        </label>
                    </div>
                    <div class="form-group">
                        <input type="submit" class="form-control btn btn-info" value="Log in!"/>
                    </div>
                    <div class="form-group">
                        <span>New user? <a href="<c:url value="/registration"/>">Register here</a></span>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</main>
<footer class="fixed-bottom page-footer bg-light">
    <p class="text-center footer-text">&copy; 2020 Java School &middot; Trofim Kremen </p>
</footer>
<script src="<c:url value="/resources/js/jquery/jquery-3.5.1.min.js"/>"></script>
<script src="<c:url value="/resources/js/popper/popper.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap/bootstrap.min.js"/>"></script>
</body>
</html>
