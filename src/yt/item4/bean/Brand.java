package yt.item4.bean;

import java.io.Serializable;
import java.util.Set;

public class Brand implements Serializable, EntityInterface {

	private static final long serialVersionUID = 1L;

	private int brandId;

	private String brandName;

	private String website;

	private String country;

	private Set<Shoes> shoesGroup;

	public Brand() {
	}

	public Brand(String brandName) {
		this.brandName = brandName;
	}

	public Brand(int brandId) {
		this.brandId = brandId;
	}

	public Brand(int brandId, String brandName, String website, String country) {
		this.brandId = brandId;
		this.brandName = brandName;
		this.website = website;
		this.country = country;
	}

	public int getBrandId() {
		return brandId;
	}

	public Brand setBrandId(int id) {
		this.brandId = id;
		return this;
	}

	public String getBrandName() {
		return brandName;
	}

	public Brand setBrandName(String brandName) {
		this.brandName = brandName;
		return this;
	}

	public String getWebsite() {
		return website;
	}

	public Brand setWebsite(String website) {
		this.website = website;
		return this;
	}

	public String getCountry() {
		return country;
	}

	public Brand setCountry(String country) {
		this.country = country;
		return this;
	}

	@Override
	public String toString() {
		return brandId + " " + brandName + " " + website + " " + country;

	}

	public Set<Shoes> getShoesGroup() {
		return shoesGroup;
	}

	public void setShoesGroup(Set<Shoes> shoesGroup) {
		this.shoesGroup = shoesGroup;
	}

	public void addShoes(Shoes shoes) {
		shoesGroup.add(shoes);
	}

	public void removeShoes(Shoes shoes) {
		shoesGroup.remove(shoes);
	}

	@Override
	public int getId() {
		return this.brandId;
	}

}
