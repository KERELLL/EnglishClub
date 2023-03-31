package ru.rychkovkirill.englishclub.domain.repository

import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.*

interface MainRepository {

    //news
    suspend fun getNews() : OperationResult<List<News>, String?>

    suspend fun addNews(title: String, content: String) : OperationResult<Unit, String?>

    suspend fun getNewsInfo(newsId: Int) : OperationResult<News, String?>

    //shifts

    suspend fun getUpcomingShifts() : OperationResult<List<Shift>, String?>

    suspend fun getShiftInfo(shift_id: Int) : OperationResult<Shift, String?>

    suspend fun addShift(name: String, start_date: String, end_date: String) : OperationResult<Unit, String?>

    suspend fun getReservations() : OperationResult<List<Reservation>, String?>

    suspend fun approveShiftReservation(shift_reservation_id: Int) : OperationResult<Unit, String?>

    suspend fun getUserShifts() : OperationResult<List<Shift>, String?>

    suspend fun reserveShift(shift_id: Int) : OperationResult<Unit, String?>

    //tasks

    suspend fun getAllTasks() : OperationResult<List<Task>, String?>

    suspend fun getActiveTasks() : OperationResult<List<Task>, String?>

    suspend fun getTaskById(task_id: Int) : OperationResult<Task, String?>

    suspend fun approveResponse(response_id: Int) : OperationResult<Unit, String?>

    suspend fun getNotApprovedResponses() : OperationResult<List<Response>, String?>

    suspend fun getNotCheckedResponses() : OperationResult<List<Response>, String?>

    suspend fun checkTask(task_id: Int) : OperationResult<Unit, String?>

    suspend fun getMyTasks() : OperationResult<List<Task>, String?>

    suspend fun responseTask(task_id: Int) : OperationResult<Unit, String?>

    suspend fun submitTask(task_id: Int): OperationResult<Unit, String?>

    suspend fun addTask(title: String,
                        description: String,
                        points: Int,
                        start_date: String,
                        end_date: String,
                        is_active: Boolean) : OperationResult<Unit, String?>
}