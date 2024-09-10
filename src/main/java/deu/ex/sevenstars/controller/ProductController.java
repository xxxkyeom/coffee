package deu.ex.sevenstars.controller;

import deu.ex.sevenstars.dto.PageRequestDTO;
import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.exception.ProductException;
import deu.ex.sevenstars.service.ProductService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> register(
            @Validated @RequestBody ProductDTO productDTO
    ){
        log.info("--- register()");
        log.info("--- productDTO : "+productDTO);

        return ResponseEntity.ok(productService.insert(productDTO));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ProductDTO> read(
            @PathVariable ("productId") Long productId
    ){
        log.info("--- read()");
        log.info("--- productId : " + productId);

        return ResponseEntity.ok(productService.read(productId));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDTO> modify(
            @PathVariable ("productId") Long productId,
            @Validated @RequestBody ProductDTO productDTO
    ){
        log.info("--- modify()");
        log.info(("--- productDTO : " + productDTO));

        if(!productId.equals(productDTO.getProductId())){
            throw ProductException.NOT_MODIFIED.get();
        }

        return ResponseEntity.ok(productService.update(productDTO));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Map<String,String >> remove (
            @PathVariable ("productId") Long productId){
        log.info("--- remove()");
        log.info("--- productId : " + productId);

        try {
            productService.delete(productId);
        }catch (Exception e){
            log.error("예외 발생 코드 : " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }

        return ResponseEntity.ok(Map.of("result", "success"));
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> page(@Validated PageRequestDTO pageRequestDTO){
        log.info("page() : " + pageRequestDTO);

        return ResponseEntity.ok(productService.page(pageRequestDTO));
    }

    @GetMapping("/price/ASC")
    public ResponseEntity<Page<Product>> pagePriceASC(@Validated PageRequestDTO pageRequestDTO){
        log.info("page() : " + pageRequestDTO);

        return ResponseEntity.ok(productService.pagePriceASC(pageRequestDTO));
    }

    @GetMapping("/price/DESC")
    public ResponseEntity<Page<Product>> pagePriceDESC(@Validated PageRequestDTO pageRequestDTO){
        log.info("page() : " + pageRequestDTO);

        return ResponseEntity.ok(productService.pagePriceDESC(pageRequestDTO));
    }

    @GetMapping("/category/ASC")
    public ResponseEntity<Page<Product>> pageCategoryASC(@Validated PageRequestDTO pageRequestDTO){
        log.info("page() : " + pageRequestDTO);

        return ResponseEntity.ok(productService.pageCategoryASC(pageRequestDTO));
    }

    @GetMapping("/category/DESC")
    public ResponseEntity<Page<Product>> pageCategoryDESC(@Validated PageRequestDTO pageRequestDTO){
        log.info("page() : " + pageRequestDTO);

        return ResponseEntity.ok(productService.pageCategoryDESC(pageRequestDTO));
    }
}
