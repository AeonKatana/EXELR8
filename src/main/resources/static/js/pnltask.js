
$(document).ready(function(){
	
	$("#myform").submit(function(e){
		e.preventDefault();
		alert("lol");
	});
	
	$(".btncancel").click(function(){
		$(this).parent().parent().parent().remove();
		window.parent.document.getElementById('framebtn').removeAttribute("disabled");
		window.parent.document.getElementById('myframe').setAttribute("height", 0);
	});
});