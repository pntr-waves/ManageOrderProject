function ServiceFactory() {

    const host = "http://localhost:8080/";

    this.post = function (url, params, onCool, onError, data) {
        const xhr = new XMLHttpRequest();
        let linkUrl = host + url + "?" + params;
        xhr.open('POST', linkUrl, true);

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
        let linkUrl = host + url + "?" + params;
        xhr.open('GET', linkUrl, true);

        xhr.onload = function () {
            const data = xhr.responseText;
            console.log(JSON.parse(data))
            onCool(JSON.parse(data));
        };

        xhr.onerror = onError;
        xhr.send();
    }
}