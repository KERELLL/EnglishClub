package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User

interface UserRepository {
    suspend fun getUserInfo(email: String) : OperationResult<User, String?>

    suspend fun getAllUsers() : OperationResult<List<User>, String?>

    suspend fun updateUser(email: String,
                           first_name: String,
                           last_name: String,
                           username: String,
                           experience: Int?,
                           rank: String?,
                           hobby: String?,
                           media_link: String?) : OperationResult<Unit, String?>

    suspend fun updateMe(first_name: String,
                         last_name: String,
                         username: String,
                         experience: Int?,
                         rank: String?,
                         hobby: String?,
                         media_link: String?) : OperationResult<Unit, String?>
}