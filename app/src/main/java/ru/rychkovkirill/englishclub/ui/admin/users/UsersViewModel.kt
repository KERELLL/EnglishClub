package ru.rychkovkirill.englishclub.ui.admin.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.User
import ru.rychkovkirill.englishclub.domain.usecase.UpdateMeUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.GetAllUsersUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.GetUserInfoUseCase
import ru.rychkovkirill.englishclub.domain.usecase.admin.UpdateUserUseCase
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    val getAllUsersUseCase: GetAllUsersUseCase,
    val getUserInfoUseCase: GetUserInfoUseCase,
    val updateUserUseCase: UpdateUserUseCase,
    val updateMeUseCase: UpdateMeUseCase
) : ViewModel() {

    private val _usersList = MutableLiveData<ViewState<List<User>, String?>>()
    val usersList: LiveData<ViewState<List<User>, String?>>
        get() = _usersList

    private val _updateMe = MutableLiveData<ViewState<Unit, String?>>()
    val updateMe: LiveData<ViewState<Unit, String?>>
        get() = _updateMe

    private val _updateUser = MutableLiveData<ViewState<Unit, String?>>()
    val updateUser: LiveData<ViewState<Unit, String?>>
        get() = _updateUser

    private val _user = MutableLiveData<ViewState<User, String?>>()
    val user: LiveData<ViewState<User, String?>>
        get() = _user


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

    fun getUserInfo(email: String){
        viewModelScope.launch {
            _user.value = ViewState.loading()
            val result = getUserInfoUseCase.invoke(email)
            _user.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun updateUser(email: String, firstName: String, lastName: String, nickname: String, mediaLink: String){
        viewModelScope.launch {
            _updateUser.value = ViewState.loading()
            val result = updateUserUseCase.invoke(email, firstName, lastName, nickname, mediaLink)
            _updateUser.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun updateMe(firstName: String, lastName: String, nickname: String, mediaLink: String){
        viewModelScope.launch {
            _updateMe.value = ViewState.loading()
            val result = updateMeUseCase.invoke(firstName, lastName, nickname, mediaLink)
            _updateMe.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}