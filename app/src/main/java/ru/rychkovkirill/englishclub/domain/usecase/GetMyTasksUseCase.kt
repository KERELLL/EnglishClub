package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Task
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class GetMyTasksUseCase @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(): OperationResult<List<Task>, String?> {
        return mainRepository.getMyTasks()
    }
}