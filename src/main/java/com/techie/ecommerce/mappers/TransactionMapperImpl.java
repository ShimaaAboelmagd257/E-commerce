package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.TransactionDto;
import com.techie.ecommerce.domain.model.shippo.TransactionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements Mapper<TransactionEntity, TransactionDto> {

    private ModelMapper mapper;

    public TransactionMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TransactionDto mapTo(TransactionEntity transactionEntity) {
        return mapper.map(transactionEntity, TransactionDto.class);
    }

    @Override
    public TransactionEntity mapFrom(TransactionDto transactionDto) {
        return mapper.map(transactionDto, TransactionEntity.class);
    }
}
