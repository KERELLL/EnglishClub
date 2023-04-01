package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class SubmitTaskUseCase  @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(task_id: Int, answer: String): OperationResult<Unit, String?> {
        return mainRepository.submitTask(task_id, answer)
    }
}