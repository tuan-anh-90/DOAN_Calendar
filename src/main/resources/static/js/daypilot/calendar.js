// Lấy giá trị email từ localStorage
const userEmail = localStorage.getItem('name');
console.log(userEmail);
// Kiểm tra xem userEmail có tồn tại và không rỗng
if (userEmail) {
    document.addEventListener('DOMContentLoaded', function() {
        const userNameElement = document.getElementById('userNameDisplay');
        // Gán giá trị userEmail vào phần tử
        userNameElement.innerText = "Xin chào: "+userEmail;
    });
    // Tìm phần tử có lớp .Name và id userNameDisplay
    
}