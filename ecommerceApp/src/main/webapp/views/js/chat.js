let customerID = 'customer1';  // You would normally get these from somewhere else, such as a login system
let vendorID = 'vendor1';  // For now, we'll just hardcode them
let websocket = new WebSocket(`ws://localhost:8080/chat/${customerID}/${vendorID}`);

websocket.onmessage = function(event) {
    let messageContainer = document.createElement('div');
    messageContainer.textContent = event.data;
    document.querySelector('#messages').appendChild(messageContainer);
};

document.querySelector('#send-button').addEventListener('click', function() {
    let messageInput = document.querySelector('#message-input');
    websocket.send(messageInput.value);
    messageInput.value = '';
});