package ru.rychkovkirill.englishclub.domain.usecase

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class ReserveShiftUseCase  @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(shift_id: Int): OperationResult<Unit, String?> {
        return mainRepository.reserveShift(shift_id)
    }
}