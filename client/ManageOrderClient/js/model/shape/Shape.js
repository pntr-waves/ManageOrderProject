function Shape(material, name) {
    this.material = material;
    this.name = name;
}
Shape.prototype.getVolume = function () {
    var shape = this;
    var timeoutPromise = new Promise(function (resolve, reject) {
        window.setTimeout(function () {
            resolve(shape.calculateVolume());
        }, 1000);
    });

    return timeoutPromise;
}
Shape.prototype.getListProperty = function () {
    return [];
}
Shape.prototype.calculateVolume = function () {
    return 0;
}
Shape.prototype.calculateWeight = function (volume) {
    return Math.floor(this.material.density * volume * 100) / 100;
}
Shape.prototype.getWeight = function () {
    var shape = this;
    return this.getVolume()
        .then(function (volume) {
            return shape.calculateWeight(volume);
        })
}
Shape.prototype.calculatePrice = function (weight) {
    return Math.floor(this.material.price * weight * 100) / 100;
}
Shape.prototype.getPrice = function () {
    var shape = this;
    return this.getWeight()
        .then(function (weight) {
            return shape.calculatePrice(weight);
        })
}
Shape.prototype.getDescription = function () {
    return this.name + " - " + this.material.name;
}
Shape.prototype.getInfo = function (callback, index) {
    return "volume of shape";
}
Shape.prototype.getParameter = function () {
    return "parameter";
}