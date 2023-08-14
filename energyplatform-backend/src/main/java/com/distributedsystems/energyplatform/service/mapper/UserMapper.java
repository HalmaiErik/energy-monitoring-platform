package com.distributedsystems.energyplatform.service.mapper;

import com.distributedsystems.energyplatform.dto.UserDto;
import com.distributedsystems.energyplatform.entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    public User mapToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    public UserDto mapToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
