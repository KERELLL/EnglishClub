package ru.rychkovkirill.englishclub.domain.models

data class Token(
    val access_token: String,
    val token_type: String,
    val expire: String,
) {
}