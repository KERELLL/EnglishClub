package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Menu
import ru.rychkovkirill.englishclub.domain.models.News

interface MainRepository {
    suspend fun getNews() : OperationResult<List<News>, String?>

    suspend fun addNews(title: String, content: String) : OperationResult<Unit, String?>

    suspend fun getNewsInfo(newsId: Int) : OperationResult<News, String?>

}