$(document).ready(function(){
	
let comp = "";
$("#deptbtn").click(function(){
	comp = $("#companyselect").val();
    console.log(comp);
})

$("#companyselect").on('change',function(){
	comp = $(this).val();
	$("#mention1").val("");
	$("#mention2").val("");
	$("#mention3").val("");
	console.log(comp);
});
 

// Reveal names in mentions when typing @

$('textarea#mention1').mentionsInput({
	minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allProjleader/' + comp, function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});

$('textarea#mention2').mentionsInput({
	minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allSupervisor/' + comp, function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
	});
$('textarea#mention3').mentionsInput({
	minChars : 1,
	  onDataRequest:function (mode, query, callback) {
		  $.getJSON('/personnel/allAssociate/' + comp, function(responseData) {
		        responseData = _.filter(responseData, function(item) { return item.name.toLowerCase().indexOf(query.toLowerCase()) > -1 });
		        callback.call(this, responseData);
		      });
	  }
});

let depttable = $('#depttable').DataTable({
				"scrollY":        "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/department/departments' , type : "GET", dataSrc : "" },
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [{
				targets: -1,
				className: 'dt-right'
				}],
			    columns : [{
				  data : 'deptname'				
				},{
			      data : 'companyname',
			      render : function(data, type , row){
					return "<p style='color :"+ row.companycolor +" '> "+ data + "</p>";
			      }
			    }, {
			      data : 'userdepartment.length'
			    },{
			    	data : null,
			    	render : function(data, type ,row){
			    		return "<a class='btn btn-primary' href='/department/"+ row.id +"'>Visit</a>"
			    		+ "<button class='btn btn-danger deletebtn'>Delete</button>";
			    	}
		}]
});	
	
$("#depttable tbody").on('click','.deletebtn',function(){
	let data = depttable.row($(this).parents('tr')).data();
	
	$.ajax({
		type : "DELETE",
		url : "/department/deleteDepartment/" + data.id,
		success : function(response){
			console.log(response);
			depttable.ajax.reload();
		},
		error : function(response){
			alert(response.responseText);
		}
	})
	
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
		department['companyname'] = $("#companyselect").val();
		$.ajax({
			type : "POST",
			url : "/department/savedept",
			contentType : "application/json",
			data : JSON.stringify(department),
			success: function(result){
				window.location.reload();
			},
			error : function(response){
				alert(response.responseText);
			}
		})
		
    
	
});
	
	
});	