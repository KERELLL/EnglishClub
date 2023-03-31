package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Task
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class ResponseTaskUseCase  @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(task_id: Int): OperationResult<Unit, String?> {
        return mainRepository.responseTask(task_id)
    }
}