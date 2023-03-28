package ru.rychkovkirill.englishclub.domain.models

data class User(
    val id: Int,
    val token: String,
    val first_name: String,
    val last_name: String,
    val username: String,
    val email: String,
    val experience: Int?,
    val rank: String?,
    val hobby: String?,
    val media_link: String?,
    val isAdmin: Boolean
)