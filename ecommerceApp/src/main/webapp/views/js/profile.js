document.addEventListener('DOMContentLoaded', (event) => {
    fetch('/getProductServlet')
    .then(response => {
        if (!response.ok) { 
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(products => {
        // Create a card for each product and insert it into the page
        for (let product of products) {
            let cardHtml = createProductCard(product);
            document.querySelector('.cards').insertAdjacentHTML('beforeend', cardHtml);
        }
    })
    .catch(err => console.error(err));


    var logoutBtn = document.getElementById('logout-btn');
    if(logoutBtn){
        logoutBtn.addEventListener('click', function(e) {
            e.preventDefault();
            fetch('/UserServlet', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: `action=logout`,
            })
            .then(response => window.location.href = '/src/main/webapp/views/login.html') // redirect to login page
            .catch(err => console.error(err));
        });
    }
});


// Create a new product card HTML string
function createProductCard(product) {
    return `
        <div class="product-card">
            <img src="/getImageServlet?ID=${product.ID}">
            <h3>${product.product_name}</h3>
            <p>${product.product_description}</p>
            <p>${product.product_price}</p>
            
        </div>
    `;
}



function searchProduct() {
    const searchQuery = document.getElementById('searchBar').value;
    fetch('/searchProductServlet', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: `query=${searchQuery}`,
    })
    .then(response => {
        if (!response.ok) { 
            throw new Error(response.statusText);
        }
        return response.json();
    })
    .then(products => {
        document.querySelector('.cards').innerHTML = '';
        for (let product of products) {
            let cardHtml = createProductCard(product);
            document.querySelector('.cards').insertAdjacentHTML('beforeend', cardHtml);
        }
    })
    .catch(err => console.error(err));
}



