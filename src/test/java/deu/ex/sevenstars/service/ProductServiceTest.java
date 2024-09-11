package deu.ex.sevenstars.service;

import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Category;
import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.exception.ProductException;
import deu.ex.sevenstars.repository.ProductRepository;
import deu.ex.sevenstars.util.UploadUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UploadUtil uploadUtil;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void insertProductWithImageAndThumbnail() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setProductName("커피");
        productDTO.setCategory(Category.COFFEE_BEAN_PACKAGE);
        productDTO.setPrice(100);
        productDTO.setDescription("맛있는 커피");

        MultipartFile imageFile = mock(MultipartFile.class);
        MultipartFile thumbnailFile = mock(MultipartFile.class);

        String imagePath = "a47ea256-4f82-4137-8f43-6ff725037284_image.jpg";
        String thumbnailPath = "28fa9d90-3e15-4e98-97fd-bc435cd41c6a_thumb.jpg";

        when(uploadUtil.upload(any(MultipartFile[].class))).thenAnswer(invocation -> {
            MultipartFile[] files = invocation.getArgument(0);
            return Arrays.asList(imagePath, thumbnailPath);
        });

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            return invocation.getArgument(0);
        });

        ProductDTO result = productService.insert(productDTO, imageFile, thumbnailFile);

        assertNotNull(result);
        assertEquals(imagePath, result.getImagePath());
        assertEquals(thumbnailPath, result.getThumbnailPath());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}
