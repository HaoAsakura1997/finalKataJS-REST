const urlUser = 'http://localhost:8080/user/userpage'
let loggedInUser = document.querySelector('#UserInfo');
let loggedUser = document.querySelector('#navBarUser')

fetch(urlUser)
    .then(res => res.json())
    .then(data => {
        loggedUser.innerHTML = `
                <span class="align-middle mr-1">${data.email}
                with roles: ${data.roles.map(role => role.name === 'USER' ? ' USER' : ' ADMIN')} </span>
                `;
        loggedInUser.innerHTML = `
                                <td>${data.id}</td>
                                <td>${data.username}</td>
                                <td>${data.surname}</td>
                                <td>${data.age}</td>
                                <td>${data.email}</td>                             
                                <td>${data.roles.map(role => role.name === 'USER' ? 'USER' : 'ADMIN')}</td>
                                `;
    })