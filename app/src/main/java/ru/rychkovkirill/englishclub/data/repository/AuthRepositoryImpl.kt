package ru.rychkovkirill.englishclub.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.rychkovkirill.englishclub.data.ApiService
import ru.rychkovkirill.englishclub.data.PrefsStorage
import ru.rychkovkirill.englishclub.data.models.RegisterRequestDTO
import ru.rychkovkirill.englishclub.data.models.toToken
import ru.rychkovkirill.englishclub.data.models.toUser
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefsStorage: PrefsStorage
) : AuthRepository {

    override suspend fun register(
        first_name: String,
        last_name: String,
        username: String,
        email: String,
        password: String,
        is_admin: Boolean
    ): OperationResult<Token, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(
                    RegisterRequestDTO(
                        first_name, last_name, username, email, password, is_admin
                    )
                )
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toToken()
                    prefsStorage.saveToSharedPreferences(result.access_token, username, "USER", email)
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

    override suspend fun login(
        username: String,
        password: String
    ): OperationResult<User, String?> {
        return withContext(Dispatchers.IO) {
            try{
                val response = apiService.login(username, password)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toUser()
                    var isAdmin = "USER"
                    if(result.isAdmin){
                        isAdmin = "ADMIN"
                    }
                    prefsStorage.saveToSharedPreferences(result.token, result.username, isAdmin, result.email)
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

    override fun getUser(): List<String>? {
        return prefsStorage.getUser()
    }
}