package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class AddShiftUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(
        title: String,
        number: Int,
        description: String,
        start_date: String,
        end_date: String
    ): OperationResult<Unit, String?> {
        return mainRepository.addShift(title, number, description, start_date, end_date)
    }
}