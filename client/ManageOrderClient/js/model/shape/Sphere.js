function Sphere(material, radius) {
    Shape.call(this, material, "Sphere");
    this.radius = radius;
}
Sphere.prototype = Object.create(Shape.prototype);
Sphere.prototype.constructor = Sphere;
Sphere.prototype.calculateVolume = function () {
    return Math.floor((4 * Math.PI * Math.pow(this.radius, 3) / 3) * 100) / 100;
}
Sphere.prototype.getInfo = function (index) {
    var shape = this;
    return this.getVolume()
        .then(function (volume) {
            return shape.getWeight()
                .then(function (weight) {
                    return shape.getPrice()
                        .then(function (price) {
                            var name = shape.getDescription();
                            var info = shape.getParameter();
                            return { name, info, volume, weight, price, index };
                        })
                })
        });
}
Sphere.prototype.getListProperty = function () {
    return ['radius'];
}
Sphere.prototype.getParameter = function () {
    return "Radius: " + this.radius;
}