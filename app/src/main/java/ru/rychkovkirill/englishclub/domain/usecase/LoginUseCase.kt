package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(username: String, password: String) : OperationResult<Token, String?> {
        return authRepository.login(username, password)
    }
}