function Cone(material, height, radius) {
    Shape.call(this, material, "Cone");
    this.height = height;
    this.radius = radius;
}
Cone.prototype = Object.create(Shape.prototype);
Cone.prototype.constructor = Cone;
Cone.prototype.calculateVolume = function () {
    return Math.floor((Math.PI * Math.pow(this.radius, 2) * this.height) / 3 * 100) / 100;
}
Cone.prototype.getInfo = function (index) {
    var shape = this;
    return this.getVolume()
        .then(function (volume) {
            return shape.getWeight()
                .then(function (weight) {
                    return shape.getPrice()
                        .then(function (price) {
                            var name = shape.getDescription();
                            var info = shape.getParameter();
                            return {name , info, volume, weight, price, index };
                        })
                })
        });
}
Cone.prototype.getListProperty = function () {
    return ['height', 'radius'];
}

Cone.prototype.getParameter = function () {
    return "Radius: " + this.radius + ", Height: " + this.height;
}