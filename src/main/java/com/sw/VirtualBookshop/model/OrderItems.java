package com.sw.VirtualBookshop.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderItems {
    
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long bookId;
	private String bookTitle;
	private int quantity;
	private double price;
	
	@ManyToOne
	@JoinColumn(name ="order_id")
	@JsonIgnore
	private Orders orders;
	
	public OrderItems() {
		super();
	}

	public OrderItems(Long id, Long bookId, String bookTitle, int quantity, double price, Orders orders) {
		super();
		this.id = id;
		this.bookId = bookId;
		this.bookTitle = bookTitle;
		this.quantity = quantity;
		this.price = price;
		this.orders = orders;
	}

	public Long getId() {
		return id;
	}

	public Long getBookId() {
		return bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public Orders getOrders() {
		return orders;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setOrders(Orders orders) {
		this.orders = orders;
	}
	
       	
	
}
