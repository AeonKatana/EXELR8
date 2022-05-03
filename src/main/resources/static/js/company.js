$(document).ready(function(){
	
	// Add Company and Master Admin
	
	$("#addcomp").submit(function(){
		$("#submit").prop('disabled',true);
		$("#submit").text("Please wait...");
		$.post($(this).attr("action"), $(this).serialize(),function(result){
			console.log(result);
			if(result === 1){
				alert("Company Successfully Added!");
				window.location.reload();
			}
			else if(result === 2){
				alert("This company name already exist");
			}
			else if(result === 3){
				alert("This email is already being used by someone else");
			}
			else if(result === 4){
				alert("An error occured. Please try again");
			}
			$("#submit").prop('disabled',false);
		    $("#submit").text("Add");
			
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
			    'ajax' : { url : '/companies/datatable' , type : "GET", dataSrc : "" },
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
			      data : 'masteradmin'
			    },{
			    	data : "user",
			    	render : function(data, type ,row){
			    		return data.length;
			    	}
			    },{
			    	data : 'id',
			    	render : function(data){
			    		return "<button class='btn btn-primary viewcomp'> View Details </button> ";
			    	}
			    }]
			  });
			 $("#mytable tbody").on('click','.viewcomp',function(){
				 var data = table.row($(this).parents('tr')).data();
				
				  window.location.href="/companies/" + data.compname;
				 
			 });
		
	
});