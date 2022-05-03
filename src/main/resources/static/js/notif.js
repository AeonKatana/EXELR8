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
});