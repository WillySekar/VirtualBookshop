package com.sw.VirtualBookshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sw.VirtualBookshop.model.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Long> {

}
