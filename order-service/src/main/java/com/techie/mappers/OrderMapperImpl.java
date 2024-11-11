package com.techie.mappers;

import com.techie.domain.OrderDto;
import com.techie.domain.OrderEntity;
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
