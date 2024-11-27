
$(document).ready(function (){
    var swiper = new Swiper(".mySwiper", {
        loop: true,
        spaceBetween: 10,
        slidesPerView: 5,
        freeMode: true,
        watchSlidesProgress: true,
    });
    var swiper2 = new Swiper(".mySwiper2", {
        loop: true,
        spaceBetween: 10,
        navigation: {
            nextEl: ".swiper-button-next",
            prevEl: ".swiper-button-prev",
        },
        thumbs: {
            swiper: swiper,
        },
    });

    // Tăng giá trị khi click vào nút tăng
    $('.quantity_plus').click(function() {
        var input = $(this).siblings('input[name="quantity_product"]');
        var currentValue = parseInt(input.val());
        var priceProduct = $('input[name="price_product"]').val();
        var totalPrice = null;
        input.val(currentValue + 1);
        totalPrice = priceProduct * (currentValue + 1);
        var formattedTotalPrice = totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
        $('#total_price span').text(formattedTotalPrice);
        $('#total_price').show();
    });

    // Giảm giá trị khi click vào nút giảm
    $('.quantity_minius').click(function() {
        var input = $(this).siblings('input[name="quantity_product"]');
        var currentValue = parseInt(input.val());
        var priceProduct = $('input[name="price_product"]').val();
        var totalPrice = null;
        if (currentValue > 1) { // Đảm bảo giá trị không nhỏ hơn 1
            input.val(currentValue - 1);
            totalPrice = priceProduct * (currentValue - 1);
            var formattedTotalPrice = totalPrice.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
            $('#total_price span').text(formattedTotalPrice);
            $('#total_price').show();
        }

    });

    // Mua hàng
    $('.btn-buy').click(function( event ) {
        var formData = $( "form" ).serialize();
        $.ajax({
            url: 'https://api.example.com/data', // URL của API mua hàng
            type: 'POST', // Phương thức POST
            data: formData, // Dữ liệu cần gửi
            contentType: 'application/json', // Định dạng dữ liệu là JSON
            success: function(response) {
                console.log('Success:', response); // Xử lý kết quả thành công
                window.location.href = window.location.origin + '/checkout';
            },
            error: function(error) {
                console.error('Error:', error); // Xử lý lỗi nếu có
            }
        });
    })

    // Thêm vào giỏ hàng
    $('.btn-addcart').click(function( event ) {
        var formData = $( "form" ).serialize();
        $('body').addClass('open-cart');
    })

})
