package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.models.User

interface AuthRepository {

    suspend fun register(
        first_name: String,
        last_name: String,
        username: String,
        email: String,
        password: String,
        is_admin: Boolean
    ) : OperationResult<Token, String?>

    suspend fun login(username: String, password: String) : OperationResult<User, String?>

    fun getUser(): List<String>?

    fun logout()
}