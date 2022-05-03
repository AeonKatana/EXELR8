$(document).ready(function() {
	
	
	// Table view for SuperAdmin
	
	const superview = $("#view").DataTable({
		"scrollY": "350px",
		"scrollCollapse": true,
		"serverSide": false,
		'ajax': {
			url: '/dashboard/tardy', type: 'GET', dataSrc: ""
		},
		"pageLength": 10,
		"lengthChange": false,
		 column :[{
			title : 'Id',
			data :  'id'	
		},{
			title : 'Full Name',
			data : 'fullname'
		},{
			title : 'Company',
			data : 'companyname'
		}]
	});
	
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