package deu.ex.sevenstars.service;

import deu.ex.sevenstars.dto.PageRequestDTO;
import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.exception.ProductException;
import deu.ex.sevenstars.repository.ProductRepository;
import deu.ex.sevenstars.util.UploadUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;

    private final UploadUtil uploadUtil;

    public ProductDTO insert(ProductDTO productDTO, MultipartFile imageFile){
        try {
            Product product = productDTO.toEntity();

            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadUtil.upload(imageFile);
                product.changeImageUrl(imageUrl);
            }

            productRepository.save(product);
            return new ProductDTO(product);
        } catch (DataIntegrityViolationException e){
            throw ProductException.NOT_FOUND.get();
        } catch (Exception ee){

            log.error("예외 발생 코드 : " + ee.getMessage());
            throw ProductException.NOT_REGISTERED.get();
        }
    }

    public ProductDTO read(Long productId){
        Product product = productRepository.findById(productId).orElseThrow(ProductException.NOT_FOUND::get);

        return new ProductDTO(product);
    }

    public ProductDTO update(ProductDTO productDTO, MultipartFile imageFile){
        Product product = productRepository.findById(productDTO.getProductId()).orElseThrow(ProductException.NOT_FOUND::get);

        try {
            product.changePrice(productDTO.getPrice());
            product.changeProductName(product.getProductName());
            product.changeCategory(productDTO.getCategory());
            product.changeDescription(product.getDescription());

            if (imageFile != null && !imageFile.isEmpty()) {
                String imageUrl = uploadUtil.upload(imageFile);
                product.changeImageUrl(imageUrl);
            }
            return new ProductDTO(product);
        } catch (Exception e){
            log.error("예외 발생 코드 : "+e.getMessage());
            throw ProductException.NOT_MODIFIED.get();
        }
    }

    public void delete(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(ProductException.NOT_FOUND::get);
        try {
            productRepository.delete(product);
        } catch (Exception e){
            log.error("예외 발생 코드 : " + e.getMessage());
            throw ProductException.NOT_REMOVED.get();
        }
    }

    public Page<ProductDTO> page(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.listDTO(pageable);
        }catch (Exception e){
            log.error("예외 코드 : " + e.getMessage());
            throw ProductException.NOT_FOUND.get();
        }
    }

    public Page<Product> pagePriceASC(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.findProductsByOrderByPriceASC(pageable);
        }catch (Exception e){
            log.error("예외 코드 : " + e.getMessage());
            throw ProductException.NOT_FOUND.get();
        }
    }

    public Page<Product> pagePriceDESC(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.findProductsByOrderByPriceDESC(pageable);
        }catch (Exception e){
            log.error("예외 코드 : " + e.getMessage());
            throw ProductException.NOT_FOUND.get();
        }
    }
    public Page<Product> pageCategoryASC(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.findProductsByOrderByCategoryASC(pageable);
        }catch (Exception e){
            log.error("예외 코드 : " + e.getMessage());
            throw ProductException.NOT_FOUND.get();
        }
    }
    public Page<Product> pageCategoryDESC(PageRequestDTO pageRequestDTO){
        try {
            Sort sort = Sort.by("productId").ascending();
            Pageable pageable = pageRequestDTO.getPageable(sort);
            return productRepository.findProductsByOrderByCategoryDESC(pageable);
        }catch (Exception e){
            log.error("예외 코드 : " + e.getMessage());
            throw ProductException.NOT_FOUND.get();
        }
    }
}