package ru.rychkovkirill.englishclub.ui.user.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.rychkovkirill.englishclub.domain.OperationResult
import ru.rychkovkirill.englishclub.domain.models.Token
import ru.rychkovkirill.englishclub.domain.usecase.LoginUseCase
import ru.rychkovkirill.englishclub.domain.usecase.RegisterUseCase
import ru.rychkovkirill.englishclub.ui.ViewState
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUserCase: RegisterUseCase
): ViewModel() {
    private val _loginResult = MutableLiveData<ViewState<Token, String?>>()
    val loginResult: LiveData<ViewState<Token, String?>>
        get() = _loginResult

    private val _registerResult = MutableLiveData<ViewState<Token, String?>>()
    val registerResult : LiveData<ViewState<Token, String?>>
        get() = _registerResult

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = ViewState.loading()
            val result = loginUseCase.invoke(email, password)
            _loginResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }

    fun register(
        first_name: String,
        last_name: String,
        username: String,
        birthday: String,
        phone_number: String,
        email: String,
        password: String,
        is_admin: Boolean
    ) {
        viewModelScope.launch {
            _registerResult.value = ViewState.loading()
            val result = registerUserCase.invoke(first_name, last_name, username, birthday, phone_number, email, password, is_admin)
            _registerResult.value = when (result) {
                is OperationResult.Error -> ViewState.error(result.data)
                is OperationResult.Success -> ViewState.success(result.data)
            }
        }
    }
}