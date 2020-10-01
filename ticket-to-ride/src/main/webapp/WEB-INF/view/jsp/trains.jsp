<%--
  Created by IntelliJ IDEA.
  User: Trofim Kremen
  Date: 19.09.2020
  Time: 9:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>Train list</title>
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
                <a class="nav-item nav-link active" href="<c:url value="/trains"/>">Trains<span class="sr-only">(current)</span></a>
                <a class="nav-item nav-link" href="<c:url value="/stations"/>">Stations</a>
            </div>
        </div>
    </nav>
</header>
<main>
    <div class="container container-fluid">
        <h3>Train List</h3>
        <div class="list-group">
            <c:forEach var="train" items="${trains}" varStatus="vs">
                <a class="list-group-item list-group-item-action btn btn-primary"
                   role="button"
                   id="trainDetains${vs.index}"
                   data-toggle="modal"
                   data-target="#modal${vs.index}"
                   aria-expanded="true">
                    Train ${train.id}
                </a>
                <div class="modal fade" id="modal${vs.index}" tabindex="-1"
                     aria-labelledby="modalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="modalLabel">Train ${train.id}</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <h5>Train name</h5>
                                <p>${train.name}</p>
                                <hr>
                                <h5>Train type</h5>
                                <p>${train.type}</p>
                                <hr>
                                <h5>Average speed</h5>
                                <p>${train.avgSpeed}</p>
                                <hr>
                                <h5>Number of seats</h5>
                                <p>${train.numberOfSeats}</p>
                            </div>
                            <div class="modal-footer">
                                <a role="button" class="btn btn-secondary" data-dismiss="modal">Close</a>
                                <a role="button" class="btn btn-primary" href="<c:url value="/trains/${train.id}"/>">Inspect</a>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
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





