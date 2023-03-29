package ru.rychkovkirill.englishclub.data.models

data class ShiftAddRequest(
    val name: String,
    val start_date: String,
    val end_date: String
) {
}