let currentPage = 0; // Start with the first page

document.getElementById('loadMore').addEventListener('click', function() {
    currentPage++; // Increment the current page

    fetch('/loadMore?page=' + currentPage)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            const productContainer = document.getElementById('productContainer');

            data.content.forEach(sanPham => {
                const productDiv = document.createElement('div');
                productDiv.className = 'product';

                productDiv.innerHTML = `
                        <div class="image-wrapper">
                            <img src="/${sanPham.anh}" alt="${sanPham.ten}" />
                            <div class="icon-heart">
                                <a href="#"><i class="bi bi-heart heart"></i></a>
                            </div>
                        </div>
                        <p class="price"><b>${sanPham.gia}</b> <s>6,999,000 đ</s></p>
                        <p class="name">${sanPham.ten}</p>
                    `;

                productContainer.appendChild(productDiv);
            });

            // Check if there are more products to load
            if (data.last) {
                document.getElementById('loadMore').style.display = 'none'; // Hide the button if no more products
            }
        })
        .catch(error => console.error('Error fetching data:', error));
});