package ru.rychkovkirill.englishclub.domain.models

data class Shift(
    val name: String,
    val start_date: String,
    val end_date: String,
    val id: Int,
    val participants_number: Int
) {
}