package model.order;

import java.sql.Date;

public class OrderDetail {
	private int id;
	private String orderNumber;
	private Date orderDate;
	private String orderDescription;
	private double totalPrice;
	private long totalItem;

	public OrderDetail(int id, String orderNumber, Date orderDate, String orderDescription, double totalPrice, long totalItem) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.orderDescription = orderDescription;
		this.totalPrice = totalPrice;
		this.totalItem = totalItem;
	}
	
	public OrderDetail(String orderNumber, String orderDate, String orderDescription, double totalPrice) {
		this.orderNumber = orderNumber;
		this.setOrderDate(orderDate);
		this.orderDescription = orderDescription;
		this.totalPrice = totalPrice;
	}
	
	public OrderDetail(int id, String orderNumber, String orderDate, String orderDescription, double totalPrice) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.setOrderDate(orderDate);
		this.orderDescription = orderDescription;
		this.totalPrice = totalPrice;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = Date.valueOf(orderDate);
	}

	public String getOrderDescription() {
		return orderDescription;
	}

	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getTotalItem() {
		return totalItem;
	}

	public void setTotalItem(long totalItem) {
		this.totalItem = totalItem;
	}
}
