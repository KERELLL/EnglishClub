package ru.rychkovkirill.englishclub.ui.user.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.*
import ru.rychkovkirill.englishclub.domain.usecase.*
import ru.rychkovkirill.englishclub.domain.usecase.admin.*
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUpcomingShiftsUseCase: GetUpcomingShiftsUseCase,
    private val addNewsUseCase: AddNewsUseCase,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getNewsInfoUseCase: GetNewsInfoUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    val getActiveTasksUseCase: GetActiveTasksUseCase,
    val addTaskUseCase: AddTaskUseCase,
    val getMyTasksUseCase: GetMyTasksUseCase,
    val responseTaskUseCase: ResponseTaskUseCase,
    val addShiftUseCase: AddShiftUseCase,
    val getMyShiftsUseCase: GetMyShiftsUseCase,
    val reserveShiftUseCase: ReserveShiftUseCase,
    val getReservationsUseCase: GetReservationsUseCase,
    val approveShiftUseCase: ApproveShiftUseCase,
    val submitTaskUseCase: SubmitTaskUseCase,
     val getCheckTasksUseCase: GetCheckTasksUseCase,
    val checkAnswerUseCase: CheckAnswerUseCase
) : ViewModel() {

    private val _addNewsResult = MutableLiveData<ViewState<Unit, String?>>()
    val addNewsResult: LiveData<ViewState<Unit, String?>>
        get() = _addNewsResult

    private val _addTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val addTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _addTaskResult

    private val _reserveShiftResult = MutableLiveData<ViewState<Unit, String?>>()
    val reserveShiftResult: LiveData<ViewState<Unit, String?>>
        get() = _reserveShiftResult

    private val _addShiftResult = MutableLiveData<ViewState<Unit, String?>>()
    val addShiftResult: LiveData<ViewState<Unit, String?>>
        get() = _addShiftResult

    private val _approveShiftResult = MutableLiveData<ViewState<Unit, String?>>()
    val approveShiftResult: LiveData<ViewState<Unit, String?>>
        get() = _approveShiftResult

    private val _newsListResult = MutableLiveData<ViewState<List<News>, String?>>()
    val newsListResult: LiveData<ViewState<List<News>, String?>>
        get() = _newsListResult

    private val _newsInfoResult = MutableLiveData<ViewState<News, String?>>()
    val newsInfoResult: LiveData<ViewState<News, String?>>
        get() = _newsInfoResult

    private val _upcomingShiftsListResult = MutableLiveData<ViewState<List<Shift>, String?>>()
    val upcomingShiftsListResult: LiveData<ViewState<List<Shift>, String?>>
        get() = _upcomingShiftsListResult

    private val _checkResponsesListResult = MutableLiveData<ViewState<List<Response>, String?>>()
    val checkResponsesListResult: LiveData<ViewState<List<Response>, String?>>
        get() = _checkResponsesListResult

    private val _myShiftsListResult = MutableLiveData<ViewState<List<Shift>, String?>>()
    val myShiftsListResult: LiveData<ViewState<List<Shift>, String?>>
        get() = _myShiftsListResult

    private val _allTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val allTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _allTasksListResult

    private val _reservationsListResult = MutableLiveData<ViewState<List<Reservation>, String?>>()
    val reservationsListResult: LiveData<ViewState<List<Reservation>, String?>>
        get() = _reservationsListResult

    private val _activeTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val activeTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _activeTasksListResult

    private val _myTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val myTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _myTasksListResult

    private val _responseTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val responseTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _responseTaskResult

    private val _checkTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val checkTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _checkTaskResult


    private val _submitTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val submitTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _submitTaskResult


    fun addNews(title: String, content: String){
        viewModelScope.launch {
            _addNewsResult.value = ViewState.loading()
            val result = addNewsUseCase.invoke(title, content)
            _addNewsResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun addShift(title: String, number: Int, description: String, start_date: String, end_date: String){
        viewModelScope.launch {
            _addShiftResult.value = ViewState.loading()
            val result = addShiftUseCase.invoke(title, number, description, start_date, end_date)
            _addShiftResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun reserveShift(shift_id: Int){
        viewModelScope.launch {
            _reserveShiftResult.value = ViewState.loading()
            val result = reserveShiftUseCase.invoke(shift_id)
            _reserveShiftResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun checkTask(id: Int){
        viewModelScope.launch {
            _checkTaskResult.value = ViewState.loading()
            val result = checkAnswerUseCase(id)
            _checkTaskResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getCheckResponses(){
        viewModelScope.launch {
            _checkResponsesListResult.value = ViewState.loading()
            val result = getCheckTasksUseCase.invoke()
            _checkResponsesListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun approveShift(shift_id: Int){
        viewModelScope.launch {
            _approveShiftResult.value = ViewState.loading()
            val result = approveShiftUseCase.invoke(shift_id)
            _approveShiftResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun addTask(title: String,
                description: String,
                points: Int,
                start_date: String,
                end_date: String){
        viewModelScope.launch {
            _addTaskResult.value = ViewState.loading()
            val result = addTaskUseCase.invoke(title, description, points, start_date, end_date)
            _addTaskResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
    fun submitTask(id: Int, answer: String){
        viewModelScope.launch {
            _submitTaskResult.value = ViewState.loading()
            val result = submitTaskUseCase.invoke(id, answer)
            _submitTaskResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun responseTask(id: Int){
        viewModelScope.launch {
            _addTaskResult.value = ViewState.loading()
            val result = responseTaskUseCase.invoke(id)
            _addTaskResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getNews(){
        viewModelScope.launch {
            _newsListResult.value = ViewState.loading()
            val result = getAllNewsUseCase.invoke()
            _newsListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getReservations(){
        viewModelScope.launch {
            _reservationsListResult.value = ViewState.loading()
            val result = getReservationsUseCase.invoke()
            _reservationsListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getNewsInfo(newsId: Int){
        viewModelScope.launch {
            _newsInfoResult.value = ViewState.loading()
            val result = getNewsInfoUseCase.invoke(newsId)
            _newsInfoResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getUpcomingShifts(){
        viewModelScope.launch {
            _upcomingShiftsListResult.value = ViewState.loading()
            val result = getUpcomingShiftsUseCase.invoke()
            _upcomingShiftsListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getMyShifts(){
        viewModelScope.launch {
            _myShiftsListResult.value = ViewState.loading()
            val result = getMyShiftsUseCase.invoke()
            _myShiftsListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getAllTasks(){
        viewModelScope.launch {
            _allTasksListResult.value = ViewState.loading()
            val result = getAllTasksUseCase.invoke()
            _allTasksListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getActiveTasks(){
        viewModelScope.launch {
            _activeTasksListResult.value = ViewState.loading()
            val result = getActiveTasksUseCase.invoke()
            _activeTasksListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun getMyTasks(){
        viewModelScope.launch {
            _myTasksListResult.value = ViewState.loading()
            val result = getMyTasksUseCase.invoke()
            _myTasksListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}