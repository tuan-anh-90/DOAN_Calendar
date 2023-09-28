let showPassword = document.getElementById('showPassword');
let inputPassword = document.getElementById('inputPassword');
showPassword.onclick = function () {
    if (inputPassword.type == 'password') {
        inputPassword.type = 'text';
        showPassword.classList.add('show');
    } else {
        inputPassword.type = 'password';
        showPassword.classList.remove('show');
    }
}

async function login(username, password) {
    const url = '/api/users/signin';
    const data = {
        email: username,
        password: password
    };

    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok');
        }

        return await response.json();
    } catch (error) {
        console.error('Error:', error);
        throw new Error('An error occurred. Please try again later.');
    }
}

document.querySelector('.signIn button').addEventListener('click', async function () {
    const username = document.querySelector('.group input[type="text"]').value;
    const password = document.getElementById('inputPassword').value;

    try {
        const response = await login(username, password);

        if (response && response.data.role) {
            if (response.data.role === 'Role_Admin') {  
                localStorage.setItem('role',response.data.role);
                window.location.href = 'http://localhost:8081/indexcrud.html';
            } else if (response.data.role === 'Role_User') {
                localStorage.setItem('role',response.data.role);
                localStorage.setItem('name', response.data.name);
                window.location.href = 'http://localhost:8081/home.html';
            } else {
                alert('Invalid role. Please enter a valid role.');
            }
        } else {
            alert('Invalid credentials. Please try again.');
        }
        
    } catch (error) {
        console.error('Error:', error);
        alert(error.message);
    }
});