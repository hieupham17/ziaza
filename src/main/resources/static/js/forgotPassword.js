$(document).ready(function() {
    $('#forgotPassword').on('click', function() {
        const email = $('#email').val();

        if (!email) {
            $('#message').text('Vui lòng nhập email của bạn').css('color', 'red');
            return;
        }
        $.ajax({
            url: '/otp/reset-password',
            type: 'GET',
            data: { email: email },
            success: function(response) {
                $('#message').text('Mã OTP đã được gửi đến email của bạn').css('color', 'green');
                window.location.href = '/v1/auth/verify-reset-password';
            },
            error: function(xhr) {
                let errorMessage;
                if (xhr.status === 404) {
                    errorMessage = 'Email không tồn tại.';
                } else {
                    errorMessage = 'Có lỗi xảy ra, vui lòng thử lại.';
                }
                $('#message').text(errorMessage).css('color', 'red');
            }
        });
    });
});
