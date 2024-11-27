$(document).ready(function() {
    $('#verifyResetPassword').on('click', function() {
        const email = $('#email').val();
        const activeCode = $('#otp').val(); // Giữ nguyên tên activeCode

        if (!email) {
            $('#message').text('Vui lòng nhập email của bạn').css('color', 'red');
            return;
        }
        if (!activeCode) {
            $('#message').text('Vui lòng nhập mã xác thực').css('color', 'red');
            return;
        }
        $.ajax({
            url: '/otp/verify-reset-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ email: email, activeCode: activeCode }),
            success: function(response) {
                // Giả sử response là token
                localStorage.setItem('tokenResetPassword', response);
                $('#message').text('Mã xác thực thành công').css('color', 'green');
                window.location.href = '/v1/auth/reset-password';
            },
            error: function(xhr) {
                let errorMessage;
                if (xhr.status === 400) {
                    errorMessage = 'Mã xác thực không đúng hoặc đã hết hạn.';
                } else {
                    errorMessage = 'Có lỗi xảy ra, vui lòng thử lại.';
                }
                $('#message').text(errorMessage).css('color', 'red');
            }
        });
    });
});
