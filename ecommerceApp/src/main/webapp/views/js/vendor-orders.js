window.onload = function() {
    fetch('/getOrdersServlet')
        .then(response => response.json())
        .then(data => {
            let ordersDiv = document.querySelector('.orders');
            data.forEach(order => {
                let orderCard = document.createElement('div');
                orderCard.className = 'order-card';
                orderCard.innerHTML = `
                    <h3>Order ID: ${order.id}</h3>
                    <p>Product Name: ${order.productName}</p>
                    <p>Quantity: ${order.quantity}</p>
                    <p>Price: ${order.price}</p>
                    <p>Status: ${order.status}</p>
                    <p>Order Date: ${new Date(order.orderDate).toLocaleDateString()}</p>
                `;
                ordersDiv.appendChild(orderCard);
            });
        })
        .catch(error => console.error(error));
};
