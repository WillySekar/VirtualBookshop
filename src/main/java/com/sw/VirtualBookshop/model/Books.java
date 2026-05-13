package com.sw.VirtualBookshop.model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder

public class Books {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String title;
	    private String author;
	    private double price;
	    private String description;
	    private int quantity;
	    
		public Long getId() {
			return id;
		}
		public String getTitle() {
			return title;
		}
		public String getAuthor() {
			return author;
		}
		public double getPrice() {
			return price;
		}
		public String getDescription() {
			return description;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		
		
		public int getQuantity() {
			return quantity;
		}
		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}
		
	    
		public Books(Long id, String title, String author, double price, String description, int quantity) {
			super();
			this.id = id;
			this.title = title;
			this.author = author;
			this.price = price;
			this.description = description;
			this.quantity = quantity;
		}
		public Books() {
			
		}
}
