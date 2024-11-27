document.addEventListener("DOMContentLoaded", function() {
    const token = localStorage.getItem('token');
    const userInfo = document.getElementById('userInfo');
    const authButtons = document.getElementById('authButtons');
    const usernameDisplay = document.getElementById('usernameDisplay');

    // Các phần tử khác cần ẩn khi không có token
    const headerIcons = document.querySelectorAll('.header_icon > *:not(#authButtons)');

    if (token) {
        // Nếu có token, hiển thị thông tin người dùng và các phần tử khác
        userInfo.style.display = 'block';
        authButtons.style.display = 'none';
        headerIcons.forEach(icon => icon.style.display = 'block');
        const decodedToken = jwt_decode(token);
        const username = decodedToken.fullName;
        document.getElementById('usernameDisplay').innerText = username;
    } else {
        userInfo.style.display = 'none';
        authButtons.style.display = 'block';
        headerIcons.forEach(icon => icon.style.display = 'none');
    }
});

function toggleDropdown() {
    const dropdownMenu = document.getElementById('dropdownMenu');
    dropdownMenu.style.display = dropdownMenu.style.display === 'none' ? 'block' : 'none';
}

function logout() {
    localStorage.clear();
    location.reload();
}

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