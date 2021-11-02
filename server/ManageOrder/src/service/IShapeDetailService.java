package service;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import model.shape.Shape;

public interface IShapeDetailService {
	public List<Shape> getListShapeByOrderId(int orderId);

	public int deleteShapeByOrderId(int orderId);

	public void addListShape(JSONArray listShapeJson, int orderId);
	
	public void deleteListShape(JSONArray listDeleteShape);
	
	public void addShapeByJson(JSONObject jsonShape, int orderId);
	
	public void editShapeByJson(JSONObject jsonShape);
	
	public void manageShape(JSONArray listEditShape, int orderId);
}
