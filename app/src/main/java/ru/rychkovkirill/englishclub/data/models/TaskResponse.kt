package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Task

data class TaskResponse(
    val title: String,
    val description: String,
    val points: Int,
    val start_date: String,
    val end_date: String,
    val is_active: Boolean,
    val id: Int,
    val author_id: Int
)
fun TaskResponse.toTask() : Task {
    return Task(
        title, description, points, start_date, end_date, is_active, id, author_id
    )
}