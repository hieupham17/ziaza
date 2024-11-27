$(document).ready(function() {
    // Hàm xử lý đăng nhập khi nhấn nút Đăng nhập
    $('#loginButton').click(function() {
        handleLogin();
    });

    // Xử lý khi nhấn Enter trong form
    $('#username, #password').keypress(function(event) {
        if (event.which === 13) { // Kiểm tra nếu phím Enter (key code 13) được nhấn
            handleLogin();
        }
    });

    // Hàm đăng nhập chính
    function handleLogin() {
        const form = $('#create_customer')[0];

        if (!form.checkValidity()) {
            form.reportValidity();
            return;
        }

        const loginData = {
            username: $('#username').val().trim(),
            password: $('#password').val().trim()
        };

        $.ajax({
            url: '/auth',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(loginData),
            success: function(response) {
                const token = response.token;
                const role = response.vaiTro;
                localStorage.setItem('token', token);

                $('#message').html('<div class="alert alert-success">Đăng nhập thành công!</div>');
                hideMessageAfterDelay();
                if (role === 'USER' || role === 'ADMIN') {
                    setTimeout(function() {
                        window.location.href = '/staff';
                    }, 500);
                } else if (role === 'CLIENT') {
                    setTimeout(function() {
                        window.location.href = '/';
                    }, 500);
                } else {
                    window.location.href = '/';
                }
            },
            error: function(jqXHR) {
                $('#message').html('<div class="alert alert-danger">Thông tin đăng nhập không chính xác!</div>');
                hideMessageAfterDelay();
            }
        });
    }

    // Hàm ẩn thông báo sau khi delay
    function hideMessageAfterDelay() {
        setTimeout(function() {
            $('#message').fadeOut('slow', function() {
                $(this).empty().show();
            });
        }, 2000);
    }
});
