package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Reservation
import ru.rychkovkirill.englishclub.domain.models.Shift
import ru.rychkovkirill.englishclub.domain.models.User

data class ReservationResponseDTO(
    val id: Int,
    val shift_info: ShiftResponseDTO,
    val user_info: UserResponseDTO,
    val is_approved: Boolean
)
fun ReservationResponseDTO.toReservation() : Reservation{
    val shift : Shift = Shift(shift_info.name, shift_info.number, shift_info.description, shift_info.start_date, shift_info.end_date, shift_info.id, shift_info.participants_number)
    return Reservation(
        id, shift, user_info.email, user_info.first_name, user_info.last_name, user_info.media_link, is_approved
    )
}