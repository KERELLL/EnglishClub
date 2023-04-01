package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.Response

data class ResponseResponseDTO(
    val id: Int,
    val answer: String,
    val user_email: String,
    val task_id: Int,
    val response_time: String,
    val is_approved: Boolean,
    val is_completed: Boolean,
    val is_checked: Boolean
)
fun ResponseResponseDTO.toResponse() : Response{
    return Response(
        id, answer, user_email, task_id, response_time, is_approved, is_completed, is_checked
    )
}