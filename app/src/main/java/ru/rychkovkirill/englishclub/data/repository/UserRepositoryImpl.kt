package ru.rychkovkirill.englishclub.data.repository

import android.graphics.Path.Op
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.rychkovkirill.englishclub.data.ApiService
import ru.rychkovkirill.englishclub.data.PrefsStorage
import ru.rychkovkirill.englishclub.data.models.toUser
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefsStorage: PrefsStorage
): UserRepository {

    override suspend fun getUserInfo(email: String): OperationResult<User, String?> {
        return withContext(Dispatchers.IO){
            try{
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getUserInfo(email, tokenHeader)
                if(response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toUser(tokenHeader)
                    return@withContext OperationResult.Success(result)
                }
                else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else{
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            }catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getAllUsers(): OperationResult<List<User>, String?> {
        return withContext(Dispatchers.IO){
            try{
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getAllUsers(tokenHeader)
                if(response.isSuccessful && response.body() != null){
                    val result = response.body()!!.map { it.toUser(tokenHeader) }
                    return@withContext OperationResult.Success(result)
                }
                else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else{
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            }catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun updateUser(
        email: String,
        first_name: String,
        last_name: String,
        username: String,
        experience: Int?,
        rank: String?,
        hobby: String?,
        media_link: String?
    ): OperationResult<Unit, String?> {
        TODO("Not yet implemented")
    }

    override suspend fun updateMe(
        first_name: String,
        last_name: String,
        username: String,
        experience: Int?,
        rank: String?,
        hobby: String?,
        media_link: String?
    ): OperationResult<Unit, String?> {
        TODO("Not yet implemented")
    }
}