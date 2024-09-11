package deu.ex.sevenstars.repository;


import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearch {



}
