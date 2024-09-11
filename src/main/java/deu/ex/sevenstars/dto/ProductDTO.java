package deu.ex.sevenstars.dto;

import deu.ex.sevenstars.entity.Category;
import deu.ex.sevenstars.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Schema(description = "상품 데이터 전송 객체")
public class ProductDTO {
    private Long productId;

    private Category category;

    private String productName;

    @Min(0)
    private int price;

    private int quantity; // orderDTO에서 사용

    private String description;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.category = product.getCategory();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
    }


    public ProductDTO(Product product, int quantity) {
        this.productId = product.getProductId();
        this.category = product.getCategory();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = quantity;  // quantity 설정
        this.description = product.getDescription();
    }

    public Product toEntity(){
        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .build();

        return product;
    }
}
