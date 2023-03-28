package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.News
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class GetAllNewsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke() : OperationResult<List<News>, String?> {
        return mainRepository.getNews()
    }
}