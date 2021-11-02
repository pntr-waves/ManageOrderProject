function OrderDetailViewDialog() {
    Dialog.call(this);

    var thiz = this;
    this.listShape = [];
    this.listDeleteShape = [];
    this.totalPrice = 0;
    this.orderDate.min = new Date().toISOString().slice(0, 10);
    this.bind("click", function () {
        var shapeDetail = new ShapeDetailViewDialog();
        shapeDetail.callback = thiz.addShapeToTable.bind(thiz);
        shapeDetail.open({
            title: "Add New Shape",
            type: "Add"
        });
    }, this.newShapeButton);
    this.bind("click", this.handleEventToTable, this.table);

    this.dataListSource = {
        order: {
            propertyName: "price",
            asc: true
        },
        term: "",
        paramName: "price",
        loadPage: function (pageIndex, pageSize, handler, failed) {
            try {
                var start = pageIndex * pageSize;
                var end = Math.min(start + pageSize, thiz.listShape.length);

                var result = thiz.listShape.slice(start, end);
                handler(result, thiz.listShape.length);
            } catch (e) {
                failed(e);
            }
        },
        getOrder: function () {
            return this.order;
        },
        setOrder: function (order) {
            this.order = order;
        }
    }

    this.initializeDataTable();
}

__extend(Dialog, OrderDetailViewDialog);

OrderDetailViewDialog.prototype.initializeDataTable = function () {

    this.dataTable.column(new DataTable.PlainTextColumn("Type", function (data) {
        return data.name + "-" + data.material;
    }).width("4*"));

    this.dataTable.column(new DataTable.PlainTextColumn("Volume Parameter", function (data) {
        return data.parameter;
    }).width("5*"));

    this.dataTable.column(new DataTable.PlainTextColumn("Volume (m3)", function (data) {
        return data.volume;
    }).width("4*"));

    this.dataTable.column(new DataTable.PlainTextColumn("Weight (kg)", function (data) {
        return data.weight;
    }).width("4*"));

    this.dataTable.column(new DataTable.PlainTextColumn("Price (kg)", function (data) {
        return data.price;
    }).width("4*"));

    var thiz = this;
    var actions = [
        {
            id: "edit",
            title: "Edit Shape",
            type: "cog",
            handler: function (item) {
                var index = thiz.listShape.indexOf(item);
                thiz.editShape(index);
            }
        },
        {
            id: "delete",
            title: "Delete Shape",
            type: "close",
            handler: function (item) {
                var index = thiz.listShape.indexOf(item);
                thiz.deleteShape(index);
            }
        }
    ];
    this.dataTable.column(new DataTable.ActionColumn(actions).width("3*"));
}

OrderDetailViewDialog.prototype.onShown = function () {
    this.dataTable.setup();
    this.dataTable.setItems(this.listShape);
    var thiz = this;
    this.paginator.setup({
        getTotalItemText: function (total) {
            return String.format("Total %d shape", total);
        },
        showPaginator: true,
        withoutStatus: true,
        onPageLoaded: function (p, count) {
            thiz.refreshedAt = new Date().getTime();
            var start = p.currentPage * p.pageSize + 1;
            var end = start + count - 1;
            Dom.setInnerText(thiz.contentDescription, String.format("Showing shapes {0}-{1} of {2}", start, end, p.totalItems));
        },
        comparer: function (a, b) {
            return a.id == b.id;
        },
        useButtons: true
    });
    this.paginator.pageSize = 4;
    this.paginator.control(this.dataTable);
    this.paginator.setSource(this.dataListSource);

    var screenW = window.innerWidth;
    var screenH = window.innerHeight - 20;

    var w = this.dialogFrame.offsetWidth;
    var h = this.dialogFrame.offsetHeight;

    var x = (screenW - w) / 2;
    var y = (screenH - h) / 2;

    this.moveTo(x, y);
}

OrderDetailViewDialog.prototype.setup = function (data) {
    this.title = data.title;
    this.type = data.type;
    if (data.id) {
        this.orderId = data.id;
    }
    this.totalPrice = data.totalPrice ? data.totalPrice : "";
    this.orderNumber.value = data.orderNumber ? data.orderNumber : "";
    this.orderDate.value = data.orderDate ? data.orderDate : "";
    this.orderDescription.value = data.orderDescription ? data.orderDescription : "";
    this.shapeTotalPrice.innerText = data.totalPrice ? data.totalPrice : "";
    this.shapeTotalItem.innerText = data.totalItem ? data.totalItem : "";

    if (data.listShape) {
        for (let i = 0; i < data.listShape.length; i++) {
            this.listShape.push(data.listShape[i]);
        }
    
        this.paginator.refresh();
    }
}

//int input and show shapeDetailView 
OrderDetailViewDialog.prototype.editShape = function (index) {
    var editedShape = this.listShape[index];
    var shapeDetail = new ShapeDetailViewDialog();
    shapeDetail.callback = this.editShapeToTable.bind(this);
    console.log(editedShape);
    shapeDetail.open({
        title: "Edit Shape",
        type: "Save",
        shapeIndex: index,
        shape: editedShape
    });
}

//delete shape in table 
OrderDetailViewDialog.prototype.deleteShape = function (index) {
    //remove shape in listShape
    var deletedShape = this.listShape.splice(index, 1)[0];
    if (deletedShape.id) {
        this.listDeleteShape.push({id: deletedShape.id});
    }
    var deletedPrice = deletedShape.price;
    this.totalPrice -= deletedPrice;
    this.shapeTotalItem.innerHTML = this.listShape.length;
    this.shapeTotalPrice.innerHTML = this.totalPrice + "$";
    this.paginator.refresh();
}

OrderDetailViewDialog.prototype.editShapeToTable = function (editedShape, index) {
    var thiz = this;
    editedShape.getInfo(index).then(function (infos) {
        var oldPrice = thiz.listShape[index].price;
        thiz.listShape[index] = {
            ...thiz.listShape[index],
            ...editedShape,
            material: editedShape.material.name,
            parameter: infos.info,
            weight: infos.weight,
            volume: infos.volume,
            price: infos.price
        };

        var newPrice = infos.price;
        thiz.totalPrice += (newPrice - oldPrice);
        thiz.shapeTotalPrice.innerHTML = thiz.totalPrice + "$";
        thiz.paginator.refresh();
    });

}

OrderDetailViewDialog.prototype.addShapeToTable = function (newShape) {
    var thiz = this;
    var index = this.listShape.length;
    newShape.getInfo(index).then(function (infos) {
        thiz.totalPrice += infos.price;
        //add shape info to d order property
        let data = {
            ...newShape,
            material: newShape.material.name,
            parameter: infos.info,
            volume: infos.volume,
            weight: infos.weight,
            price: infos.price
        }
        thiz.listShape.push(data);
        thiz.shapeTotalPrice.innerHTML = thiz.totalPrice + "$";
        thiz.shapeTotalItem.innerHTML = thiz.listShape.length;

        console.log(thiz.listShape);
        thiz.paginator.refresh();
    });


}

OrderDetailViewDialog.prototype.getError = function () {
    var orderNumber = this.orderNumber.value;
    var orderDate = this.orderDate.value;
    var error = [];

    if (!orderNumber || +orderNumber < 0) {
        error.push("Order Number must be great than 0 or not empty");
    }

    if (!orderDate) {
        error.push("Order Date not empty");
    }

    if (!this.listShape.length) {
        error.push("Order must have a shape or more");
    }

    return error;
}

OrderDetailViewDialog.prototype.run = function () {
    var errors = this.getError();
    if (errors.length) {
        Dialog.alert("Invalid!", errors);
        return false;
    } else {
        let order = new Order(this.orderNumber.value, this.orderDate.value, this.orderDescription.value, parseFloat(this.totalPrice).toFixed(2), this.listShape);

        if (this.type == "Add") {
            this.close(order);
        }

        if (this.type == "Save") {
            let data = {
                orderId: this.orderId,
                ...order,
                listShapeInfo: this.listShape,
                listDeleteShape: this.listDeleteShape
            }
            console.log(data);
            this.close(data);
        }
    }
}

OrderDetailViewDialog.prototype.getDialogActions = function () {
    return [
        {
            type: "accept",
            title: this.type,
            run: this.run
        },
        {
            type: "cancel",
            title: "Cancel",
            isCloseHandler: true,
            run: function () {
                return true;
            }
        }
    ]
}