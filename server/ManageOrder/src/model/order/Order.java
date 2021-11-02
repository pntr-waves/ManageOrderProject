package model.order;

import java.sql.Date;
import java.util.List;

import model.shape.Shape;

public class Order {
	private String orderNumber;
	private Date orderDate;
	private double orderPrice;
	private List<Shape> listShape;

	public Order(String orderNumber, Date orderDate, double orderPrice, List<Shape> listShape) {
		this.orderNumber = orderNumber;
		this.orderDate = orderDate;
		this.orderPrice = orderPrice;
		this.listShape = listShape;
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

	public double getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(double orderPrice) {
		this.orderPrice = orderPrice;
	}

	public List<Shape> getListShape() {
		return listShape;
	}

	public void setListShape(List<Shape> listShape) {
		this.listShape = listShape;
	}
}
