package ru.rychkovkirill.englishclub.domain.models

data class Reservation(
    val id: Int,
    val shift: Shift,
    val email: String,
    val first_name: String,
    val last_name: String,
    val media_link: String?,
    val is_approved: Boolean
) {
}