function sendMessage() {
    const messageInput = document.querySelector('#messageInput');
    const message = messageInput.value;
    messageInput.value = '';

    // Get vendorId and customerId from the URL
    const urlParams = new URLSearchParams(window.location.search);
    const vendorId = urlParams.get('vendorId');
    const customerId = urlParams.get('customerId');

    fetch('/chatServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `message=${message}&vendorId=${vendorId}&customerId=${customerId}`,
    })
    .catch(err => console.error(err));
}

// Call receiveMessages() once when the page loads
document.addEventListener('DOMContentLoaded', receiveMessages);

// Call receiveMessages() again after sending a new message
document.querySelector('#sendButton').addEventListener('click', function() {
    sendMessage();
    receiveMessages();
});

document.querySelector('#sendButton').addEventListener('click', sendMessage);

function receiveMessages() {
    fetch('/chatServlet', {
        method: 'GET'
    })
    .then(response => {
        if (!response.ok) { 
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(messages => {
        const messagesContainer = document.querySelector('#messages');
        messagesContainer.innerHTML = '';

        for (let message of messages) {
            let messageElement = document.createElement('div');
            messageElement.textContent = message.content;

            // Add CSS class based on message type
            if (message.sender === 'user') {
                messageElement.classList.add('user-message');
            } else if (message.sender === 'vendor') {
                messageElement.classList.add('vendor-message');
            }

            messagesContainer.appendChild(messageElement);
        }
    })
    .catch(err => console.error(err));
}

// Call receiveMessages() every 5 seconds
setInterval(receiveMessages, 5000);
