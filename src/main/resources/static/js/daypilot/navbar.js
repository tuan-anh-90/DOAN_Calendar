function showCRUDContent() {
    const contentContainer = document.getElementById('contentContainer');
    contentContainer.innerHTML = '';  // Clear previous content
    contentContainer.innerHTML = '<object type="text/html" data="/src/main/resources/static/template/crud.html" width="100%" height="100%"></object>';
  }
  
  // Hook the function to the CRUDLink click event
  const CRUDLink = document.getElementById('CRUDLink');
  CRUDLink.addEventListener('click', showCRUDContent);
