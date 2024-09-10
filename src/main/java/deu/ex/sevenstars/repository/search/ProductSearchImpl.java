package deu.ex.sevenstars.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import deu.ex.sevenstars.dto.ProductDTO;
import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.entity.QProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Component;

import java.util.List;


public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }

    @Override
    public Page<Product> findProductsByOrderByPriceASC(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.orderBy(product.price.asc());

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Product> productPage = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }

    @Override
    public Page<Product> findProductsByOrderByPriceDESC(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.orderBy(product.price.desc());

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Product> productPage = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }

    @Override
    public Page<Product> findProductsByOrderByCategoryASC(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.orderBy(product.category.asc());

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Product> productPage = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }

    @Override
    public Page<Product> findProductsByOrderByCategoryDESC(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.orderBy(product.category.desc());

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Product> productPage = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }

    @Override
    public Page<Product> list(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.where(product.productId.gt(0L));

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Product> productPage = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }

    @Override
    public Page<ProductDTO> listDTO(Pageable pageable) {
        QProduct product = QProduct.product;

        JPQLQuery<Product> query = from(product);
        query.where(product.productId.gt(0L));
        getQuerydsl().applyPagination(pageable, query); //페이징 적용

        JPQLQuery<ProductDTO> dtoQuery =
                query.select(Projections.constructor(ProductDTO.class,product));
        //생성자 방식 projections
        List<ProductDTO> productPage = dtoQuery.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(productPage, pageable, count);
    }
}
