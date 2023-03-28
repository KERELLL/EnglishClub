package ru.rychkovkirill.englishclub.ui.admin.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.usecase.admin.GetAllUsersUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.GetUserInfoUseCase
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _usersList = MutableLiveData<ViewState<List<User>, String?>>()
    val usersList: LiveData<ViewState<List<User>, String?>>
        get() = _usersList

    fun getUsers(){
        viewModelScope.launch {
            _usersList.value = ViewState.loading()
            val result = getAllUsersUseCase.invoke()
            _usersList.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}