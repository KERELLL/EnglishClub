package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.User

data class UserResponseDTO(
    val id: Int,
    val first_name: String,
    val last_name: String,
    val username: String,
    val email: String,
    val experience: Int?,
    val rank: String?,
    val hobby: String?,
    val media_link: String?,
    val is_admin: Boolean
)

fun UserResponseDTO.toUser(token: String) : User{
    return User(id, token, first_name, last_name, username, email, experience, rank, hobby, media_link, is_admin)
}