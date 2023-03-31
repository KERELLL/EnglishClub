package ru.rychkovkirill.englishclub.ui.user.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.News
import ru.rychkovkirill.englishclub.domain.models.Shift
import ru.rychkovkirill.englishclub.domain.models.Task
import ru.rychkovkirill.englishclub.domain.usecase.*
import ru.rychkovkirill.englishclub.domain.usecase.admin.AddNewsUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.AddTaskUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.GetAllTasksUseCase
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
    val responseTaskUseCase: ResponseTaskUseCase
) : ViewModel() {

    private val _addNewsResult = MutableLiveData<ViewState<Unit, String?>>()
    val addNewsResult: LiveData<ViewState<Unit, String?>>
        get() = _addNewsResult

    private val _addTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val addTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _addTaskResult

    private val _newsListResult = MutableLiveData<ViewState<List<News>, String?>>()
    val newsListResult: LiveData<ViewState<List<News>, String?>>
        get() = _newsListResult

    private val _newsInfoResult = MutableLiveData<ViewState<News, String?>>()
    val newsInfoResult: LiveData<ViewState<News, String?>>
        get() = _newsInfoResult

    private val _upcomingShiftsListResult = MutableLiveData<ViewState<List<Shift>, String?>>()
    val upcomingShiftsListResult: LiveData<ViewState<List<Shift>, String?>>
        get() = _upcomingShiftsListResult

    private val _allTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val allTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _allTasksListResult

    private val _activeTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val activeTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _activeTasksListResult

    private val _myTasksListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val myTasksListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _myTasksListResult

    private val _responseTaskResult = MutableLiveData<ViewState<Unit, String?>>()
    val responseTaskResult: LiveData<ViewState<Unit, String?>>
        get() = _responseTaskResult


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