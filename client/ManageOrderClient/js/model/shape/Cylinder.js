function Cylinder(material, height, radius) {
    Shape.call(this, material, "Cylinder");
    this.height = height;
    this.radius = radius;
}
Cylinder.prototype = Object.create(Shape.prototype);
Cylinder.prototype.constructor = Cylinder;
Cylinder.prototype.calculateVolume = function () {
    return Math.floor(Math.PI * Math.pow(this.radius, 2) * this.height * 100) / 100;
}
Cylinder.prototype.getInfo = function (index) {
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
Cylinder.prototype.getListProperty = function () {
    return ['height', 'radius'];
}
Cylinder.prototype.getParameter = function () {
    return "Radius: " + this.radius + ", Height: " + this.height;
}