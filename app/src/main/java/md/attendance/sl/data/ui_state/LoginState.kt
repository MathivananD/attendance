package md.attendance.sl.data.ui_state


sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val message: String) : LoginState()
    data class FieldError(var userName: String?, var password: String?) : LoginState()
    data class Error(val error: String) : LoginState()
}
