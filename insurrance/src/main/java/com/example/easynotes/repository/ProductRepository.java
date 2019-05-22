package com.example.easynotes.repository;

import com.example.easynotes.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ThuongPham on 22/05/2019.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
