package com.example.miniProject.dto;

import com.example.miniProject.Entity.Product;

public class ProductDto {

	private long id;
	private String name;
	private long price;
	private int stockQuantity;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public int getStockQuantity() {
		return stockQuantity;
	}
	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}
	public int setStockQuantity(Product product) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
