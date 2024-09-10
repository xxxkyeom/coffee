package deu.ex.sevenstars.repository.search;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import deu.ex.sevenstars.dto.OrderDTO;
import deu.ex.sevenstars.entity.Orders;
import deu.ex.sevenstars.entity.QOrders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;


public class OrderSearchImpl extends QuerydslRepositorySupport implements OrderSearch {
    public OrderSearchImpl() {
        super(Orders.class);
    }

    @Override
    public Page<Orders> list(Pageable pageable) {
        QOrders orders = QOrders.orders;

        JPQLQuery<Orders> query = from(orders);

        getQuerydsl().applyPagination(pageable, query); //페이징 적용
        List<Orders> ordersList = query.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(ordersList, pageable, count);
    }

    @Override
    public Page<OrderDTO> listDTO(Pageable pageable) {
        QOrders order = QOrders.orders;

        JPQLQuery<Orders> query = from(order);
        query.where(order.orderId.gt(0L));
        getQuerydsl().applyPagination(pageable, query); //페이징 적용

        JPQLQuery<OrderDTO> dtoQuery =
                query.select(Projections.constructor(OrderDTO.class,order));
        //생성자 방식 projections
        List<OrderDTO> orderPage = dtoQuery.fetch(); //쿼리 실행
        long count = query.fetchCount();  //레코드 수 조회

        return new PageImpl<>(orderPage, pageable, count);
    }
}
