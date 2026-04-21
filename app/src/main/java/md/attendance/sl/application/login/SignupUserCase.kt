package md.attendance.sl.application.login

import kotlinx.coroutines.flow.Flow
import md.attendance.sl.application.login.interfaces.SignUpInterface
import md.attendance.sl.data.UserEntity
import md.attendance.sl.infrastructure.SignUpRepository
import md.attendance.sl.infrastructure.validators.UserValidator
import md.attendance.sl.infrastructure.validators.ValidationResult
import javax.inject.Inject

class SignupUserCase @Inject constructor(
    val signUpRepository: SignUpRepository,
     val validator: UserValidator
) {
    fun signUp(userEntity: UserEntity): Flow<Boolean> {
        return signUpRepository.signUp(userEntity)

    }

    fun signUpValidate(userEntity: UserEntity): ValidationResult {
        return validator.validate(userEntity);
    }
}