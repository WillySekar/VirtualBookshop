package com.sw.VirtualBookshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VirtualBookshopApplication {
	
	 private static final Logger log =
	            LoggerFactory.getLogger(VirtualBookshopApplication.class);
	public static void main(String[] args) {
		  log.info("🚀 Application starting...");
		SpringApplication.run(VirtualBookshopApplication.class, args);
	}

}
