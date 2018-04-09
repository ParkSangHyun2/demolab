<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>create New Posting</title>
<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
</head>

<!-- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-- Navigation -->
<%@ include file="nav.jsp"%>
<!-- Navigation -->

<article>
	<div class="container">
		<h2>New Posting</h2>
		<p>write your article</p>
		<form action="/posting/create" class="loginForm" name="signUp"
			method="post">
			<div class="form-group">
				<label for="inputdefault">Title</label> <input class="form-control"
					id="inputdefault" type="text" name="postingTitle">
			</div>
			<div class="form-group">
				<label for="inputlg">Article</label>
				<textArea style="height: 8cm;" class="form-control input-lg"
					id="inputlg" name="postingContents"></textArea>
			</div>
			<div>
				<button id="backBtn" type="button" onclick="history.back();">Back</button>
				<input id="createBtn" type="submit" value="Create" />
			</div>
		</form>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</article>

<script type="text/javascript">
	$(function() {
		$('#createBtn').click(function(event) {
			if ($("#inputdefault").val() == '') {
				alert("Enter Title");
				event.preventDefault();
				$('form').event.preventDefault();
			}
		});
	});
</script>
<footer>
	<%@ include file="footer.jsp"%>
</footer>
</html>