package ru.rychkovkirill.englishclub.data.models

data class TaskRequestDTO(
    val title: String,
    val description: String,
    val points: Int,
    val start_date: String,
    val end_date: String,
    val is_active: Boolean,
) {
}