package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        first_name: String,
        last_name: String,
        username: String,
        email: String,
        password: String,
        is_admin: Boolean
    ): OperationResult<Token, String?> {
        return authRepository.register(first_name, last_name, username, email, password, is_admin)
    }
}