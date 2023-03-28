package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.UserRepository
import javax.inject.Inject

class GetAllUsersUseCase  @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): OperationResult<List<User>, String?> {
        return userRepository.getAllUsers()
    }
}