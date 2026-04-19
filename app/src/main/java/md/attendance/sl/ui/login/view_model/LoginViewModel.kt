package md.attendance.sl.ui.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import md.attendance.sl.application.login.LoginUserCase


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val message: String) : LoginState()
    data class FieldError(var userName: String?,var password: String?) : LoginState()
    data class Error(val error: String) : LoginState()
}


class LoginViewModel(val loginUserCase: LoginUserCase) : ViewModel() {


    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _loginState.value = LoginState.FieldError(if(username.isEmpty())"Enter username" else null,if(password.isEmpty())"Enter password" else null)
            return
        }


        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {
                delay(1500) // simulate API

                if (username == "admin" && password == "1234") {
                    _loginState.value = LoginState.Success("Login successful")
                } else {
                    _loginState.value = LoginState.Error("Invalid credentials")
                }

            } catch (e: Exception) {
                _loginState.value = LoginState.Error("Something went wrong")
            }
        }
    }
}