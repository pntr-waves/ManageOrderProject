package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

import connection.OrderDetailDAO;
import core.ServiceFactory;
import model.order.OrderDetail;

public class OrderDetailService implements IOrderDetailService{
	
	OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
	
	@Override
	public List<OrderDetail> getListOrderDetail() {
		List<OrderDetail> listOrderDetail = new ArrayList<>();
		try {
			listOrderDetail = orderDetailDAO.getListOrderDetail();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOrderDetail;
	}

	@Override
	public int deleteOrder(int orderId) {
		ServiceFactory.get(IShapeDetailService.class).deleteShapeByOrderId(orderId);
		int rowDelete = 0;
		
		try {
			rowDelete = orderDetailDAO.deleteOrder(orderId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return rowDelete;
	}
	
	public OrderDetail getOrderFromJson(JSONObject orderJson) {
		String orderNumber = (String) orderJson.get("orderNumber");
		String orderDate = (String) orderJson.get("orderDate");
		String orderDescription = (String) orderJson.get("orderDescription");
		double totalPrice = Double.parseDouble((String) orderJson.get("totalPrice"));
		OrderDetail order = null;
		if (orderJson.get("orderId") != null) {
			long orderId = (Long) orderJson.get("orderId");
			order = new OrderDetail((int) orderId, orderNumber, orderDate, orderDescription, totalPrice);
			System.out.println("order Id " + orderId);
		} else {
			order = new OrderDetail(orderNumber, orderDate, orderDescription, totalPrice);
		}
		System.out.println(order.getOrderNumber());
		return order;
	}

	@Override
	public int addOrder(JSONObject orderJson) {
		int newId = -1;

		OrderDetail order = getOrderFromJson(orderJson);
		try {
			newId = orderDetailDAO.addOrder(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return newId;
	}

	@Override
	public int editOrder(JSONObject orderJson) {
		OrderDetail order = getOrderFromJson(orderJson);
		System.out.println(order.getOrderNumber());
		
		try {
			orderDetailDAO.editShape(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return order.getId();
	}

	
}
