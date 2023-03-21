package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Token

data class LoginResponseDTO(
    val access_token: String,
    val token_type: String,
    val expire: String
)

fun LoginResponseDTO.toToken() : Token{
    return Token(
        access_token,
        token_type,
        expire
    )
}