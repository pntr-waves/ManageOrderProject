function OrderListManagement() {
    BaseTemplatedWidget.call(this);

    var thiz = this;
    this.listOrder = [];

    this.bind("click", function () {
        var orderDetail = new OrderDetailViewDialog();
        orderDetail.callback = thiz.addOrderToTable.bind(thiz);
        orderDetail.open({
            title: "Add New Order",
            type: "Add"
        });

    }, this.createNewOrder);

    this.serviceFactory = new ServiceFactory();
    this.dataListSource = {
        order: {
            propertyName: "orderNumber",
            asc: true
        },
        term: "",
        paramName: "orderNumber",
        loadPage: function (pageIndex, pageSize, handler, failed) {
            thiz.serviceFactory.post("ManageOrder", "", function (data) {
                thiz.totalOrder.innerText = data.length;
                thiz.totalAmount.innerText = thiz.getTotalAmount(data);
                handler(data.slice(pageIndex * pageSize, Math.min(data.length, (pageIndex + 1) * pageSize)), data.length);
            }, function (error) {
                failed(error)
            });
        },

        getOrder: function () {
            return this.order;
        },
        setOrder: function (order) {
            this.order = order;
        }
    };


    this.initializeDataTable();
}

__extend(BaseTemplatedWidget, OrderListManagement);

// listNewOrder -> listOrder

OrderListManagement.prototype.onAttached = function () {
    this.dataTable.setup();
    this.dataTable.setItems(this.listOrder);
    var thiz = this;
    this.paginator.setup({
        getTotalItemText: function (total) {
            return String.format("Total %d order", total);
        },
        showPaginator: true,
        withoutStatus: true,
        onPageLoaded: function (p, count) {
            thiz.refreshedAt = new Date().getTime();
            var start = p.currentPage * p.pageSize + 1;
            var end = start + count - 1;
            Dom.setInnerText(thiz.contentDescription, String.format("Showing orders {0}-{1} of {2}", start, end, p.totalItems));
        },
        comparer: function (a, b) {
            return a.id == b.id;
        },
        useButtons: true
    });
    this.paginator.pageSize = 1;
    this.paginator.control(this.dataTable);
    this.paginator.setSource(this.dataListSource);
}

OrderListManagement.prototype.addOrderToTable = function (order) {
    console.log(JSON.stringify(order));
    var thiz = this;
    this.serviceFactory.post("CreateOrder", "", function (data) {
        if (data.code == 1) {
            thiz.paginator.refresh();
        } else {
            console.log(data);
        }
    }, function (error) {
        console.log(error);
    }, JSON.stringify(order));
}

OrderListManagement.prototype.initializeDataTable = function () {
    this.dataTable.column(new DataTable.PlainTextColumn("Order Number", function (data) {
        return (data.orderNumber);
    }).width("150px"));
    this.dataTable.column(new DataTable.PlainTextColumn("Order Date", function (data) {
        return (data.orderDate);
    }).width("150px"));
    this.dataTable.column(new DataTable.PlainTextColumn("Total Price", function (data) {
        return (data.totalPrice);
    }).width("150px"));
    var thiz = this;
    var actions = [{
        id: "edit", type: "cog", title: "Edit Order",
        isApplicable: function (item) {
            return true;
        },
        handler: function (item) {
            thiz.editOrder(item);
        }
    }, {
        id: "delete", type: "close", title: "Delete Order",
        isApplicable: function (item) {
            return true;
        },
        handler: function (item) {
            thiz.serviceFactory.get("ManageOrder", "action=delete&orderId=" + item.id, function (data) {
                if (data.statusCode) {
                    thiz.paginator.refresh();
                }
            }, function (error) {
                console.log(error);
            })
        }
    }];
    this.dataTable.column(new DataTable.ActionColumn(actions).width("150px"));
}

OrderListManagement.prototype.editOrder = function (item) {
    var orderDetail = new OrderDetailViewDialog();
    orderDetail.callback = this.editOrderToTable.bind(this);
    this.serviceFactory.get("ManageOrder", "orderId=" + item.id
        , function (listShape) {
            orderDetail.open({
                title: "Edit Order",
                type: "Save",
                ...item,
                listShape
            });
        }, function (error) {
            console.log(error);
        })
}

OrderListManagement.prototype.getTotalAmount = function (data) {
    var totalPrice = 0;
    for (let i = 0; i < data.length; i++) {
        totalPrice += data[i].totalPrice;
    }
    return totalPrice;
}

OrderListManagement.prototype.editOrderToTable = function (data) {
    console.log(JSON.stringify(data));
    var thiz = this;
    this.serviceFactory.post("EditOrder", "", function (response) {
        if (response.code === 0) {
            thiz.paginator.refresh();
        }
    }, function (error) {
        console.log(error);
    }, JSON.stringify(data));
}

