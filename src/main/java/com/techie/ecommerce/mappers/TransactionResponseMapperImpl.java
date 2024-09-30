package com.techie.ecommerce.mappers;

import com.techie.ecommerce.domain.dto.shippo.TransactionResponseDto;
import com.techie.ecommerce.domain.model.shippo.TransactionsResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionResponseMapperImpl implements Mapper<TransactionsResponse, TransactionResponseDto>{

    private ModelMapper mapper;

    public TransactionResponseMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TransactionResponseDto mapTo(TransactionsResponse transactionsResponse) {
        return mapper.map(transactionsResponse, TransactionResponseDto.class);
    }

    @Override
    public TransactionsResponse mapFrom(TransactionResponseDto transactionResponseDto) {
        return mapper.map(transactionResponseDto, TransactionsResponse.class);
    }
}
