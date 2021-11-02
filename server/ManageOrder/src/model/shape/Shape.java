package model.shape;

import java.util.List;
import java.util.Map;

import constant.Material;

public abstract class Shape {
	private int id;
	private String name;
	private Material material;

	public Shape(int id, String name, String material) {
		this.id = id;
		this.name = name;
		this.setMeterial(material);;
	}
	
	public Shape(String name, String material) {
		this.name = name;
		this.setMeterial(material);;
	}
	
	public Shape(String name, Material material) {
		this.name = name;
		this.material = material;
	}
	
	public Shape(String name) {
		this.name = name;
	}

	public abstract double getVolume();

	public double getWeight() {
		return Math.floor(this.getVolume() * this.material.getDensity() * 1000) / 1000;
	};

	public double getPrice() {
		return Math.floor(this.getWeight() * this.material.getPrice() * 1000) / 1000;
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}
	
	public void setMeterial(String material) {
		Material[] arrayMaterial = Material.values();
		for (int i = 0; i < arrayMaterial.length; i++) {
			if (arrayMaterial[i].getName().compareTo(material) == 0) {
				this.material = arrayMaterial[i];
				break;
			}
		}
	}
	
	public abstract List<String> getListProperty();
	
	public abstract Map<String, Double> getMapProperty();
	
	public abstract String getParameter();
	
	public abstract String getStringProperty();
	
	public abstract Map<String, String> getMapType();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
