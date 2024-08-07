package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.OrderItemDto;
import com.techie.ecommerce.domain.model.OrderItemEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapperImpl implements Mapper<OrderItemEntity, OrderItemDto>{

    public OrderItemMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    private ModelMapper mapper;
    @Override
    public OrderItemDto mapTo(OrderItemEntity orderItemEntity) {
        return mapper.map(orderItemEntity, OrderItemDto.class);
    }

    @Override
    public OrderItemEntity mapFrom(OrderItemDto orderItemDto) {
        return mapper.map(orderItemDto,OrderItemEntity.class);
    }
}
