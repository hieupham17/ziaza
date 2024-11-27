/*!
    * Start Bootstrap - SB Admin v7.0.7 (https://startbootstrap.com/template/sb-admin)
    * Copyright 2013-2023 Start Bootstrap
    * Licensed under MIT (https://github.com/StartBootstrap/startbootstrap-sb-admin/blob/master/LICENSE)
    */
//
// Scripts
//
window.addEventListener('DOMContentLoaded', event => {
    // Toggle the side navigation
    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }
});


// hình ảnh sản phẩm
let selectedFiles = []; // Danh sách các file đã chọn

function updateImagePreview3() {
    const fileInput = document.getElementById('profileImage3');
    const previewContainer = document.getElementById('previewContainer');

    // Duyệt qua các file mới được chọn
    Array.from(fileInput.files).forEach((file) => {
        // Kiểm tra nếu file chưa có trong danh sách selectedFiles
        if (!selectedFiles.some(selectedFile => selectedFile.name === file.name && selectedFile.size === file.size)) {
            selectedFiles.push(file); // Thêm file vào danh sách đã chọn
            const reader = new FileReader();

            reader.onload = function (event) {
                // Tạo một thẻ div cho mỗi ảnh
                const imageWrapper = document.createElement('div');
                imageWrapper.classList.add('image-wrapper');

                const img = document.createElement('img');
                img.src = event.target.result;

                // Tạo biểu tượng xóa
                const deleteIcon = document.createElement('button');
                deleteIcon.innerHTML = '<i class="fas fa-times"></i>';
                deleteIcon.classList.add('delete-icon');

                // Thêm sự kiện xóa ảnh
                deleteIcon.addEventListener('click', () => {
                    // Xóa ảnh và biểu tượng xóa khỏi previewContainer
                    imageWrapper.remove();
                    // Xóa file khỏi danh sách selectedFiles
                    selectedFiles = selectedFiles.filter(item => item !== file);

                    // Cập nhật lại fileInput
                    const dataTransfer = new DataTransfer();
                    selectedFiles.forEach(item => dataTransfer.items.add(item));
                    fileInput.files = dataTransfer.files; // Cập nhật lại danh sách file
                });

                // Thêm ảnh và biểu tượng xóa vào wrapper
                imageWrapper.appendChild(img);
                imageWrapper.appendChild(deleteIcon);
                previewContainer.appendChild(imageWrapper); // Thêm wrapper vào previewContainer
            };

            reader.readAsDataURL(file); // Đọc file dưới dạng Data URL
        }
    });

    // Cập nhật lại fileInput với danh sách selectedFiles
    const dataTransfer = new DataTransfer();
    selectedFiles.forEach(file => dataTransfer.items.add(file));
    fileInput.files = dataTransfer.files;
}

///// đổi màu menu
document.addEventListener("DOMContentLoaded", function () {
    const nav = document.getElementById("sidenavAccordion");
    const themeToggle = document.getElementById("themeToggle");

    // Kiểm tra trạng thái checkbox khi trang được tải
    if (themeToggle.checked) {

        nav.classList.remove("sb-sidenav-dark");
        nav.classList.add("sb-sidenav-light");
    } else {
        nav.classList.remove("sb-sidenav-light");
        nav.classList.add("sb-sidenav-dark");
    }

    // Xử lý sự kiện thay đổi trạng thái checkbox
    themeToggle.addEventListener("change", function () {
        if (themeToggle.checked) {
            nav.classList.remove("sb-sidenav-dark");
            nav.classList.add("sb-sidenav-light");
        } else {
            nav.classList.remove("sb-sidenav-light");
            nav.classList.add("sb-sidenav-dark");
        }
    });
});