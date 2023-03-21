package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token

interface AuthRepository {

    suspend fun register(
        first_name: String,
        last_name: String,
        username: String,
        birthday: String,
        phone_number: String,
        email: String,
        password: String,
        is_admin: Boolean
    ) : OperationResult<Token, String?>

    suspend fun login(username: String, password: String) : OperationResult<Token, String?>
}