package com.egroc.repository;

import com.egroc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{
List<Product> findAllByCategory_Id(int id);
}