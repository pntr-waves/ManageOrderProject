function Order(orderNumber, orderDate, orderDescription, totalPrice,listShapeInfo) {
    this.orderNumber = orderNumber;
    this.orderDate = orderDate;
    this.orderDescription = orderDescription;
    this.totalPrice = totalPrice;
    this.listShapeInfo = listShapeInfo ? listShapeInfo : [];
}

/*
    listShapeInfo = [{name, info, volume, weight, price}]
*/

Order.prototype.getTotalPrice = function () {
    var totalPrice = 0;
    for (let i = 0; i < this.listShapeInfo.length; i++) {
        totalPrice += this.listShapeInfo[i].price;
    }

    return totalPrice;
}


Order.prototype.getTotalVolume = function () {
    var totalVolume = 0;
    for (let i = 0; i < this.listShapeInfo.length; i++) {
        totalVolume += this.listShapeInfo[i].volume;
    }

    return totalVolume;
}

Order.prototype.getTotalWeight = function () {
    var totalWeight = 0;
    for (let i = 0; i < this.listShapeInfo.length; i++) {
        totalWeight += this.listShapeInfo[i].weight;
    }

    return totalWeight;
}