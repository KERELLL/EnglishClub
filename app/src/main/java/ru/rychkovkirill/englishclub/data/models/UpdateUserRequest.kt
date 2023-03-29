package ru.rychkovkirill.englishclub.data.models

data class UpdateUserRequest(
    val first_name: String,
    val last_name: String,
    val username: String,
    val experience: Int?,
    val rank: String?,
    val hobby: String?,
    val media_link: String?,
) {
}