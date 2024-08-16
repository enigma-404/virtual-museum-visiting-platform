package org.enigma.virtualmuseumvisitingplatform.mapper;

import org.enigma.virtualmuseumvisitingplatform.dto.request.user.UserLoginDto;
import org.enigma.virtualmuseumvisitingplatform.entity.users.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = Mappers.getMapper(UserMapper.class);
    }

    @Test
    void testUserLoginDtoToUser() {
        // Arrange
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUsername("testUser");
        userLoginDto.setPassword("testPassword");

        // Act
        User user = userMapper.userLoginDtoToUser(userLoginDto);

        // Assert
        assertNotNull(user);
        assertEquals("testUser", user.getUsername());
        assertEquals("testPassword", user.getPassword());
    }
}
