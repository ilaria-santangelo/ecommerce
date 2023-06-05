document.addEventListener("DOMContentLoaded", (event) => {
  fetch("/getOrdersCustomerServlet")
    .then((response) => {
      if (!response.ok) {
        throw new Error(response.statusText);
      }
      return response.json();
    })
    .then((data) => {
      // Prepare a map to hold orders
      let ordersMap = {};

      // Loop through the data and populate the orders map
      // Loop through the data and populate the orders map
      for (let item of data) {
        // Swap the IDs

        if (!ordersMap[item.id]) {
          ordersMap[item.id] = {
            id: item.id,
            orderDate: item.orderDate,
            status: item.status,
            items: [],
          };
        }

        // Add the product to the order
        // Add the product to the order
        ordersMap[item.id].items.push({
          productName: item.productName,
          quantity: item.quantity,
          price: item.price,
          orderItemId: item.orderItemId,
          reviewText: item.reviewText,
          starRating: item.starRating,
        });
      }

      // Now create HTML for each order and its items
      for (let id in ordersMap) {
        let orderHtml = createOrderItem(ordersMap[id]);
        document
          .querySelector(".orders")
          .insertAdjacentHTML("beforeend", orderHtml);
      }
    })
    .catch((err) => console.error(err));
});

document.querySelector(".review-form").addEventListener("submit", (event) => {
  event.preventDefault();

  let productId = event.target.elements.orderItemId.value;
  let rating = event.target.elements.rating.value;
  let comment = event.target.elements.comment.value;

  submitReview(productId, rating, comment);
});

document.addEventListener("DOMContentLoaded", (event) => {
  // Check if reviewSubmitted query parameter is present and true
  const urlParams = new URLSearchParams(window.location.search);
  const reviewSubmitted = urlParams.get("reviewSubmitted");
  if (reviewSubmitted === "true") {
    // Display a "Review Submitted" message
    const reviewMessage = document.createElement("div");
    reviewMessage.textContent = "Review Submitted";
    reviewMessage.style.color = "green";
    document.body.prepend(reviewMessage);
  }
});

// Create a new order item HTML string
// Create a new order item HTML string
function createOrderItem(order) {
    let itemsHtml = order.items.map(item => {
        if (item.reviewText != null && item.starRating != null) {
            return `
                <div class="order-item-product">
                    <p>Product Name: ${item.productName}</p>
                    <p>Quantity: ${item.quantity}</p>
                    <p>Price: ${item.price}</p>
                    <p><strong>Review:</strong> ${item.reviewText}</p>
                    <p><strong>Rating:</strong> ${item.starRating}</p>
                </div>
            `;
        } else {
            return `
                <div class="order-item-product">
                    <p>Product Name: ${item.productName}</p>
                    <p>Quantity: ${item.quantity}</p>
                    <p>Price: ${item.price}</p>
                    
                    <form class="review-form" onsubmit="submitReview(event, ${item.orderItemId})">
                    <p name="orderItemId" >${item.orderItemId}</p>
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

    return `
        <div class="order-item">
            <h3>Order ID: ${order.id}</h3>
            ${itemsHtml}
            <p>Status: ${order.status}</p>
            <p>Order Date: ${new Date(order.orderDate).toLocaleString()}</p>
        </div>
    `;
}


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
        <p><strong>Review Submitted:</strong> ${reviewText}</p>
        <p><strong>Rating:</strong> ${starRating}</p>
    `;
    })
    .catch((err) => console.error(err));
}
