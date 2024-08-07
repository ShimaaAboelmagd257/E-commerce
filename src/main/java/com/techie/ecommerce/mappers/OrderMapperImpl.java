package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.OrderDto;
import com.techie.ecommerce.domain.model.OrderEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderMapperImpl implements Mapper<OrderEntity, OrderDto>{

    private ModelMapper mapper;

    public OrderMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public OrderDto mapTo(OrderEntity orderEntity) {
        return mapper.map(orderEntity,OrderDto.class);
    }

    @Override
    public OrderEntity mapFrom(OrderDto orderDto) {
        return mapper.map(orderDto, OrderEntity.class);
    }
}
