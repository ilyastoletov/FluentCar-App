package com.appninjas.data.network.auth.mapper

import com.appninjas.data.network.auth.dto.UserDto
import com.appninjas.domain.model.User

class UserMapper {

    fun userToUserDto(user: User): UserDto = UserDto(
        phone = user.phone,
        email = user.email,
        name = user.name,
        password = user.password
    )

    fun userDtoToUser(userDto: UserDto): User = User(
        phone = userDto.phone,
        email = userDto.email,
        name = userDto.name,
        password = userDto.password
    )

}