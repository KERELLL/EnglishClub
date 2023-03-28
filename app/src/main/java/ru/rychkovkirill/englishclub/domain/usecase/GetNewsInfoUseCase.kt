package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.News
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class GetNewsInfoUseCase  @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(newsId: Int) : OperationResult<News, String?> {
        return mainRepository.getNewsInfo(newsId)
    }
}