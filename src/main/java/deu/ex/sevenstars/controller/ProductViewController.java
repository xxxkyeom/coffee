package deu.ex.sevenstars.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Log4j2
public class ProductViewController {

    @GetMapping("/products")
    public String listProducts() {
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

        model.addAttribute("productName", productName);
        model.addAttribute("category", category);
        model.addAttribute("price", price);
        model.addAttribute("description", description);
        model.addAttribute("image", image.getOriginalFilename());


        return "product-list"; //
    }
}
