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

    // var userDetailButton = document.querySelectorAll('.user-details-button');
    // userDetailButton.forEach(function(button){
    //     button.addEventListener('click', function(){
    //         var username = this.getAttribute('data-username');
    //
    //         fetch(`/userDetail?username=${username}`)
    //             .then(reponse => response.json())
    //             .then(user => {
    //                 document.getElementById('modalName').textContent = 'Name : ' + user.name;
    //                 document.getElementById('modalId').textContent = 'Id : ' + user.username;
    //                 document.getElementById('modalEmail').textContent = 'Email : ' + user.email;
    //                 document.getElementById('modalBirthday').textContent = 'Birthday : ' + user.birthday;
    //                 document.getElementById('modalRegDate').textContent = 'Register Date : ' + user.regDate;
    //             });
    //     });
    // });
});
