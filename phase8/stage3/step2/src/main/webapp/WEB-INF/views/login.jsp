<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>

<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">

<body style="margin-bottom: 90%">

	<form action="/member/signin" method="post" class="loginForm" name="signin"
		style="margin-left: 30%; margin-right: 30%;">
		<div class="border">
			<h3>Login</h3>
			<div class="fieldForm">
				<div>Email</div>
				<div>
					<input id="emailForm" type="text" name="memberEmail"
						class="form-control">
				</div>
			</div>
			<div class="fieldForm">
				<div>Name</div>
				<div>
					<input id="nameForm" type="text" name="memberName"
						class="form-control">
				</div>
			</div>
			<div style="margin: 15px; float: right;">
				<button class="btn btn-primary btn-xm" type="submit" id="signInBtn">SignIn</button>
				<button class="btn btn-primary btn-xm" type="button"
					onclick="window.location='/member/signup-page'">SignUp</button>

			</div>
		</div>
	</form>
	<form action="club/clublists" method="get">
		<input id="success_login" type="submit" style="display: none;">
	</form>
<!-- 	<script src="/js/memberRequest.js"></script> -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script type="text/javascript">
		$(function() {
			//
			$('#signInBtn').click(function(event) {

				if ($("[name=memberEmail]").val() == '') {
					alert('Enter your Email');
					event.preventDefault();
					$("[name=memberEmail]").focus();
				}
				if ($("[name=memberName]").val() == '') {
					event.preventDefault();
					$("[name=memberName]").focus();
					alert('Enter your Name');
				}

			});
		});
	</script>
</body>
<br>
