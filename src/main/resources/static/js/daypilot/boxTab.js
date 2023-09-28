function loadScript(src, callback) {
    var script = document.createElement('script');
    script.type = 'text/javascript';
    script.src = src;
    script.onload = callback;
    document.head.appendChild(script);
}
document.addEventListener('DOMContentLoaded', function () {
    const boxTab = document.querySelector('.boxTabbar');

    fetch('tabbar.html')
        .then(response => response.text())
        .then(data => {
            boxTab.innerHTML = data;

            loadScript('tabbar/tabbar.js', function () {
                console.log('JavaScript from tabbar.html is loaded and executed.');
            });
        })
        .catch(error => console.error('Error:', error));
});