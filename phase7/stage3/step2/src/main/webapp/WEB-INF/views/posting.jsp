<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
<title>Posting</title>
</head>

<!-- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-- Navigation -->
<%@ include file="nav.jsp"%>
<!-- Navigation -->

<article style="margin-left: 25%; margin-right: 25%">
	<h3 style="text-align: center;">Postings</h3>
	<div>
		<div>
			<input class="btn-success" type="button" style="float: right;"
				onclick="window.location='/postings/new'" value="New Posting">
		</div>
		<br>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>No</th>
					<th>Title</th>
					<th>Writer E-mail</th>
					<th>Written Date</th>
					<th>Read Count</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${empty postingList}">
						<tr>
							<td rowspan="3" colspan="5">No in Postings</td>
						</tr>
					</c:when>
					<c:otherwise>
						<c:forEach var="posting" items="${postingList}" varStatus="index">

							<tr>
								<th>${index.count}</th>
								<th><a href="load?usid=${ posting.usid }">${posting.title}</a></th>
								<th>${posting.writerEmail}</th>
								<th>${posting.writtenDate}</th>
								<th>${posting.readCount}</th>
							</tr>

						</c:forEach>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
	<div>
		<button onclick="history.back();">Back</button>
	</div>

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>

</article>
<footer>
	<%@ include file="footer.jsp"%>
</footer>
</html>