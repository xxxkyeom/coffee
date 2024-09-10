package deu.ex.sevenstars.repository.search;

import deu.ex.sevenstars.dto.OrderDTO;
import deu.ex.sevenstars.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderSearch {
    Page<Orders> list(Pageable pageable);
    Page<OrderDTO> listDTO(Pageable pageable);
}
