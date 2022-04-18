$(document).ready(
				function() {

					$("#framebtn").click(function() {
						$("#framebtn").prop("disabled", true);
						$("#myframe").attr("src", url);
						$("#myframe").css("display", "block");

						$("#myframe").prop("height", 400);
					});

					let taskid = 0;
					$(".donebtn").click(function() {
						taskid = $(this).attr("tid");
					});

					$("#confirmdone").click(
							function() {

								$.ajax({
									type : "POST",
									url : "/task/markasdone",
									data : {
										status : true,
										id : taskid
									},
									success : function(result) {
										console.log(result);
										location.reload();
										$("#done" + taskid).parent().parent()
												.fadeOut();
									}
								});

							})

					$(".deletebtn").click(function() {
						taskid = $(this).attr("tid");
					});

					$("#confirmdelete").click(
							function() {
								$
										.ajax({
											type : "DELETE",
											url : "/task/deleteTask",
											data : {
												id : taskid
											},
											success : function(result) {
												console.log(result);
												$("#exampleModalCenter2")
														.modal('hide');
												$("#delete" + taskid).parent()
														.parent().fadeOut();
											}
										});
							})

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