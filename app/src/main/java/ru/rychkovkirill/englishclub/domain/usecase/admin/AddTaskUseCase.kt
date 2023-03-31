package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(title: String,
                                description: String,
                                points: Int,
                                start_date: String,
                                end_date: String) : OperationResult<Unit, String?> {
        return mainRepository.addTask(title, description, points, start_date, end_date, true)
    }
}