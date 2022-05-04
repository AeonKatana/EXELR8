$(document).ready(function() {
	
	
	// Table view for SuperAdmin
	
	var tardytable = $('#mytable').DataTable({
		'ajax': { url: '/dashboard/tardy', type: "GET" , dataSrc: ""},
		columns: [{
			data: 'id'
		}, {
			data: 'fullname',
			render : function(data,type,row){
				return "<a href='/profile/" + row.id + "'>"+ data + "</a>";
			}
		}, {
			data: "company.compname",
			render: function(data, type, row) {
				if (data == null) {
					return "Does not belong to any company";
				}
				else {
					return "<a href='/companies/"+ data +"' style='color :" + row.company.color + " '> " + data + "</a>";
				}
			}
		}]
	});
	
	var overduetable = $('#overduetbl').DataTable({
		'ajax': { url: '/dashboard/overdue', type: "GET" , dataSrc: ""},
		"createdRow": function( row, data, dataIndex ) {
    	
      		$(row).addClass( 'table-danger' );
    	  
  		},
		columns: [{
			data: 'user.companyname',
			render : function(data,type,row){
				return "<a href='/companies/" + data + "' style='color : "+ row.user.companycolor +";text-decoration:none;'>"+ data + "</a>";
			}
		}, {
			data: 'user.fullname',
			render : function(data,type,row){
				return "<a href='/profile/" + row.user.id + "'>"+ data + "</a>";
			}
		}, {
			data: "description",
		},{
			data : "until"
		}]
	});
	
	var dailytable = $('#dailytable').DataTable({
		'ajax': { url: '/dashboard/daily', type: "GET" , dataSrc: ""},
		columns: [{
			data: 'id'
		}, {
			data: 'user.fullname',
			render : function(data,type,row){
				return "<a href='/profile/" + row.id + "'>"+ data + "</a>";
			}
		}, {
			data: "description",
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