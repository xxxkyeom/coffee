package deu.ex.sevenstars.dto;

import deu.ex.sevenstars.entity.Orders;
import deu.ex.sevenstars.entity.OrderItem;
import deu.ex.sevenstars.entity.Product;
import deu.ex.sevenstars.repository.ProductRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderDTO {

    private Long orderId;

    private String email;

    private String address;

    private String postcode;

    private int quantity; //주문한 상품의  총개수

    private int price;

    private List<ProductDTO> orderItems;



    public OrderDTO(Orders orders) {
        this.orderId = orders.getOrderId();
        this.email = orders.getEmail();
        this.address = orders.getAddress();
        this.quantity = orders.getOrderItems().stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();
        this.postcode = orders.getPostcode();
        //수정부분
        this.price = orders.getOrderItems().stream()
                .mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity())
                .sum();
        this.orderItems = orders.getOrderItems().stream()
                .map(orderItem -> new ProductDTO(orderItem.getProduct(), orderItem.getQuantity())) // Pass quantity here
                .collect(Collectors.toList()); //상품의 각개수 ex)상품1은 2개 상품2는 3개
    }

    public Orders toEntity() {
        Orders orders = Orders.builder()
                .orderId(orderId)
                .email(email)
                .address(address)
                .postcode(postcode)
                .build();

        return orders;
    }
}
