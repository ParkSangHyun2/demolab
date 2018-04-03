<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Posting</title>

<link rel="stylesheet"
	href="${ pageContext.request.contextPath }/resources/css/bootstrap.css">

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
				onclick="window.location='/step2/postings/delete?usid=${selectedPosting.usid}'">
			<input style="float: right;" type="button" value="Modify"
				onclick="postingModify.style.display='block'; postingDetail.style.display='none'">
		</div>
	</div>

	<div id="postingModify" style="display: none;">
		<h3 style="text-align: center;">Posting</h3>
		<form action="${pageContext.request.contextPath}/posting/modify"
			method="post">
			<table class="table-bordered table-hover" style="width: 100%;">
				<tr>
					<th style="text-align: center;">Title</th>
					<td name = "title">${selectedPosting.title}</td>
				</tr>
				<tr>
					<th style="text-align: center;">WriterEmail</th>
					<td name="email">${ selectedPosting.writerEmail }</td>
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

</article>
<footer>
	<%@ include file="footer.jsp"%>
</footer>
</html>