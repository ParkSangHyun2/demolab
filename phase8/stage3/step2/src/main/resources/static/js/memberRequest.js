$(function() {
	let signinServiceUrl = "http://localhost:8090/member/signin";

//	$('#signInBtn').click(function(event) {
//		event.preventDefault();
//		login();
//	});
//
//	function login() {
//		//
//		var memberData = {
//			email : $("#emailForm").val(),
//			name : $("#nameForm").val()
//		}
//
//		$.ajax({
//			type : "post",
//			contentType : "application/json",
//			url : signinServiceUrl,
//			data : JSON.stringify(memberData),
//			dataType : "json",
//			success : function(result) {
//				switch (result) {
//				case "success":
//					//
//					$("#success_login").click();
//					break;
//				case "fail":
//					// ERROR Alert
//					alert("Fail to Signin");
//					break;
//				default:
//					// ERROR MESSAGE
//					alert("ERROR")
//				}
//			},
//		 error : function(message){
//		 alert("Error!")
//		 console.log("ERROR ---> ", message);
//		 }
//		});
//	}
})
