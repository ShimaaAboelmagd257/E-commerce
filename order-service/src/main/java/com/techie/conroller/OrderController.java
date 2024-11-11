package com.techie.conroller;

import com.techie.domain.OrderDto;
import com.techie.domain.OrderEntity;
import com.techie.mappers.Mapper;
import com.techie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController implements OrderApi {

    private OrderService service;
    private Mapper<OrderEntity, OrderDto> mapper;

    @Autowired
    public OrderController(OrderService service, Mapper<OrderEntity, OrderDto> mapper) {
        this.service = service;
        this.mapper = mapper;
    }
    @Override
    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        OrderEntity orderEntity =mapper.mapFrom(orderDto);
        OrderEntity savedOrder = service.save(orderEntity);
        OrderDto dto = mapper.mapTo(savedOrder);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @Override

    @GetMapping(path = "/{orderId}")
    public ResponseEntity<OrderDto> getOrderById (@PathVariable Long orderId){
        Optional<OrderEntity> orderEntity = service.getOrderById(orderId);
        return orderEntity.map(order -> {
            OrderDto dto  = mapper.mapTo(order);
             return new ResponseEntity<>(dto,HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @Override

    @GetMapping
    public  ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderEntity> orderEntity = service.getAllOrders();
        List<OrderDto> orderDto = orderEntity
                .stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderDto);
    }
    @Override

    @PutMapping(path = "/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(
            @PathVariable("orderId") Long orderId,
            @RequestBody OrderDto orderDto
    ){
        if(!service.isExists(orderId)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        orderDto.setOrderId(orderId);
        OrderEntity updatedOrder = mapper.mapFrom(orderDto);
        OrderEntity entity = service.save(updatedOrder);
        return new ResponseEntity<>(
                mapper.mapTo(entity),
                HttpStatus.OK);
    }
    @Override
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable Long orderId){
        service.deleteOrderById(orderId);
        return ResponseEntity.noContent().build();
    }


}
