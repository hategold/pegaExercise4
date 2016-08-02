package yt.item4;

public class Shoes {

	private int shoesId;

	private String shoesName;

	private String series;

	private String catagory;

	private int price;

	private Brand brand;

	public Shoes() {

	}

	public int getShoesId() {
		return shoesId;
	}

	public Shoes setShoesId(int shoesId) {
		this.shoesId = shoesId;
		return this;
	}

	public String getShoesName() {
		return shoesName;
	}

	public Shoes setShoesName(String shoesName) {
		this.shoesName = shoesName;
		return this;
	}

	public String getSeries() {
		return series;
	}

	public Shoes setSeries(String series) {
		this.series = series;
		return this;
	}

	public String getCatagory() {
		return catagory;
	}

	public Shoes setCatagory(String catagory) {
		this.catagory = catagory;
		return this;
	}

	public int getPrice() {
		return price;
	}

	public Shoes setPrice(int price) {
		this.price = price;
		return this;
	}

	public Brand getBrand() {
		return brand;
	}

	public Shoes setBrand(Brand brand) {
		this.brand = brand;
		return this;
	}
}
