package ru.rychkovkirill.englishclub.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import ru.rychkovkirill.englishclub.data.ApiService
import ru.rychkovkirill.englishclub.data.PrefsStorage
import ru.rychkovkirill.englishclub.data.models.*
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.*
import ru.rychkovkirill.englishclub.domain.repository.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    val apiService: ApiService,
    val prefsStorage: PrefsStorage
) : MainRepository {
    override suspend fun getNews(): OperationResult<List<News>, String?> {

        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNews(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map{it.toNews()}
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun addNews(title: String, content: String): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.addNews(tokenHeader, NewsAddRequest(title, content))
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getNewsInfo(newsId: Int): OperationResult<News, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNewsInfo(newsId, tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toNews()
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getUpcomingShifts(): OperationResult<List<Shift>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getUpcomingShifts(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toShift()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getShiftInfo(shift_id: Int): OperationResult<Shift, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getShiftInfo(tokenHeader, shift_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toShift()
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun addShift(
        name: String,
        start_date: String,
        end_date: String
    ): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.addShift(tokenHeader, ShiftAddRequest(name, start_date, end_date))
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getReservations(): OperationResult<List<Reservation>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getReservations(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toReservation()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun approveShiftReservation(shift_reservation_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.approveResponse(tokenHeader, shift_reservation_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getUserShifts(): OperationResult<List<Shift>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getUserShifts(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toShift()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun reserveShift(shift_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.reserveShift(tokenHeader, shift_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getAllTasks(): OperationResult<List<Task>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getAllTasks(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toTask()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getActiveTasks(): OperationResult<List<Task>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getActiveTask(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toTask()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getTaskById(task_id: Int): OperationResult<Task, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getTaskById(tokenHeader, task_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.toTask()
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun approveResponse(response_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.approveResponse(tokenHeader, response_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getNotApprovedResponses(): OperationResult<List<Response>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNotApprovedResponses(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toResponse()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getNotCheckedResponses(): OperationResult<List<Response>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getNotCheckedResponses(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toResponse()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun checkTask(task_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.checkTask(tokenHeader, task_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun getMyTasks(): OperationResult<List<Task>, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.getMyTasks(tokenHeader)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!.map {
                        it.toTask()
                    }
                    return@withContext OperationResult.Success(result)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun responseTask(task_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.responseTask(tokenHeader, task_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun submitTask(task_id: Int): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.submitTask(tokenHeader, task_id)
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }

    override suspend fun addTask(
        title: String,
        description: String,
        points: Int,
        start_date: String,
        end_date: String,
        is_active: Boolean
    ): OperationResult<Unit, String?> {
        return withContext(Dispatchers.IO) {
            try {
                val tokenHeader = "Bearer ${prefsStorage.getUser()?.get(0).orEmpty()}"
                val response = apiService.addTask(tokenHeader, TaskRequestDTO(
                    title, description, points, start_date, end_date, is_active)
                )
                if (response.isSuccessful && response.body() != null) {
                    val result = response.body()!!
                    return@withContext OperationResult.Success(Unit)
                } else if (response.errorBody() != null) {
                    val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
                    return@withContext OperationResult.Error(errorObj.getString("detail"))
                } else {
                    return@withContext OperationResult.Error("Что-то пошло не так!")
                }
            } catch (e: Exception){
                return@withContext OperationResult.Error("Ошибка подключения")
            }
        }
    }
}