$(document).ready(function(){
	
	let newcompany = {};
	newcompany['companyname'] = $("#company").val();
	
	let masteradmin = {};
	masteradmin['fname'] = $("#firstname").val();
	masteradmin['lname'] = $("#lastname").val();
	masteradmin['useremail'] = $("#useremail").val();
	masteradmin['contact'] = $("#contact").val();
	
	$("#addcomp").submit(function(){
		$.post($(this).attr("action"), $(this).serialize(),function(result){
			alert("Company and Master Admin successfully added!");
			window.location.reload();
		});
		return false;
	});
	

			
	var table = $('#mytable').DataTable({
				
				"scrollY":        "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/companies/datatable' , type : "GET" },
			    'serverSide' : true,
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [{
				targets: -1,
				className: 'dt-right'
				}],
			    columns : [{
			      data : 'compname',
			      render : function(data, type , row){
					return "<p style='color :"+ row.color +" '> "+ data + "</p>";
			      }
			    }, {
			      data : 'masteradmin',
			      orderable : false,
			      searchable : false
			    },{
			    	data : "user",
			    	render : function(data, type ,row){
			    		return data.length;
			    	}
			    },{
			    	data : 'id',
			    	render : function(data){
			    		return "<button class='btn btn-primary viewcomp'> View Details </button> "+
			    		" <button class='btn btn-secondary' data-bs-toggle='modal' data-bs-target='#companyDNA'>View DNA</button>";
			    	}
			    }]
			  });
			 $("#mytable tbody").on('click','.viewcomp',function(){
				 var data = table.row($(this).parents('tr')).data();
				
				  window.location.href="/companies/" + data.compname;
				 
			 });
		
	
});