$(document).ready(function(){
	
	// Add Company and Master Admin
	
	$("#addcomp").submit(function(){
		$("#submit").prop('disabled',true);
		$("#submit").text("Please wait...");
		$.post($(this).attr("action"), $(this).serialize(),function(result){
			if(result){
				alert("Company and Master Admin successfully added!");
				window.location.reload();
			}
			else{
				alert("That email already exists");
				$("#submit").prop('disabled',false);
				$("#submit").text("Add");
			}
			
		});
		return false;
	});
	
	$("#addna").click(function(){
		$("#history").val($("#histval").text());
		$("#vision").val($("#visval").text());
		$("#mission").val($("#misval").text());
		$("#philosophy").val($("#philval").text());
		$("#corevalue").val($("#coreval").text());
	})
	
	// Add Company DNA
	
	$("#dnaform").submit(function(){
		$.post($(this).attr('action'),$(this).serialize(),function(result){
			alert(result);
			window.location.reload();
		});
		return false;
	})


	// Show Companies in the Table
			
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