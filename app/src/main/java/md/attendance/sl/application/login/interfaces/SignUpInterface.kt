package md.attendance.sl.application.login.interfaces

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.data.UserEntity
import md.attendance.sl.infrastructure.validators.ValidationResult

interface SignUpInterface {
    sealed class Exceptions(msg: String) : Exception(msg) {

        class AlreadyExist : Exceptions("Email is already exist")

    }

    fun signUp(userEntity: UserEntity): Flow<Boolean>
    fun signUpValidate(userEntity: UserEntity): ValidationResult
}