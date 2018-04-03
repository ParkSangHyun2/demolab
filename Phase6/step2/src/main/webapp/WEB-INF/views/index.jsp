<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="${ pageContext.request.contextPath }/resources/css/bootstrap.css">
<title>Phase6</title>
</head>

<!----------------------------------- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-------------------------------- Navigation -->
<%@ include file="nav.jsp"%>
<!--  /Navigation -->

<body>
	<%@ include file="login.jsp"%>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script
		src="${ pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>
</body>

</html>
