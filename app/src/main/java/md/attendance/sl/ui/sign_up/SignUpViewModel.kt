package md.attendance.sl.ui.sign_up

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import md.attendance.sl.data.ui_state.SignupState
import md.attendance.sl.use_case.SignupUserCase
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.repository.validators.ValidationResult
import javax.inject.Inject


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