window.onload = function() {
    fetch('/getOrdersServlet')
        .then(response => {
            if (!response.ok) { 
                throw new Error(response.statusText);
            }
            return response.json();
        })
        .then(data => {
            let ordersMap = {};

            for (let item of data) {
                if (!ordersMap[item.id]) {
                    ordersMap[item.id] = {
                        id: item.id,
                        orderDate: item.orderDate,
                        status: item.status,
                        items: []
                    };
                }

                ordersMap[item.id].items.push({
                    productName: item.productName,
                    quantity: item.quantity,
                    price: item.price
                });
            }

            let ordersDiv = document.querySelector('.orders');

            for (let id in ordersMap) {
                let orderCard = document.createElement('div');
                orderCard.className = 'order-card';
                
                let itemsHtml = ordersMap[id].items.map(item => `
                    <div class="order-item-product">
                        <p>Product Name: ${item.productName}</p>
                        <p>Quantity: ${item.quantity}</p>
                        <p>Price: ${item.price}</p>
                    </div>
                `).join('');

                orderCard.innerHTML = `
                    <h3>Order ID: ${ordersMap[id].id}</h3>
                    ${itemsHtml}
                    <p>Status: ${ordersMap[id].status}</p>
                    <p>Order Date: ${new Date(ordersMap[id].orderDate).toLocaleDateString()}</p>
                `;

                ordersDiv.appendChild(orderCard);
            }
        })
        .catch(error => console.error(error));
};
