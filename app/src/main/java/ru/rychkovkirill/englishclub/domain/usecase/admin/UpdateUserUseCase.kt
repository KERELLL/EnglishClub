package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserUseCase  @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(email: String, firstName: String, lastName: String, nickname: String, mediaLink: String): OperationResult<Unit, String?> {
        return userRepository.updateUser(email, firstName, lastName, nickname, mediaLink)
    }
}