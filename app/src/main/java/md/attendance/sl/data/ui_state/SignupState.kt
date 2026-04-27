package md.attendance.sl.data.ui_state

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