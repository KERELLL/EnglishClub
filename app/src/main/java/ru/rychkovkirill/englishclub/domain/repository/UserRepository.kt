package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User

interface UserRepository {
    suspend fun getUserInfo(email: String) : OperationResult<User, String?>

    suspend fun getAllUsers() : OperationResult<List<User>, String?>
}