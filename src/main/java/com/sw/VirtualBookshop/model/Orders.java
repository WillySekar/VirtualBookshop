package com.sw.VirtualBookshop.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Orders {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long id;
	
	private String email;
	
	private LocalDateTime orderdate;
	private double totalAmount;
	
	@OneToMany(cascade = CascadeType.ALL ,mappedBy = "orders", fetch = FetchType.EAGER)
	private List<OrderItems> items =new ArrayList<>();
	
	public Orders() {
		super();
	}

	public Orders(Long id, String email, LocalDateTime orderdate, double totalAmount, List<OrderItems> items) {
		super();
		this.id = id;
		this.email = email;
		this.orderdate = orderdate;
		this.totalAmount = totalAmount;
		this.items = items;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public LocalDateTime getOrderdate() {
		return orderdate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public List<OrderItems> getItems() {
		return items;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setOrderdate(LocalDateTime orderdate) {
		this.orderdate = orderdate;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setItems(List<OrderItems> items) {
		this.items = items;
	}

	
	

}
