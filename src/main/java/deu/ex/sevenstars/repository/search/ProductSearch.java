package deu.ex.sevenstars.repository.search;

import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductSearch {
    Page<Product> list(Pageable pageable);
    Page<ProductDTO> listDTO(Pageable pageable);

    Page<Product> findProductsByOrderByPriceASC(Pageable pageable);
    Page<Product> findProductsByOrderByPriceDESC(Pageable pageable);
    Page<Product> findProductsByOrderByCategoryASC(Pageable pageable);
    Page<Product> findProductsByOrderByCategoryDESC(Pageable pageable);
}
