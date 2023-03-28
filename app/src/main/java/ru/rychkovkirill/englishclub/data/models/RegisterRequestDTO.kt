package ru.rychkovkirill.englishclub.data.models

data class RegisterRequestDTO(
    val first_name: String,
    val last_name: String,
    val username: String,
    val email: String,
    val password: String,
    val is_admin: Boolean
)

