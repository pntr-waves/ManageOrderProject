function ShapeDetailViewDialog() {
    Dialog.call(this);

    var thiz = this;
    this.comboManagerShapeType.setItems(["Cone", "Cylinder", "Cube", "Sphere"]);
    this.comboManagerMaterial.setItems(["Wooden", "Plastic", "Paper"]);
    this.bind("p:ItemSelected", function () {
        thiz.renderPropertyInput();
    }, this.comboManagerShapeType);    
}

__extend(Dialog, ShapeDetailViewDialog);

//init shape detail view dialog 

ShapeDetailViewDialog.prototype.setup = function (data) {
    this.title = data.title;
    this.type = data.type;
    var editedShape = data.shape;
    if (editedShape) {
        this.shapeIndex = data.shapeIndex;
        this.comboManagerShapeType.selectItem(editedShape.name)
        this.comboManagerMaterial.selectItem(editedShape.material);
        this.renderPropertyInput(editedShape);
    } else {
        this.renderPropertyInput();
    }

}

ShapeDetailViewDialog.prototype.renderPropertyInput = function (shape) {
    //list property of shape => render input property shape
    var listProperty = this.getListProperty(this.comboManagerShapeType.getSelectedItem());

    this.listInputProperty.innerHTML = "";
    for (let i = 0; i < listProperty.length; i++) {
        var shapePropertyElement = document.createElement("div");
        shapePropertyElement.className = "ShapeProperty";
        var textLabelElement = document.createElement("div");
        textLabelElement.className = "Text";
        textLabelElement.innerHTML = listProperty[i] + ":"

        var inputProperty = document.createElement("input");
        inputProperty.type = "number";
        inputProperty.min = "0";
        inputProperty.className = "Input";
        inputProperty._shapeType = listProperty[i];
        shape ? inputProperty.value = +shape[listProperty[i]] : inputProperty.value = "";

        shapePropertyElement.append(textLabelElement, inputProperty);
        this.listInputProperty.appendChild(shapePropertyElement);
    }
}

ShapeDetailViewDialog.prototype.getListProperty = function (shapeType) {
    switch (shapeType) {
        case "Cone": {
            return new Cone().getListProperty();
        }
        case "Cylinder": {
            return new Cylinder().getListProperty();
        }
        case "Cube": {
            return new Cube().getListProperty();
        }
        case "Sphere": {
            return new Sphere().getListProperty();
        }
    }
}


//Get error

ShapeDetailViewDialog.prototype.getErrorFromShapeInputPopup = function () {
    var shapeType = this.comboManagerShapeType.getSelectedItem();
    var error = [];
    if (!shapeType) {
        error.push("shape type must be selected");
    } else {
        var listInputProperty = this.listInputProperty.childNodes;
        for (let i = 0; i < listInputProperty.length; i++) {
            var shapeType = listInputProperty[i].getElementsByTagName("input")[0]._shapeType;
            var valueInput = listInputProperty[i].getElementsByTagName("input")[0].value;
            if (!valueInput || +valueInput < 0) {
                error.push(shapeType + " not empty or less than 0! ");
            }
        }
    }
    return error;
}


//Get New Shape

ShapeDetailViewDialog.prototype.initShape = function () {
    var shape;
    switch (this.comboManagerShapeType.getSelectedItem()) {
        case "Cone": {
            shape = new Cone();
            break;
        }
        case "Cylinder": {
            shape = new Cylinder();
            break;
        }
        case "Cube": {
            shape = new Cube();
            break;
        }
        case "Sphere": {
            shape = new Sphere();
            break;
        }
    }

    return shape;
}

ShapeDetailViewDialog.prototype.setValueForShape = function (shape) {
    var listInputProperty = this.listInputProperty.childNodes;
    for (let i = 0; i < listInputProperty.length; i++) {
        var shapeType = listInputProperty[i].getElementsByTagName("input")[0]._shapeType;
        var valueInput = listInputProperty[i].getElementsByTagName("input")[0].value;

        shape[shapeType] = parseFloat(valueInput).toFixed(2);
    }
    shape['material'] = MATERIAL[this.comboManagerMaterial.getSelectedItem().toLowerCase()];

    return shape;
}

ShapeDetailViewDialog.prototype.getShape = function () {
    var newShape = this.initShape();
    return this.setValueForShape(newShape);
}

ShapeDetailViewDialog.prototype.run = function () {
    //this function called by accept button
    var errors = this.getErrorFromShapeInputPopup();
    if (errors.length) {
        Dialog.alert("Invalid!", errors);
        return false;
    } else {
        var shape = this.getShape();
        if (this.type == "Add") {
            this.close(shape);
        }

        if (this.type == "Save") {
            this.close(shape, this.shapeIndex);
        }
    }
}

//Handle Action
ShapeDetailViewDialog.prototype.getDialogActions = function () {
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