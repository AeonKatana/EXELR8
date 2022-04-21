$(document).ready(function(){
	// For Recurring which is not available
	$("#radio1").click(function(){
		
		$("#date").prop("disabled",false);
	});
	// For Recurring which is not available
	$("#radio2").click(function(){
		$("#date").prop("disabled",true);
	});
	// Destroy IFrame
	$(".btncancel").click(function(){
		$("#radio2").attr("checked", true);
		$(this).parent().parent().parent().remove();
		window.parent.document.getElementById('framebtn').removeAttribute("disabled");
		window.parent.document.getElementById('myframe').setAttribute("height", 0);
	});
	 
	
	// For Add Task (Reveal names when using @ for notify)
	$('textarea.mention').mentionsInput({
	  minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/people', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});	
	//For Add Task (Reveal names when using @ for assign)
	$('textarea#who').mentionsInput({
		 minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/people', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	})
	//For Edit Task (Reveal names when using @ for notify)
	$('textarea.mentions').mentionsInput({
	  minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/people', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});	
	//For Edit Task (Reveal names when using @ for assign)
	$('textarea#whos').mentionsInput({
		 minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/people', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	})
	


	
	// Add the Task
	
	$("#addform").submit(function(e){
		e.preventDefault();
		var task = {};
		$('textarea#who').mentionsInput('getMentions', function(data){
			task['who'] = data;
		});
	
		$('textarea.mention').mentionsInput('getMentions', function(data) {
		
		
		task['title'] = $("#title").val();
		task['taskdetail'] = $("#verb").val() + " " + $("#number").val() + " " + $("#what").val();
		task['until'] = $("#date").val();
		task['mentions'] = data;
		
		$.ajax({
			type : "POST",
			url : "/task/savemytask",
			contentType : "application/json",
			data : JSON.stringify(task),
			success: function(result){
				parent.location.href = "/dashboard/task/mytask"
				$("#addform").parent().parent().remove();	
				alert("Task Added!");
			}
		})
		
    });
		
	});
	let taskid = 0;
	
	
	// Get task detail and make it as a placeholder for edit
	$(".editbtn").click(function(){
		taskid = $(this).attr("tid");
		
		$.ajax({
			type : "GET",
			url : "/task/getTask/" + taskid,
			contentType : "application/json",
			success : function(data){
				let word = data.taskdetail;
				alert(word);
				let verb = word.split(' ')[0];
				let number = word.split(' ')[1];
				let what = word.split(' ')[2];
				$("#titles").val(data.title);
				$("#verbs").val(verb);
				$("#numbers").val(number);
				$("#whats").val(what);
				$("#dates").val(data.until);
			}
		});
		
	});
	
	// Save edit changes
	$("#edittask").submit(function(e){
		e.preventDefault();
		var task = {};
		$('textarea#whos').mentionsInput('getMentions', function(data){
			task['who'] = data;
		});
	
		$('textarea.mentions').mentionsInput('getMentions', function(data) {
		
		
		task['title'] = $("#titles").val();
		task['taskdetail'] = $("#verbs").val() + " " + $("#numbers").val() + " " + $("#whats").val();
		task['until'] = $("#dates").val();
		task['mentions'] = data;
		
		$.ajax({
			type : "PUT",
			url : "/task/edittask/" + taskid,
			contentType : "application/json",
			data : JSON.stringify(task),
			success: function(result){
				parent.location.href = "/dashboard/task/mytask"
				alert("Task Updated!");
			}
		})
		
    });
		
		
	});
	
	
	
});