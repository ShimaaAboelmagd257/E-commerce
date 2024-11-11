package com.techie.mappers;

import com.techie.domain.UserDto;
import com.techie.domain.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements Mapper<UserEntity, UserDto>{

    private ModelMapper mapper;

    public UserMapperImpl(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public UserDto mapTo(UserEntity userEntity) {
        return mapper.map(userEntity,UserDto.class);
    }

    @Override
    public UserEntity mapFrom(UserDto userDto) {
        return mapper.map(userDto,UserEntity.class);
    }
}
