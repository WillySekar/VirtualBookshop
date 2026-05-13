package com.sw.VirtualBookshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sw.VirtualBookshop.model.BookTitleAuthorProjection;
import com.sw.VirtualBookshop.model.Books;

@Repository
public interface BookRepository extends JpaRepository<Books, Long> {
	@Query(value = "select title,author from books" ,nativeQuery = true)
	List<BookTitleAuthorProjection> findRequiredColumns();

}
