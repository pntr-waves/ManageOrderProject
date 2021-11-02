package constant;

public enum Material {
	WOODEN("Wooden", 480, 30),
	PAPER("Paper", 50, 10),
	PLASTIC("Plastic", 100, 20);
	
	private String name;
	private double density;
	private double price;
	
	Material (String name, double density, double price) {
		this.setName(name);
		this.setDensity(density);
		this.setPrice(price);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
}
