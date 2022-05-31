
$(document).ready(function() {

	
	//	$("#viewscore :input").prop("disabled",true); // Disables Edit on View Scorecard

	// Show Personnel of My Companies (MASTERADMIN and others)

	var cptable = $("#cptable").DataTable({
	
        responsive: true,
		"serverSide": false,
		"scrollY": "350px",
		"scrollCollapse": true,
		"columnDefs": [{
			targets: -1,
			className: 'dt-right'
		},{
                targets: 0,
                visible: false,
            }],
		'ajax': {
			url: '/personnel/mycompanypeople', type: 'GET', dataSrc: ""
		},
		"pageLength": 5,
		"lengthChange": false,
		columns: [{
			title: "Id",
			data: 'id'
		}, {
			title: "Full Name",
			data: null,
			render: function(data, type, row) {
				if (row.enabled) {
					return row.firstname + ' ' + row.lastname
				}
				else
					return "<p style='opacity : 0.3'>" + row.firstname + ' ' + row.lastname + "</p>";
			}
		}, {
			data: "companyname",
			render: function(data, type, row) {
				if (data == null) {
					return "Does not belong to any company";
				}
				else {
					return "<p style='color :" + row.companycolor + " '> " + data + "</p>";
				}
			}
		}, {
			data: "role",
		}, {
			data: 'id',
			render: function(data, type, row) {
				
					return "<button class='btn btn-primary viewercp' > View Details </button> <button class='btn btn-secondary addscorecard' data-bs-toggle='modal' data-bs-target='#addscorecard'> Scorecard</button>";
				

			}
		}]
	});
	$("#cptable tbody").on('click', '.addscorecard', function() {
		var data = cptable.row($(this).parents('tr')).data();
	});


	// Show all personnels (SUPERADMIN)

	var table = $('#mytable').DataTable({
		
		"scrollY": "350px",
		"scrollCollapse": true,
		'ajax': { url: '/personnel/datatable', type: "GET" , dataSrc: ""},
		'serverSide': false,
		"pageLength": 5,
		"lengthChange": false,
		"columnDefs": [{
			targets: -2,
			className: 'dt-right'
		},{
                targets: 0,
                visible: false,
            }],
		columns: [{
			data: 'id'
		}, {
			data: 'firstname',
			render: function(data, type, row) {
				if (row.enabled) {
					return row.firstname + ' ' + row.lastname;
				}
				else
					return "<p style='opacity : 0.3'>" + row.firstname + ' ' + row.lastname + "</p>";

			}
		}, {
			data: "companyname",
			render: function(data, type, row) {
				if (data == null) {
					return "Does not belong to any company";
				}
				else {
					return "<p style='color :" + row.companycolor + " '> " + data + "</p>";
				}
			}
		}, {
			data: "role",
			
		}, {
			data: 'id',
			render: function(data, type, row) {
				
					return "<button class='btn btn-primary viewermy' > View Details </button> <button class='btn btn-secondary addscorecard' data-bs-toggle='modal' data-bs-target='#addscorecard'>Scorecard</button>";
			}
		}, {
			data: 'lastname',
			visible: false
		}]
	});
	
	let id = 0;
	let ids = 0;
	
	// Check Profile of Personnel
	
	$("#mytable tbody").on('click', '.viewermy', function() {
		var data = table.row($(this).parents('tr')).data();
		ids = data.id;
		window.location.href="/profile/" + ids;
		
	});
	
	// Check Profile of Personnel
	
	$("#cptable tbody").on('click', '.viewercp', function() {
		var data = cptable.row($(this).parents('tr')).data();
		id = data.id;
		window.location.href="/profile/" + id;
	});
	
	
	$("#mytable tbody").on('click', '.addscorecard', function() {
		var data = table.row($(this).parents('tr')).data();
		$("#cores").find('tr').remove();
		$("#scoreid").val("0");
					$("#mainscorecard").val("");
					$("#perforaccel").val("");
					$("#educationalbg").val("");
					$("#corecompetencies").val("");
					$("#indicators").val("");
					$("#definition").val("");
					$("#metrics").val("");
					$("#roledesc").val("");
		$.ajax({
			type : "GET",
			url : "/personnel/getDetail",
			data : {
				id : data.id
			},
			success: function(result){
				$("#role").val(result.role);
				$("#role").text(result.role);
				$("#team").val(result.companyname);
				
				$("#userid").val(result.id);
				    
				if(result.scorecard != null){
					$("#scoreid").val(result.scorecard.id);
					$("#mainscorecard").val(result.scorecard.mainscorecard);
					$("#perforaccel").val(result.scorecard.perforaccel);
					$("#educationalbg").val(result.scorecard.educationalbg);
					$("#corecompetencies").val(result.scorecard.corecompetencies);
					$("#indicators").val(result.scorecard.indicators);
					$("#definition").val(result.scorecard.definition);
					$("#metrics").val(result.scorecard.metrics);
					$("#roledesc").val(result.scorecard.roledesc);
				}
				for(var i = 0;i < result.company.dna.corevalue.length;i++){
					$("#cores").append("<tr colspan='3'> "
                         +  "<th class='p-5' style='text-align:center'>" + result.company.dna.corevalue[i].name +"</th> "
                          +  "<th>"
                         + "<textarea class='textareadef' rows='4' style='font-size:14px; width:100%;border: none;text-align:center;' disabled>" + result.company.dna.corevalue[i].description
                           + " </textarea> </th><th><input class='bi-form form-control' type='text' disabled value='" + result.company.dna.corevalue[i].indicator +"'></th></tr>")
				}
			}
		});
		
		
	});
	
	$("#cptable tbody").on('click', '.addscorecard', function() {
		var data = cptable.row($(this).parents('tr')).data();
		$("#cores").find('tr').remove();
		$("#scoreid").val("0");
					$("#mainscorecard").val("");
					$("#perforaccel").val("");
					$("#educationalbg").val("");
					$("#corecompetencies").val("");
					$("#indicators").val("");
					$("#definition").val("");
					$("#metrics").val("");
					$("#roledesc").val("");
		$.ajax({
			type : "GET",
			url : "/personnel/getDetail",
			data : {
				id : data.id
			},
			success: function(result){
				
				$("#role").val(result.role);
				$("#role").text(result.role);
				$("#team").val(result.companyname);
				
				$("#userid").val(result.id);
				if(result.scorecard != null){
					$("#scoreid").val(result.scorecard.id);
					$("#mainscorecard").val(result.scorecard.mainscorecard);
					$("#perforaccel").val(result.scorecard.perforaccel);
					$("#educationalbg").val(result.scorecard.educationalbg);
					$("#corecompetencies").val(result.scorecard.corecompetencies);
					$("#indicators").val(result.scorecard.indicators);
					$("#definition").val(result.scorecard.definition);
					$("#metrics").val(result.scorecard.metrics);
					$("#roledesc").val(result.scorecard.roledesc);
				}
				
				for(var i = 0;i < result.company.dna.corevalue.length;i++){
					console.log(i);
					$("#cores").append("<tr colspan='3'> "
                         +  "<th class='p-5' style='text-align:center'>" + result.company.dna.corevalue[i].name +"</th> "
                          +  "<th>"
                           + "<textarea class='textareadef' rows='4' style='font-size:14px; width:100%;border: none;text-align:center;' disabled>" + result.company.dna.corevalue[i].description
                           + " </textarea> </th><th><input class='bi-form form-control' type='text' disabled value='" + result.company.dna.corevalue[i].indicator +"'></th></tr>")
			
				}
			}
		});
		
		
	});
	
	// For Saving Scorecard (testing only not working)
	
	
	
	$("#viewscore").submit(function(){
		$.post($(this).attr('action'), $(this).serialize(), function(result){
			alert(result);
			window.location.reload();
		})
		return false;
	})
	
	
	$("#addPersonnel").submit(function(){
		$("#submit").prop('disabled',true);
		$('#submit').text('Please wait...');
		$.post($(this).attr('action'), $(this).serialize(),function(result){
			if(result){
				alert("Personnel Successfully Added!");
				window.location.reload();
			}
			else{
				alert("User with that email already exist");
				$('#submit').text('Add');
				$('#submit').prop('disabled',false);
			}
		});
		return false;
	})
	
});
