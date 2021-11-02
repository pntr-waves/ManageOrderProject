package model.shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Material;

public class Sphere extends Shape {
	private double radius;
	
	public Sphere() {
		super("Sphere");
	}

	public Sphere(String name, Material material, double radius) {
		super(name, material);
		this.radius = radius;
	}

	public Sphere(int id, String material, double radius) {
		super(id, "Sphere", material);
		this.radius = radius;
	}
	
	public Sphere(String material, double radius) {
		super("Sphere", material);
		this.radius = radius;
	}
	
	@Override
	public double getVolume() {
		return Math.floor((4 * Math.PI * Math.pow(this.radius, 3)) / 3 * 1000) / 1000;
	}

	public double getRadius() {
		return this.radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	@Override
	public List<String> getListProperty() {
		List<String> listProperty = new ArrayList<>();
		listProperty.add("radius");
		return listProperty;
	}

	@Override
	public Map<String, Double> getMapProperty() {
		Map<String, Double> mapProperty = new HashMap<>();
		mapProperty.put("radius", this.radius);
		mapProperty.put("volume", this.getVolume());
		mapProperty.put("weight", this.getWeight());
		mapProperty.put("price", this.getPrice());
		return mapProperty;
	}

	@Override
	public String getParameter() {
		return String.format("Radius: %.2f", this.radius);
	}

	@Override
	public String getStringProperty() {
		return String.format("{\"radius\": %.2f}",this.radius);
	}

	@Override
	public Map<String, String> getMapType() {
		Map<String, String> mapType = new HashMap<>();
		mapType.put("id", String.valueOf(this.getId()));
		mapType.put("name", this.getName());
		mapType.put("radius", String.format("%.2f", this.radius));
		mapType.put("material", this.getMaterial().getName());
		mapType.put("parameter", this.getParameter());
		return mapType;
	}

}
