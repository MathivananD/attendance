package md.attendance.sl.ui.login.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import md.attendance.sl.use_case.LoginUserCase
import md.attendance.sl.application.login.interfaces.LoginInterfaces
import md.attendance.sl.data.ui_state.LoginState
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userCase: LoginUserCase,
) : ViewModel() {


    private val _loginState = MutableLiveData<LoginState>(LoginState.Idle)
    val loginState: LiveData<LoginState> = _loginState

    fun login(username: String, password: String) {

        if (username.isEmpty() || password.isEmpty()) {
            _loginState.value = LoginState.FieldError(
                if (username.isEmpty()) "Enter username" else null,
                if (password.isEmpty()) "Enter password" else null
            )
            return
        }
        proceedLogin(username, password);

    }

    fun proceedLogin(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            try {

                userCase(username, password).catch { e ->
                    when (e) {
                        is LoginInterfaces.Exceptions.UserNameNotFound -> {
                            _loginState.value = LoginState.FieldError(
                                userName = e.message,
                                password = null
                            )
                        }

                        is LoginInterfaces.Exceptions.PasswordIncorrect -> {
                            _loginState.value = LoginState.FieldError(
                                userName = null,
                                password = e.message
                            )
                        }

                        else -> {
                            _loginState.value =
                                LoginState.Error(e.message ?: "Something went wrong")
                        }
                    }
                }.collect {
                    _loginState.value = LoginState.Success("Login successful")
                }


            } catch (e: Exception) {
                _loginState.value = LoginState.Error(e.message ?: "Something went wrong")
            }
        }
    }

    fun reset() {
        _loginState.value = LoginState.Idle
    }

    fun logOut(): Flow<Boolean> = userCase.logOut().map { true }
}
