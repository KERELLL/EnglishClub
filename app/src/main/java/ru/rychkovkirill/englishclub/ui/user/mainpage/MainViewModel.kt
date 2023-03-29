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
import ru.rychkovkirill.englishclub.domain.usecase.GetAllNewsUseCase
import ru.rychkovkirill.englishclub.domain.usecase.GetAllTasksUseCase
import ru.rychkovkirill.englishclub.domain.usecase.GetNewsInfoUseCase
import ru.rychkovkirill.englishclub.domain.usecase.GetUpcomingShiftsUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.AddNewsUseCase
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUpcomingShiftsUseCase: GetUpcomingShiftsUseCase,
    private val addNewsUseCase: AddNewsUseCase,
    private val getAllNewsUseCase: GetAllNewsUseCase,
    private val getNewsInfoUseCase: GetNewsInfoUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase
) : ViewModel() {

    private val _addNewsResult = MutableLiveData<ViewState<Unit, String?>>()
    val addNewsResult: LiveData<ViewState<Unit, String?>>
        get() = _addNewsResult

    private val _newsListResult = MutableLiveData<ViewState<List<News>, String?>>()
    val newsListResult: LiveData<ViewState<List<News>, String?>>
        get() = _newsListResult

    private val _newsInfoResult = MutableLiveData<ViewState<News, String?>>()
    val newsInfoResult: LiveData<ViewState<News, String?>>
        get() = _newsInfoResult

    private val _upcomingShiftsListResult = MutableLiveData<ViewState<List<Shift>, String?>>()
    val upcomingShiftsListResult: LiveData<ViewState<List<Shift>, String?>>
        get() = _upcomingShiftsListResult

    private val _allTaskListResult = MutableLiveData<ViewState<List<Task>, String?>>()
    val allTaskListResult: LiveData<ViewState<List<Task>, String?>>
        get() = _allTaskListResult


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
            _allTaskListResult.value = ViewState.loading()
            val result = getAllTasksUseCase.invoke()
            _allTaskListResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}