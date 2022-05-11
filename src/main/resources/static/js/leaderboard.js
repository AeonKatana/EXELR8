$(document).ready(function(){
	
	var companytbl = $("#companytbl").DataTable({
	"createdRow": function( row, data, dataIndex ) {
    	
    		$(row).css('background-color', data.color)
      		$(row).addClass( 'text-white' );
    		
  		},searching : false,
				"scrollY": "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/leaderboard/company' , type : "GET", dataSrc : "" },
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 2, 'desc' ]],
			    columns : [{
				   data : null	
				},{
			      data : 'compname',
			     },{
			      data : 'dailydone'
			    }]
	});
	companytbl.on( 'order.dt search.dt', function () {
    companytbl.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();
	 $("#companytbl tbody").on('click','tr',function(){
		 var data = companytbl.row(this).data();
		 window.location.href="/companies/" + data.compname;
				 
	});
	 $("#yearmonth").on('change',function(){
		
		companytbl.ajax.url('/leaderboard/company?yearmonth=' + $(this).val()).load();
			
	 });
	 
	 
	 var usertbl = $("#usertbl").DataTable({
	"createdRow": function( row, data, dataIndex ) {
    	
      		if(dataIndex === 0){
				$(row).addClass('bg-warning text-white')
			}
			else if(dataIndex === 1){
				$(row).addClass('bg-secondary text-white')
			}
			else if(dataIndex === 2){
				$(row).addClass('text-white')
				$(row).css('background-color','#CD7F32');
			}
    		
  		},searching : false,
				"scrollY": "380px",
		        "scrollCollapse": true,
			    'ajax' : { url : '/leaderboard/user' , type : "GET", dataSrc : "" },
			    "pageLength": 5,
				"lengthChange": false,
				"columnDefs": [ {
        "searchable": false,
        "orderable": false,
        "targets": 0
    } ],
    "order": [[ 3, 'desc' ]],
			    columns : [{
				   data : null
				},{
				  data : 'compname',
				  "searchable": true,
        "orderable": false
				},{
			      data : 'fullname',
			      "searchable": true,
        "orderable": false
			     },{
			      data : 'dailydone',
			      "searchable": false,
        "orderable": false
			    }]
	});
	usertbl.on( 'order.dt search.dt', function () {
    usertbl.column(0, {search:'applied', order:'applied'}).nodes().each( function (cell, i) {
        cell.innerHTML = i+1;
    } );
} ).draw();
	 
	  $("#yearmonthuser").on('change',function(){
		usertbl.ajax.url('/leaderboard/user?yearmonth=' + $(this).val()).load();	
	 });
	 
});