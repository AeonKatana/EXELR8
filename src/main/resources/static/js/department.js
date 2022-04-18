$(document).ready(function(){
	



$('textarea.mention').mentionsInput({
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/people', function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});
	
$("#adddept").submit(function(e){
	
	e.preventDefault();
	$('textarea.mention').mentionsInput('getMentions', function(data) {
		
		var department = {};
		department['deptname'] = $("#deptname").val();
		department['people'] = data;
		
		
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
	
	
});	