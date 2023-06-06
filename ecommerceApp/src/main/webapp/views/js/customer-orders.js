window.onload = function() {
  fetch('/getOrdersCustomerServlet')
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
                  price: item.price,
                  reviewText: item.reviewText,
                  starRating: item.starRating,
                  reply: item.reply,
                  orderItemId: item.orderItemId
              });
          }

          let ordersDiv = document.querySelector('.orders');

          for (let id in ordersMap) {
              let orderCard = document.createElement('div');
              orderCard.className = 'order-card';
              
              let itemsHtml = ordersMap[id].items.map(item => {
                  if (item.reviewText != null && item.starRating != null) {
                      return `
                          <div class="order-item-product">
                              <p>Product Name: ${item.productName}</p>
                              <p>Quantity: ${item.quantity}</p>
                              <p>Price: ${item.price}</p>
                              <p><strong>Review:</strong> ${item.reviewText}</p>
                              <p><strong>Rating:</strong> ${item.starRating}</p>
                              ${item.reply ? `<p style="color: #007BFF;"><strong>Reply:</strong> ${item.reply}</p>` : ''}
                          </div>
                      `;
                  } else {
                      return `
                          <div class="order-item-product">
                              <p>Product Name: ${item.productName}</p>
                              <p>Quantity: ${item.quantity}</p>
                              <p>Price: ${item.price}</p>
                              
                              <form class="review-form" onsubmit="submitReview(event, ${item.orderItemId})">
                              <p name="orderItemId">${item.orderItemId}</p>
                              <textarea name="reviewText" placeholder="Write your review here"></textarea>
                              <select name="starRating">
                                  <option value="1">1</option>
                                  <option value="2">2</option>
                                  <option value="3">3</option>
                                  <option value="4">4</option>
                                  <option value="5">5</option>
                              </select>
                              <button type="submit">Submit Review</button>
                          </form>
                          </div>
                      `;
                  }
              }).join('');
              
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

function submitReview(event, orderItemId) {
event.preventDefault();

let starRating = event.target.elements.namedItem("starRating").value;
let reviewText = event.target.elements.namedItem("reviewText").value;

let formdata = new URLSearchParams();
formdata.append("orderItemId", orderItemId);
formdata.append("starRating", starRating);
formdata.append("reviewText", reviewText);

fetch("/reviewServlet", {
  method: "POST",
  body: formdata,
})
  .then((response) => {
    if (!response.ok) {
      throw new Error(response.statusText);
    }
    alert("Review Submitted");

    event.target.innerHTML = `
    <p ><strong>Review Submitted: ${reviewText}</strong></p>
    <p ><strong>Rating: ${starRating}</strong></p>
  `;
  
  })
  .catch((err) => console.error(err));
}
