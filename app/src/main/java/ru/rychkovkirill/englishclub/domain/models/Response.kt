package ru.rychkovkirill.englishclub.domain.models

data class Response(
    val id: Int,
    val answer: String,
    val user_id: String,
    val task_id: Int,
    val response_time: String,
    val is_approved: Boolean,
    val is_completed: Boolean,
    val is_checked: Boolean
)