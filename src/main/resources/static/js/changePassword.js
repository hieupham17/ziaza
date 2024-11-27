$(document).ready(function () {
    $('#changePassword').on('click', async function () {
        const oldPassword = $('#oldPassword').val();
        const newPassword = $('#newPassword').val();
        const retypePassword = $('#retypeNewPassword').val();

        if (!oldPassword) {
            $('#message').text('Vui lòng nhập mật khẩu cũ').css('color', 'red');
            return;
        }
        if (!newPassword) {
            $('#message').text('Vui lòng nhập mật khẩu mới').css('color', 'red');
            return;
        }
        if (!retypePassword) {
            $('#message').text('Vui lòng xác nhận mật khẩu mới').css('color', 'red');
            return;
        }
        if (newPassword !== retypePassword) {
            $('#message').text('Mật khẩu mới không khớp!').css('color', 'red');
            return;
        }

        const token = localStorage.getItem('token');
        console.log('Token:', token);

        try {
            const response = await $.ajax({
                url: '/auth/change-password',
                type: 'POST',
                contentType: 'application/json',
                headers: {
                    'Authorization': `Bearer ${token}`
                },
                data: JSON.stringify({
                    oldPassword: oldPassword,
                    password: newPassword,
                    retypePassword: retypePassword
                })
            });

            $('#message').html('<div class="alert alert-success">Đổi mật khẩu thành công!</div>');
            hideMessageAfterDelay();
            setTimeout(function () {
                window.location.href = '/';
            }, 500);
        } catch (xhr) {
            let errorMessage;
            if (xhr.status === 400) {
                errorMessage = 'Có lỗi xảy ra với thông tin bạn đã nhập.';
            } else {
                errorMessage = 'Có lỗi xảy ra, vui lòng thử lại.';
            }
            $('#message').text(errorMessage).css('color', 'red');
        }
    });

    function hideMessageAfterDelay() {
        setTimeout(function () {
            $('#message').fadeOut('slow', function () {
                $(this).empty().show();
            });
        }, 2000);
    }
});


