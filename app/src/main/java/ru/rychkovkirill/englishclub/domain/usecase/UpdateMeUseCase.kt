package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.repository.UserRepository
import javax.inject.Inject

class UpdateMeUseCase  @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(firstName: String, lastName: String, nickname: String, mediaLink: String): OperationResult<Unit, String?> {
        return userRepository.updateMe(firstName, lastName, nickname, mediaLink)
    }
}