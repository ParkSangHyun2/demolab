<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Posting</title>

<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">

<style type="text/css">

th {
	background-color: menu;
}

table {
	border-style: solid;
	border-color: black;
}

</style>
</head>

<header>
	<%@ include file="header.jsp"%>
</header>
<nav>
	<%@ include file="nav.jsp"%>
</nav>
<article style="margin-left: 25%; margin-right: 25%">
	<div id="postingDetail" style="display: block;">
		<h3 style="text-align: center;">Posting</h3>
		<table class="table-bordered table-hover" style="width: 100%;">
			<tr>
				<th style="text-align: center;">Title</th>
				<td>${selectedPosting.title}</td>
			</tr>
			<tr>
				<th style="text-align: center;">WriterEmail</th>
				<td>${ selectedPosting.writerEmail }</td>
			</tr>
			<tr>
				<th style="text-align: center;">WrittenDate</th>
				<td>${ selectedPosting.writtenDate }</td>
			</tr>
			<tr>
				<th style="text-align: center;">Contents</th>
				<td id="contents" style="height: 300px">${selectedPosting.contents}</td>
			</tr>
		</table>
		<div style="margin: 10px;">
			<button onclick="history.back();">Back</button>
			<input style="float: right;" type="button" value="Delete"
				onclick="window.location='/postings/delete?usid=${selectedPosting.usid}'">
			<input style="float: right;" type="button" value="Modify"
				onclick="postingModify.style.display='block'; postingDetail.style.display='none'">
		</div>
	</div>

	<div id="postingModify" style="display: none;">
		<h3 style="text-align: center;">Posting</h3>
		<form action="/postings/modify" method="post">
			<table class="table-bordered table-hover" style="width: 100%;">
				<tr>
					<th style="text-align: center;">Title</th>
					<td>${selectedPosting.title}</td>
				</tr>
				<tr>
					<th style="text-align: center;">WriterEmail</th>
					<td>${ selectedPosting.writerEmail }</td>
				</tr>
				<tr>
					<th style="text-align: center;">WrittenDate</th>
					<td>${ selectedPosting.writtenDate }</td>
				</tr>
				<tr>
					<th style="text-align: center;">Contents</th>
					<td><textarea rows="" cols="" name="contents"></textarea></td>
				</tr>
			</table>
			<div style="margin: 10px;">
				<button onclick="history.back();">Back</button>
				<input style="float: right;" type="submit" value="Confirm">
			</div>
		</form>
	</div>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>

</article>
<footer>
	<%@ include file="footer.jsp"%>
</footer>
</html>