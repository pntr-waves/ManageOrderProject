package model.shape;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constant.Material;

public class Cube extends Shape {
	private double edge;

	public Cube() {
		super("Cube");
	}

	public Cube(Material material, double edge) {
		super("Cube", material);
		this.edge = edge;
	}

	public Cube(int id, String material, double edge) {
		super(id, "Cube", material);
		this.edge = edge;
	}
	
	public Cube(String material, double edge) {
		super("Cube", material);
		this.edge = edge;
	}

	@Override
	public double getVolume() {
		return Math.floor(Math.pow(edge, 3) * 1000) / 1000;
	}

	public double getEdge() {
		return edge;
	}

	public void setEdge(double edge) {
		this.edge = edge;
	}

	@Override
	public List<String> getListProperty() {
		List<String> listProperty = new ArrayList<>();
		listProperty.add("edge");
		return listProperty;
	}

	@Override
	public Map<String, Double> getMapProperty() {
		Map<String, Double> mapProperty = new HashMap<>();
		mapProperty.put("edge", this.edge);
		mapProperty.put("volume", this.getVolume());
		mapProperty.put("weight", this.getWeight());
		mapProperty.put("price", this.getPrice());
		return mapProperty;
	}

	@Override
	public String getParameter() {
		return String.format("Edge: %.2f", this.edge);
	}

	@Override
	public String getStringProperty() {
		return String.format("{\"edge\": %.2f}", this.edge);
	}

	@Override
	public Map<String, String> getMapType() {
		Map<String, String> mapType = new HashMap<>();
		mapType.put("id", String.valueOf(this.getId()));
		mapType.put("name", this.getName());
		mapType.put("edge", String.format("%.2f", this.edge));
		mapType.put("material", this.getMaterial().getName());
		mapType.put("parameter", this.getParameter());
		return mapType;
	}
}
