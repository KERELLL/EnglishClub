package ru.rychkovkirill.englishclub.domain.models

data class Task(
    val title: String,
    val description: String,
    val points: Int,
    val start_date: String,
    val end_date: String,
    val is_active: Boolean,
    val id: Int,
    val author_id: Int
) {
}