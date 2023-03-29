package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Shift

data class ShiftResponseDTO(
    val name: String,
    val start_date: String,
    val end_date: String,
    val id: Int,
    val participants_number: Int
)
fun ShiftResponseDTO.toShift() : Shift{
    return Shift(
        name, start_date, end_date, id, participants_number
    )
}