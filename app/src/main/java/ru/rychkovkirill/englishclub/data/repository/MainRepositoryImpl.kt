package ru.rychkovkirill.englishclub.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.rychkovkirill.englishclub.data.ApiService
import ru.rychkovkirill.englishclub.data.PrefsStorage
import ru.rychkovkirill.englishclub.data.models.*
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.News
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val prefsStorage: PrefsStorage
) : MainRepository {
    override suspend fun getNews(): OperationResult<List<News>, String?> {

        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNews(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map{it.toNews()}
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun addNews(title: String, content: String): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.addNews(tokenHeader, NewsAddRequest(title, content))
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getNewsInfo(newsId: Int): OperationResult<News, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNewsInfo(newsId, tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toNews()
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }
}