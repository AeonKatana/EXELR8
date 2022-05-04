$(document).ready(function() {
	
	
	// Table view for SuperAdmin
	
	var table = $('#mytable').DataTable({
		'ajax': { url: '/dashboard/tardy', type: "GET" , dataSrc: ""},
		columns: [{
			data: 'id'
		}, {
			data: 'fullname',
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
		}]
	});
	
	var table = $('#overduetbl').DataTable({
		'ajax': { url: '/dashboard/overdue', type: "GET" , dataSrc: ""},
		columns: [{
			data: 'user.companyname'
		}, {
			data: 'user.fullname',
		}, {
			data: "description",
		},{
			data : "until"
		}]
	});
	
	
	
	function getTardy(){
		$.ajax({
			type : 'GET',
			url : 'dashboard/overdue',
			success : function(result){
				console.log(result);
			}
		})
	}
	
	getTardy();
	
	// Table view for MASTERADMIN and others
	
/*	const compqview = $("#compqview").DataTable({
		"scrollY": "350px",
		"scrollCollapse": true,
		"serverSide": false,
		'ajax': {
			url: '/compqview', type: 'GET', dataSrc: ""
		},
		"pageLength": 10,
		"lengthChange": false,
		 column :[{
			title : 'Company',
			data :  'compname'	
		},{
			title : 'Department',
			data : 'deptname'
		},{
			title : 'Title Task',
			data : 'taskname'
		},{
			title : 'Date Started',
			data : 'sdate'
		},{
			title : 'Deadline',
			data : 'edate'
		},{
			
			title : 'Progress',
			data : null,
			render : function (data , type ,row){
				return "<progress  max='" +row.totaltask + "' value='" + row.completed + "'>"
			}
		}]
	});
*/

});