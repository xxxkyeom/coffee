package deu.ex.sevenstars.repository;


import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.repository.search.ProductSearch;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product,Long>, ProductSearch {
}
