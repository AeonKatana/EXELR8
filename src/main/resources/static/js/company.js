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
	
	$("#editbtn").click(function(){
		$("#purpose").val($("#purposeval").text());
	})
	
	$(".corebtn").click(function(){
		
		$("#coretitleedit").val($(this).attr('title'));
		$("#descedit").val($(this).attr('desc'));
		$("#coreid").val($(this).attr('cid'));
		$("#indicator").val($(this).attr('ind'));
	})
	
	
	// Add Company DNA
	
	
	
	
	$("#dnaform").submit(function(){
		$.post($(this).attr('action'),$(this).serialize(),function(result){
			alert(result);
			window.location.reload();
		});
		return false;
	})

	$("#coreform").submit(function(e){
		$.post($(this).attr('action'),$(this).serialize(),function(result){
			alert(result);
			window.location.reload();
		});
		return false;
	})

	// Show Companies in the Table
	$('#depttable').DataTable({
				"scrollY":        "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/department/comdept/' + company , type : "GET", dataSrc : "" },
			    "pageLength": 3,
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
					return "<p style='color :"+ row.color +" '> "+ data + "</p>";
			      }
			    }, {
			      data : 'userdepartment.length'
			    },{
			    	data : null,
			    	render : function(data, type ,row){
			    		return "<a class='btn btn-primary' href='/department/"+ row.id +"'>Visit</a>";
			    	}
		}]
});			
	var table = $('#mytable').DataTable({
				"createdRow": function( row, data, dataIndex ) {
    	
      		$(row).addClass( 'pe-auto' );
    		
  		},
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
			    }]
			  });
			 $("#mytable tbody").on('click','tr',function(){
				 var data = table.row(this).data();
				  window.location.href="/companies/" + data.compname;
				 
			 });
			 
			
		
	
});