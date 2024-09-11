package deu.ex.sevenstars.dto;

import deu.ex.sevenstars.entity.Category;
import deu.ex.sevenstars.entity.Product;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDTO {
    private Long productId;

    private Category category;

    private String productName;

    @Min(0)
    private int price;

    private String description;

    // 추가 필드
    private String imagePath;
    private String thumbnailPath;

    public ProductDTO(Product product) {
        this.productId = product.getProductId();
        this.category = product.getCategory();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.imagePath = product.getImagePath();
        this.thumbnailPath = product.getThumbnailPath();
    }

    public Product toEntity(){
        Product product = Product.builder()
                .productId(productId)
                .productName(productName)
                .category(category)
                .price(price)
                .description(description)
                .imagePath(imagePath)
                .thumbnailPath(thumbnailPath)
                .build();

        return product;
    }
}
