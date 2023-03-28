package ru.rychkovkirill.englishclub.domain.models

data class News(
    val title: String,
    val content: String,
    val id: Int,
    val created_at: String
)