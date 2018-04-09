<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<!-- Header -->
<%@ include file="header.jsp"%>
<!-- Header -->

<!-- Navigation -->
<%@ include file="nav.jsp"%>
<!-- Navigation -->
<article>
	<div class="container">
		<h2>Create new TravelClub</h2>
		<form class="form-horizontal" action="/step2/club/create" method="post">
			<div class="form-group">
				<label class="control-label col-sm-2">ClubName:</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="clubTitle"
						placeholder="Enter Clubname">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2">Introduce:</label>
				<div class="col-sm-8">
					<input type="text" class="form-control" name="clubIntro"
						placeholder="enter your club's introduce">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button id="createButton" type="submit" class="btn btn-default" name="createBtn">create</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript"
		src="http://code.jquery.com/jquery-2.1.1.min.js"></script>
</article>
<script type="text/javascript">
	$(function(){
		//
		$('#createButton').click(function(event){
			if($('[name=clubIntro]').val().length <= 10){
				event.preventDefault();
				$(this).focus();
				alert("write introduce more than 10");
			}
		});
	});
</script>

<%@ include file="footer.jsp"%>
</html>