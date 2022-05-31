$(document).ready(function(){
			let stompClient;
			let sendTo;

				const socket = new SockJS('/notifications');
				stompClient = Stomp.over(socket);
				stompClient.connect({}, function(){
				stompClient.subscribe('/user/queue/message',function(message){
					const parsed = JSON.parse(message.body);
					$("#text").text(parsed.content);
					$("#toaster").toast({delay:5000});
					$("#toaster").toast("show");
					let count = parseInt($("#notifcount").text());
					$("#notifcount").css('display','');
					$("#notifcount").text(count + 1);
				});				
				stompClient.subscribe('/user/queue/kick',function(message){
					window.location.reload();
				});
			});
		$(".donebtn").click(function(){
				let assignedby = $(this).attr("aid");
				sendTo = assignedby;
				const notif={
					to : sendTo
				}
				stompClient.send('/app/sendDone', {}, JSON.stringify(notif));	
		});
		
		$("#addform").submit(function(){
			$('textarea#who').mentionsInput('getMentions', function(data){
				const notif ={
					tos : data
				}
				stompClient.send('/app/sendAddTask', {}, JSON.stringify(notif));
			});
		});
		
		$("#confirmdelete").click(function(){
			const notif = {
				content : "has kicked you from the department",
				to : kickid
			}
			
			stompClient.send('/app/sendKick',{},JSON.stringify(notif));
		});
		
});