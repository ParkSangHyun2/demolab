$(function() {
	let signinServiceUrl = "http://localhost:8090/member/profile/modify";

	$("[value=Confirm]").click(function(event) {
		event.preventDefault();
		modify();
	});

	function modify() {
		//
		var memberData = {
			email : $("#emailForm").val(),
			name : $("#nameForm").val()
		}
		
		var memberBirthDay = $("[value=birthDay]").val();
		var memberNickName = $("[value=nickName]").val();
		var memberPhone = $("[value=phone]").val();
		
		var memberInfo = {
				//
				birthDay : memberBirthDay,
				nickName : memberNickName,
				memberPhone : memberPhone
		}	

		$.ajax({
			type : "put",
			contentType : "application/json",
			url : signinServiceUrl,
			data : JSON.stringify(memberInfo),
			dataType : "json",
			success : function(result) {
				var json = result;
				
			},
		 error : function(message){
		 alert("Error!")
		 console.log("ERROR ---> ", message);
		 }
		});
	}
})
