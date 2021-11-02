package model.shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Material;

public class Cylinder extends Shape {
	private double height;
	private double radius;

	public Cylinder() {
		super("Cylinder");
	}

	public Cylinder(String name, Material material, double height, double radius) {
		super(name, material);
		this.setHeight(height);
		this.setRadius(radius);
	}
	
	public Cylinder(int id, String material, double height, double radius) {
		super(id, "Cylinder", material);
		this.height = height;
		this.radius = radius;
	}
	
	public Cylinder(String material, double height, double radius) {
		super("Cylinder", material);
		this.height = height;
		this.radius = radius;
	}

	/*
	 * orderDecription: "", orderNumber: "", orderPrice: "", listShape: [ { shape: {
	 * name:"", material: "", }, price: "", weight: "", volume: "", }
	 * 
	 * ]
	 */
	@Override
	public double getVolume() {
		return Math.floor(Math.PI * Math.pow(this.radius, 2) * this.height * 1000) / 1000;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public List<String> getListProperty() {
		List<String> listProperty = new ArrayList<>();
		listProperty.add("height");
		listProperty.add("radius");
		return listProperty;
	}

	@Override
	public Map<String, Double> getMapProperty() {
		Map<String, Double> mapProperty = new HashMap<>();
		mapProperty.put("height", this.height);
		mapProperty.put("radius", this.radius);
		mapProperty.put("volume", this.getVolume());
		mapProperty.put("weight", this.getWeight());
		mapProperty.put("price", this.getPrice());
		return mapProperty;
	}

	@Override
	public String getParameter() {
		return String.format("Height: %.2f, Radius: %.2f", this.height, this.radius);
	}

	@Override
	public String getStringProperty() {
		return String.format("{\"height\": %.2f, \"radius\": %.2f}", this.height, this.radius);
	}

	@Override
	public Map<String, String> getMapType() {
		Map<String, String> mapType = new HashMap<>();
		mapType.put("id", String.valueOf(this.getId()));
		mapType.put("name", this.getName());
		mapType.put("height", String.format("%.2f", this.height));
		mapType.put("radius", String.format("%.2f", this.radius));
		mapType.put("material", this.getMaterial().getName());
		mapType.put("parameter", this.getParameter());
		return mapType;
	}

}
