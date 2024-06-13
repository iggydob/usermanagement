package bg.energopro.mapper;

import bg.energopro.domain.User;
import bg.energopro.dto.UserDto;
import bg.energopro.configuration.MappingConfig;
import org.mapstruct.Mapper;

@Mapper(config = MappingConfig.class)
public interface UserDtoMapper {

    UserDto mapToUserDto(User user);

    User mapToUser(UserDto userDto);
}
