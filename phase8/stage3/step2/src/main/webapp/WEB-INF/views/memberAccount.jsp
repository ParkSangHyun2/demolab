<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="/webjars/bootstrap/3.3.4/dist/css/bootstrap.min.css">
<title>Profile</title>
</head>

<!-- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-- Navigation -->
<%@ include file="nav.jsp"%>
<!-- Navigation -->

<article style="margin-left: 25%; margin-right: 25%">
	<h1 class="center-title">Profile</h1>
	<div class="profile_table_block">

		<table class="table">
			<tr>
				<th scope="row">
					<div>
						<span class="thcell">Email</span>
					</div>
				</th>
				<td>
					<div class="tdCell">
						<p class="context_title">${memberEmail}</p>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<div>
						<span class="thcell">Name</span>
					</div>
				</th>
				<td>
					<div class="tdCell">
						<p class="context_title">${memberName}</p>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<div>
						<span id="phone" class="thcell">PhoneNumber</span>
					</div>
				</th>
				<td>
					<div class="tdCell">
						<p class="context_title">${phoneNumber}</p>

						<div id="modify_phone" style="display: none;">
							<form
								action="${pageContext.request.contextPath}/member/profile/modify"
								method="post">
								<p>
									<strong>modify your PhoneNumber</strong>
								</p>
								<input type="text" name="value"> <input type="text"
									name="memberInfo" value="phone" style="display: none;">
								<input type="submit" value="Confirm"
									class="btn btn-primary btn-xs"
									onclick="modify_phone.style.display='none';  modify_phone_btn.style.display='block'">
							</form>
						</div>

						<button id="modify_phone_btn" type="button"
							class="btn btn-primary btn-xs"
							onclick="modify_phone.style.display='block'; modify_phone_btn.style.display='none'">
							Modify</button>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row">
					<div>
						<span class="thcell">NickName</span>
					</div>
				</th>
				<td>
					<div class="tdCell">
						<p class="context_title">${nickName}</p>

						<div id="modify_nickName" style="display: none;">
							<form
								action="${pageContext.request.contextPath}/member/profile/modify"
								method="post">
								<p>
									<strong>modify your Name</strong>
								</p>
								<input type="text" name="value"> <input type="text"
									name="memberInfo" value="nickName" style="display: none;">
								<input type="submit" value="Confirm"
									class="btn btn-primary btn-xs"
									onclick="modify_nickName.style.display='none';  modify_btn.style.display='block'">
							</form>
						</div>

						<button id="nickName_modify_btn" type="button"
							class="btn btn-primary btn-xs"
							onclick="modify_nickName.style.display='block'; nickName_modify_btn.style.display='none'">
							Modify</button>
					</div>
				</td>

			</tr>
			<tr>
				<th scope="row">
					<div>
						<span class="thcell">BirthDay</span>
					</div>
				</th>
				<td>
					<div class="tdCell">
						<p class="context_title">${birthDay}</p>

						<div id="modify_birthDay" style="display: none;">
							<form
								action="${pageContext.request.contextPath}/member/profile/modify"
								method="post">
								<p>
									<strong>modify your BirthDay</strong>
								</p>
								<input type="text" name="value"> <input type="text"
									name="memberInfo" value="birthDay" style="display: none;">
								<input type="submit" value="Confirm"
									class="btn btn-primary btn-xs"
									onclick="modify_birthDay.style.display='none';  modify_btn.style.display='block'">
							</form>
						</div>

						<button id="birthDay_modify_btn" type="button"
							class="btn btn-primary btn-xs"
							onclick="modify_birthDay.style.display='block'; birthDay_modify_btn.style.display='none'">
							Modify</button>
					</div>
				</td>
			</tr>
		</table>
	</div>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="/webjars/bootstrap/3.3.4/dist/js/bootstrap.min.js"></script>
</article>
<footer>
	<%@ include file="footer.jsp"%>
</footer>
</html>