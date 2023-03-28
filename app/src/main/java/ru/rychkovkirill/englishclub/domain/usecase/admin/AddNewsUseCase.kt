package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class AddNewsUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(title: String, content: String) : OperationResult<Unit, String?>{
        return mainRepository.addNews(title, content)
    }
}