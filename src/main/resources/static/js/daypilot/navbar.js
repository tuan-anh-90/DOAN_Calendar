const crudLink = document.getElementById('CRUDLink');
    const contentContainer = document.getElementById('contentContainer');

    crudLink.addEventListener('click', () => {
        const xhr = new XMLHttpRequest();

        xhr.onreadystatechange = function() {
            if (xhr.readyState === XMLHttpRequest.DONE) {
                if (xhr.status === 200) {
                    contentContainer.innerHTML = xhr.responseText;
                } else {
                    console.error('Failed to load content: ' + xhr.status);
                }
            }
        };

        xhr.open('GET', '/src/main/resources/static/template/crud.html');
        xhr.send();
    });