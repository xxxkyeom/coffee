package deu.ex.sevenstars.controller;

import deu.ex.sevenstars.dto.PageRequestDTO;
import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@Log4j2
public class ProductViewController {
    private final ProductService productService;

    @GetMapping("/products") //페이징 처리
    public String pageProduct(@PageableDefault(page = 1)PageRequestDTO pageRequestDTO, Model model) {
        Page<ProductDTO> productDTOPage = productService.page(pageRequestDTO);

        return "product-list"; // 템플릿 이름 (templates/product-list.html)
    }

    @GetMapping("/new-product")
    public String newProduct () {
        return "new-product"; // 템플릿 이름 (templates/product-list.html)
    }

    @PostMapping("/products")
    public String processProductForm(@RequestParam String productName,
                                     @RequestParam String category,
                                     @RequestParam int price,
                                     @RequestParam String description,
                                     @RequestParam("image") MultipartFile image,
                                     Model model) {
        log.info("Product Name: {}", productName);
        log.info("Category: {}", category);
        log.info("Price: {}", price);
        log.info("Description: {}", description);
        log.info("image: {}", image);


        return "product-list"; //
    }
}
