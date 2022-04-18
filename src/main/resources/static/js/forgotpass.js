$(document).ready(function(){
	
	
	$("#register-form").submit(function(){
		$("#reset").prop("disabled", true);
		$("#sendmessage").text("Sending");
		$(".spins").css("display", "block");
		$.post($(this).attr("action"), $(this).serialize(), function(result){
			$(".spins").css("display", "none");
		    $("#sendmessage").text("Email Sent!");
		    $("#alert").text(result).addClass("text-warning");
		});
		return false;
	});
});