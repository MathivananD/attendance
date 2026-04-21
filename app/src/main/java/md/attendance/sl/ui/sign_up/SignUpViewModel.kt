package md.attendance.sl.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import md.attendance.sl.application.login.SignupUserCase
import md.attendance.sl.data.UserEntity
import md.attendance.sl.infrastructure.validators.ValidationResult
import md.attendance.sl.ui.login.view_model.LoginState
import javax.inject.Inject

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val message: String) : SignupState()
    data class FieldError(
        var userName: String?,
        var email: String?,
        var mobileNumber: String?,
        var password: String?
    ) : SignupState()

    data class Error(val error: String) : SignupState()
}

@HiltViewModel
class SignUpViewModel @Inject constructor(val signupUserCase: SignupUserCase) : ViewModel() {
    private val _state = MutableLiveData<SignupState>(SignupState.Idle)
    val states: LiveData<SignupState> = _state
    fun signUp(userEntity: UserEntity) {
        val error = signupUserCase.signUpValidate(userEntity);
        when (error) {
            is ValidationResult.Error -> {
                _state.value = SignupState.FieldError(
                    error.usernameError,
                    error.emailError,
                    error.mobileNumber,
                    error.passwordError
                )
            }

            is ValidationResult.Success -> {
                _state.value = SignupState.Idle
                proceedSignUp(userEntity)
            }
        }

    }

    fun proceedSignUp(userEntity: UserEntity) {
        _state.value = SignupState.Loading
        viewModelScope.launch {
            signupUserCase.signUp(userEntity).catch { error ->
                _state.value = SignupState.Error(error.message.toString())
            }.collect { result ->

                _state.value = SignupState.Success("Login success full")
            }
        }
    }

    fun reset() {
        _state.value = SignupState.Idle
    }

}