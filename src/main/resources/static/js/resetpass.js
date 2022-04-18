$(document).ready(function(){
	
	$("#submit").prop("disabled",true);
		
		$("#newPassword").keyup(function(){
			if($(this).val() === $("#retypePassword").val() && $(this).val() !== ""){
				$(this).css({"border" : "2px solid green"});
				$("#retypePassword").css({"border" : "2px solid green"});
				$("#submit").prop("disabled",false);
			}
			else{
				$("#retypePassword").css({"border" : "2px solid red"});
				$("#submit").prop("disabled",true);
			}
		});
		
		$("#retypePassword").keyup(function(){
			if($(this).val() === $("#newPassword").val() && $(this).val() !== ""){
				$(this).css({"border" : "2px solid green"});
				$("#submit").prop("disabled",false);
			}
			else{
				$(this).css({"border" : "2px solid red"});
				$("#submit").prop("disabled",true);
			}
		})
	
	
});