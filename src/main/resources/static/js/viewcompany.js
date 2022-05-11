$(document).ready(function(){
	
	
	$("#addna").click(function(){
		$("#history").val($("#histval").text());
		$("#philosophy").val($("#philval").text());
		$("#corevalue").val($("#coreval").text());
	})
	
	// Add Company DNA
	
	$("#editbtn").click(function(){
		$("#purpose").val($("#purposeval").text());
	})
	
	$(".corebtn").click(function(){
		
		$("#coretitleedit").val($(this).attr('title'));
		$("#descedit").val($(this).attr('desc'));
		$("#coreid").val($(this).attr('cid'));
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
		});
		return false;
	})
	
	
	// Show Personnels when viewing the company
	var cptable = $("#cptable").DataTable({
        responsive: true,
		"serverSide": false,
		"columnDefs": [{
			targets: -1,
			className: 'dt-right'
		}],
		'ajax': {
			url: '/personnel/companypeople', type: 'GET', dataSrc: ""
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
			data: "userrole",
			render: function(data, type, row) {
				if (row.enabled)
					return data[0].role.rolename;
				else {
					return "<p style='opacity : 0.3'>" + data[0].role.rolename + "</p>";
				}
			}
		}]
	});
});
