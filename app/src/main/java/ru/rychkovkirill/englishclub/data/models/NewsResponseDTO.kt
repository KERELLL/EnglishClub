package ru.rychkovkirill.englishclub.data.models

import ru.rychkovkirill.englishclub.domain.models.News

class NewsResponseDTO(
    val title: String,
    val content: String,
    val id: Int,
    val created_at: String
)

fun NewsResponseDTO.toNews() : News{
    return News(title, content, id, created_at)
}