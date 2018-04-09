<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
</head>
<header>
	<%@ include file="header.jsp"%>
</header>

<nav>
	<%@ include file="nav.jsp"%>
</nav>
<body>
	<form action="/member/signup-page/signup" class="loginForm"
		name="signUp" style="margin-left: 30%; margin-right: 30%;"
		method="post">
		<div>
			<h3 id="label">SignUp</h3>
			<div class="fieldForm">
				<div class="fieldLabel">Email</div>
				<div class="field">
					<input type="text" name="memberEmail" class="form-control">
				</div>
			</div>
			<div class="fieldForm">
				<div class="fieldLabel">Name</div>
				<div class="field">
					<input type="text" name="memberName" class="form-control">
				</div>
			</div>

			<div class="fieldForm">
				<div class="fieldLabel">Phone</div>
				<div class="field">
					<input type="text" name="memberPhone" class="form-control">
				</div>
			</div>
		</div>
		<div style="margin-top: 2%;">
			<button class="btn btn-primary btn-xm" type="submit" id="createBtn">Confirm</button>
			<button class="btn btn-default" type="button" id="cancleBtn"
				onclick="history.back();">Cancel</button>
		</div>
	</form>

	<script src="/js/memberRequest.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</body>
</html>