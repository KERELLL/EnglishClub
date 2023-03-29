package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Reservation

data class ReservationResponseDTO(
    val id: Int,
    val shift_id: Int,
    val user_id: Int,
    val is_approved: Boolean
)
fun ReservationResponseDTO.toReservation() : Reservation{
    return Reservation(
        id, shift_id, user_id, is_approved
    )
}