package service;

import java.util.List;

import org.json.simple.JSONObject;

import model.order.OrderDetail;

public interface IOrderDetailService {
	public List<OrderDetail> getListOrderDetail();
	
	public int deleteOrder(int orderId);
	
	public int addOrder(JSONObject orderJson);
	
	public int editOrder(JSONObject orderJson);
}
