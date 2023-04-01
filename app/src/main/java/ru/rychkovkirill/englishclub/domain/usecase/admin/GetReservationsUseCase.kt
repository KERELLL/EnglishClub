package ru.rychkovkirill.englishclub.domain.usecase.admin

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Reservation
import ru.rychkovkirill.englishclub.domain.models.Shift
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class GetReservationsUseCase  @Inject constructor(
    val mainRepository: MainRepository
) {
    suspend operator fun invoke(): OperationResult<List<Reservation>, String?> {
        return mainRepository.getReservations()
    }
}