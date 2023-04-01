package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Reservation
import ru.rychkovkirill.englishclub.domain.models.Response
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class GetCheckTasksUseCase   @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(): OperationResult<List<Response>, String?> {
        return mainRepository.getNotCheckedResponses()
    }
}