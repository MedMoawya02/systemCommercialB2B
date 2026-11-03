package com.microtech.b2b.mapper;

import com.microtech.b2b.dto.request.UserRequest;
import com.microtech.b2b.dto.response.UserResponse;
import com.microtech.b2b.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    UserResponse toRespone(User user);
    List<UserResponse> toResponseList(List<User> users);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    User toEntity(UserRequest request);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "password",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    void toUpdateEntity(UserRequest request,@MappingTarget User user);
}
