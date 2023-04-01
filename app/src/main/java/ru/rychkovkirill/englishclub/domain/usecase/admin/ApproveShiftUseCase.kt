package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class ApproveShiftUseCase  @Inject constructor(
    private val mainRepository: MainRepository
) {
    suspend operator fun invoke(shift_reservation_id: Int): OperationResult<Unit, String?> {
        return mainRepository.approveShiftReservation(shift_reservation_id)
    }
}