package com.sw.VirtualBookshop.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sw.VirtualBookshop.model.BookTitleAuthorProjection;
import com.sw.VirtualBookshop.model.Books;
import com.sw.VirtualBookshop.model.CartPayload;
import com.sw.VirtualBookshop.model.Orders;
import com.sw.VirtualBookshop.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
	
	@Autowired
    private BookService bookService;   

	@GetMapping
	public List<Books> getAllBooks() {
		return bookService.getAllBooks();
		}

	@GetMapping("/{id}")
	public ResponseEntity<?> getBookById(@PathVariable  Long id) {
		try {
			Books book=bookService.getBookById(id);
			return ResponseEntity.ok(book);
		}catch (Exception e) {
			 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@GetMapping("/title-author")
	public List<BookTitleAuthorProjection> getAllauthors() {
		return bookService.getAllauthors();
	}

	@PutMapping("/buy/{id}")
	public String OnBuy(@PathVariable Long id ,@RequestHeader("Authorization")String authHeader) {
		try {
			String token=authHeader.substring(7);
			return bookService.OnBuy(id,token);
		}catch(Exception e) {
			return "out of stocks";
		}
	}
	
   @PostMapping("/checkout")
    public String checkout(@RequestBody CartPayload payload , @RequestHeader("Authorization")String authHeader) {
	          String token=authHeader.substring(7);
	         //System.out.println("Checkout controller");
	         return bookService.buyMultiple(payload.getItems(),token);
	    }
	 @GetMapping("/orders")
	 public ResponseEntity<List<Orders>> getOrders(@RequestHeader ("Authorization")String authheader){
		 String token =authheader.substring(7);
		 return bookService.getOrders(token);	
	 }
}    
