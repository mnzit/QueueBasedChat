
var socket = new SockJS('/ws');
stompClient = Stomp.over(socket);
//stompClient.debug = null
stompClient.connect({}, function (frame) {
    stompClient.subscribe('/user/queue/private', function (greeting) {
        alert(greeting.body)
    });
    stompClient.subscribe('/app/participants', function (greeting) {
        let users = JSON.parse(greeting.body);
        let data = "";
        
        for (let i = 0; i < users.length; i++) {
            data += `<li class="list-group-item">${users[i]}</li>`
        }
        $('#client-list').html(data)
    });
});

stompClient.dis

$(document).ready(function () {
    $('#send').on('click', function () {
        let user = $('#name').val();
        let msg = $('#msg').val();
        stompClient.send(`/app/private/message/${user}`, {}, msg);
    });
});



