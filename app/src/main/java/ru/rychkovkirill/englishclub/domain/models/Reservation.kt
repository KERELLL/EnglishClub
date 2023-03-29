package ru.rychkovkirill.englishclub.domain.models

data class Reservation(
    val id: Int,
    val shift_id: Int,
    val user_id: Int,
    val is_approved: Boolean
) {
}