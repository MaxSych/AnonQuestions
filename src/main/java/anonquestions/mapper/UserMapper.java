package anonquestions.mapper;

import anonquestions.User;
import anonquestions.dto.request.RegistrationRequest;
import anonquestions.dto.response.UserResponse;
import org.hibernate.annotations.Cache;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")

public interface UserMapper {


    UserResponse toResponse(User user);


    @Mapping(target = "password", ignore = true)
    User toEntity(RegistrationRequest request);
}