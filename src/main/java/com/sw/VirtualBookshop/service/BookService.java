package com.sw.VirtualBookshop.service;

import org.springframework.stereotype.Service;
import com.sw.VirtualBookshop.Config.JwtUtil;
import com.sw.VirtualBookshop.model.BookTitleAuthorProjection;
import com.sw.VirtualBookshop.model.Books;
import com.sw.VirtualBookshop.model.CartItem;
import com.sw.VirtualBookshop.model.OrderItems;
import com.sw.VirtualBookshop.model.Orders;
import com.sw.VirtualBookshop.repository.BookRepository;
import com.sw.VirtualBookshop.repository.OrderItemsRepository;
import com.sw.VirtualBookshop.repository.OrdersRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@Service
public class BookService {
	
	  @Autowired
	    private BookRepository bookRepository;
	   
	  @Autowired
	  private JwtUtil jwtUtil;
	  
	  @Autowired
	  private OrderItemsRepository itemsRepository;
	  @Autowired
	  private OrdersRepository ordersRepository;
	  
	  private static final Logger log=LoggerFactory.getLogger(BookService.class);
          
	    public List<Books> getAllBooks() {
	    	log.info("BookService loaded successfully");
	        return bookRepository.findAll();
	    }

		public List<BookTitleAuthorProjection> getAllauthors() {
			return bookRepository.findRequiredColumns();
		}

		public Books getBookById(Long id) {
			return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
		}


		public String OnBuy(Long id, String token) {
			Books book= bookRepository.findById(id).orElseThrow(() ->new RuntimeException("Book not found with id: " + id));
			String email=jwtUtil.extractEmail(token);
			log.info("Buy request received for book id: {}", id);
			if(book.getQuantity()>=1) 
			{
				book.setQuantity(book.getQuantity()-1);
		        bookRepository.save(book);
		        
		        Orders orders =new Orders();
		        
		        orders.setEmail(email);
		        orders.setOrderdate(LocalDateTime.now());
		        orders.setTotalAmount(book.getPrice());
		        ordersRepository.save(orders);
		        
		        OrderItems items =new  OrderItems();
		        items.setOrders(orders);
		        items.setBookTitle(book.getTitle());
		        items.setBookId(book.getId());
		        items.setPrice(book.getPrice());
		        items.setQuantity(1);
		        itemsRepository.save(items);
		        
		        log.info("Book purchased successfully");
		        return "Book purchased successfully";
			}else {
				log.error("Error while buying book {}", id, "Out of Stocks");
				return "Out of Stocks ";
			}
		}
		
		@Transactional
		public String buyMultiple(List<CartItem> items , String token) {
			 double total=0;
			 String email=jwtUtil.extractEmail(token);
			 
			 Orders orders = new Orders();
			 orders.setEmail(email);
			 orders.setOrderdate(LocalDateTime.now());
			 
		     List<OrderItems> list = new ArrayList<>();

			 for (CartItem ci : items) {
		            Books book = getBookById(ci.getId());
		            
		            if (book.getQuantity() < ci.getQty()) throw new RuntimeException("Not enough stock: " + book.getTitle());
		            book.setQuantity(book.getQuantity() - ci.getQty());
		            bookRepository.save(book);
		            
		            
		            OrderItems orderItems =new OrderItems();
		            orderItems.setBookTitle(book.getTitle());
		            orderItems.setOrders(orders);
		            orderItems.setBookId(book.getId());
		            orderItems.setQuantity(ci.getQty());
		            orderItems.setPrice(book.getPrice());
		            list.add(orderItems);
		            total += book.getPrice() * ci.getQty();
		            //System.out.println("Checkout Service");
		        }
			    orders.setTotalAmount(total);
			    orders.setItems(list);
			    ordersRepository.save(orders);
			    
		        return "Checkout successful!";
		}

		public ResponseEntity<List<Orders>> getOrders(String token) {
			
			    String email=jwtUtil.extractEmail(token);
			    List<Orders> orders= ordersRepository.findByEmail(email);
			    if(orders.isEmpty()) {
			    	return ResponseEntity.badRequest().body(new ArrayList<>());
			    }
			    return ResponseEntity.ok(orders);
		}

}
