function Cube(material, edge) {
    Shape.call(this, material, "Cube");
    this.edge = edge;
}
Cube.prototype = Object.create(Shape.prototype);
Cube.prototype.constructor = Cube;
Cube.prototype.calculateVolume = function () {
    return Math.floor(Math.pow(this.edge, 3) * 100) / 100;
}
Cube.prototype.getInfo = function (index) {
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
Cube.prototype.getListProperty = function () {
    return ['edge'];
}
Cube.prototype.getParameter = function () {
    return "Edge: " + this.edge;
}