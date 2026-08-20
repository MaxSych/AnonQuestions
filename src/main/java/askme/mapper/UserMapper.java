package askme.mapper;

import askme.User;
import askme.dto.request.RegistrationRequest;
import askme.dto.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")

public interface UserMapper {


    UserResponse toResponse(User user);


    @Mapping(target = "password", ignore = true)
    User toEntity(RegistrationRequest request);
}