function ServiceFactory() {

    this.post = function (url, params, onCool, onError, data) {
        const xhr = new XMLHttpRequest();
        xhr.open('POST', url + params, true);

        xhr.onload = function () {
            const data = xhr.responseText;
            console.log(JSON.parse(data))
            onCool(JSON.parse(data));
        };

        xhr.onerror = onError;
        xhr.send(data);
    };

    this.get = function (url, params, onCool, onError) {
        const xhr = new XMLHttpRequest();
        xhr.open('GET', url + params, true);

        xhr.onload = function () {
            const data = xhr.responseText;
            console.log(JSON.parse(data))
            onCool(JSON.parse(data));
        };

        xhr.onerror = onError;
        xhr.send();
    }
}