package ru.rychkovkirill.englishclub.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import ru.rychkovkirill.englishclub.data.ApiService
import ru.rychkovkirill.englishclub.data.PrefsStorage
import ru.rychkovkirill.englishclub.data.models.LoginRequestDTO
import ru.rychkovkirill.englishclub.data.models.RegisterRequestDTO
import ru.rychkovkirill.englishclub.data.models.toToken
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import java.io.IOException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val prefsStorage: PrefsStorage
) : AuthRepository {

    override suspend fun register(
        first_name: String,
        last_name: String,
        username: String,
        birthday: String,
        phone_number: String,
        email: String,
        password: String,
        is_admin: Boolean
    ): OperationResult<Token, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.register(
                    RegisterRequestDTO(
                        first_name, last_name, username, birthday, phone_number, email, password, is_admin
                    )
                )
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toToken()
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("msg"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            }catch (e: HttpException){
                return@withContext OperationResult.Error("Ошибка подключения")
            } catch (e : IOException){
                return@withContext OperationResult.Error("Ошибка подключения")
            } catch (e: Exception){
                return@withContext OperationResult.Error("Неизвестная ошибка")
            }
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): OperationResult<Token, String?> {
//        return withContext(Dispatchers.IO) {
//            try{
//                val response = apiService.login(LoginRequestDTO(username, password))
//                if (response.isSuccessful && response.body() != null) {
//                    val result = response.body()!!.toToken()
//
//                    return@withContext OperationResult.Success(result)
//                } else if (response.errorBody() != null) {
//                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
//                    return@withContext OperationResult.Error(errorObj.getString("error"))
//                } else {
//                    return@withContext OperationResult.Error("Что-то пошло не так!")
//                }
//            }catch (e: HttpException){
//                return@withContext OperationResult.Error("Ошибка подключения")
//            } catch (e : IOException){
//                return@withContext OperationResult.Error("Ошибка подключения")
//            } catch (e: Exception){
//                return@withContext OperationResult.Error("Неизвестная ошибка")
//            }
//        }
        prefsStorage.saveToSharedPreferences(username, "u")
        return OperationResult.Success(Token("", "", ""))
    }

    override fun getUser(): List<String>? {
        return prefsStorage.getUser()
    }
}