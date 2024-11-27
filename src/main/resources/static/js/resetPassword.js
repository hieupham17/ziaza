$(document).ready(function() {
    $('#resetPassword').on('click', function() {
        const password = $('#password').val();
        const retypePassword = $('#retypePassword').val();

        if (!password) {
            $('#message').text('Vui lòng nhập mật khẩu của bạn').css('color', 'red');
            return;
        }
        if (!retypePassword) {
            $('#message').text('Vui lòng xác nhận mật khẩu của bạn').css('color', 'red');
            return;
        }
        if (password !== retypePassword) {
            $('#message').text('Mật khẩu và xác nhận mật khẩu không khớp').css('color', 'red');
            return;
        }
        const activeToken = localStorage.getItem('tokenResetPassword');
        if (!activeToken) {
            $('#message').text('Token không hợp lệ, vui lòng thử lại.').css('color', 'red');
            return;
        }
        $.ajax({
            url: '/otp/reset-password',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                password: password,
                retypePassword: retypePassword,
                activeToken: activeToken
            }),
            success: function(response) {
                localStorage.removeItem('tokenResetPassword');
                $('#message').text('Mật khẩu đã được thay đổi thành công').css('color', 'green');
                window.location.href = '/v1/auth/login';
            },
            error: function(xhr) {
                $('#message').text('Có lỗi xảy ra, vui lòng thử lại.').css('color', 'red');
            }
        });
    });
});
