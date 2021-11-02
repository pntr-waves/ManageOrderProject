package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import connection.ShapeDetailDAO;
import model.shape.Cone;
import model.shape.Cube;
import model.shape.Cylinder;
import model.shape.Shape;
import model.shape.Sphere;

public class ShapeDetailService implements IShapeDetailService {

	ShapeDetailDAO shapeDetailDAO = new ShapeDetailDAO();

	@Override
	public List<Shape> getListShapeByOrderId(int orderId) {
		List<Shape> listShape = new ArrayList<>();
		try {
			listShape = shapeDetailDAO.getListShapeByOrderId(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listShape;
	}

	@Override
	public int deleteShapeByOrderId(int orderId) {
		int rowDelete = 0;
		try {
			rowDelete = shapeDetailDAO.deleteShapeByOrderId(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowDelete;
	}

	@Override
	public void addListShape(JSONArray listShapeJson, int orderId) {

		for (int i = 0; i < listShapeJson.size(); i++) {
			JSONObject shapeJson = (JSONObject) listShapeJson.get(i);
			Shape shape = getShapeFromJsonObject(shapeJson);
			try {
				shapeDetailDAO.addShape(shape, orderId);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Shape getShapeFromJsonObject(JSONObject shapeJson) {
		String name = (String) shapeJson.get("name");
		String material = (String) shapeJson.get("material");
		Shape shape = null;
		System.out.println(material);
		switch (name) {
		case "Cone": {
			double height = Double.parseDouble((String) shapeJson.get("height"));
			double radius = Double.parseDouble((String) shapeJson.get("radius"));
			shape = new Cone(material, height, radius);
			break;
		}
		case "Cylinder": {
			double height = Double.parseDouble((String) shapeJson.get("height"));
			double radius = Double.parseDouble((String) shapeJson.get("radius"));
			shape = new Cylinder(material, height, radius);
			break;
		}
		case "Cube": {
			double edge = Double.parseDouble((String) shapeJson.get("edge"));
			shape = new Cube(material, edge);
			break;
		}
		case "Sphere": {
			double radius = Double.parseDouble((String) shapeJson.get("radius"));
			shape = new Sphere(material, radius);
			break;
		}
		}

		if (shapeJson.get("id") != null) {
			System.out.println("do");
			System.out.println(shapeJson.get("id"));
			int id = Integer.parseInt((String) shapeJson.get("id"));
			shape.setId(id);
		}
		System.out.println(shape.getMaterial().getName());

		return shape;
	}

	@Override
	public void deleteListShape(JSONArray listDeleteShape) {
		for (int i = 0; i < listDeleteShape.size(); i++) {
			JSONObject deleteShape = (JSONObject) listDeleteShape.get(i);
			int id = Integer.parseInt((String) deleteShape.get("id"));

			System.out.println("delet id " + id);
			try {
				shapeDetailDAO.deleteShapeById((int) id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void manageShape(JSONArray listEditShape, int orderId) {
		for (int i = 0; i < listEditShape.size(); i++) {
			JSONObject shapeJson = (JSONObject) listEditShape.get(i);
			System.out.println(shapeJson.get("id"));
			if (shapeJson.get("id") != null) {
				System.out.println("do");
				editShapeByJson(shapeJson);
			} else {
				addShapeByJson(shapeJson, orderId);
			}
		}
	}

	@Override
	public void addShapeByJson(JSONObject jsonShape, int orderId) {
		Shape shape = getShapeFromJsonObject(jsonShape);
		try {
			shapeDetailDAO.addShape(shape, orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void editShapeByJson(JSONObject jsonShape) {
		Shape shape = getShapeFromJsonObject(jsonShape);
		try {
			shapeDetailDAO.editShape(shape);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
