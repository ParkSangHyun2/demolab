<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/resources/css/style.css" />

<footer
	style="position: absolute; bottom: 10%; right: 10%">

	<button type="button" onclick="window.location='/step2/logout'" class="btn-danger">
		<span class="glyphicon glyphicon-off"><strong>Logout</strong></span>
	</button>
</footer>