$(document).ready(function() {
	
						$('textarea.mention3').mentionsInput({
							 minChars : 1,
										onDataRequest : function(mode,query, callback) {
										$.getJSON('/personnel/people',function(responseData) {
												responseData = _.filter(responseData,function(item) {
									           return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1});
										        callback.call(this,responseData);
										});
							}
						});

							// Add the members
							$("#adddeptuserform").submit(function(e) {

										e.preventDefault();
								$('textarea.mention3').mentionsInput('getMentions',function(data) {

										var department = {};
										department['deptname'] = $("#deptid").val();
										department['people'] = data;
										$.ajax({
												type : "POST",
												url : "/department/addDeptUser",
												contentType : "application/json",
											    data : JSON.stringify(department),
												success : function(result) {
													
														window.location.reload();
												},error : function(){
													alert("An error occured while adding members. Maybe the personnel doesn't exist anymore");
													window.location.reload();
												}
										})

									});

							});
							$('textarea#who').mentionsInput({
								 minChars : 0,
										onDataRequest : function(mode,query, callback) {
										$.getJSON('/personnel/deptpeople',function(responseData) {
												responseData = _.filter(responseData,function(item) {
									           return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1});
										        callback.call(this,responseData);
										});
							}
							});

							$("#addform").submit(function(e){
									e.preventDefault();
									var task = {};
									$("#submit").prop('disabled',true);
										$("#submit").text('Please wait...');
								$('textarea#who').mentionsInput('getMentions', function(data){
									task['who'] = data;
									task['taskdetail'] = $("#verb3").val() + " " + $("#number3").val() + " " + $("#what3").val();
									task['until'] = $("#date3").val();
									task['deptid'] = $("#deptid2").val();
									$.ajax({
										type : "POST",
										url : "/task/multiAdd",
										contentType : "application/json",
										data : JSON.stringify(task),
										success: function(result){
											window.location.reload();
											
										},
										error : function(){
											alert("Task creation failed. Please reload and try again");
											window.location.reload();
										}
									})
									
							    });
		
						});

							
							
							let userid = 0;
							$(".addbtn").click(function(){
								userid = $(this).attr("uid");
								
							})
							
							// Add the task	
							$("#detailform").submit(function(e) {
								e.preventDefault();
								var task = {};
										$("#submit").prop('disabled',true);
										$("#submit").text('Please wait...');
										task['taskdetail'] = $("#verb").val() + ' ' + $("#number").val() + ' ' + $("#what").val();
										task['until'] = $("#date").val();
									   	task['userid']= userid;
										$.ajax({
											type : "POST",
											url : "/task/savemytask",
											contentType : "application/json",
											data : JSON.stringify(task),
											success : function(result) {
												
												window.location.reload();
											},
											error : function() {
												$("#submit").prop('disabled',false);
												$("#submit").text('Add Task');
												alert('An Error occured. Reloading the page...');
												window.location.reload();
											}
										})

									});
								
							let editid;
							let userid2;
							$(".editbtn").click(function(){
								editid = $(this).attr("did");
								userid2 = $(this).attr("uid");
								$.ajax({
									type : "GET",
									url : "/task/getTask/" + editid,
									success : function(data){
										let word = data.taskdetail;
										let verb = word.split(' ')[0];
										let number = word.split(' ')[1];
										let what = word.split(' ')[2];
										$("#verb2").val(verb);
										$("#number2").val(number);
										$("#what2").val(what);
										$("#date2").val(data.until);
									}
								});
								
							});	
							
							$("#editform").submit(function(e){
								e.preventDefault();
								var task ={};
								task['taskdetail'] = $("#verb2").val() + " " + $("#number2").val() + " " + $("#what2").val();
								task['until'] = $("#date2").val();
								task['userid'] = userid2;
								
								$("#submit2").prop('disabled',true);
								$("#submit2").text("Please wait...");
								
								$.ajax({
									type : "PUT",
									url : "/task/edittask/" + editid,
									contentType : "application/json",
									data : JSON.stringify(task),
									success: function(result){
										window.location.reload();
									
										$("#submit2").prop('disabled',false);
										$("#submit2").text("Save Changes");
									},
									error: function(){
										alert("An error occured. Reloading the page...");
									}
								});
							});

										
							let kickid = 0;
							let deptid = 0;
							$(".kickbtn").click(function() {

								kickid = $(this).attr("kid");
								deptid = $(this).attr("dep");

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
										window.location.reload();
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
										window.location.reload();
									}
								})
								 
							});
						/*	$("#deletetask").click(function(){
								$.ajax({
									type : "DELETE",
									url : "/task/deleteTask",
									data : {
										id : taskid
									},
									success : function(result) {
										$("#row" + taskid).fadeOut();
										let count = parseInt($("#count" + userid3).text());
										count--;
										$("#count" + userid3).text(count);
										window.location.reload();
									},
									error : function(){
										alert('An error occured. Reloading the page...');
										window.location.reload();
									}
								});
							})
							 */
							$("#confirmdelete").click(function() {
								$.ajax({
									type : "DELETE",
									url : "/department/kickUser/" + kickid,
									data : {
										deptid : deptid
									},
									success : function(result) {
										
										window.location.reload();
										
									},error : function(){
										alert("Something went wrong.Reloading the page...");
									}
								});
							});
							
							
							$("#undobtn").click(function(){
							
								if(undotype === "done"){
									$.ajax({
									type : "PUT",
									url : "/task/undocheck/" + taskid,
									success : function(){
										window.location.reload();
									},
									error : function(response){
										if(response.status == 403){
											alert('Only SUPERADMIN and MASTERADMIN can only control others tasks')
										}
										else if(response.status == 500){
											alert('An error occured. Reloading the page...');
										}
										window.location.reload();
									}
								})
							}
								else if (undotype === "delete"){
								$.ajax({
									type : "PUT",
									url : "/task/undodelete/" + taskid,
									success : function(){
										window.location.reload();
									},
									error : function(response){
										if(response.status == 403){
											alert('Only SUPERADMIN and MASTERADMIN can only control others tasks')
										}
										else if(response.status == 500){
											alert('An error occured. Reloading the page...');
										}
										window.location.reload();
									}
								})
								}
								
							});
							
							
						});