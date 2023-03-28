package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Token

data class RegisterResponseDTO(
    val access_token: String,
    val token_type: String,
    val expire: String,
)
fun RegisterResponseDTO.toToken() : Token{
    return Token(access_token, token_type, expire)
}