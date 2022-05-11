$(document).ready(
				function() {

					$("#framebtn").click(function() {
						$("#framebtn").prop("disabled", true);
						$("#myframe").attr("src", url);
						$("#myframe").css("display", "block");

						$("#myframe").prop("height", 400);
					});

					let undotype = "";
							let taskid = 0;
							$(".donebtn").click(function(){
								taskid = $(this).attr("did");
								let userid = $(this).attr("uid");
								undotype = "done";
								$.ajax({
									type : "POST",
									url : "/task/markasdone",
									data : {
										status : true,
										id : taskid
									},
									success : function(result){
										$("#row" + taskid).fadeOut();
										let count = parseInt($("#count" + userid).text());
										count--;
										$("#count" + userid).text(count);
											$("#undotext").text("Task completed!");
											$("#undo").toast({delay:5000});
											$("#undo").toast("show");	
										
									},
									error : function(response){
										if(response.status == 403){
											alert('Only SUPERADMIN and MASTERADMIN can only control others tasks')
										}
										
									}
								})
							});
							
							
							let userid3 = 0;
							$(".deletebtn").click(function() {
								 taskid = $(this).attr("did");
								 userid3 = $(this).attr("uid");
								 undotype = "delete";
								 
								 $.ajax({
									type : "PUT",
									url : "/task/softdelete/" + taskid,
									success : function(){
										$("#row" + taskid).fadeOut();
										let count = parseInt($("#count" + userid3).text());
										count--;
										$("#count" + userid3).text(count);
											$("#undotext").text("Task deleted!");
											$("#undo").toast({delay:5000});
											$("#undo").toast("show");	
									},
									error : function(response){
										if(response.status == 403){
											alert('Only SUPERADMIN and MASTERADMIN can only control others tasks')
										}
										else if(response.status == 500){
											alert('An error occured. Reloading the page...');
										}
										
									}
								})
								 
							});

					$("#register").prop("disabled", true);

					$("#pass").keyup(
							function() {
								if ($(this).val() === $("#repass").val()
										&& $(this).val() !== "") {
									$(this).css({
										"border" : "2px solid green"
									});
									$("#repass").css({
										"border" : "2px solid green"
									});
									$("#register").prop("disabled", false);
								} else {
									$("#repass").css({
										"border" : "2px solid red"
									});
									$("#register").prop("disabled", true);
								}
							});

					$("#repass").keyup(
							function() {
								if ($(this).val() === $("#pass").val()
										&& $(this).val() !== "") {
									$(this).css({
										"border" : "2px solid green"
									});
									$("#register").prop("disabled", false);
								} else {
									$(this).css({
										"border" : "2px solid red"
									});
									$("#register").prop("disabled", true);
								}
							});
					$("#changepass").submit(
							function(e) {
								let currentPassword = $("#currentPass").val();
								let newPass = $("#pass").val();
								e.preventDefault();
								$(this).find("input[type=submit]").prop(
										"disabled", true);

								$.ajax({
									type : "POST",
									url : "/settings/updatePassword",
									data : {
										password : newPass,
										currentPass : currentPassword
									},
									success : function(result) {
										$("#changepass").find(
												"input[type=submit]").prop(
												"disabled", false);
										alert(result);
									}
								});

							});

				});