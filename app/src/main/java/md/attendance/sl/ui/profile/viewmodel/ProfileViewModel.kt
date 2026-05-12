package md.attendance.sl.ui.profile.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import md.attendance.sl.data.ui_state.SignupState
import md.attendance.sl.data.ui_state.UiState
import md.attendance.sl.data.users.UserEntity
import md.attendance.sl.repository.validators.ValidationResult
import md.attendance.sl.use_case.ProfileUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileUseCase: ProfileUseCase
) : ViewModel() {

    private val _state: MutableLiveData<UiState<UserEntity>> =
        MutableLiveData(UiState.Idle)
    val states: LiveData<UiState<UserEntity>> = _state
    private val _updateState = MutableLiveData<SignupState>(SignupState.Idle)
    val updateState: MutableLiveData<SignupState> = _updateState
    fun load() {
        _state.value = UiState.Loading
        viewModelScope.launch {
            val user = profileUseCase.getUser()
            if (user != null) {
                _state.value = UiState.Success(user)
            } else {
                _state.value = UiState.Error("Something went wrong")
            }

        }
    }

   fun updateUser(user: UserEntity){
       _updateState.value = SignupState.Loading
       val error = profileUseCase.signUpValidate(user);
       when (error) {
           is ValidationResult.Error -> {
               _updateState.value = SignupState.FieldError(
                   error.usernameError,
                   error.emailError,
                   error.mobileNumber,
                   error.passwordError
               )
           }

           is ValidationResult.Success -> {

               viewModelScope.launch {
                   profileUseCase.updateUser(user)
                   _updateState.value = SignupState.Success("User updated successfully")
               }
           }
       }

   }
}