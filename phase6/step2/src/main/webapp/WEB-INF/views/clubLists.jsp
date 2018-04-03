<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet"
	href="${ pageContext.request.contextPath }/resources/css/bootstrap.css">
<title>Club Lists</title>
</head>

<!-- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-- Navigation -->
<%@ include file="nav.jsp"%>
<!-- Navigation -->

<article style="margin-left: 20%; margin-right: 20%;">
	<div class="clubListCell">
		<div class="clubList" style="border-bottom: 1px solid silver">
			<div>
				<h3 style="text-align: center;">
					<strong>All ClubList</strong>
				</h3>
				<br> <input class="btn-success" type="button" value="Create"
					onclick="window.location='/step2/club/new'" style="float: right;">
				<table id="clubTable" class="table table-hover">
					<thead>
						<tr>
							<th>ClubName</th>
							<th>ClubIntroduce</th>
							<th>FoundationDay</th>
							<th>Join</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty clubList}">
								<tr>
									<td colspan="3">No in Clubs</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="club" items="${clubList}" varStatus="index">

									<tr title="${club.name}">
										<th>${club.name}</th>
										<th>${club.intro}</th>
										<th>${club.foundationDay}</th>
										<th><a href="/step2/club/join?index=${ index.index }"><span
												class="glyphicon glyphicon-ok"></span></a></th>
									</tr>

								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div class="listController">
				<form action=""
					method="get" style="float: right;">
					<input id="searchField" type="text" name="findClubName">
					<input id="searchBtn" type="button" class="btn-info" value="search">
				</form>
				<br> <br> <br> <br> <br>
			</div>
		</div>
		<div class="myClubList">
			<div>
				<h3 style="text-align: center;">
					<strong>My ClubList</strong>
				</h3>
				<br>
				<table class="table table-hover">
					<thead>
						<tr>
							<th>ClubName</th>
							<th>ClubIntroduce</th>
							<th>FoundationDay</th>
							<th>Postings</th>
							<th>Withdrawal</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${empty myClubList}">
								<tr>
									<td colspan="5">Join in club</td>
								</tr>
							</c:when>
							<c:otherwise>

								<c:forEach var="club" items="${myClubList}" varStatus="index">
									<tr>
										<th name="clubName">${club.name}</th>
										<th>${club.intro}</th>
										<th>${club.foundationDay}</th>
										<th><a
											href="/step2/club/posting?index=${ index.index }&boardId=${club.usid}"><span
												class="glyphicon glyphicon-th-list"></span></a></th>
										<th><a href="/step2/club/withdraw?index=${ index.index }"><span
												class="glyphicon glyphicon-remove"></span></a></th>
									</tr>

								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script
		src="${ pageContext.request.contextPath }/resources/js/bootstrap.min.js"></script>
		<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</article>
<footer>
	<%@ include file="footer.jsp"%>
</footer>

<script>
	$(function(){
		$("#searchBtn").click(function(event){
			let searchValue = $("#searchField").val();

			if(searchValue){
				$("#clubTable tbody tr").hide().filter("[title=" + searchValue + "]").show();
			}else{
				$("#clubTable tbody tr").show();
			}	
		});
	});
</script>
</html>