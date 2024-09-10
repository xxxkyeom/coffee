package deu.ex.sevenstars.repository;

import deu.ex.sevenstars.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {

}
