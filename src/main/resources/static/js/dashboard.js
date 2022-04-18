$(document).ready(function() {
	const superview = $("#view").DataTable({
		"scrollY": "350px",
		"scrollCollapse": true,
		"serverSide": false,
		'ajax': {
			url: '/superview', type: 'GET', dataSrc: ""
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
	
	const compqview = $("#compqview").DataTable({
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


});