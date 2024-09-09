package deu.ex.sevenstars.repository.search;

import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductSearch {
    Page<Product> list(Pageable pageable);
    Page<ProductDTO> listDTO(Pageable pageable);
}
