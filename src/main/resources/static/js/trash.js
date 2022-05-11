$(document).ready(function(){
	
	var trashtbl = $("#trashtbl").DataTable({
		"createdRow": function( row, data, dataIndex ) {
    	
      		$(row).addClass( 'pe-auto' );
    		
  		},
				"scrollY":        "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/department/'+ department +'/taskTrash' , type : "GET", dataSrc : "" },
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [{
				targets: -1,
				className: 'dt-right'
				}],
			    columns : [{
			      data : 'description',
			      render : function(data, type , row){
					return "<p style='color :"+ row.color +" '> "+ data + "</p>";
			      }
			    }, {
			      data : 'user.fullname'
			    },{
			    	data : "until"
			    },{
					data : null,
					render : function(data, type ,row){
						return "<button class='btn btn-primary restore'>Restore</button>" + "<button class='btn btn-danger delete' data-bs-target='#exampleModalCenter2' data-bs-toggle='modal'>Delete</button>";
					}
			}]
	});
	
	$("#trashtbl tbody").on('click','.restore',function(){
		var data = trashtbl.row($(this).parents('tr')).data();
		$.ajax({
			type : "PUT",
			url : "/task/undodelete/" + data.id,
			success : function(){
				trashtbl.ajax.reload();
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
	let taskid = 0;
	$("#trashtbl tbody").on('click','.delete',function(){
		var data = trashtbl.row($(this).parents('tr')).data();
		taskid = data.id;
		
	});
	
	$("#deletetask").click(function(){
			$.ajax({
			type : "DELETE",
			url : "/task/deleteTask",
			data : {
				id : taskid
				},
			success : function(result) {
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
		});
	});
	
	var donetbl = $("#donetbl").DataTable({
		"createdRow": function( row, data, dataIndex ) {
    	
      		$(row).addClass( 'pe-auto' );
    		
  		},
				"scrollY":        "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/department/'+ department + '/taskDone' , type : "GET", dataSrc : "" },
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [{
				targets: -1,
				className: 'dt-right'
				}],
			    columns : [{
			      data : 'description',
			      render : function(data, type , row){
					return "<p style='color :"+ row.color +" '> "+ data + "</p>";
			      }
			    }, {
			      data : 'user.fullname'
			    },{
			    	data : "until"
			    },{
					data : null,
					render : function(){
						return "<button class='btn btn-primary undo'>Undo</button>";
					}
			}]
	});
	
	$("#donetbl tbody").on('click', '.undo', function(){
		var data = donetbl.row($(this).parents('tr')).data();
		$.ajax({
			type : "PUT",
			url : "/task/undocheck/" + data.id,
			success: function(){
				donetbl.ajax.reload();
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
	
	
	
	
	
});