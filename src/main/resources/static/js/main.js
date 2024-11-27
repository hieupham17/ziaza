//chuyen huong logo den trang Home
document.addEventListener('DOMContentLoaded', function () {
    const favicon = document.querySelector('link[rel="icon"]');
    favicon.addEventListener('click', function () {
        window.location.href = '/home';
    });
});
// document.addEventListener('DOMContentLoaded', function() {
//     const overLay = document.createElement('div');
//     overLay.classList.add('over_lay');
//     document.body.appendChild(overLay);
//     const navBtnMb = document.querySelector('.nav_btn_mb');
//     const btnSearch = document.querySelector('.icon_search');
//     const headerSearch = document.querySelector('.header_search');
//     // const btnCart = document.querySelector('.icon_card');
//     const overLayCart = document.querySelector('.over_lay_cart');
//     const closeCart = document.querySelector('.close-cart');
//
//     if (navBtnMb) {
//         navBtnMb.addEventListener('click', function() {
//             document.body.classList.toggle('open');
//         });
//         overLay.addEventListener('click', function() {
//             document.body.classList.remove('open');
//         });
//     }
//     if (btnSearch && headerSearch) {
//         btnSearch.addEventListener('click', function () {
//             headerSearch.classList.toggle('open_search');
//         });
//
//         document.addEventListener('click', function (event) {
//             if (!headerSearch.contains(event.target) && !btnSearch.contains(event.target)) {
//                 headerSearch.classList.remove('open_search');
//             }
//         });
//     }
//     // if (btnCart) {
//     //     btnCart.addEventListener('click', function() {
//     //         document.body.classList.toggle('open-cart');
//     //     });
//     //     overLayCart.addEventListener('click', function() {
//     //         document.body.classList.remove('open-cart');
//     //     });
//     //     closeCart.addEventListener('click', function() {
//     //         document.body.classList.remove('open-cart');
//     //     });
//     // }
// });

$(window).scroll(function () {
    if ($(this).scrollTop() > 200) {
        $('header').addClass('fixed-header');
    } else {
        $('header').removeClass('fixed-header');
    }
})
document.addEventListener('DOMContentLoaded', function () {
    const token = localStorage.getItem('token');
    const userInfoContainer = document.getElementById('userInfo');
    const authButtons = document.getElementById('authButtons');

    if (token) {
        try {
            const decodedToken = jwt_decode(token);
            const username = decodedToken.fullName;

            userInfoContainer.style.display = 'block';
            document.getElementById('usernameDisplay').innerText = username;
            authButtons.style.display = 'none';
        } catch (error) {
            console.error('Error decoding token:', error);
        }
    } else {
        userInfoContainer.style.display = 'none';
        authButtons.style.display = 'block';
    }
});

function toggleDropdown() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
}

// Đóng menu khi nhấp ra ngoài
document.addEventListener('click', function(event) {
    const dropdownMenu = document.getElementById('dropdownMenu');
    const usernameDisplay = document.getElementById('usernameDisplay');

    if (!usernameDisplay.contains(event.target) && !dropdownMenu.contains(event.target)) {
        dropdownMenu.style.display = 'none';
    }
});

function logout() {
    localStorage.clear();
    location.reload();
}

// Để tự động ẩn menu khi người dùng nhấn ra ngoài
window.onclick = function(event) {
    if (!event.target.matches('.username') && !event.target.matches('.user-icon')) {
        const dropdowns = document.getElementsByClassName("dropdown-menu");
        for (let i = 0; i < dropdowns.length; i++) {
            const openDropdown = dropdowns[i];
            if (openDropdown.style.display === 'block') {
                openDropdown.style.display = 'none';
            }
        }
    }
}
// Kích hoạt tooltip cho tất cả các phần tử có data-bs-toggle="tooltip"
document.addEventListener('DOMContentLoaded', function () {
    var tooltipTriggerList = document.querySelectorAll('[data-bs-toggle="tooltip"]');
    tooltipTriggerList.forEach(function (tooltipTriggerEl) {
        new bootstrap.Tooltip(tooltipTriggerEl);
    });
});


