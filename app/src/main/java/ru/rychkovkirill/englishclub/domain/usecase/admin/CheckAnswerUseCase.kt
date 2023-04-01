package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class CheckAnswerUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(task_id: Int): OperationResult<Unit, String?> {
        return mainRepository.checkTask(task_id)
    }
}