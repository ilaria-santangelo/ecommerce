function getQueryParam(param) {
    let urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

document.addEventListener('DOMContentLoaded', (event) => {
    let productId = getQueryParam('id');
    if (productId) {
        // Fetch the product details and populate the form fields
        fetch(`/getSingleProductServlet?id=${productId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! status: ${response.status}`);
                }
                return response.json();
            })
            .then(product => {
                console.log(product); // Add this line to print the product
                // Populate the form fields with the product data
                document.querySelector('#productName').value = product.product_name;
                document.querySelector('#productDescription').value = product.product_description;
                document.querySelector('#productPrice').value = product.product_price;
                document.querySelector('#productCategory').value = product.product_category;
            })
            .catch(err => console.error("Fetch Error: ", err)); // Change here to log any Fetch API errors
    }
});
