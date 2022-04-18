$(document).ready(function(){
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
