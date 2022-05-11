
$(document).ready(function() {

	
		$("#viewscore :input").prop("disabled",true); // Disables Edit on View Scorecard

	// Show Personnel of My Companies (MASTERADMIN and others)

	var cptable = $("#cptable").DataTable({
	
        responsive: true,
		"serverSide": false,
		"columnDefs": [{
			targets: -1,
			className: 'dt-right'
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
			data: "company.compname",
			render: function(data, type, row) {
				if (data == null) {
					return "Does not belong to any company";
				}
				else {
					return "<p style='color :" + row.company.color + " '> " + data + "</p>";
				}
			}
		}, {
			data: "userrole",
			render: function(data, type, row) {
				if (row.enabled)
					return data[0].role.rolename;
				else {
					return "<p style='opacity : 0.3'>" + data[0].role.rolename + "</p>";
				}
			}
		}, {
			data: 'id',
			render: function(data, type, row) {
				if (row.enabled) {
					return "<button class='btn btn-primary viewercp' > View Details </button> <button class='btn btn-secondary addscorecard' data-bs-toggle='modal' data-bs-target='#addscorecard'>View Scorecard</button>";
				}
				else {
					return "<button class='btn btn-primary viewercp'> View Details </button> <button class='btn btn-secondary addscorecard' data-bs-toggle='modal' data-bs-target='#addscorecard' > View Scorecard </button>";
				}

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
			data: "company.compname",
			render: function(data, type, row) {
				if (data == null) {
					return "Does not belong to any company";
				}
				else {
					return "<p style='color :" + row.company.color + " '> " + data + "</p>";
				}
			}
		}, {
			data: "userrole",
			render: function(data, type, row) {
				if (row.enabled)
					return data[0].role.rolename;
				else {
					return "<p style='opacity : 0.3'>" + data[0].role.rolename + "</p>";
				}
			}
		}, {
			data: 'id',
			render: function(data, type, row) {
				if (row.enabled) {
					return "<button class='btn btn-primary viewermy' > View Details </button> <button class='btn btn-secondary addscorecard' data-bs-toggle='modal' data-bs-target='#addscorecard'>View Scorecard</button>";
				}
				else {
					return "<button class='btn btn-primary viewermy' > View Details </button> <button class='btn btn-secondary addscorecard' disabled> View Scorecard </button>";
				}
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
		$.ajax({
			type : "GET",
			url : "/personnel/getDetail",
			data : {
				id : data.id
			},
			success: function(result){
				$("#role").val(result.role);
				for(var i = 0;i < result.company.dna.corevalue.length;i++){
					$("#cores").append("<tr colspan='3'> "
                         +  "<th class='p-5' style='text-align:center'>" + result.company.dna.corevalue[i].name +"</th> "
                          +  "<th>"
                           + "<textarea class='textareadef' rows='4' style='font-size:14px; width:100%;border: none;text-align:center;'>"
                           + " </textarea> </th><th><input class='bi-form form-control' type='text'><input class='bi-form form-control' type='text'><input class='bi-form form-control' type='text'> </th></tr>")
			
				}
			}
		});
		
		
	});
	
	$("#cptable tbody").on('click', '.addscorecard', function() {
		var data = cptable.row($(this).parents('tr')).data();
		$("#cores").find('tr').remove();
		$.ajax({
			type : "GET",
			url : "/personnel/getDetail",
			data : {
				id : data.id
			},
			success: function(result){
				$("#role").val(result.role);
				for(var i = 0;i < result.company.dna.corevalue.length;i++){
					$("#cores").append("<tr colspan='3'> "
                         +  "<th class='p-5' style='text-align:center'>" + result.company.dna.corevalue[i].name +"</th> "
                          +  "<th>"
                           + "<textarea class='textareadef' rows='4' style='font-size:14px; width:100%;border: none;text-align:center;'>"
                           + " </textarea> </th><th><input class='bi-form form-control' type='text'><input class='bi-form form-control' type='text'><input class='bi-form form-control' type='text'> </th></tr>")
			
				}
			}
		});
		
		
	});
	
	// For Saving Scorecard (testing only not working)
	
	$("#save").click(function(){
		$.ajax({
			type : "POST",
			url : "/personnel/savecard",
			contentType : "application/json",
			success : function(result){
				alert(result);
			}
		})
	});
	
	
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
