package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.User

data class LoginResponseDTO(
    val access_token: String,
    val token_type: String,
    val expire: String,
    val user_info: UserResponseDTO
)

fun LoginResponseDTO.toUser(): User {
    return User(
        user_info.id,
        access_token,
        user_info.first_name,
        user_info.last_name,
        user_info.username,
        user_info.email,
        user_info.experience,
        user_info.rank,
        user_info.hobby,
        user_info.media_link,
        user_info.is_admin
    )
}