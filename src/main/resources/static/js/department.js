$(document).ready(function(){
	

// Reveal names in mentions when typing @

$('textarea#mention1').mentionsInput({
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allProjleader', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});

$('textarea#mention2').mentionsInput({
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allSupervisor', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});
$('textarea#mention3').mentionsInput({
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allAssociate', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
});
	
	
	
// Add the department	
$("#adddept").submit(function(e){
	
	e.preventDefault();
	var supervisors = [];
	var projleaders = [];
	var associates = [];
	$("textarea#mention1").mentionsInput('getMentions',function(data){
		projleaders = data;
	});
	
	$("textarea#mention2").mentionsInput('getMentions',function(data){
		supervisors = data;
	});
	
	
	$('textarea#mention3').mentionsInput('getMentions', function(data) {
		associates = data;
	});
	
		const allmembers = projleaders.concat(supervisors,associates);
		var department = {};
		department['deptname'] = $("#deptname").val();
		department['people'] = allmembers;

		$.ajax({
			type : "POST",
			url : "/department/savedept",
			contentType : "application/json",
			data : JSON.stringify(department),
			success: function(result){
				window.location.reload();
			}
		})
		
    
	
});
	
	
});	