package ru.rychkovkirill.englishclub.ui.user.mainpage.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.News
import ru.rychkovkirill.englishclub.domain.usecase.GetAllNewsUseCase
import ru.rychkovkirill.englishclub.domain.usecase.GetNewsInfoUseCase
import ru.rychkovkirill.englishclub.ui.ViewState
import ru.rychkovkirill.englishclub.domain.usecase.admin.AddNewsUseCase
import javax.inject.Inject

class NewsViewModel @Inject constructor(
    val addNewsUseCase: AddNewsUseCase,
    val getAllNewsUseCase: GetAllNewsUseCase,
    val getNewsInfoUseCase: GetNewsInfoUseCase
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
}