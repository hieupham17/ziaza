document.addEventListener('DOMContentLoaded', function() {
    document.querySelector('#edit-profile').addEventListener('click', function() {
        document.querySelector('.nav-tabs').style.display = 'none';
        document.querySelector('.tab-content').style.display = 'none';
        document.querySelector('#profile-form').style.display = 'block';
    });
});
