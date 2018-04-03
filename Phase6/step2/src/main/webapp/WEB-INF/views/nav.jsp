<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet"
	href="${ pageContext.request.contextPath }/resources/css/bootstrap.css">

<nav class="navbar navbar-inverse">
	<div class="container">
		<ul class="nav navbar-nav">
			<li><a
				href="${pageContext.request.contextPath}/clubMenu"><strong>Club</strong></a></li>
			<li> <a
				href="${pageContext.request.contextPath}/memberMenu"><strong>Member</strong></a>
			</li>
		</ul>
	</div>
</nav>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script
	src="${ pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>