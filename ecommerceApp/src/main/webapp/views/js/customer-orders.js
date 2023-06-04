document.addEventListener('DOMContentLoaded', (event) => {
    fetch('/getOrdersCustomerServlet')
        .then(response => {
            if (!response.ok) { 
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .then(data => {
            // Prepare a map to hold orders
            let ordersMap = {};

            // Loop through the data and populate the orders map
            for (let item of data) {
                if (!ordersMap[item.id]) {
                    ordersMap[item.id] = {
                        id: item.id,
                        orderDate: item.orderDate,
                        status: item.status,
                        items: []
                    };
                }

                // Add the product to the order
                ordersMap[item.id].items.push({
                    productName: item.productName,
                    quantity: item.quantity,
                    price: item.price
                });
            }

            // Now create HTML for each order and its items
            for (let id in ordersMap) {
                let orderHtml = createOrderItem(ordersMap[id]);
                document.querySelector('.orders').insertAdjacentHTML('beforeend', orderHtml);
            }
        })
        .catch(err => console.error(err));
});

// Create a new order item HTML string
function createOrderItem(order) {
    let itemsHtml = order.items.map(item => `
        <div class="order-item-product">
            <p>Product Name: ${item.productName}</p>
            <p>Quantity: ${item.quantity}</p>
            <p>Price: ${item.price}</p>
        </div>
    `).join('');

    return `
        <div class="order-item">
            <h3>Order ID: ${order.id}</h3>
            ${itemsHtml}
            <p>Status: ${order.status}</p>
            <p>Order Date: ${new Date(order.orderDate).toLocaleString()}</p>
        </div>
    `;
}
