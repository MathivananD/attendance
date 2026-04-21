package md.attendance.sl.infrastructure.validators

import android.util.Log
import md.attendance.sl.data.UserEntity

sealed class ValidationResult {
    object Success : ValidationResult()

    data class Error(
        val usernameError: String? = null,
        val passwordError: String? = null,
        val emailError: String? = null,
        val mobileNumber: String? = null
    ) : ValidationResult()
}

class UserValidator {

    fun validate(user: UserEntity): ValidationResult {

        var usernameError: String? = null
        var passwordError: String? = null
        var emailError: String? = null
        var mobileNumber: String? = null
        Log.d("ValidatorState",user.toString())
        if (user.name.isBlank()) {
            usernameError = "Username cannot be empty"
        }

        if (user.password.length < 4) {
            passwordError = "Password must be at least 4 characters"
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(user.email).matches()) {
            emailError = "Invalid email"
        }
        if (user.mobileNumber.isBlank()) {
            mobileNumber = "Mobile number should not be empty"
        }else if(user.mobileNumber.length<10){
            mobileNumber = "Enter valid mobile number"
        }

        return if (
            usernameError == null &&
            passwordError == null &&
            emailError == null && mobileNumber==null
        ) {
            ValidationResult.Success
        } else {
            ValidationResult.Error(usernameError, passwordError, emailError,mobileNumber)
        }
    }
}